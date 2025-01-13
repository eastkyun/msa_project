package com.msa.membership.adapter.in.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class RegisterMembershipRequest {
    private String name;
    private String password;
    private String email;
    private String address;
    private boolean isCorp;

}
