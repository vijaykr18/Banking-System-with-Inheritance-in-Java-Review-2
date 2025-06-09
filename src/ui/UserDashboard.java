package ui;

import model.User;
import model.Account;
import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {
    private User user;
    private JTabbedPane accountTabs;

    public UserDashboard(User user) {
        this.user = user;

        setTitle("Java Bank - Welcome " + user.getUsername());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Account tabs
        accountTabs = new JTabbedPane();
        refreshAccounts();

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu accountMenu = new JMenu("Account");

        JMenuItem createAccountItem = new JMenuItem("Create New Account");
        createAccountItem.addActionListener(e -> showCreateAccountDialog());

        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> {
            this.dispose();
            new BankLoginUI().setVisible(true);
        });

        accountMenu.add(createAccountItem);
        accountMenu.addSeparator();
        accountMenu.add(logoutItem);
        menuBar.add(accountMenu);

        setJMenuBar(menuBar);
        mainPanel.add(accountTabs, BorderLayout.CENTER);
        add(mainPanel);
    }

    public void refreshAccounts() {
        accountTabs.removeAll();

        if (user.getAccounts().isEmpty()) {
            accountTabs.addTab("No Accounts", new JLabel("You don't have any accounts yet."));
        } else {
            for (Account account : user.getAccounts()) {
                if (account instanceof model.SavingsAccount) {
                    accountTabs.addTab(account.getAccountNumber(),
                            new SavingsAccountPanel((model.SavingsAccount)account, this));
                } else {
                    accountTabs.addTab(account.getAccountNumber(),
                            new CurrentAccountPanel((model.CurrentAccount)account, this));
                }
            }
        }

        accountTabs.addTab("Transactions", new TransactionPanel(user, this));
    }

    private void showCreateAccountDialog() {
        JDialog dialog = new JDialog(this, "Create New Account", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new GridLayout(3, 1));

        JLabel titleLabel = new JLabel("Select Account Type:", JLabel.CENTER);
        JButton savingsButton = new JButton("Savings Account");
        JButton currentButton = new JButton("Current Account");

        savingsButton.addActionListener(e -> {
            createAccount("Savings");
            dialog.dispose();
        });

        currentButton.addActionListener(e -> {
            createAccount("Current");
            dialog.dispose();
        });

        dialog.add(titleLabel);
        dialog.add(savingsButton);
        dialog.add(currentButton);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void createAccount(String accountType) {
        String accountHolder = JOptionPane.showInputDialog(this,
                "Enter account holder name:", user.getUsername());

        if (accountHolder != null && !accountHolder.trim().isEmpty()) {
            Account newAccount;
            if (accountType.equals("Savings")) {
                newAccount = new model.SavingsAccount(accountHolder,
                        "SAV" + (user.getAccounts().size() + 1));
            } else {
                newAccount = new model.CurrentAccount(accountHolder,
                        "CUR" + (user.getAccounts().size() + 1));
            }

            user.addAccount(newAccount);
            refreshAccounts();
            JOptionPane.showMessageDialog(this,
                    "Account created successfully!\nAccount Number: " + newAccount.getAccountNumber());
        }
    }
}