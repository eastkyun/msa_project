package com.msa.membership.application.service;

import com.msa.common.UseCase;
import com.msa.membership.adapter.out.persistence.MembershipJpaEntity;
import com.msa.membership.application.port.in.command.RegisterMembershipCommand;
import com.msa.membership.application.port.in.usecase.RegisterMembershipUseCase;
import com.msa.membership.application.port.out.RegisterMembershipPort;
import com.msa.membership.domain.Membership;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final RegisterMembershipPort registerMembershipPort;
    private final ObjectMapper objectMapper;

    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {
        MembershipJpaEntity entity = registerMembershipPort.createMembership(new Membership.MembershipName(command.getName()),
                new Membership.MembershipPassword(command.getPassword()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp())
                );

        Membership membership = objectMapper.convertValue(entity, Membership.class);

        System.out.println(membership);

        return membership;
    }
}