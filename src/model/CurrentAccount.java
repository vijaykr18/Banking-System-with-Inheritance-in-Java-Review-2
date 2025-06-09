package model;

import exceptions.InsufficientFundsException;

public class CurrentAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 1000.0;

    public CurrentAccount(String accountHolder, String accountNumber) {
        super(accountHolder, accountNumber);
    }

    @Override
    public void withdraw(double amount, String description) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (balance - amount < -OVERDRAFT_LIMIT) {
            throw new InsufficientFundsException(
                    String.format("Cannot withdraw $%.2f - overdraft limit of $%.2f exceeded",
                            amount, OVERDRAFT_LIMIT)
            );
        }
        balance -= amount;
        transactions.add(new Transaction("WITHDRAWAL", -amount, description));
    }
}