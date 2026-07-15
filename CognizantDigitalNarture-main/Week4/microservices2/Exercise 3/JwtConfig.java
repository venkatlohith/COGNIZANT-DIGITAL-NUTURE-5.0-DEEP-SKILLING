package com.example.jwtauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

// Reads JWT secret key from application.yml
// Injected into JwtTokenProvider to sign and validate tokens
@Configuration
public class JwtConfig {

    @Value("${spring.security.jwt.secret}")
    private String secret;

    public String getSecret() {
        return secret;
    }
}
