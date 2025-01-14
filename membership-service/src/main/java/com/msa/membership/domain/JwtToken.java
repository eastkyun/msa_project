package com.msa.membership.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtToken {
    private final String name;

    private final String jwtToken;

    private final String refreshToken;

    public static JwtToken generateJwtToken(MembershipName membershipName,
                                            MembershipJwtToken jwtToken,
                                            MembershipRefreshToken refreshToken) {

        return new JwtToken(
                membershipName.name,
                jwtToken.membershipJwtToken,
                refreshToken.membershipRefreshToken
        );
    }

    @Value
    public static class MembershipName {
        public MembershipName(String value) {
            this.name = value;
        }
        String name;
    }

    @Value
    public static class MembershipJwtToken {
        public MembershipJwtToken(String value) {
            this.membershipJwtToken = value;
        }
        String membershipJwtToken;
    }

    @Value
    public static class MembershipRefreshToken {
        public MembershipRefreshToken(String value) {
            this.membershipRefreshToken = value;
        }
        String membershipRefreshToken;
    }
}
