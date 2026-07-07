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

    @Autowired
    private CountryController countryController;

    @Autowired
    private MockMvc mvc;

    // Test 1: Check if CountryController bean is loaded
    @Test
    public void contextLoads() {
        assertNotNull(countryController);
    }

    // Test 2: Valid country code returns 200 with correct data
    @Test
    public void testGetCountry() throws Exception {
        ResultActions actions = mvc.perform(get("/country"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$.code").exists());
        actions.andExpect(jsonPath("$.code").value("IN"));
        actions.andExpect(jsonPath("$.name").exists());
        actions.andExpect(jsonPath("$.name").value("India"));
    }

    // Test 3: Invalid country code returns 404 Not Found with correct reason
    @Test
    public void testGetCountryException() throws Exception {
        // "az" is not a valid country code in our list
        ResultActions actions = mvc.perform(get("/countries/az"));

        // Expect 404 Not Found
        actions.andExpect(status().isNotFound());

        // Expect reason message "Country not found"
        actions.andExpect(status().reason("Country not found"));
    }
}
