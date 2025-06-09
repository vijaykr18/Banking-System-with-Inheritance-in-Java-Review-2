package ui;

import model.CurrentAccount;
import javax.swing.*;

public class CurrentAccountPanel extends AccountPanel {
    public CurrentAccountPanel(CurrentAccount account, UserDashboard dashboard) {
        super(account, dashboard);

        JLabel typeLabel = new JLabel("Account Type: Current");
        JLabel overdraftLabel = new JLabel("Overdraft Limit: $1,000.00");

        add(typeLabel);
        add(overdraftLabel);
    }
}