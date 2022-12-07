package banking;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Bank {
    ArrayList<Account> accountList = new ArrayList<>();

    public Account createAccount(String type, int id, double apr) {
        String accType = type.toLowerCase();
        Account acc = null;
        if (accType.equals("checking")) {
            acc = new Account(id, apr);
            acc.type = AccountType.CHECKING;
            accountList.add(acc);
        } else if (accType.equals("savings")) {
            acc = new Account(id, apr);
            acc.type = AccountType.SAVINGS;
            accountList.add(acc);
        }

        System.out.println("Created " + accType + " account with ID: " + id);
        return acc;
    }

    public Account createAccount(String type, int id, double apr, double startingAmount) {
        String accType = type.toLowerCase();
        Account cd = new Account(id, apr, startingAmount);
        cd.type = AccountType.CD;
        accountList.add(cd);
        System.out.println("Created " + accType + " account with ID: " + id);
        return cd;
    }

    public void deposit(int id, double amount) {
        getAccountByID(id).balance += amount;
        System.out.println("Deposited " + amount + " into " + id);
    }

    public void withdraw(int id, double amount) {
        if (amount > getAccountByID(id).balance) {
            getAccountByID(id).balance = 0;
        } else {
            getAccountByID(id).balance -= amount;
        }
        getAccountByID(id).withdrewThisMonth = true;
        if(getAccountByID(id).type == AccountType.CD){
            getAccountByID(id).noMoreWithdrawals = true;
        }
        System.out.println("Withdrew " + amount + " from " + id);
    }

    public void transfer(int fromID, int toID, double amount) {
        getAccountByID(fromID).balance -= amount;
        getAccountByID(toID).balance += amount;

        System.out.println("Transferred " + amount + " from " + fromID + " to " + toID);
    }

    public void passTime(int months) {

        for (int i = 0; i < months; i++) {
            for (int j = 0; j < this.accountList.size(); j++) {
                this.accountList.get(j).age ++;
                this.accountList.get(j).withdrewThisMonth = false;
                if (this.accountList.get(j).balance < 100 && this.accountList.get(j).balance > 0) {
                    this.accountList.get(j).balance -= 25;
                }
                if (this.accountList.get(j).balance <= 0) {
                    System.out.println("Removing account " + this.accountList.get(j).id + " in coming pass");
                    this.accountList.remove(j);
                }
            }
            calculateAPR();

        }
        System.out.println("Passed " + months + " months");
    }

    public void calculateAPR() {
        DecimalFormat f = new DecimalFormat("##.00");
        for (Account acc : accountList) {
            double dec = acc.apr / 100;
            dec /= 12;
            if (acc.type == AccountType.CD) {
                acc.balance += (acc.balance * dec);
                acc.balance += (acc.balance * dec);
                acc.balance += (acc.balance * dec);
                acc.balance += (acc.balance * dec);
                acc.balance = Double.parseDouble(f.format(acc.balance));
            } else {
                acc.balance += (acc.balance * dec);
            }
        }
    }


    public Account getAccountByID(int id) {
        for (Account acc : accountList) {
            if (acc.id == id) {
                return acc;
            }
        }
        return null;
    }
}
