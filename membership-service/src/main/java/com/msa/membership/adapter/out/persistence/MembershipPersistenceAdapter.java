package com.msa.membership.adapter.out.persistence;

import com.msa.common.PersistenceAdapter;
import com.msa.membership.application.port.out.RegisterMembershipPort;
import com.msa.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;

    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipPassword  membershipPassword,
                                                Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress,
                                                Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {

        MembershipJpaEntity entity = new MembershipJpaEntity(
                membershipName.getName(),
                membershipPassword.getPassword(),
                membershipAddress.getAddress(),
                membershipEmail.getEmail(),
                membershipIsValid.isValid(),
                membershipIsCorp.isCorp());

        // entity db save
        membershipRepository.save(entity);

        return entity;
    }

}
