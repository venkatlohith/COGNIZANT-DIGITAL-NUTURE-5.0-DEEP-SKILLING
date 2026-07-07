package com.cognizant.ormlearn.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    // Query 1: Get all Facebook stock details in September 2019
    // (st_code = 'FB' AND st_date BETWEEN startDate AND endDate)
    List<Stock> findByCodeAndDateBetween(String code, Date startDate, Date endDate);

    // Query 2: Get all Google stocks where close price is greater than 1250
    List<Stock> findByCodeAndCloseGreaterThan(String code, double closePrice);

    // Query 3: Top 3 dates with highest volume
    List<Stock> findTop3ByOrderByVolumeDesc();

    // Query 4: Bottom 3 Netflix stocks (lowest close price)
    List<Stock> findTop3ByCodeOrderByCloseAsc(String code);
}
