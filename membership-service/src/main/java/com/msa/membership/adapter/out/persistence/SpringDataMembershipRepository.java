package com.msa.membership.adapter.out.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataMembershipRepository extends JpaRepository<MembershipJpaEntity, Long> {
    boolean existsByName(String name);

    @Query("select e from MembershipJpaEntity e where e.name = :name")
    MembershipJpaEntity findByName(@Param("name") String name);

    @Query("select e from MembershipJpaEntity e where e.name = :name and e.password = :password")
    MembershipJpaEntity findByNameAndPassword(@Param("name") String name, @Param("password") String password);


}
