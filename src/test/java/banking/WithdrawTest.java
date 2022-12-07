package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WithdrawTest {
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
    void withdrawFromCheckingAccount() {
        String a = "Create checking 12345678 3.4";
        assertTrue(cv.validate(a));
        cp.processCommand(a);
        String b = "deposit 12345678 600";
        assertTrue(cv.validate(b));
        cp.processCommand(b);
        String c = "withdraw 12345678 100";
        assertTrue(cv.validate(c));
        cp.processCommand(c);
        assertEquals(500, cp.bank.getAccountByID(12345678).balance);
    }

    @Test
    void withdrawFromSavingsAccount() {
        String a = "Create savings 12345678 3.4";
        assertTrue(cv.validate(a));
        cp.processCommand(a);
        String b = "deposit 12345678 600";
        assertTrue(cv.validate(b));
        cp.processCommand(b);
        String c = "withdraw 12345678 100";
        assertTrue(cv.validate(c));
        cp.processCommand(c);
        assertEquals(500, cp.bank.getAccountByID(12345678).balance);
    }

    @Test
    void noOverdraftGoToZero(){
        cp.processCommand("Create checking 12345678 3.4");
        cp.processCommand("deposit 12345678 200");
        String c = "withdraw 12345678 350";
        assertTrue(cv.validate(c));
        cp.processCommand(c);
        assertEquals(0, cp.bank.getAccountByID(12345678).balance);
    }

    @Test
    void checkingMaximumWithdrawal(){
        cp.processCommand("Create checking 12345678 3.4");
        cp.processCommand("deposit 12345678 1500");
        String c = "withdraw 12345678 500";
        assertFalse(cv.validate(c));
    }

    @Test
    void savingsMaximumWithdrawal(){
        cp.processCommand("Create savings 12345678 3.4");
        cp.processCommand("deposit 12345678 1500");
        String c = "withdraw 12345678 1100";
        assertFalse(cv.validate(c));
    }

    @Test
    void savingsOneWithdrawalPerMonth(){
        cp.processCommand("Create savings 12345678 3.4");
        cp.processCommand("deposit 12345678 1500");
        String c = "withdraw 12345678 100";
        assertTrue(cv.validate(c));
        cp.processCommand(c);
        String d = "withdraw 12345678 100";
        assertFalse(cv.validate(d));
    }

    @Test
    void cdWithdrawalTest(){
        cp.processCommand("Create cd 12345678 3.4 6000");
        String a = "withdraw 12345678 100";
        assertFalse(cv.validate(a));
        String b = "withdraw 12345678 6000";
        assertFalse(cv.validate(b));
        cp.processCommand("pass 12");
        String c = "withdraw 12345678 6872.77";
        assertTrue(cv.validate(c));
    }




}
