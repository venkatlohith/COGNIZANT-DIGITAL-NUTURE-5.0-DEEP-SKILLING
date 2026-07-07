-- Scenario 1
DECLARE
    CURSOR c_customers IS
        SELECT CustomerID,
               FLOOR(MONTHS_BETWEEN(SYSDATE,DOB)/12) Age
        FROM Customers;
BEGIN
    FOR cust IN c_customers LOOP

        IF cust.Age > 60 THEN
            UPDATE Loans
            SET InterestRate = InterestRate - 1
            WHERE CustomerID = cust.CustomerID;
        END IF;

    END LOOP;

    COMMIT;
END;
/

-- Scenario 2
ALTER TABLE Customers
ADD IsVIP VARCHAR2(5);

BEGIN
    FOR cust IN
    (SELECT CustomerID,Balance FROM Customers)
    LOOP

        IF cust.Balance > 10000 THEN
            UPDATE Customers
            SET IsVIP='TRUE'
            WHERE CustomerID=cust.CustomerID;
        END IF;

    END LOOP;

    COMMIT;
END;
/

-- Scenario 3
BEGIN

    FOR loan IN
    (
        SELECT CustomerID,
               LoanID,
               EndDate
        FROM Loans
        WHERE EndDate BETWEEN
              SYSDATE AND SYSDATE+30
    )
    LOOP

        DBMS_OUTPUT.PUT_LINE(
        'Reminder: Customer '
        ||loan.CustomerID||
        ' Loan Due Soon');

    END LOOP;

END;
/