package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositCommandTest {
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }


    @Test
    void depositIntoCheckingAccount() {
        Account checking = bank.createAccount("checking", 12345677, 3.4);
        bank.deposit(checking.id, 100);
        assertEquals(100, checking.balance);
    }

    @Test
    void depositIntoSavingsAccount() {
        Account savings = bank.createAccount("checking", 12345677, 3.4);
        bank.deposit(savings.id, 100);
        assertEquals(100, savings.balance);
    }

//    @Test
//    void savingsMaximumDeposit(){
//        banking.Account savings = bank.createAccount("checking", 12345677, 3.4);
//        // Deposit more than maximum amount into savings
//        assertThrows(IllegalArgumentException.class, () -> {
//            bank.deposit(savings.id, 3000);
//        });
//    }
//
//    @Test
//    void checkingMaximumDeposit(){
//        banking.Account checking = bank.createAccount("checking", 12345677, 3.4);
//        assertThrows(IllegalArgumentException.class, () -> {
//            bank.deposit(checking.id, 1001);
//        });
//    }
//
//    @Test
//    void cannotDepositIntoCDAccount(){
//        banking.Account cd = bank.createAccount("cd", 12345677, 3.4, 5000);
//        assertThrows(RuntimeException.class, () -> {
//            bank.deposit(cd.id, 1000);
//        });
//    }
}
