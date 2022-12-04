import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateCommandTest {
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    // Test that account type is Checking, Savings, or CD, or else invalid

    @Test
    void validAccountTypeChecking(){
        Account ca = bank.createAccount("checking", 12345677, 3.4);
        assertEquals(AccountType.CHECKING, ca.type);
    }

    @Test
    void validAccountTypeSavings(){
        Account sa = bank.createAccount("savings", 12345677, 3.4);
        assertEquals(AccountType.SAVINGS, sa.type);
    }

    @Test
    void validAccountTypeCD(){
        Account cd = bank.createAccount("cd", 12345677, 3.4, 2000);
        assertEquals(AccountType.CD, cd.type);
    }

    @Test
    void invalidAccountType(){
        // Test create without CD argument
        assertThrows(IllegalArgumentException.class, () -> {
            bank.createAccount("test", 12345677, 3.4);
        });

        // Test create with CD argument
        assertThrows(IllegalArgumentException.class, () -> {
            bank.createAccount("test", 12345677, 3.4, 3000);
        });
    }

    @Test
    void validIDFormat(){
        // Less than 8 digits
        assertThrows(IllegalArgumentException.class, () -> {
           bank.createAccount("checking", 123779, 3.4);
        });

        // More than 8 digits
        assertThrows(IllegalArgumentException.class, () -> {
            bank.createAccount("checking", 123456779, 3.4);
        });
    }

    @Test
    void notDuplicateID(){
        Account test1 = bank.createAccount("checking", 12345677, 3.4);
        assertThrows(IllegalArgumentException.class, () -> {
            Account test2 = bank.createAccount("checking", 12345677, 3.4);
        });
    }

    @Test
    void validAPR(){
        // APR less than 0
        assertThrows(IllegalArgumentException.class, () -> {
            Account ca = bank.createAccount("checking", 12377900, -10);
        });

        // APR greater than 10
        assertThrows(IllegalArgumentException.class, () -> {
            Account ca = bank.createAccount("checking", 12377900, 11);
        });
    }

    @Test
    void checkingAndSavingsOpenWithZeroBalance(){
        Account ca = bank.createAccount("checking", 12300000, 4);
        Account sa = bank.createAccount("savings", 12400000, 4);
        assertEquals(0, ca.balance);
        assertEquals(0, sa.balance);
    }

    @Test
    void validCDInitialValue(){
        Account cd = bank.createAccount("cd", 12345678, 5, 2000);
        // Balance below minimum of 1000
        assertThrows(IllegalArgumentException.class, () -> {
            Account test1 = bank.createAccount("cd", 12345678, 5, 500);
        });
        // Balance above maximum of 10000
        assertThrows(IllegalArgumentException.class, () -> {
            Account test2 = bank.createAccount("cd", 12345678, 5, 12000);
        });
    }
}
