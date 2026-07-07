package com.cognizant.springlearn.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

// Filter that intercepts every HTTP request and validates the JWT token
// Extends BasicAuthenticationFilter to hook into Spring Security filter chain
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    // Constructor sets the authentication manager
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        LOGGER.info("Start");
        LOGGER.debug("{}: ", authenticationManager);
    }

    // Intercepts every request
    // If Authorization header contains "Bearer " token -> validate and authenticate
    // If no Bearer token -> pass the request through (let Spring Security handle it)
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException {

        LOGGER.info("Start");

        String header = req.getHeader("Authorization");
        LOGGER.debug(header);

        // If no Authorization header or not a Bearer token, skip JWT validation
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        // Validate the JWT token and get the authentication object
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        // Set the authentication in the Spring Security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(req, res);

        LOGGER.info("End");
    }

    // Parses and validates the JWT token from the Authorization header
    // Returns authentication token if valid, null otherwise
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        if (token != null) {
            Jws<Claims> jws;
            try {
                // Parse and validate the JWT token using the same secret key used to sign it
                jws = Jwts.parser()
                        .setSigningKey("secretkey")
                        .parseClaimsJws(token.replace("Bearer ", ""));

                // Extract the username (subject) from the token payload
                String user = jws.getBody().getSubject();
                LOGGER.debug(user);

                if (user != null) {
                    // Return authenticated token with empty authorities list
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }
            } catch (JwtException ex) {
                // Token is invalid or expired
                LOGGER.error("JWT validation failed: {}", ex.getMessage());
                return null;
            }
            return null;
        }
        return null;
    }
}
