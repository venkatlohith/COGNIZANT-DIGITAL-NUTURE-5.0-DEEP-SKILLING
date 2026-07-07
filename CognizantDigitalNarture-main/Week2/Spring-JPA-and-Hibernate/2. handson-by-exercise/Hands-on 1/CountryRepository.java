package com.cognizant.ormlearn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    // Query 1: Find all countries containing a given text (e.g., "ou")
    List<Country> findByNameContaining(String keyword);

    // Query 2: Find all countries containing a given text, sorted ascending by name
    List<Country> findByNameContainingOrderByNameAsc(String keyword);

    // Query 3: Find all countries whose name starts with a given letter (e.g., "Z")
    List<Country> findByNameStartingWith(String letter);
}
