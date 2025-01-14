package com.msa.membership.application.port.in.command;

import com.msa.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper=false)
public class LoginMembershipCommand extends SelfValidating<LoginMembershipCommand> {
    private final String name;
    private final String password;
}
