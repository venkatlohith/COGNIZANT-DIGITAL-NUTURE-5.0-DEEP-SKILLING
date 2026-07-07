package com.cognizant.ormlearn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.repository.CountryRepository;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    // Query 1: Find countries containing partial name
    @Transactional
    public List<Country> findCountriesByPartialName(String keyword) {
        return countryRepository.findByNameContaining(keyword);
    }

    // Query 2: Find countries containing partial name sorted ascending
    @Transactional
    public List<Country> findCountriesByPartialNameSorted(String keyword) {
        return countryRepository.findByNameContainingOrderByNameAsc(keyword);
    }

    // Query 3: Find countries starting with a letter
    @Transactional
    public List<Country> findCountriesStartingWith(String letter) {
        return countryRepository.findByNameStartingWith(letter);
    }
}
