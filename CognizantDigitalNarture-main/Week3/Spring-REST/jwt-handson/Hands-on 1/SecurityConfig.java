package com.cognizant.springlearn.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// Step 1: Add spring-boot-starter-security dependency in pom.xml
// Step 2: Create this class extending WebSecurityConfigurerAdapter
// Step 3: Add @Configuration and @EnableWebSecurity annotations
//
// Effect: All REST services are now locked and require authentication.
// Spring auto-generates a password and prints it in the console logs.
// Test without credentials (expect 401):
//   curl -s http://localhost:8090/countries
// Test with auto-generated password (expect 200):
//   curl -s -v -u user:<password-from-logs> http://localhost:8090/countries

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
}
