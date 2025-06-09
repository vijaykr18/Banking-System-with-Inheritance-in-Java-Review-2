package ui;

import model.CurrentAccount;
import model.SavingsAccount;
import model.User;
import javax.swing.*;
import java.awt.*;

public class BankLoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public BankLoginUI() {
        setTitle("Java Bank - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Java Bank Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> showRegistrationDialog());

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        mainPanel.add(titleLabel);
        mainPanel.add(formPanel);
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // In a real app, this would check against a database
        if (username.equals("admin") && password.equals("admin123")) {
            User user = new User(username, password);
            // Add sample accounts for demo
            user.addAccount(new SavingsAccount("Admin User", "SAV001"));
            user.addAccount(new CurrentAccount("Admin User", "CUR001"));

            this.dispose();
            new UserDashboard(user).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!");
        }
    }

    private void showRegistrationDialog() {
        JDialog dialog = new JDialog(this, "Register New User", true);
        dialog.setSize(350, 200);
        dialog.setLayout(new GridLayout(3, 2, 5, 5));
        dialog.setLocationRelativeTo(this);

        JTextField newUsernameField = new JTextField();
        JPasswordField newPasswordField = new JPasswordField();

        dialog.add(new JLabel("Username:"));
        dialog.add(newUsernameField);
        dialog.add(new JLabel("Password:"));
        dialog.add(newPasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            String username = newUsernameField.getText();
            String password = new String(newPasswordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Username and password cannot be empty!");
                return;
            }

            JOptionPane.showMessageDialog(dialog, "Registration successful! Please login.");
            dialog.dispose();
        });

        dialog.add(new JLabel());
        dialog.add(registerButton);
        dialog.setVisible(true);
    }
}