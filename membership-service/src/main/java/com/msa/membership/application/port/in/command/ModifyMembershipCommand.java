package com.msa.membership.application.port.in.command;

import com.msa.common.SelfValidating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
@Data
@EqualsAndHashCode(callSuper=false)
public class ModifyMembershipCommand extends SelfValidating<ModifyMembershipCommand> {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String address;

    @AssertTrue
    private boolean isValid;

    private boolean isCorp;

    private final String refreshToken;

    public ModifyMembershipCommand(String name, String email, String address, boolean isValid, boolean isCorp, String refreshToken) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.isValid = isValid;
        this.isCorp = isCorp;
        this.refreshToken = refreshToken;

        this.validateSelf();
    }
}
