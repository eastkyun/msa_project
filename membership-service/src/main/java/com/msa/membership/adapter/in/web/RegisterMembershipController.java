package com.msa.membership.adapter.in.web;

import com.msa.common.WebAdapter;
import com.msa.membership.adapter.in.web.request.RegisterMembershipRequest;
import com.msa.membership.application.port.in.command.RegisterMembershipCommand;
import com.msa.membership.application.port.in.usecase.RegisterMembershipUseCase;
import com.msa.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterMembershipController {
    private final RegisterMembershipUseCase registerMembershipUseCase;

    @PostMapping("/membership/register")
    Membership registerMembership(@RequestBody RegisterMembershipRequest request){
        RegisterMembershipCommand command = RegisterMembershipCommand.builder()
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .isCorp(request.isCorp()).build();
        System.out.println(command.toString());
        return registerMembershipUseCase.registerMembership(command);
    }
}
