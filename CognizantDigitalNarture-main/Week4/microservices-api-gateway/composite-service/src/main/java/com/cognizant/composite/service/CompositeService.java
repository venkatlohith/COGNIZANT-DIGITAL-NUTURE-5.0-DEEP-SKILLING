package com.cognizant.composite.service;

import com.cognizant.composite.client.AccountClient;
import com.cognizant.composite.client.LoanClient;
import com.cognizant.composite.dto.Account;
import com.cognizant.composite.dto.CustomerProfile;
import com.cognizant.composite.dto.Loan;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompositeService {

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private LoanClient loanClient;

    /**
     * Aggregates account details and (optionally) loan details for a
     * customer into a single response, so callers don't need to know
     * that account and loan live in two separate microservices.
     *
     * loanNumber is optional - pass null/blank if the customer has no
     * loan, and the composite response will just omit that section.
     */
    public CustomerProfile getCustomerProfile(String accountNumber, String loanNumber) {
        Account account = accountClient.getAccount(accountNumber);

        Loan loan = null;
        if (loanNumber != null && !loanNumber.isBlank()) {
            try {
                loan = loanClient.getLoan(loanNumber);
            } catch (FeignException.NotFound ex) {
                // No matching loan - that's fine, just leave it out of the composite response
                loan = null;
            }
        }

        return new CustomerProfile(account, loan);
    }
}
