package com.cognizant.springlearn;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.cognizant.springlearn.controller.CountryController;

@SpringBootTest
@AutoConfigureMockMvc
public class SpringLearnApplicationTests {

    // Autowire CountryController to verify it is loaded
    @Autowired
    private CountryController countryController;

    // Autowire MockMvc to perform HTTP calls in tests
    @Autowired
    private MockMvc mvc;

    // Test 1: Check if CountryController bean is loaded in context
    @Test
    public void contextLoads() {
        assertNotNull(countryController);
    }

    // Test 2: Invoke /country and validate the response
    @Test
    public void testGetCountry() throws Exception {
        // Perform GET /country
        ResultActions actions = mvc.perform(get("/country"));

        // Check HTTP status is 200 OK
        actions.andExpect(status().isOk());

        // Check 'code' field exists in response JSON
        actions.andExpect(jsonPath("$.code").exists());

        // Check 'code' value is "IN"
        actions.andExpect(jsonPath("$.code").value("IN"));

        // Check 'name' field exists in response JSON
        actions.andExpect(jsonPath("$.name").exists());

        // Check 'name' value is "India"
        actions.andExpect(jsonPath("$.name").value("India"));
    }
}
