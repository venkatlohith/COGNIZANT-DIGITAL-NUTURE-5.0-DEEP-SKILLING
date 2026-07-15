package com.example.resourceserver.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// Resource Server Configuration
// A Resource Server hosts the actual protected resources/APIs
// It validates the JWT token sent in the Authorization header of each request
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // All requests must be authenticated
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            // Enable OAuth2 Resource Server with JWT token validation
            // Spring auto-validates JWT signature, expiry, and issuer
            .oauth2ResourceServer()
            .jwt();
    }
}
