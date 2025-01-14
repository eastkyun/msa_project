package com.msa.membership.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.common.UseCase;
import com.msa.membership.adapter.out.jwt.JwtTokenProvider;
import com.msa.membership.adapter.out.persistence.MembershipJpaEntity;
import com.msa.membership.application.port.in.command.LoginMembershipCommand;
import com.msa.membership.application.port.in.command.RefreshTokenCommand;
import com.msa.membership.application.port.in.command.ValidateTokenCommand;
import com.msa.membership.application.port.in.usecase.AuthMembershipUseCase;
import com.msa.membership.application.port.out.AuthMembershipPort;
import com.msa.membership.application.port.out.FindMembershipPort;
import com.msa.membership.application.port.out.ModifyMembershipPort;
import com.msa.membership.domain.JwtToken;
import com.msa.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class AuthMemberService implements AuthMembershipUseCase {

    private final ObjectMapper objectMapper;
    private final FindMembershipPort findMembershipPort;
    private final AuthMembershipPort authMembershipPort;
    private final ModifyMembershipPort modifyMembershipPort;
    @Override
    public JwtToken loginMembership(LoginMembershipCommand command) {
        String membershipName = command.getName();
        String membershipPassword = command.getPassword();

        MembershipJpaEntity entity = findMembershipPort.findMembershipByNameAndPassword(
                new Membership.MembershipName(membershipName),
                new Membership.MembershipPassword(membershipPassword)
        );

        // 유효한 계정이라면,,,
        if(entity.isValid()){
            // jwtToken 발급
            String jwtToken = authMembershipPort.generateJwtToken(
              new Membership.MembershipName(membershipName)
            );
            String refreshToken = authMembershipPort.generateRefreshToken(
                    new Membership.MembershipName(membershipName)
            );

            modifyMembershipPort.modifyMembership(
                    new Membership.MembershipName(entity.getName()),
                    new Membership.MembershipEmail(entity.getEmail()),
                    new Membership.MembershipAddress(entity.getAddress()),
                    new Membership.MembershipIsValid(entity.isValid()),
                    new Membership.MembershipIsCorp(entity.isCorp()),
                    new Membership.MembershipRefreshToken(refreshToken)

            );
            return JwtToken.generateJwtToken(
                    new JwtToken.MembershipName(membershipName),
                    new JwtToken.MembershipJwtToken(jwtToken),
                    new JwtToken.MembershipRefreshToken(refreshToken)
            );
        }
        return null;
    }

    @Override
    public JwtToken refreshJwtTokenByRefreshToken(RefreshTokenCommand command) {
        String requestedRefreshToken = command.getRefreshToken();
        // 요청받은 token 이 유효한 token 인가?
        boolean isValid = authMembershipPort.validateJwtToken(requestedRefreshToken);

        if(isValid){
            Membership.MembershipName membershipName = authMembershipPort.parseMembershipNameFromToken(requestedRefreshToken);
            String membershipNameValue = membershipName.getName();

            MembershipJpaEntity entity = findMembershipPort.findMembershipByName(membershipName);

            if(!entity.getRefreshToken().equals(command.getRefreshToken())){
                return null;
            }
            
            // 계정의 refresh token 과  요청받은 refresh token  정보가 일치하는지 보장됨
            if(entity.isValid()){
                String newJwtToken = authMembershipPort.generateJwtToken(new Membership.MembershipName(membershipNameValue));
                return JwtToken.generateJwtToken(
                        new JwtToken.MembershipName(membershipNameValue),
                        new JwtToken.MembershipJwtToken(newJwtToken),
                        new JwtToken.MembershipRefreshToken(requestedRefreshToken)
                );
            }
        }
        return null;
    }

    @Override
    public boolean validateJwtToken(ValidateTokenCommand command) {
        String jwtToken = command.getJwtToken();
        return authMembershipPort.validateJwtToken(jwtToken);
    }

    @Override
    public Membership getMembershipByJwtToken(ValidateTokenCommand command) {
        String jwtToken = command.getJwtToken();
        boolean isValid = authMembershipPort.validateJwtToken(jwtToken);

        if(isValid){
            Membership.MembershipName membershipName = authMembershipPort.parseMembershipNameFromToken(jwtToken);
            String membershipNameValue = membershipName.getName();

            MembershipJpaEntity entity = findMembershipPort.findMembershipByName(membershipName);
            if(!entity.getRefreshToken().equals(jwtToken)){
                return null;
            }

            Membership membership = objectMapper.convertValue(entity, Membership.class);
            System.out.println(membership);


            return membership;
        }

        return null;
    }
}
