package com.cognizant.loan.controller;

import com.cognizant.loan.dto.Loan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {

    // GET /loans/{number} -> dummy loan details, e.g.
    // { "number": "H00987987972342", "type": "car", "loan": 400000, "emi": 3258, "tenure": 18 }
    @GetMapping("/loans/{number}")
    public Loan getLoanDetails(@PathVariable String number) {
        return new Loan(number, "car", 400000, 3258, 18);
    }
}
