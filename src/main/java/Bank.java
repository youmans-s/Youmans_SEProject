import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class Bank {

    ArrayList<Account> accountList = new ArrayList<>();


    public Account createAccount(String type, int id, double apr){
        isLegalAccount(id, apr);
        String accType = type.toLowerCase();
        switch (accType){
            case "checking":
                Account ca = new Account(id, apr);
                ca.type = AccountType.CHECKING;
                accountList.add(ca);
                return ca;
            case "savings":
                Account sa = new Account(id, apr);
                sa.type = AccountType.SAVINGS;
                accountList.add(sa);
                return sa;
            default:
                throw new IllegalArgumentException("Illegal account type for given arguments");
        }
    }

    public Account createAccount(String type, int id, double apr, double startingAmount){
        isLegalAccount(id, apr);
        String accType = type.toLowerCase();
        if(accType.equals("cd")){
            Account cd = new Account(id, apr, startingAmount);
            cd.type = AccountType.CD;
            accountList.add(cd);
            return cd;
        }
        else{
            throw new IllegalArgumentException("Illegal account type for given arguments");
        }
    }

    public void deposit(int id, double amount){
        getAccountByID(id).balance += amount;
    }

    public void withdraw(int id, double amount){
        if(amount > getAccountByID(id).balance){
            getAccountByID(id).balance = 0;
        }
        else{
            getAccountByID(id).balance -= amount;
        }
    }

    public void transfer(int fromID, int toID, double amount){
        getAccountByID(fromID).balance -= amount;
        getAccountByID(toID).balance += amount;
    }

    public void passTime(int months){
        for(int i = 0; i < months; i++){
            for(Account acc : accountList){
                // If balance below 0, close account
                if(acc.balance ==  0){
                    accountList.remove(acc);
                }
                if(acc.balance < 100){
                    acc.balance -= 25;
                }
                calculateAPR();
            }
        }
    }

    public void calculateAPR(){
        for(Account acc : accountList){
            double dec = acc.apr / 100;
            dec /= 12;
            if(acc.type == AccountType.CD){
                acc.balance += (acc.balance * dec);
                acc.balance += (acc.balance * dec);
                acc.balance += (acc.balance * dec);
                acc.balance += (acc.balance * dec);
            }
            else{
                acc.balance += (acc.balance * dec);
            }
        }
    }



    public Account getAccountByID(int id){
        for(Account acc : accountList){
            if(acc.id == id){
                return acc;
            }
        }
        return null;
    }
    public int getIndexByID(int id){
        for(Account acc : accountList){
            if(acc.id == id){
                return accountList.indexOf(acc);
            }
        }
        return -1;
    }
    public void isLegalAccount(int id, double apr){
        // ID is 8 digits long
        if(String.valueOf(id).length() != 8){
            throw new IllegalArgumentException("Illegal ID");
        }

        // Valid APR value
        if(apr < 0 || apr > 10){
            throw new IllegalArgumentException("Illegal APR");
        }

        // ID is not duplicate
        for(Account a : accountList){
            if(a.id == id){
                throw new IllegalArgumentException("Duplicate ID");
            }
        }
    }
}
