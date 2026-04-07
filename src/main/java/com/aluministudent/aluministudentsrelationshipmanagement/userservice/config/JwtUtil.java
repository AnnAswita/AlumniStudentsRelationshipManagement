package com.aluministudent.aluministudentsrelationshipmanagement.userservice.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "mysecretkeymysecretkeymysecretkey";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // ---------------------------
    // GENERATE TOKEN WITH ROLE
    // ---------------------------
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)  // Add role to JWT
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ---------------------------
    // EXTRACT EMAIL
    // ---------------------------
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // ---------------------------
    // EXTRACT ROLE
    // ---------------------------
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // ---------------------------
    // VALIDATE TOKEN
    // ---------------------------
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ---------------------------
    // INTERNAL CLAIM PARSER
    // ---------------------------
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}