package com.msa.membership.adapter.out.jwt;

import com.msa.membership.application.port.out.AuthMembershipPort;
import com.msa.membership.domain.Membership;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
@Log4j2
public class JwtTokenProvider implements AuthMembershipPort {

    private final SecretKey jwtSecret; // 내부 비밀키. 즉, jwt token 생성을 위한 비밀키에요.

    private final long jwtTokenExpirationInMs; // jwt token 의 만료시간. 즉, jwt token 의 유효시간에요.

    private final long refreshTokenExpirationInMs; // refresh token 의 만료시간. 즉, refresh token 의 유효시간에요.

    public JwtTokenProvider() {
        // 512bit 알고리즘 지원을 위한 비밀키 입니다.
        // 512 bit = 64 byte
        // env 등을 통해서, 외부 환경변수로부터 데이터를 받아올 수도 있어요.
        this.jwtSecret = Keys.hmacShaKeyFor("ayNJkDfvNXd1OF25WaNVH2P54gL9VHNA".getBytes());
        this.jwtTokenExpirationInMs = 1000L * 60;
        this.refreshTokenExpirationInMs = 1000L * 60;
    }

    @Override
    public String generateJwtToken(Membership.MembershipName membershipName) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtTokenExpirationInMs);

        return Jwts.builder()
                .setSubject(membershipName.getName())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(jwtSecret)
                .compact();
    }

    @Override
    public String generateRefreshToken(Membership.MembershipName membershipName) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + refreshTokenExpirationInMs);

        return Jwts.builder()
                .setSubject(membershipName.getName())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(jwtSecret)
                .compact();
    }

    @Override
    public boolean validateJwtToken(String jwtToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(jwtToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token: {}", ex.getMessage());
            // Invalid JWT token: 유효하지 않은 JWT 토큰일 때 발생하는 예외
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token: {}", ex.getMessage());
            // Expired JWT token: 토큰의 유효기간이 만료된 경우 발생하는 예외
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token: {}", ex.getMessage());
            // Unsupported JWT token: 지원하지 않는 JWT 토큰일 때 발생하는 예외
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty: {}", ex.getMessage());
            // JWT claims string is empty: JWT 토큰이 비어있을 때 발생하는 예외
        }
        return false;
    }

    @Override
    public Membership.MembershipName parseMembershipNameFromToken(String jwtToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
        return new Membership.MembershipName(claims.getSubject());
    }
}
