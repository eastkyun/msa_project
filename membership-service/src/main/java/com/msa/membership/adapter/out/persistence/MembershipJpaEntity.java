package com.msa.membership.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "membership")
@Data
@NoArgsConstructor @AllArgsConstructor
public class    MembershipJpaEntity {
    @Id
    private String name;

    private String password;

    private String address;

    private String email;

    private boolean isValid;

    private boolean isCorp;

    private String refreshToken;

    @Override
    public String toString() {
        return "MembershipJpaEntity{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", isValid=" + isValid +
                ", isCorp=" + isCorp +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }

    public MembershipJpaEntity(String name, String address, String email, boolean isValid, boolean isCorp, String refreshToken) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.isValid = isValid;
        this.isCorp = isCorp;
        this.refreshToken = refreshToken;
    }
    public MembershipJpaEntity clone() {
        return new MembershipJpaEntity(this.name, this.address, this.email, this.isValid, this.isCorp, this.refreshToken);
    }
}
