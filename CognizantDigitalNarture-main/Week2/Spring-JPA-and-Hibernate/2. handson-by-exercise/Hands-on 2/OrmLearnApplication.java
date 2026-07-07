package com.cognizant.ormlearn;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cognizant.ormlearn.model.Stock;
import com.cognizant.ormlearn.repository.StockRepository;

@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    // Autowire StockRepository directly as per the hands-on instructions
    private static StockRepository stockRepository;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
        stockRepository = context.getBean(StockRepository.class);

        testGetFacebookSeptemberStocks();
        testGetGoogleStocksGreaterThan1250();
        testGetTop3HighestVolume();
        testGetBottom3NetflixStocks();
    }

    // Query 1: Facebook stocks in September 2019
    private static void testGetFacebookSeptemberStocks() {
        LOGGER.info("Start");
        List<Stock> stocks = stockRepository.findByCodeAndDateBetween(
                "FB",
                Date.valueOf("2019-09-01"),
                Date.valueOf("2019-09-30"));
        stocks.forEach(s -> LOGGER.debug("{}", s));
        LOGGER.info("End");
    }

    // Query 2: Google stocks where close > 1250
    private static void testGetGoogleStocksGreaterThan1250() {
        LOGGER.info("Start");
        List<Stock> stocks = stockRepository.findByCodeAndCloseGreaterThan("GOOGL", 1250.0);
        stocks.forEach(s -> LOGGER.debug("{}", s));
        LOGGER.info("End");
    }

    // Query 3: Top 3 dates with highest volume
    private static void testGetTop3HighestVolume() {
        LOGGER.info("Start");
        List<Stock> stocks = stockRepository.findTop3ByOrderByVolumeDesc();
        stocks.forEach(s -> LOGGER.debug("{}", s));
        LOGGER.info("End");
    }

    // Query 4: 3 dates when Netflix stocks were lowest
    private static void testGetBottom3NetflixStocks() {
        LOGGER.info("Start");
        List<Stock> stocks = stockRepository.findTop3ByCodeOrderByCloseAsc("NFLX");
        stocks.forEach(s -> LOGGER.debug("{}", s));
        LOGGER.info("End");
    }
}
