// ===== CountryService.java - updateCountry() =====
// Steps:
// 1. Get the reference of the country using findById() method in repository
// 2. In the country reference obtained, update the name of country using setter method
// 3. Call countryRepository.save() method to update the name

@Transactional
public void updateCountry(String code, String name) {

    Optional<Country> result = countryRepository.findById(code);

    if (!result.isPresent())
        throw new CountryNotFoundException("Country not found for code: " + code);

    Country country = result.get();
    country.setName(name);

    countryRepository.save(country);
}


// ===== OrmLearnApplication.java - test method =====
// Invokes updateCountry() method in CountryService passing a country's code
// and a different name for the country. Check in database table if name is modified.

private static void testUpdateCountry() {
    LOGGER.info("Start");

    countryService.updateCountry("ZZ", "Updated Test Land");

    Country updated = countryService.findCountryByCode("ZZ");
    LOGGER.debug("Updated country:{}", updated);

    LOGGER.info("End");
}
