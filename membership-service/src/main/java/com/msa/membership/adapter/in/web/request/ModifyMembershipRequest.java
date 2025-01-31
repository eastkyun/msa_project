package com.msa.membership.adapter.in.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyMembershipRequest {
    private String name;
    private String email;
    private String address;
    private boolean isCorp;
    private boolean isValid;

}
