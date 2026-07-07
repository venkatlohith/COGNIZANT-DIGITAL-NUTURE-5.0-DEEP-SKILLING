package com.cognizant.springlearn.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin").password(passwordEncoder().encode("pwd")).roles("ADMIN")
            .and()
            .withUser("user").password(passwordEncoder().encode("pwd")).roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        LOGGER.info("Start");
        return new BCryptPasswordEncoder();
    }

    // Final configure: HTTP Basic retained for /authenticate
    // JWT filter added to validate token on all other requests
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().httpBasic().and()
            .authorizeRequests()
            // /countries role check removed - now handled by JWT filter
            // .antMatchers("/countries").hasRole("USER")
            .antMatchers("/authenticate").hasAnyRole("USER", "ADMIN")
            .anyRequest().authenticated()
            .and()
            // Register the JWT filter in the Spring Security filter chain
            .addFilter(new JwtAuthorizationFilter(authenticationManager()));
    }

    // Test workflow:
    // Step 1: Get token
    //   curl -s -u user:pwd http://localhost:8090/authenticate
    //   -> { "token": "eyJhbGciOiJIUzI1NiJ9..." }
    //
    // Step 2: Use token to access protected resource
    //   curl -s -H "Authorization: Bearer <TOKEN>" http://localhost:8090/countries
    //
    // Step 3: Tamper token -> expect 403 Forbidden
    //   curl -s -H "Authorization: Bearer <TAMPERED_TOKEN>" http://localhost:8090/countries
}
