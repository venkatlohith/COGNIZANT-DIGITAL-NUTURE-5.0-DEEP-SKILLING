package com.cognizant.ormlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cognizant.ormlearn.model.Attempt;
import com.cognizant.ormlearn.model.AttemptOption;
import com.cognizant.ormlearn.model.AttemptQuestion;
import com.cognizant.ormlearn.service.AttemptService;

@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);
    private static AttemptService attemptService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
        attemptService = context.getBean(AttemptService.class);

        testGetAttemptDetail();
    }

    private static void testGetAttemptDetail() {
        LOGGER.info("Start");

        // Pass userId and attemptId as needed
        Attempt attempt = attemptService.getAttempt(1, 1);

        LOGGER.debug("User: {}", attempt.getUser().getName());
        LOGGER.debug("Date: {}", attempt.getDate());

        for (AttemptQuestion aq : attempt.getAttemptQuestionList()) {
            System.out.println(aq.getQuestion().getText());
            for (AttemptOption ao : aq.getAttemptOptionList()) {
                System.out.printf(" %d) %-15s %.1f\t%s%n",
                        ao.getOption().getId(),
                        ao.getOption().getText(),
                        ao.getOption().getScore(),
                        ao.isSelected());
            }
        }

        LOGGER.info("End");
    }
}
