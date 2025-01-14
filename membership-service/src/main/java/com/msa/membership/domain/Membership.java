package com.msa.membership.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Membership {
    private final String name;
    private final String password;
    private final String email;
    private final String address;
    private final boolean isValid;
    private final boolean isCorp;

    private final String membershipRefreshToken ;

    public static Membership generateMembership(MembershipName membershipName,
                                                MembershipPassword membershipPassword,
                                                MembershipEmail membershipEmail,
                                                MembershipAddress membershipAddress,
                                                MembershipIsValid membershipIsValid,
                                                MembershipIsCorp membershipIsCorp,
                                                MembershipRefreshToken membershipRefreshToken) {
        return new Membership(membershipName.name,
                membershipPassword.password,
                membershipEmail.email,
                membershipAddress.address,
                membershipIsValid.isValid,
                membershipIsCorp.isCorp,
                membershipRefreshToken.refreshToken);
    }

    @Value
    public static class MembershipName {
        public MembershipName(String value) {
            this.name = value;
        }
        String name ;
    }

    @Value
    public static class MembershipPassword {
        public MembershipPassword(String value) {
            this.password = value;
        }
        String password ;
    }

    @Value
    public static class MembershipEmail {
        public MembershipEmail(String value) {
            this.email = value;
        }
        String email ;
    }

    @Value
    public static class MembershipAddress {
        public MembershipAddress(String value) {
            this.address = value;
        }
        String address ;
    }

    @Value
    public static class MembershipIsValid {
        public MembershipIsValid(boolean value) {
            this.isValid = value;
        }
        boolean isValid ;
    }

    @Value
    public static class MembershipIsCorp {
        public MembershipIsCorp(boolean value) {
            this.isCorp = value;
        }
        boolean isCorp ;
    }

    @Value
    public static class MembershipRefreshToken {
        public MembershipRefreshToken(String value) {
            this.refreshToken = value;
        }
        String refreshToken;
    }

}
