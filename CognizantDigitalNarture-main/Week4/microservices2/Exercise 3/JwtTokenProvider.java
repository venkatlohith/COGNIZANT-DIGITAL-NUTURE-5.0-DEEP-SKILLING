package com.example.jwtauth.security;

import com.example.jwtauth.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

// Responsible for:
// 1. Creating JWT tokens for authenticated users
// 2. Validating incoming JWT tokens
// 3. Extracting authentication from tokens
@Component
public class JwtTokenProvider {

    @Autowired
    private JwtConfig jwtConfig;

    // Creates a JWT token for the given username
    // Token is valid for 1 hour (3600000 ms)
    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour validity

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
                .compact();
    }

    // Validates the JWT token - checks signature and expiry
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extracts the username from the JWT token
    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Creates a Spring Security Authentication object from the JWT token
    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
    }
}
