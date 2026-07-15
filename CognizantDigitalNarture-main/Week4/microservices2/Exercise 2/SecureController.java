package com.example.resourceserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureController {

    // GET /secure
    // Protected endpoint - requires valid JWT token in Authorization header
    // Test with: curl -H "Authorization: Bearer <JWT_TOKEN>" http://localhost:8080/secure
    // Without token: 401 Unauthorized
    // With invalid/expired token: 401 Unauthorized
    // With valid token: 200 OK -> "This is a secure endpoint"
    @GetMapping("/secure")
    public String secure() {
        return "This is a secure endpoint";
    }
}
