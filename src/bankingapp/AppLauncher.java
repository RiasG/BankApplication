package bankingapp;

import bankingapp.guis.LoginGui;

import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGui().setVisible(true);
//                new RegisterGui().setVisible(true);
//                new BankingGui(
//                        new User(1,"name","pass", new BigDecimal(20.00))
//                ).setVisible(true);
            }
        });
    }
}
