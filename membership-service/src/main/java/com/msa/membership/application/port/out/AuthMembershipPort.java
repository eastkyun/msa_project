package com.msa.membership.application.port.out;

import com.msa.membership.domain.Membership;

public interface AuthMembershipPort {
    // membership name 를 기준으로, jwt token 을 생성한다.
    String generateJwtToken(
            Membership.MembershipName membershipName
    );
    // membership name 를 기준으로, refresh token 을 생성한다.
    String generateRefreshToken(
            Membership.MembershipName membershipName
    );

    // jwtToken 이 유효한지 확인
    boolean validateJwtToken(String jwtToken);

    // jwtToken 으로, 어떤 멤버십 name 를 가지는지 확인
    Membership.MembershipName parseMembershipNameFromToken(String jwtToken);

}
