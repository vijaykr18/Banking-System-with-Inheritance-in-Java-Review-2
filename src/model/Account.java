package model;

import exceptions.InsufficientFundsException;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;
    protected List<Transaction> transactions;

    public Account(String accountHolder, String accountNumber) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public void deposit(double amount, String description) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
        transactions.add(new Transaction("DEPOSIT", amount, description));
    }

    public abstract void withdraw(double amount, String description) throws InsufficientFundsException;

    public void transfer(Account recipient, double amount, String description)
            throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        if (recipient == null) {
            throw new IllegalArgumentException("Recipient account cannot be null");
        }
        this.withdraw(amount, "Transfer to " + recipient.getAccountNumber());
        recipient.deposit(amount, "Transfer from " + this.getAccountNumber());
    }

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }
    public double getBalance() { return balance; }
    public List<Transaction> getTransactions() { return new ArrayList<>(transactions); }
}