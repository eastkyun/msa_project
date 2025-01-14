package com.msa.membership.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.common.UseCase;
import com.msa.membership.adapter.out.persistence.MembershipJpaEntity;
import com.msa.membership.application.port.in.command.FindMembershipCommand;
import com.msa.membership.application.port.in.usecase.FindMembershipUseCase;
import com.msa.membership.application.port.out.FindMembershipPort;
import com.msa.membership.domain.Membership;
import lombok.AllArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@AllArgsConstructor
@Transactional
public class FindMembershipService implements FindMembershipUseCase {

    private final ObjectMapper objectMapper;
    private final FindMembershipPort findMembershipPort;

    @Override
    public Membership findMembership(FindMembershipCommand command) {
        MembershipJpaEntity entity = findMembershipPort.findMembershipByName(new Membership.MembershipName(command.getMembershipName()));
        Membership membership = objectMapper.convertValue(entity, Membership.class);

        System.out.printf("membership: %s\n", membership.toString());

        return membership;
    }
}
