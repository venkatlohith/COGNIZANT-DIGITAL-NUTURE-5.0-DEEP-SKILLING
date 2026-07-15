package com.example.oauth2client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    // GET /user
    // Returns the authenticated user's principal information (name, attributes)
    // Principal is automatically populated by Spring Security after OAuth2 login
    // Sample Response:
    // { "name": "user@gmail.com", "attributes": { "sub": "...", "email": "..." } }
    @GetMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }
}
