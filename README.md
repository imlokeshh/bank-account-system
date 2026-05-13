# 💰 Bank Account Management System

A console-based banking application built with Core Java, JDBC, and MySQL.
Supports real-time database operations with full CRUD functionality and custom exception handling.

## 🛠️ Tech Stack
- **Language:** Java
- **Database:** MySQL
- **Connectivity:** JDBC (Java Database Connectivity)
- **Concepts:** OOP, Exception Handling, Collections, PreparedStatement

## ✨ Features
- ✅ Create new bank accounts
- ✅ Deposit money into accounts
- ✅ Withdraw money with insufficient balance protection
- ✅ Display all accounts with real-time balance
- ✅ Interactive menu-driven interface
- ✅ Custom exception: `InsufficientBalanceException`
- ✅ Data persists in MySQL database across sessions

## 🗄️ Database Setup
```sql
CREATE DATABASE bankdb;
USE bankdb;

CREATE TABLE accounts (
    account_number INT PRIMARY KEY,
    holder_name VARCHAR(100),
    balance DOUBLE
);
```

## ⚙️ How to Run
1. Clone this repository
```
git clone https://github.com/imlokeshh/bank-account-system.git
```
2. Open in Eclipse or VS Code
3. Add `mysql-connector-j-9.7.0.jar` to your build path
4. Update database credentials in `BankAccount.java`:
```java
static final String URL = "jdbc:mysql://localhost:3306/bankdb";
static final String USER = "root";
static final String PASSWORD = "your_password";
```
5. Run `BankAccount.java`

## 📸 Sample Output
```
===== BANK ACCOUNT SYSTEM =====
1. Add Account
2. Display All Accounts
3. Deposit Money
4. Withdraw Money
5. Exit
Enter your choice: 1
Enter Account Number: 1001
Enter Holder Name: Lokesh
Enter Initial Balance: 10000
Account created for: Lokesh

Transaction failed: Insufficient balance! Available: Rs.4000.0, Requested: Rs.99999.0
```

## 👨‍💻 Author
**Lokesh Kurumula**
- GitHub: [@imlokeshh](https://github.com/imlokeshh)
- LinkedIn: [lokesh-kurumula](https://www.linkedin.com/in/lokesh-kurumula)
