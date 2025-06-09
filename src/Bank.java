import ui.BankLoginUI;

import javax.swing.*;

public class Bank {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BankLoginUI().setVisible(true);
        });
    }
}