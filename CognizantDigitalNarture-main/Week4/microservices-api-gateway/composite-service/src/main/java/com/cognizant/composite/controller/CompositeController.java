package com.cognizant.composite.controller;

import com.cognizant.composite.dto.CustomerProfile;
import com.cognizant.composite.service.CompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompositeController {

    @Autowired
    private CompositeService compositeService;

    // GET /composite/{accountNumber}?loanNumber=H00987987972342
    // Calls account-service and loan-service (via Eureka + OpenFeign)
    // and returns both in a single aggregated response.
    @GetMapping("/composite/{accountNumber}")
    public CustomerProfile getCustomerProfile(
            @PathVariable String accountNumber,
            @RequestParam(required = false) String loanNumber) {
        return compositeService.getCustomerProfile(accountNumber, loanNumber);
    }
}
