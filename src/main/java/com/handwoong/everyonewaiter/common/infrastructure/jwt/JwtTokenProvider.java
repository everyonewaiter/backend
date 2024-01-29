package com.handwoong.everyonewaiter.common.infrastructure.jwt;

import com.handwoong.everyonewaiter.common.exception.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final SecretKey key;

    public JwtTokenProvider(@Value("${jwt.secret}") final String secretKey) {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken createToken(final TokenInfo tokenInfo) {
        return new JwtToken(
            Jwts.builder()
                .subject(tokenInfo.subject())
                .claim(tokenInfo.claimKey(), tokenInfo.claimValue())
                .expiration(tokenInfo.expire())
                .signWith(key)
                .compact()
        );
    }

    public TokenInfo parseToken(final String token, final String claimKey) {
        final Claims claims = decode(token);
        return TokenInfo.builder()
            .subject(claims.getSubject())
            .claimKey(claimKey)
            .claimValue(claims.get(claimKey).toString())
            .expire(claims.getExpiration())
            .build();
    }

    private Claims decode(final String token) {
        try {
            return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (final IllegalArgumentException | JwtException exception) {
            throw new InvalidJwtTokenException(exception);
        }
    }
}
