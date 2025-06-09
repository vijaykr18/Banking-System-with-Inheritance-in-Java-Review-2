package model;

import exceptions.InsufficientFundsException;

public class SavingsAccount extends Account {
    private static final double INTEREST_RATE = 0.03;
    private static final double MIN_BALANCE = 100.0;

    public SavingsAccount(String accountHolder, String accountNumber) {
        super(accountHolder, accountNumber);
    }

    @Override
    public void withdraw(double amount, String description) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (balance - amount < MIN_BALANCE) {
            throw new InsufficientFundsException(
                    String.format("Cannot withdraw $%.2f - minimum balance of $%.2f must be maintained",
                            amount, MIN_BALANCE)
            );
        }
        balance -= amount;
        transactions.add(new Transaction("WITHDRAWAL", -amount, description));
    }

    public void applyInterest() {
        double interest = balance * INTEREST_RATE;
        deposit(interest, "Monthly Interest");
    }
}