package com.msa.membership.application.port.in.usecase;

import com.msa.common.UseCase;
import com.msa.membership.application.port.in.command.ModifyMembershipCommand;
import com.msa.membership.domain.Membership;

@UseCase
public interface ModifyMembershipUseCase {
    Membership modifyMembership(ModifyMembershipCommand command);
}
