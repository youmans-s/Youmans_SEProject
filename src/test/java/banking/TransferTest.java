package banking;

import org.junit.jupiter.api.BeforeEach;

public class TransferTest {
    Bank bank;
    CommandValidator cv;
    CommandProcessor cp;

    @BeforeEach
    void setup() {
        bank = new Bank();
        cv = new CommandValidator(bank);
        cp = new CommandProcessor(bank);
    }
}
