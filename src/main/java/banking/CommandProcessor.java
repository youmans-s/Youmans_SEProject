package banking;

public class CommandProcessor {
    Bank bank;

    public CommandProcessor(Bank bank) {
        this.bank = bank;

    }

    public void processCommand(String command) {
        String[] splitStr = command.split(" ");
        String action = splitStr[0].toLowerCase();

        switch (action) {
            case "create":
                this.bank.createAccount(splitStr[1].toLowerCase(), Integer.parseInt(splitStr[2]), Double.parseDouble(splitStr[3]));
                System.out.println("Created account");
                break;
            case "deposit":
                bank.deposit(Integer.parseInt(splitStr[1]), Double.parseDouble(splitStr[2]));
                System.out.println("Deposited into " + Integer.parseInt(splitStr[1]));
                break;
            case "withdraw":
                bank.withdraw(Integer.parseInt(splitStr[1]), Double.parseDouble(splitStr[2]));
                System.out.println("Withdrew from " + Integer.parseInt(splitStr[1]));
                break;
            case "transfer":
                bank.transfer(Integer.parseInt(splitStr[1]), Integer.parseInt(splitStr[2]), Double.parseDouble(splitStr[3]));
                break;
            case "pass":
                bank.passTime(Integer.parseInt(splitStr[1]));
                System.out.println("Passed " + Integer.parseInt(splitStr[1]) + "months.");
                break;

            default:
                throw new IllegalArgumentException("Illegal Command");


        }
    }

}
