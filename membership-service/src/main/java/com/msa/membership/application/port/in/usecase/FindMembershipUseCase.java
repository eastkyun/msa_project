package com.msa.membership.application.port.in.usecase;

import com.msa.common.UseCase;
import com.msa.membership.application.port.in.command.FindMembershipCommand;
import com.msa.membership.domain.Membership;

@UseCase
public interface FindMembershipUseCase{
    Membership findMembership(FindMembershipCommand command);
}
