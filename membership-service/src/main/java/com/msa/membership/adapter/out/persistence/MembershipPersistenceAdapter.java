package com.msa.membership.adapter.out.persistence;

import com.msa.common.PersistenceAdapter;
import com.msa.membership.adapter.out.security.SecurityAdapter;
import com.msa.membership.application.port.out.FindMembershipPort;
import com.msa.membership.application.port.out.ModifyMembershipPort;
import com.msa.membership.application.port.out.RegisterMembershipPort;
import com.msa.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@PersistenceAdapter
@RequiredArgsConstructor
@Log4j2
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;

    private final SecurityAdapter securityAdapter;

    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipPassword  membershipPassword,
                                                Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress,
                                                Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {
        // 암호화
        String encryptedPassword = securityAdapter.encrypt(membershipPassword.getPassword());
        String encryptedAddress = securityAdapter.encrypt(membershipAddress.getAddress());

        boolean isExisted = membershipRepository.existsByName(membershipName.getName());

        if (!isExisted) {
            MembershipJpaEntity entity = new MembershipJpaEntity(
                    membershipName.getName(),
                    encryptedPassword,
                    encryptedAddress,
                    membershipEmail.getEmail(),
                    membershipIsValid.isValid(),
                    membershipIsCorp.isCorp(),
                    "");

            // entity db save
            membershipRepository.save(entity);

            MembershipJpaEntity clone = entity.clone();
            clone.setAddress(membershipAddress.getAddress());

            return clone;

        }
        return null;

    }

    @Override
    public MembershipJpaEntity findMembershipByName(Membership.MembershipName membershipName) {
        MembershipJpaEntity entity = membershipRepository.findByName(membershipName.getName());

        MembershipJpaEntity clone = entity.clone();
        clone.setAddress(securityAdapter.decrypt(entity.getAddress()));

        System.out.println(clone.toString());
        return clone;
    }

    @Override
    public MembershipJpaEntity findMembershipByNameAndPassword(Membership.MembershipName membershipName, Membership.MembershipPassword membershipPassword) {
        // 암호화

        String encryptedPassword = securityAdapter.encrypt(membershipPassword.getPassword());

        MembershipJpaEntity entity = membershipRepository.findByNameAndPassword(membershipName.getName(), encryptedPassword);
        if(entity == null) {
            log.info("Membership with name {} not found", membershipName.getName());
            return null;
        }

        MembershipJpaEntity clone = entity.clone();

        clone.setAddress(securityAdapter.decrypt(entity.getAddress()));
        System.out.println("findMembershipByNameAndPassword");
        System.out.println(clone.toString());
        return clone;
    }

    @Override
    public MembershipJpaEntity modifyMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail,
                                                Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid,
                                                Membership.MembershipIsCorp membershipIsCorp, Membership.MembershipRefreshToken membershipRefreshToken) {
        MembershipJpaEntity entity = membershipRepository.findByName(membershipName.getName());

        entity.setName(membershipName.getName());
        //  암호화
        entity.setAddress(securityAdapter.encrypt(membershipAddress.getAddress()));

        entity.setEmail(membershipEmail.getEmail());
        entity.setCorp(membershipIsCorp.isCorp());
        entity.setValid(membershipIsValid.isValid());
        entity.setRefreshToken(membershipRefreshToken.getRefreshToken());
        membershipRepository.save(entity);

        MembershipJpaEntity clone = entity.clone();

        clone.setAddress(securityAdapter.decrypt(entity.getAddress()));

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
