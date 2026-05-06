package project2;

import java.util.ArrayList;

class Account {
    int accountNumber;
    String holderName;
    double balance;

    public Account(int accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public void displayInfo() {
        System.out.println("Account No: " + accountNumber);
        System.out.println("Holder: " + holderName);
        System.out.println("Balance: ₹" + balance);
        System.out.println("-------------------");
    }
}

public class BankAccount {

    ArrayList<Account> accounts = new ArrayList<>();

    // Add account
    public void addAccount(int accountNumber, String holderName, double balance) {
        Account a = new Account(accountNumber, holderName, balance);
        accounts.add(a);
        System.out.println("Account created for: " + holderName);
    }

    // Deposit money
    public void deposit(int accountNumber, double amount) {
        for (Account a : accounts) {
            if (a.accountNumber == accountNumber) {
                a.balance += amount;
                System.out.println("Deposited ₹" + amount + " to " + a.holderName);
                return;
            }
        }
        System.out.println("Account not found.");
    }

    // Withdraw money
    public void withdraw(int accountNumber, double amount) {
        for (Account a : accounts) {
            if (a.accountNumber == accountNumber) {
                if (a.balance < amount) {
                    System.out.println("Insufficient balance!");
                } else {
                    a.balance -= amount;
                    System.out.println("Withdrawn ₹" + amount + " from " + a.holderName);
                }
                return;
            }
        }
        System.out.println("Account not found.");
    }

    // Display all accounts
    public void displayAll() {
        System.out.println("===== All Accounts =====");
        for (Account a : accounts) {
            a.displayInfo();
        }
    }

    public static void main(String[] args) {
        BankAccount bank = new BankAccount();

        bank.addAccount(1001, "Lokesh", 10000);
        bank.addAccount(1002, "Rahul", 5000);

        bank.displayAll();

        bank.deposit(1001, 2000);
        bank.withdraw(1002, 1000);
        bank.withdraw(1002, 9000);

        bank.displayAll();
    }
}