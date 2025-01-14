package com.msa.membership.application.port.in.command;

import com.msa.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper=false)
public class RefreshTokenCommand extends SelfValidating<RefreshTokenCommand> {
    private final String refreshToken;
}
