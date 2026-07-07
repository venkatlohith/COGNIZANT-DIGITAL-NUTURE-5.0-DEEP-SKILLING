// ===== CountryService.java - findCountryByCode() =====

@Transactional
public Country findCountryByCode(String countryCode) throws CountryNotFoundException {

    Optional<Country> result = countryRepository.findById(countryCode);

    if (!result.isPresent())
        throw new CountryNotFoundException("Country not found for code: " + countryCode);

    Country country = result.get();

    return country;
}


// ===== OrmLearnApplication.java - test method =====

private static void getAllCountriesTest() {
    LOGGER.info("Start");
    Country country = countryService.findCountryByCode("IN");
    LOGGER.debug("Country:{}", country);
    LOGGER.info("End");
}

// NOTE: SME to explain the importance of @Transactional annotation.
// Spring takes care of creating the Hibernate session and manages the
// transactionality when executing the service method.
