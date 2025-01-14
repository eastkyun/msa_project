package com.msa.membership.application.port.out;

import com.msa.membership.adapter.out.persistence.MembershipJpaEntity;
import com.msa.membership.domain.Membership;

public interface ModifyMembershipPort {
    MembershipJpaEntity modifyMembership(
            Membership.MembershipName membershipName
            , Membership.MembershipEmail membershipEmail
            , Membership.MembershipAddress membershipAddress
            , Membership.MembershipIsValid membershipIsValid
            , Membership.MembershipIsCorp membershipIsCorp
            , Membership.MembershipRefreshToken membershipRefreshToken);

    MembershipJpaEntity modifyMembershipPassword(
            Membership.MembershipName membershipName,
            Membership.MembershipPassword newMembershipPassword
    );
}
