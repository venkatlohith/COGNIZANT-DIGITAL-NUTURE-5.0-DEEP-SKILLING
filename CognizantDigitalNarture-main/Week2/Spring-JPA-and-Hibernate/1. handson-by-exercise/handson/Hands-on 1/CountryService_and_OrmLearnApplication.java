// ===== CountryService.java =====
// package com.cognizant.ormlearn.service;
//
// @Service
// public class CountryService {
//
//     @Autowired
//     private CountryRepository countryRepository;
//
//     @Transactional
//     public List<Country> getAllCountries() {
//         return countryRepository.findAll();
//     }
// }

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Transactional
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
}


// ===== OrmLearnApplication.java (relevant snippets) =====

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

public static void main(String[] args) {
    SpringApplication.run(OrmLearnApplication.class, args);
    LOGGER.info("Inside main");
}

// static reference to CountryService in OrmLearnApplication class
private static CountryService countryService;

// test method to get all countries from service
private static void testGetAllCountries() {
    LOGGER.info("Start");
    List<Country> countries = countryService.getAllCountries();
    LOGGER.debug("countries={}", countries);
    LOGGER.info("End");
}

// Modify SpringApplication.run() invocation to set the application context
// and the CountryService reference from the application context.
ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
countryService = context.getBean(CountryService.class);

testGetAllCountries();
