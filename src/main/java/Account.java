public class Account {
    int id;
    double apr;
    double balance;
    AccountType type;

    public Account(int id, double apr) {
        this.id = id;
        this.apr = apr;
        balance = 0;
    }

    public Account(int id, double apr, double startingAmount) throws IllegalArgumentException {
        this.id = id;
        this.apr = apr;
        this.type = AccountType.CD;

        balance = startingAmount;
    }
}
