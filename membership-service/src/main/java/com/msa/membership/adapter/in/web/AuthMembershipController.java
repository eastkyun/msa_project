package com.msa.membership.adapter.in.web;

import com.msa.common.WebAdapter;
import com.msa.membership.adapter.in.web.request.LoginMembershipRequest;
import com.msa.membership.adapter.in.web.request.RefreshTokenRequest;
import com.msa.membership.adapter.in.web.request.ValidateTokenRequest;
import com.msa.membership.application.port.in.command.LoginMembershipCommand;
import com.msa.membership.application.port.in.command.RefreshTokenCommand;
import com.msa.membership.application.port.in.command.ValidateTokenCommand;
import com.msa.membership.application.port.in.usecase.AuthMembershipUseCase;
import com.msa.membership.domain.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class AuthMembershipController {

    private final AuthMembershipUseCase authMembershipUseCase;
    @PostMapping("/membership/login")
    JwtToken loginMembership(@RequestBody LoginMembershipRequest request){
        // password 암호화 로직 필요
        LoginMembershipCommand command = LoginMembershipCommand.builder()
                .name(request.getName())
                .password(request.getPassword())
                .build();
        return authMembershipUseCase.loginMembership(command);
    }

    @PostMapping(path = "/membership/refresh-token")
    JwtToken refreshToken(@RequestBody RefreshTokenRequest request) {
        System.out.println("Received refresh token: " + request.getRefreshToken()); // 디버깅용 출력

        RefreshTokenCommand command = RefreshTokenCommand.builder()
                .refreshToken(request.getRefreshToken())
                .build();

        return authMembershipUseCase.refreshJwtTokenByRefreshToken(command);
    }

    @PostMapping(path = "/membership/token-validate")
    boolean validateToken(@RequestBody ValidateTokenRequest request) {
        ValidateTokenCommand command = ValidateTokenCommand.builder()
                .jwtToken(request.getJwtToken())
                .build();

        return authMembershipUseCase.validateJwtToken(command);
    }
}
