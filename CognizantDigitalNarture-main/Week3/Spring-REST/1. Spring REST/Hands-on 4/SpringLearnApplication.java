package com.cognizant.springlearn;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SpringLearnApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

	public static void main(String[] args) throws ParseException {
		LOGGER.info("START");
		SpringApplication.run(SpringLearnApplication.class, args);
		displayCountry();
		LOGGER.info("END");
	}

	private static void displayCountry() {
		LOGGER.info("START");

		// Load country.xml Spring configuration file
		ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");

		// Retrieve Country bean
		Country country = context.getBean("country", Country.class);

		LOGGER.debug("Country : {}", country.toString());

		LOGGER.info("END");
	}
}
