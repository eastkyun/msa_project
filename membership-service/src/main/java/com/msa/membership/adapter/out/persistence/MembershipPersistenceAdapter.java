package com.msa.membership.adapter.out.persistence;

import com.msa.common.PersistenceAdapter;
import com.msa.membership.application.port.out.FindMembershipPort;
import com.msa.membership.application.port.out.ModifyMembershipPort;
import com.msa.membership.application.port.out.RegisterMembershipPort;
import com.msa.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;

    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipPassword  membershipPassword,
                                                Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress,
                                                Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {
        // TODO 중복 체크 필요
        boolean isExisted = membershipRepository.existsByName(membershipName.getName());

        if (!isExisted) {
            MembershipJpaEntity entity = new MembershipJpaEntity(
                    membershipName.getName(),
                    membershipPassword.getPassword(),
                    membershipAddress.getAddress(),
                    membershipEmail.getEmail(),
                    membershipIsValid.isValid(),
                    membershipIsCorp.isCorp(),
                    "");

            // entity db save
            membershipRepository.save(entity);

            return entity;

        }
        return null;

    }

    @Override
    public MembershipJpaEntity findMembershipByName(Membership.MembershipName membershipName) {
        MembershipJpaEntity entity = membershipRepository.findByName(membershipName.getName());
        System.out.println(entity.toString());
        return entity;
    }

    @Override
    public MembershipJpaEntity findMembershipByNameAndPassword(Membership.MembershipName membershipNamem, Membership.MembershipPassword membershipPassword) {
        MembershipJpaEntity entity = membershipRepository.findByNameAndPassword(membershipNamem.getName(), membershipPassword.getPassword());
        System.out.println(entity.toString());
        return entity;
    }

    @Override
    public MembershipJpaEntity modifyMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail,
                                                Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid,
                                                Membership.MembershipIsCorp membershipIsCorp, Membership.MembershipRefreshToken membershipRefreshToken) {
        MembershipJpaEntity entity = membershipRepository.findByName(membershipName.getName());

        entity.setName(membershipName.getName());
        entity.setAddress(membershipAddress.getAddress());
        entity.setEmail(membershipEmail.getEmail());
        entity.setCorp(membershipIsCorp.isCorp());
        entity.setValid(membershipIsValid.isValid());
        entity.setRefreshToken(membershipRefreshToken.getRefreshToken());
        membershipRepository.save(entity);

        MembershipJpaEntity clone = entity.clone();

        System.out.printf(clone.toString());

        return clone;
    }

    @Override
    public MembershipJpaEntity modifyMembershipPassword(Membership.MembershipName membershipName, Membership.MembershipPassword newMembershipPassword) {
        MembershipJpaEntity entity = membershipRepository.findByName(membershipName.getName());
        if(entity.isValid()) {
            entity.setPassword(newMembershipPassword.getPassword());
            membershipRepository.save(entity);
        }
        MembershipJpaEntity clone = entity.clone();
        System.out.printf("clone.toString(): %s\n", clone.toString());
        return clone;
    }

}
