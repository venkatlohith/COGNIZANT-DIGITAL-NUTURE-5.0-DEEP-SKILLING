package com.cognizant.springlearn.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.cognizant.springlearn.Country;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;

@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    // Get a country by code (case-insensitive)
    // Throws CountryNotFoundException if code does not exist
    public Country getCountry(String code) throws CountryNotFoundException {
        LOGGER.info("START");

        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        List<Country> countryList = context.getBean("countryList", List.class);

        Country result = countryList.stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);

        // Throw exception if country not found
        if (result == null) {
            throw new CountryNotFoundException("Country not found for code: " + code);
        }

        LOGGER.debug("Country : {}", result);
        LOGGER.info("END");

        return result;
    }
}
