package com.cognizant.composite.dto;

// The composite response: account details + (optionally) loan details
// for the same customer, aggregated behind a single API call.
public class CustomerProfile {

    private Account account;
    private Loan loan;

    public CustomerProfile() {
    }

    public CustomerProfile(Account account, Loan loan) {
        this.account = account;
        this.loan = loan;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
