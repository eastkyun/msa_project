package com.msa.membership.adapter.in.web;

import com.msa.common.WebAdapter;
import com.msa.membership.adapter.in.web.request.ModifyMembershipRequest;
import com.msa.membership.application.port.in.command.ModifyMembershipCommand;
import com.msa.membership.application.port.in.usecase.ModifyMembershipUseCase;
import com.msa.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class ModifyMembershipController {
    private final ModifyMembershipUseCase modifyMembershipUseCase;

    @PostMapping("/membership/modify")
    ResponseEntity<Membership> modifyMembershipByName(@RequestBody ModifyMembershipRequest request) {

        ModifyMembershipCommand command = ModifyMembershipCommand.builder()
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .isValid(request.isValid())
                .isCorp(request.isCorp())
                .build();

        return ResponseEntity.ok(modifyMembershipUseCase.modifyMembership(command));
    }
}
