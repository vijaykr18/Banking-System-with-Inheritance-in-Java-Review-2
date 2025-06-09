package ui;

import model.SavingsAccount;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SavingsAccountPanel extends AccountPanel {
    private JButton applyInterestButton;

    public SavingsAccountPanel(SavingsAccount account, UserDashboard dashboard) {
        super(account, dashboard);

        JLabel typeLabel = new JLabel("Account Type: Savings");
        add(typeLabel);

        applyInterestButton = new JButton("Apply Monthly Interest");
        applyInterestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                account.applyInterest();
                updateBalance();
                JOptionPane.showMessageDialog(SavingsAccountPanel.this,
                        "Interest applied successfully!");
            }
        });

        add(applyInterestButton);
    }
}