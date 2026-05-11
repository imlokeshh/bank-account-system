package project2;
import java.util.Scanner;
import java.sql.*;
import java.util.ArrayList;

// Custom Exception
class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

// Account class
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
        System.out.println("Balance: Rs." + balance);
        System.out.println("-------------------");
    }
}

public class BankAccount {

    // Database connection details
    static final String URL = "jdbc:mysql://localhost:3306/bankdb";
    static final String USER = "root";
    static final String PASSWORD = "Admin1234";

    // Get database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Add account to database
    public void addAccount(int accountNumber, String holderName, double balance) {
        String query = "INSERT INTO accounts VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, accountNumber);
            ps.setString(2, holderName);
            ps.setDouble(3, balance);
            ps.executeUpdate();
            System.out.println("Account created for: " + holderName);
        } catch (SQLException e) {
            System.out.println("Error adding account: " + e.getMessage());
        }
    }

    // Display all accounts from database
    public void displayAll() {
        String query = "SELECT * FROM accounts";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            System.out.println("===== All Accounts =====");
            while (rs.next()) {
                System.out.println("Account No: " + rs.getInt("account_number"));
                System.out.println("Holder: " + rs.getString("holder_name"));
                System.out.println("Balance: Rs." + rs.getDouble("balance"));
                System.out.println("-------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error displaying accounts: " + e.getMessage());
        }
    }

    // Deposit money
    public void deposit(int accountNumber, double amount) {
        String query = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDouble(1, amount);
            ps.setInt(2, accountNumber);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Deposited Rs." + amount + " successfully!");
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error depositing: " + e.getMessage());
        }
    }

    // Withdraw money
    public void withdraw(int accountNumber, double amount) 
        throws InsufficientBalanceException {
        String checkQuery = "SELECT balance FROM accounts WHERE account_number = ?";
        String updateQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement checkPs = conn.prepareStatement(checkQuery)) {
            checkPs.setInt(1, accountNumber);
            ResultSet rs = checkPs.executeQuery();
            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                if (currentBalance < amount) {
                    throw new InsufficientBalanceException(
                        "Insufficient balance! Available: Rs." + currentBalance + 
                        ", Requested: Rs." + amount
                    );
                }
                try (PreparedStatement updatePs = conn.prepareStatement(updateQuery)) {
                    updatePs.setDouble(1, amount);
                    updatePs.setInt(2, accountNumber);
                    updatePs.executeUpdate();
                    System.out.println("Withdrawn Rs." + amount + " successfully!");
                }
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error withdrawing: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        BankAccount bank = new BankAccount();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== BANK ACCOUNT SYSTEM =====");
            System.out.println("1. Add Account");
            System.out.println("2. Display All Accounts");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Account Number: ");
                    int accNum = scanner.nextInt();
                    System.out.print("Enter Holder Name: ");
                    String name = scanner.next();
                    System.out.print("Enter Initial Balance: ");
                    double balance = scanner.nextDouble();
                    bank.addAccount(accNum, name, balance);
                    break;

                case 2:
                    bank.displayAll();
                    break;

                case 3:
                    System.out.print("Enter Account Number: ");
                    int depAcc = scanner.nextInt();
                    System.out.print("Enter Amount to Deposit: ");
                    double depAmount = scanner.nextDouble();
                    bank.deposit(depAcc, depAmount);
                    break;

                case 4:
                    System.out.print("Enter Account Number: ");
                    int witAcc = scanner.nextInt();
                    System.out.print("Enter Amount to Withdraw: ");
                    double witAmount = scanner.nextDouble();
                    try {
                        bank.withdraw(witAcc, witAmount);
                    } catch (InsufficientBalanceException e) {
                        System.out.println("Transaction failed: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Thank you for using Bank Account System!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 5);
        scanner.close();
    }
    
}