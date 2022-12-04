public class CommandValidator {
    Bank bank;
    public CommandValidator(Bank bank){
        this.bank = bank;

    }

    public boolean validate(String command){
        String[] splitStr = command.split(" ");
        switch(command){
            case "create":
                Account ac0 = bank.getAccountByID(Integer.parseInt(splitStr[1]));
                if(ac0.type == AccountType.CHECKING){
                    if(Integer.parseInt(splitStr[2]) > 1000 || Integer.parseInt(splitStr[2]) < 0){
                        throw new IllegalArgumentException("Illegal Deposit Amount");
                    }
                }
                else if(ac0.type == AccountType.SAVINGS){

                }
                else if(ac0.type == AccountType.CD){
                    if(Double.parseDouble(splitStr[2]) < 1000 || Double.parseDouble(splitStr[2]) > 10000){
                        throw new IllegalArgumentException("Illegal Starting Amount");
                    }
                }


                break;
            case "deposit":
                Account ac1 = bank.getAccountByID(Integer.parseInt(splitStr[1]));
                if(ac1.type == AccountType.CHECKING){
                    if(Integer.parseInt(splitStr[2]) > 1000 || Integer.parseInt(splitStr[2]) < 0){
                        throw new IllegalArgumentException("Illegal Deposit Amount");
                    }
                }
                else if(ac1.type == AccountType.SAVINGS){
                    if(Integer.parseInt(splitStr[2]) > 2500 || Integer.parseInt(splitStr[2]) < 0){
                        throw new IllegalArgumentException("Illegal Deposit Amount");
        }
                }
                else if(ac1.type == AccountType.CD){
                    throw new RuntimeException("Cannot deposit into CD account");
                }
                break;
            case "withdraw":
                Account ac2 = bank.getAccountByID(Integer.parseInt(splitStr[1]));
                if(ac2.type == AccountType.CHECKING){
                    if(Integer.parseInt(splitStr[2]) > 400 || Integer.parseInt(splitStr[2]) < 0){
                        throw new IllegalArgumentException("Illegal Deposit Amount");
                    }
                }
                else if(ac2.type == AccountType.SAVINGS){
                    if(Integer.parseInt(splitStr[2]) > 1000 || Integer.parseInt(splitStr[2]) < 0){
                     throw new IllegalArgumentException("Illegal Deposit Amount");
                      }
                }
                else if(ac2.type == AccountType.CD){
                    if(Integer.parseInt(splitStr[2]) != ac2.balance){
            throw new IllegalArgumentException("Illegal Deposit Amount");
        }
                }
                break;
            case "transfer":
                break;
            case "pass":

                break;

            default:
                throw new IllegalArgumentException("Illegal Command");
        }


        return false;




    }

}
