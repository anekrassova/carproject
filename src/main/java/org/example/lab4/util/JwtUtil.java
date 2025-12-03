package org.example.lab4.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    @Autowired
    public JwtUtil(SecretKey secretKey) {
        this.secretKey = secretKey;
        System.out.println("secret key in jwt util"+ this.secretKey);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long extractUserId(String token) {
        System.out.println("Token in extract user id: " + token);
        Claims claims = getClaims(token);
        return claims.get("userId", Long.class);
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }

//    public String extractEmail(String token) {
//        Claims claims = getClaims(token);
//        return claims.getSubject();
//    }
//
//    public String extractUsername(String token) {
//        Claims claims = getClaims(token);
//        return claims.getSubject();
//    }
}
