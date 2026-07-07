package com.cognizant.springlearn;

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
		displayCountry();
		LOGGER.info("END");
	}

	private static void displayCountry() {
		LOGGER.info("START");

		ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");

		// First getBean() call
		Country country = context.getBean("country", Country.class);
		LOGGER.debug("Country : {}", country.toString());

		// Second getBean() call
		// Singleton scope  -> constructor called ONCE  (same instance)
		// Prototype scope  -> constructor called TWICE (new instance each time)
		Country anotherCountry = context.getBean("country", Country.class);
		LOGGER.debug("Another Country : {}", anotherCountry.toString());

		// Check if both references point to the same object
		LOGGER.debug("Same instance? {}", (country == anotherCountry));

		LOGGER.info("END");
	}
}
