package com.cognizant.springlearn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SpringLearnApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

	public static void main(String[] args) {
		LOGGER.info("START");
		SpringApplication.run(SpringLearnApplication.class, args);
		displayCountries();
		LOGGER.info("END");
	}

	private static void displayCountries() {
		LOGGER.info("START");

		// Load the Spring XML configuration
		ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");

		// Retrieve the ArrayList of Country beans
		List<Country> countryList = context.getBean("countryList", List.class);

		// Display all countries
		LOGGER.debug("List of Countries: {}", countryList);

		LOGGER.info("END");
	}
}
