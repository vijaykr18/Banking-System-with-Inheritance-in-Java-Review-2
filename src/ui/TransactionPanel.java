package ui;

import model.User;
import model.Account;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionPanel extends JPanel {
    private User user;
    private UserDashboard dashboard;
    private JComboBox<String> accountComboBox;
    private JComboBox<String> transactionTypeComboBox;
    private JTextField amountField;
    private JTextField recipientField;

    public TransactionPanel(User user, UserDashboard dashboard) {
        this.user = user;
        this.dashboard = dashboard;
        setLayout(new GridLayout(6, 2, 5, 5));

        initComponents();
    }

    private void initComponents() {
        // Account selection
        add(new JLabel("Select Account:"));
        accountComboBox = new JComboBox<>();
        refreshAccountComboBox();
        add(accountComboBox);

        // Transaction type
        add(new JLabel("Transaction Type:"));
        transactionTypeComboBox = new JComboBox<>(new String[]{"Deposit", "Withdraw", "Transfer"});
        transactionTypeComboBox.addActionListener(e -> toggleRecipientField());
        add(transactionTypeComboBox);

        // Amount
        add(new JLabel("Amount ($):"));
        amountField = new JTextField();
        add(amountField);

        // Recipient (initially hidden)
        add(new JLabel("Recipient Account:"));
        recipientField = new JTextField();
        recipientField.setVisible(false);
        add(recipientField);

        // Buttons
        JButton executeButton = new JButton("Execute Transaction");
        executeButton.addActionListener(e -> executeTransaction());
        add(executeButton);

        JButton refreshButton = new JButton("Refresh Accounts");
        refreshButton.addActionListener(e -> {
            refreshAccountComboBox();
            dashboard.refreshAccounts();
        });
        add(refreshButton);
    }

    private void toggleRecipientField() {
        String selected = (String) transactionTypeComboBox.getSelectedItem();
        recipientField.setVisible("Transfer".equals(selected));
        revalidate();
        repaint();
    }

    private void refreshAccountComboBox() {
        accountComboBox.removeAllItems();
        for (Account account : user.getAccounts()) {
            accountComboBox.addItem(account.getAccountNumber() + " (" + account.getClass().getSimpleName() + ")");
        }
    }

    private void executeTransaction() {
        if (user.getAccounts().isEmpty()) {
            JOptionPane.showMessageDialog(this, "You don't have any accounts!");
            return;
        }

        try {
            String selectedAccount = (String) accountComboBox.getSelectedItem();
            String accountNumber = selectedAccount.split(" ")[0];
            Account account = findAccount(accountNumber);

            String transactionType = (String) transactionTypeComboBox.getSelectedItem();
            double amount = Double.parseDouble(amountField.getText());

            switch (transactionType) {
                case "Deposit":
                    account.deposit(amount, "Manual deposit");
                    break;
                case "Withdraw":
                    account.withdraw(amount, "Manual withdrawal");
                    break;
                case "Transfer":
                    String recipientAccountNumber = recipientField.getText();
                    Account recipientAccount = findAccount(recipientAccountNumber);
                    if (recipientAccount != null) {
                        account.transfer(recipientAccount, amount, "Transfer to " + recipientAccountNumber);
                    } else {
                        JOptionPane.showMessageDialog(this, "Recipient account not found!");
                        return;
                    }
                    break;
            }

            dashboard.refreshAccounts();
            JOptionPane.showMessageDialog(this, "Transaction completed successfully!");
            amountField.setText("");
            recipientField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private Account findAccount(String accountNumber) {
        for (Account account : user.getAccounts()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}