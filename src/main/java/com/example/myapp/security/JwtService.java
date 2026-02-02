package com.example.myapp.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.example.myapp.entity.Role;
import com.example.myapp.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // 👉 NÊN đưa vào application.yml sau, hiện để cứng cho dễ hiểu
    private static final String SECRET_KEY = "my-super-secret-key-my-super-secret-key-123456";

    private static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000; // 15 phút

    /* ================== GENERATE TOKEN ================== */

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("roles", user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .toList())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(getKey())
                .compact();
    }

    /* ================== VALIDATE TOKEN ================== */

    public boolean isTokenValid(String token, User user) {
        final String email = extractEmail(token);
        return email.equals(user.getEmail()) && !isTokenExpired(token);
    }

    /* ================== EXTRACT DATA ================== */

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return extractAllClaims(token).get("roles", List.class);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /* ================== INTERNAL METHODS ================== */

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }
}
