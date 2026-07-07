// ===== CountryService.java - deleteCountry() =====

@Transactional
public void deleteCountry(String code) {
    countryRepository.deleteById(code);
}


// ===== OrmLearnApplication.java - test method =====
// Steps:
// 1. Call the delete method based on the country code used during the add country hands on
// 2. Check in database if the country is deleted

private static void testDeleteCountry() {
    LOGGER.info("Start");

    countryService.deleteCountry("ZZ");

    LOGGER.info("End");
}
