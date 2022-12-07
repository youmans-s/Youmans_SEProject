package banking;

public class CommandValidator {
    Bank bank;
    boolean status;

    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        status = true;
        String[] splitStr = command.split(" ");
        String action = splitStr[0].toLowerCase();

        switch (action) {
            case "create":
                String type = splitStr[1].toLowerCase();
                int id = Integer.parseInt(splitStr[2]);
                double apr = Double.parseDouble(splitStr[3]);
                double startingAmount = -1;
                Account test = null;
                isLegalAccount(id, apr);


                if (splitStr.length == 4) {
                    test = new Account(id, apr);
                    if (type.equals("checking")) {
                        test.type = AccountType.CHECKING;
                    } else if (type.equals("savings")) {
                        test.type = AccountType.SAVINGS;
                    }

                } else if (splitStr.length == 5) {
                    if(type.equals("cd")){
                        startingAmount = Double.parseDouble(splitStr[4]);
                        test = new Account(id, apr, startingAmount);
                        test.type = AccountType.CD;
                    }
                    else{
                        return false;
                    }
                }

                if (test.type == AccountType.CHECKING) {

                } else if (test.type == AccountType.SAVINGS) {

                } else if (test.type == AccountType.CD) {
                    if (startingAmount < 1000 || startingAmount > 10000) {
                        System.out.println("Illegal Starting Amount");
                        status = false;
                    }
                } else {
                    System.out.println("Illegal account type given");
                    status = false;
                }
                break;
            case "deposit":
                Account ac1 = bank.getAccountByID(Integer.parseInt(splitStr[1]));
                if (ac1.type == AccountType.CHECKING) {
                    if (Double.parseDouble(splitStr[2]) > 1000 || Double.parseDouble(splitStr[2]) < 0) {
                        System.out.println("Illegal Deposit Amount");
                        status = false;
                    }
                } else if (ac1.type == AccountType.SAVINGS) {
                    if (Double.parseDouble(splitStr[2]) > 2500 || Double.parseDouble(splitStr[2]) < 0) {
                        System.out.println("Illegal Deposit Amount");
                        status = false;
                    }
                } else if (ac1.type == AccountType.CD) {
                    System.out.println("Cannot deposit into CD account");
                    status = false;
                }
                break;
            case "withdraw":
                Account ac2 = bank.getAccountByID(Integer.parseInt(splitStr[1]));


                if (ac2.type == AccountType.CHECKING) {
                    if (Double.parseDouble(splitStr[2]) > 400 || Double.parseDouble(splitStr[2]) < 0) {
                        System.out.println("Illegal Withdraw Amount");
                        status = false;
                    }
                } else if (ac2.type == AccountType.SAVINGS) {
                    if (Double.parseDouble(splitStr[2]) > 1000 || Double.parseDouble(splitStr[2]) < 0) {
                        System.out.println("Illegal Withdraw Amount");
                        status = false;
                    }
                    if (ac2.withdrewThisMonth){
                        System.out.println("Already withdrew maximum of 1 this month for savings");
                        status = false;
                    }
                } else if (ac2.type == AccountType.CD) {
                    if (Double.parseDouble(splitStr[2]) < ac2.balance) {
                        System.out.println("Illegal Withdraw Amount");
                        status = false;
                    }
                    if(ac2.age < 12){
                        System.out.println("Cannot withdraw from CD until 12 months");
                        status = false;
                    }
                    if(ac2.noMoreWithdrawals){
                        System.out.println("Maximum number of withdrawals reached");
                        status = false;
                    }
                }
                break;
            case "transfer":
                break;
            case "pass":
                break;
        }
        return status;
    }

    public void isLegalAccount(int id, double apr) {
        // ID is 8 digits long
        if (String.valueOf(id).length() != 8) {
            System.out.println("Illegal ID");
            status = false;
        }

        // Valid APR value
        if (apr < 0 || apr > 10) {
            System.out.println("Illegal APR");
            status = false;
        }

        // ID is not duplicate
        for (Account a : this.bank.accountList) {
            if (a.id == id) {
                System.out.println("Duplicate ID");
                status = false;
            }
        }
    }

}
