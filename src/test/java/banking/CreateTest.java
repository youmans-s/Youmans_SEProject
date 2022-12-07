package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateTest {
    Bank bank;
    CommandValidator cv;
    CommandProcessor cp;

    @BeforeEach
    void setup() {
        bank = new Bank();
        cv = new CommandValidator(bank);
        cp = new CommandProcessor(bank);
    }

    // Test that account type is Checking, Savings, or CD, or else invalid

    @Test
    void validAccountTypeChecking() {
        String a = "create checking 12345678 3.4";
        assertTrue(cv.validate(a));
        cp.processCommand(a);
        assertEquals(AccountType.CHECKING, cp.bank.getAccountByID(12345678).type);
    }

    @Test
    void validAccountTypeSavings() {
        String a = "create savings 12345678 3.4";
        assertTrue(cv.validate(a));
        cp.processCommand(a);
        assertEquals(AccountType.SAVINGS, cp.bank.getAccountByID(12345678).type);
    }

    @Test
    void validAccountTypeCD() {
        String a = "create cd 12345678 3.4 3000";
        assertTrue(cv.validate(a));
        cp.processCommand(a);
        assertEquals(AccountType.CD, cp.bank.getAccountByID(12345678).type);
    }

    @Test
    void invalidAccountType() {
        // Test create without CD argument
        String a = "create test 12345678 3.4";
        assertFalse(cv.validate(a));

        // Test create with CD argument
        String b = "create test 12345678 3.4 3000";
        assertFalse(cv.validate(b));
    }

    @Test
    void validIDFormat() {
        // Less than 8 digits
        assertFalse(cv.validate("create checking 1234567 3.4"));

        // More than 8 digits
        assertFalse(cv.validate("create checking 123456789 3.4"));
    }

    @Test
    void notDuplicateID() {
        cp.processCommand("create checking 12345678 3.4");
        assertFalse(cv.validate("create savings 12345678 3.4"));
    }

    @Test
    void validAPR() {
        // APR less than 0
        String a = "create checking 12345678 -0.5";
        assertFalse(cv.validate(a));

        // APR greater than 10
        String b = "create checking 12345678 11";
        assertFalse(cv.validate(b));
    }

    @Test
    void checkingAndSavingsOpenWithZeroBalance() {
        cp.processCommand("create checking 12345678 2.5");
        cp.processCommand("create savings 12345679 2.5");
        assertEquals(0, cp.bank.getAccountByID(12345678).balance);
        assertEquals(0, cp.bank.getAccountByID(12345679).balance);
    }

    @Test
    void validCDInitialValue() {
        String a = "create cd 12345678 5 2000";
        assertTrue(cv.validate(a));

        // Balance below minimum of 1000
        String b = "create cd 12345678 5 999";
        assertFalse(cv.validate(b));

        // Balance above maximum of 10000
        String c = "create cd 12345678 5 10001";
        assertFalse(cv.validate(c));
    }
}
