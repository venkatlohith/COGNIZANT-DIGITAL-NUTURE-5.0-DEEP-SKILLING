package com.example.oauth2client.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// @EnableWebSecurity enables Spring Security's web security support
// Extending WebSecurityConfigurerAdapter allows customizing security config
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // All requests require authentication
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            // Enable OAuth2 login - Spring handles the OAuth2 flow automatically
            // Redirects unauthenticated users to the OAuth2 provider login page
            .oauth2Login();
    }
}
