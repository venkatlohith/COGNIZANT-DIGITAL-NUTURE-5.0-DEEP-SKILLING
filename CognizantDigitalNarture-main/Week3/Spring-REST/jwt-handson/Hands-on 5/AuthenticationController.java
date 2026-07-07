package com.cognizant.springlearn.controller;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    // GET /authenticate
    // Step 1: Read Authorization header
    // Step 2: Decode Base64 to get user
    // Step 3: Generate JWT token for the user
    // Test:
    //   curl -s -u user:pwd http://localhost:8090/authenticate
    // Expected Response:
    //   {"token":"eyJhbGciOiJIUzI1NiJ9..."}
    @GetMapping("/authenticate")
    public Map<String, String> authenticate(
            @RequestHeader("Authorization") String authHeader) {

        LOGGER.info("Start");
        LOGGER.debug("Authorization Header : {}", authHeader);

        // Decode the user from the Authorization header
        String user = getUser(authHeader);
        LOGGER.debug("User : {}", user);

        // Generate JWT token for the user
        String token = generateJwt(user);
        LOGGER.debug("Token : {}", token);

        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);

        LOGGER.info("End");
        return map;
    }

    // Decodes the Base64 Authorization header to extract the username
    // Authorization header format: "Basic Base64(username:password)"
    private String getUser(String authHeader) {
        LOGGER.info("Start");

        // Get the Base64 encoded text after "Basic "
        String encodedCredentials = authHeader.substring("Basic ".length());
        LOGGER.debug("Encoded Credentials : {}", encodedCredentials);

        // Decode the Base64 encoded credentials
        byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
        String decodedCredentials = new String(decodedBytes);
        LOGGER.debug("Decoded Credentials : {}", decodedCredentials);

        // Get the text before the colon to get the username
        String user = decodedCredentials.substring(0, decodedCredentials.indexOf(":"));
        LOGGER.debug("User : {}", user);

        LOGGER.info("End");
        return user;
    }

    // Generates a JWT token for the given username
    // Token expires in 20 minutes (1200000 ms)
    private String generateJwt(String user) {
        LOGGER.info("Start");

        JwtBuilder builder = Jwts.builder();
        builder.setSubject(user);
        // Set the token issue time as current time
        builder.setIssuedAt(new Date());
        // Set the token expiry as 20 minutes from now
        builder.setExpiration(new Date((new Date()).getTime() + 1200000));
        builder.signWith(SignatureAlgorithm.HS256, "secretkey");
        String token = builder.compact();

        LOGGER.debug("Token : {}", token);
        LOGGER.info("End");

        return token;
    }
}
