package com.msa.membership.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.common.UseCase;
import com.msa.membership.adapter.out.persistence.MembershipJpaEntity;
import com.msa.membership.application.port.in.command.ModifyMembershipCommand;
import com.msa.membership.application.port.in.usecase.ModifyMembershipUseCase;
import com.msa.membership.application.port.out.ModifyMembershipPort;
import com.msa.membership.domain.Membership;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@UseCase
@AllArgsConstructor
@Transactional
public class ModifyMembershipService implements ModifyMembershipUseCase {

    private final ObjectMapper objectMapper;
    private final ModifyMembershipPort modifyMembershipPort;

    @Override
    public Membership modifyMembership(ModifyMembershipCommand command) {
        MembershipJpaEntity entity = modifyMembershipPort.modifyMembership(
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp()),
                new Membership.MembershipRefreshToken(command.getRefreshToken())
        );
        Membership membership = objectMapper.convertValue(entity, Membership.class);
        System.out.printf("ModifyMembership: %s\n", membership.toString());
        return membership;
    }
}
