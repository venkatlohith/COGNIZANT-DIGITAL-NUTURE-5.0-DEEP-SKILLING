package com.cognizant.account.controller;

import com.cognizant.account.dto.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    // GET /accounts/{number} -> dummy account details, e.g.
    // { "number": "00987987973432", "type": "savings", "balance": 234343 }
    @GetMapping("/accounts/{number}")
    public Account getAccountDetails(@PathVariable String number) {
        return new Account(number, "savings", 234343);
    }
}
