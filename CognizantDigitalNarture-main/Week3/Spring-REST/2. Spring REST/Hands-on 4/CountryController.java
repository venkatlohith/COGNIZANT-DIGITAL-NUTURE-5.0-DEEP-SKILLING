package com.cognizant.springlearn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.Country;

@RestController
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    public CountryController() {
        LOGGER.debug("Inside CountryController Constructor.");
    }

    // GET /country
    // Sample Request : http://localhost:8083/country
    // Sample Response: { "code": "IN", "name": "India" }
    @RequestMapping("/country")
    public Country getCountryIndia() {
        LOGGER.info("START");

        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        Country country = context.getBean("in", Country.class);

        LOGGER.debug("Country : {}", country);
        LOGGER.info("END");

        return country;
    }

    // GET /countries
    // Returns all countries loaded from country.xml
    // Sample Request : http://localhost:8083/countries
    // Sample Response:
    // [
    //   { "code": "IN", "name": "India" },
    //   { "code": "US", "name": "United States" },
    //   { "code": "JP", "name": "Japan" },
    //   { "code": "DE", "name": "Germany" }
    // ]
    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        LOGGER.info("START");

        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        List<Country> countryList = context.getBean("countryList", List.class);

        LOGGER.debug("Countries : {}", countryList);
        LOGGER.info("END");

        return countryList;
    }
}
