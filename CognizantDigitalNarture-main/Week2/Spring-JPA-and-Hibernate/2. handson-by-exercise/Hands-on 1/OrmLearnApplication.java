package com.cognizant.ormlearn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.CountryService;

@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);
    private static CountryService countryService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
        countryService = context.getBean(CountryService.class);

        testSearchByContaining();
        testSearchByContainingSorted();
        testSearchByStartingLetter();
    }

    // Query 1: Search countries containing "ou"
    private static void testSearchByContaining() {
        LOGGER.info("Start");
        List<Country> countries = countryService.findCountriesByPartialName("ou");
        LOGGER.debug("Countries containing 'ou': {}", countries);
        LOGGER.info("End");
    }

    // Query 2: Search countries containing "ou" sorted ascending
    private static void testSearchByContainingSorted() {
        LOGGER.info("Start");
        List<Country> countries = countryService.findCountriesByPartialNameSorted("ou");
        LOGGER.debug("Countries containing 'ou' sorted: {}", countries);
        LOGGER.info("End");
    }

    // Query 3: Search countries starting with "Z"
    private static void testSearchByStartingLetter() {
        LOGGER.info("Start");
        List<Country> countries = countryService.findCountriesStartingWith("Z");
        LOGGER.debug("Countries starting with 'Z': {}", countries);
        LOGGER.info("End");
    }
}
