// ===== CountryService.java - addCountry() =====

@Transactional
public void addCountry(Country country) {
    countryRepository.save(country);
}


// ===== OrmLearnApplication.java - testAddCountry() =====
// Steps:
// 1. Create new instance of country with a new code and name
// 2. Call countryService.addCountry() passing the country created in the previous step
// 3. Invoke countryService.findCountryByCode() passing the same code used when adding
// 4. Check in the database if the country is added

private static void testAddCountry() {
    LOGGER.info("Start");

    Country country = new Country();
    country.setCode("ZZ");
    country.setName("Test Land");

    countryService.addCountry(country);

    Country added = countryService.findCountryByCode("ZZ");
    LOGGER.debug("Added country:{}", added);

    LOGGER.info("End");
}
