package com.msa.membership.application.port.in.command;

import com.msa.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper=false)
public class RegisterMembershipCommand extends SelfValidating<RegisterMembershipCommand> {
    private String membershipId;
    private String name;
    private String password;
    private String email;
    private String address;
    private boolean isValid;
    private boolean isCorp;

    public RegisterMembershipCommand(String membershipId, String name, String password, String email, String address, boolean isValid, boolean isCorp) {
        this.membershipId = membershipId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.isValid = isValid;
        this.isCorp = isCorp;

        this.validateSelf();
    }
}
