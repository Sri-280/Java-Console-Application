package ATM_Application;

import java.util.ArrayList;

public class User extends Accounts{
//    private final String id;
//    private String pin;
    private double balance;
//    private final ArrayList<Transactions> userTransactions = new ArrayList<>();

    public User(String id, String pin,double balance) {
        super(id,pin);
//        this.id = id;
//        this.pin = pin;
        this.balance = balance;
    }
//    public String getId() {
//        return id;
//    }
//    public String getPin() {
//        return pin;
//    }
    public double getBalance() {
        return balance;
    }
//    public void setPin(String newPin) {
//        this.pin = newPin;
//    }
    public void setBalance(double balance){this.balance= balance;}
//    public ArrayList<Transactions> getUserTransactions() {
//        return userTransactions;
//    }
    public void deposit(double amount) {
        balance += amount;
    }
}



