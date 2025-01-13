package com.msa.membership.application.port.out;

import com.msa.membership.adapter.out.persistence.MembershipJpaEntity;
import com.msa.membership.domain.Membership;

public interface RegisterMembershipPort {

    MembershipJpaEntity createMembership(
            Membership.MembershipName membershipName
            , Membership.MembershipPassword membershipPassword
            , Membership.MembershipEmail membershipEmail
            , Membership.MembershipAddress membershipAddress
            , Membership.MembershipIsValid membershipIsValid
            , Membership.MembershipIsCorp membershipIsCorp
    );
}
