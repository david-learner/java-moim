package com.david.javamoim.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final static SecretKey SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final static long SIX_HOURS_IN_MILLISECONDS = 1000 * 60 * 60 * 6L;
    private final static String TOKEN_SUBJECT_FOR_MEMBER = "member authentication";

    public String createToken(String payload) {
        Claims claims = Jwts.claims().setSubject(TOKEN_SUBJECT_FOR_MEMBER);
        Date now = new Date(); // 현재 시간을 ms로 반환
        Date expiration = new Date(now.getTime() + SIX_HOURS_IN_MILLISECONDS);
        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SIGNING_KEY)
                .compact();
    }
}
