package com.msa.membership.application.port.in.usecase;

import com.msa.common.UseCase;
import com.msa.membership.application.port.in.command.LoginMembershipCommand;
import com.msa.membership.application.port.in.command.RefreshTokenCommand;
import com.msa.membership.application.port.in.command.ValidateTokenCommand;
import com.msa.membership.domain.JwtToken;
import com.msa.membership.domain.Membership;

@UseCase
public interface AuthMembershipUseCase {
    JwtToken loginMembership(LoginMembershipCommand command);

    JwtToken refreshJwtTokenByRefreshToken(RefreshTokenCommand command);

    boolean validateJwtToken(ValidateTokenCommand command);

    Membership getMembershipByJwtToken(ValidateTokenCommand command);
}
