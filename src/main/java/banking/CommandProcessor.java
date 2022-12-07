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
                String type = splitStr[1].toLowerCase();
                int id = Integer.parseInt(splitStr[2]);
                Double apr = Double.parseDouble(splitStr[3]);
                double startingAmount = -1;
                if (splitStr.length == 4) {
                    this.bank.createAccount(type, id, apr);
                } else if (splitStr.length == 5) {
                    startingAmount = Double.parseDouble(splitStr[4]);
                    this.bank.createAccount(type, id, apr, startingAmount);
                }
                break;
            case "deposit":
                this.bank.deposit(Integer.parseInt(splitStr[1]), Double.parseDouble(splitStr[2]));
                break;
            case "withdraw":
                this.bank.withdraw(Integer.parseInt(splitStr[1]), Double.parseDouble(splitStr[2]));
                break;
            case "transfer":
                this.bank.transfer(Integer.parseInt(splitStr[1]), Integer.parseInt(splitStr[2]), Double.parseDouble(splitStr[3]));
                break;
            case "pass":
                this.bank.passTime(Integer.parseInt(splitStr[1]));
                break;
            default:
                throw new IllegalArgumentException("Illegal Command");
        }
    }

}
