-- Scenario 1
DECLARE

    CURSOR GenerateMonthlyStatements IS
        SELECT *
        FROM Transactions
        WHERE EXTRACT(MONTH FROM TransactionDate)
              = EXTRACT(MONTH FROM SYSDATE);

BEGIN

    FOR trans IN GenerateMonthlyStatements LOOP

        DBMS_OUTPUT.PUT_LINE(
            'Transaction ID: ' ||
            trans.TransactionID ||
            ' Account ID: ' ||
            trans.AccountID ||
            ' Amount: ' ||
            trans.Amount
        );

    END LOOP;

END;
/

-- Scenario 2
DECLARE

    CURSOR ApplyAnnualFee IS
        SELECT AccountID
        FROM Accounts;

BEGIN

    FOR acc IN ApplyAnnualFee LOOP

        UPDATE Accounts
        SET Balance = Balance - 100
        WHERE AccountID = acc.AccountID;

    END LOOP;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE(
        'Annual Fee Applied Successfully'
    );

END;
/

-- Scenario 3
DECLARE

    CURSOR UpdateLoanInterestRates IS
        SELECT LoanID,
               InterestRate
        FROM Loans;

BEGIN

    FOR loan IN UpdateLoanInterestRates LOOP

        UPDATE Loans
        SET InterestRate =
            loan.InterestRate + 0.5
        WHERE LoanID = loan.LoanID;

    END LOOP;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE(
        'Loan Interest Rates Updated'
    );

END;
/