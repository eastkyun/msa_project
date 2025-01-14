package com.msa.membership.application.port.out;

import com.msa.membership.adapter.out.persistence.MembershipJpaEntity;
import com.msa.membership.domain.Membership;

public interface FindMembershipPort {
    MembershipJpaEntity findMembershipByName(Membership.MembershipName membershipName);
    MembershipJpaEntity findMembershipByNameAndPassword(Membership.MembershipName membershipNamem, Membership.MembershipPassword membershipPassword);

}