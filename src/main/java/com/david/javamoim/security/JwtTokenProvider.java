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

    private static final SecretKey SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long SIX_HOURS_IN_MILLISECONDS = 1000 * 60 * 60 * 6L;
    private static final String TOKEN_SUBJECT_FOR_MEMBER = "member authentication";
    private static final String NO_SUCH_DATA_IN_TOKEN_MESSAGE = "해당 정보가 토큰에 존재하지 않습니다.";

    /**
     * 인증토큰을 생성한다
     *
     * @param key, value
     * @return access token as string
     */
    public String createToken(String key, String value) {
        Date now = new Date(); // 현재 시간을 ms로 반환
        Date expiration = new Date(now.getTime() + SIX_HOURS_IN_MILLISECONDS);
        return Jwts.builder()
                .setSubject(TOKEN_SUBJECT_FOR_MEMBER)
                .claim(key, value)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SIGNING_KEY)
                .compact();
    }

    public String extractClaimValue(String token, String claimKey) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token).getBody();
        for (String key : claims.keySet()) {
            if (key.equals(claimKey)) {
                return (String) claims.get(key);
            }
        }
        throw new IllegalStateException(NO_SUCH_DATA_IN_TOKEN_MESSAGE);
    }
}
