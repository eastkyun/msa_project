package com.msa.membership.application.port.in.command;

import com.msa.common.SelfValidating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FindMembershipCommand extends SelfValidating<FindMembershipCommand> {
    private final String membershipName;
}
