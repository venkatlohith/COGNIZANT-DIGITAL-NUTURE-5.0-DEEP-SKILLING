package com.cognizant.springlearn.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.cognizant.springlearn.Country;

@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    // Get a country based on country code (case-insensitive)
    public Country getCountry(String code) {
        LOGGER.info("START");

        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        List<Country> countryList = context.getBean("countryList", List.class);

        // Iterate through the list and do case-insensitive match
        Country result = null;
        for (Country country : countryList) {
            if (country.getCode().equalsIgnoreCase(code)) {
                result = country;
                break;
            }
        }

        // Lambda expression alternative:
        // Country result = countryList.stream()
        //         .filter(c -> c.getCode().equalsIgnoreCase(code))
        //         .findFirst()
        //         .orElse(null);

        LOGGER.debug("Country : {}", result);
        LOGGER.info("END");

        return result;
    }
}
