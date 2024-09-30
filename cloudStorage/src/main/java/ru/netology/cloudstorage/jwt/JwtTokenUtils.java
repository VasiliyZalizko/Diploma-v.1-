package ru.netology.cloudstorage.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration jwtLifeTime;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateToken(UserDetails userDetails) {
        String role = userDetails.getAuthorities().stream().map(String::valueOf).collect(Collectors.joining());
        Date currentTime = new Date();
        Date expireTime = new Date(currentTime.getTime() + jwtLifeTime.toMillis());
        return Jwts.builder()
                .claim("role", role)
                .subject(userDetails.getUsername())
                .issuedAt(currentTime)
                .expiration(expireTime)
                .signWith(getSecretKey())
                .compact();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public String getRole(String token) {
        return getClaimsFromToken(token).get("role").toString();
    }
}