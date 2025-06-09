package ui;

import model.Account;
import javax.swing.*;
import java.awt.*;

public abstract class AccountPanel extends JPanel {
    protected Account account;
    protected JLabel accountNumberLabel;
    protected JLabel accountHolderLabel;
    protected JLabel balanceLabel;
    protected UserDashboard dashboard;

    public AccountPanel(Account account, UserDashboard dashboard) {
        this.account = account;
        this.dashboard = dashboard;
        setLayout(new GridLayout(5, 1));
        setBorder(BorderFactory.createTitledBorder("Account Information"));

        accountNumberLabel = new JLabel("Account Number: " + account.getAccountNumber());
        accountHolderLabel = new JLabel("Account Holder: " + account.getAccountHolder());
        balanceLabel = new JLabel("Balance: $" + String.format("%.2f", account.getBalance()));

        add(accountNumberLabel);
        add(accountHolderLabel);
        add(balanceLabel);
    }

    public void updateBalance() {
        balanceLabel.setText("Balance: $" + String.format("%.2f", account.getBalance()));
        dashboard.refreshAccounts();
    }
}