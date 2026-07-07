package com.cognizant.springlearn.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    // GET /authenticate
    // Reads Authorization header from HTTP request (Spring injects it automatically)
    // Returns { "token": "" } initially (token generation added in Hands-on 5)
    //
    // Test:
    //   curl -s -u user:pwd http://localhost:8090/authenticate
    // Expected response:
    //   { "token": "" }
    // Check logs: Authorization header value is displayed with "Basic" prefix
    //   and Base64 encoding of "user:pwd"
    @GetMapping("/authenticate")
    public Map<String, String> authenticate(
            @RequestHeader("Authorization") String authHeader) {

        LOGGER.info("Start");
        LOGGER.debug("Authorization Header : {}", authHeader);

        Map<String, String> map = new HashMap<String, String>();
        map.put("token", "");

        LOGGER.info("End");
        return map;
    }
}
