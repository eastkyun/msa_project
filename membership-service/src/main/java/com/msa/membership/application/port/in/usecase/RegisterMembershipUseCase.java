package com.msa.membership.application.port.in.usecase;

import com.msa.common.UseCase;
import com.msa.membership.application.port.in.command.RegisterMembershipCommand;
import com.msa.membership.domain.Membership;

@UseCase
public interface RegisterMembershipUseCase {
    Membership registerMembership(RegisterMembershipCommand command);
}
