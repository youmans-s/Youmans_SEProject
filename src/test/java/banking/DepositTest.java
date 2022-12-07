package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DepositTest {
    Bank bank;
    CommandValidator cv;
    CommandProcessor cp;

    @BeforeEach
    void setup() {
        bank = new Bank();
        cv = new CommandValidator(bank);
        cp = new CommandProcessor(bank);
    }


    @Test
    void depositIntoCheckingAccount() {
        String a = "Create checking 12345678 3.4";
        assertTrue(cv.validate(a));
        cp.processCommand(a);
        String b = "deposit 12345678 100";
        assertTrue(cv.validate(b));
        cp.processCommand(b);
        assertEquals(100, cp.bank.getAccountByID(12345678).balance);
    }

    @Test
    void depositIntoSavingsAccount() {
        String a = "Create savings 12345678 3.4";
        assertTrue(cv.validate(a));
        cp.processCommand(a);
        String b = "deposit 12345678 100";
        assertTrue(cv.validate(b));
        cp.processCommand(b);
        assertEquals(100, cp.bank.getAccountByID(12345678).balance);
    }

    @Test
    void checkingMaximumDeposit(){
        String a = "Create checking 12345678 3.4";
        assertTrue(cv.validate(a));
        cp.processCommand(a);
        String b = "deposit 12345678 3000";
        assertFalse(cv.validate(b));
    }

    @Test
    void savingsMaximumDeposit(){
        String a = "Create savings 12345678 3.4";
        assertTrue(cv.validate(a));
        cp.processCommand(a);
        String b = "deposit 12345678 3000";
        assertFalse(cv.validate(b));
    }


    @Test
    void cannotDepositIntoCDAccount(){
        String a = "Create cd 12345678 3.4 5000";
        assertTrue(cv.validate(a));
        cp.processCommand(a);
        String b = "deposit 12345678 1000";
        assertFalse(cv.validate(b));
    }
}
