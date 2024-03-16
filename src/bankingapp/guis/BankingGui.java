package bankingapp.guis;

import bankingapp.dao.*;

import javax.swing.*;
import java.awt.*;

public class BankingGui extends BaseFrame {
    private JTextField currentBalanceTextField;

    public void setCurrentBalanceTextField(JTextField currentBalanceTextField) {
        this.currentBalanceTextField = currentBalanceTextField;
    }

    public JTextField getCurrentBalanceTextField() {
        return currentBalanceTextField;
    }


    public BankingGui(User user) {
        super("Banking app gui", user);
    }

    @Override
    protected void addGuiComponents() {
        String welcomeMessage = "<html>" +
                "<body style='text-align:center'>" +
                "<b>Hello " + user.getName() + "</b><br>" +
                "What would you like to do today? </body></html>";

        JLabel welcomeMessageLabel = new JLabel(welcomeMessage);
        welcomeMessageLabel.setBounds(0, 20, getWidth() - 10, 40);
        welcomeMessageLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        welcomeMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeMessageLabel);

        JLabel currentBalanceLabel = new JLabel("Current balance");
        currentBalanceLabel.setBounds(0, 50, getWidth() - 10, 40);
        currentBalanceLabel.setFont(new Font("Dialog", Font.BOLD, 15));
        currentBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(currentBalanceLabel);


        currentBalanceTextField = new JTextField("$" + user.getCurrentBalance());
        currentBalanceTextField.setBounds(15, 80, getWidth() - 50, 40);
        currentBalanceTextField.setFont(new Font("Dialog", Font.BOLD, 15));
        currentBalanceTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        currentBalanceTextField.setEditable(false);
        add(currentBalanceTextField);

        JButton depositButton = new JButton("Deposit");

        depositButton.setBounds(15, 140, getWidth() - 50, 40);
        depositButton.setFont(new Font("Dialog", Font.BOLD, 15));
        depositButton.setHorizontalAlignment(SwingConstants.CENTER);
        //depositButton.addActionListener(this);
        depositButton.addActionListener(e -> {
            BankingAppDialog dialog = addBankingDialog(e.getActionCommand());
            dialog.setVisible(true);
        });
        add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(15, 200, getWidth() - 50, 40);
        withdrawButton.setFont(new Font("Dialog", Font.BOLD, 15));
        withdrawButton.setHorizontalAlignment(SwingConstants.CENTER);
        //withdrawButton.addActionListener(this);
        withdrawButton.addActionListener(e -> {
            BankingAppDialog dialog = addBankingDialog(e.getActionCommand());
            dialog.setVisible(true);
        });
        add(withdrawButton);

        JButton pastTransactionButton = new JButton("Past transaction");
        pastTransactionButton.setBounds(15, 260, getWidth() - 50, 40);
        pastTransactionButton.setFont(new Font("Dialog", Font.BOLD, 15));
        pastTransactionButton.setHorizontalAlignment(SwingConstants.CENTER);
        //pastTransactionButton.addActionListener(this);
        pastTransactionButton.addActionListener(e -> {
            BankingAppDialog dialog = addPastTransDialog(e.getActionCommand());
            dialog.setVisible(true);
        });
        add(pastTransactionButton);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(15, 320, getWidth() - 50, 40);
        transferButton.setFont(new Font("Dialog", Font.BOLD, 15));
        transferButton.setHorizontalAlignment(SwingConstants.CENTER);
        //transferButton.addActionListener(this);
        transferButton.addActionListener(e -> {
            BankingAppDialog dialog = addTransferDialog(e.getActionCommand());
            dialog.setVisible(true);
        });
        add(transferButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(15, 380, getWidth() - 50, 40);
        logoutButton.setFont(new Font("Dialog", Font.BOLD, 15));
        logoutButton.setHorizontalAlignment(SwingConstants.CENTER);
        //logoutButton.addActionListener(this);
        logoutButton.addActionListener(e -> {
            new LoginGui().setVisible(true);
            this.dispose();
        });
        add(logoutButton);


    }

    private BankingAppDialog addBankingDialog(String buttonPressed) {
        BankingAppDialog dialog = new BankingAppDialog(BankingGui.this, user);
        dialog.setTitle(buttonPressed);
        dialog.addCurrentBalanceAndAmount();
        dialog.addActionButton(buttonPressed);
        return dialog;

    }

    private BankingAppDialog addTransferDialog(String buttonPressed) {
        BankingAppDialog dialog = addBankingDialog(buttonPressed);
        dialog.addUserField();
        return dialog;
    }
    private BankingAppDialog addPastTransDialog(String buttonPressed) {
        BankingAppDialog dialog = new BankingAppDialog(this, user);
        dialog.setTitle(buttonPressed);
        dialog.addPastTransComponents();
        return dialog;
    }

    /*
    @Override
    public void actionPerformed(ActionEvent e) {

        String buttonPressed = e.getActionCommand();

        if (buttonPressed.equals("Logout")) {
            new LoginGui().setVisible(true);
            this.dispose();
            return;
        }

        BankingAppDialog bankingAppDialog = new BankingAppDialog(this, user);
        bankingAppDialog.setTitle(buttonPressed);

        if (buttonPressed.equalsIgnoreCase("Deposit") ||
                buttonPressed.equalsIgnoreCase("Withdraw") ||
                buttonPressed.equalsIgnoreCase("Transfer")) {

            bankingAppDialog.addCurrentBalanceAndAmount();
            bankingAppDialog.addActionButton(buttonPressed);


            if (buttonPressed.equalsIgnoreCase("Transfer")) {
                bankingAppDialog.addUserField();
            }

            bankingAppDialog.setVisible(true);
        } else if (buttonPressed.equalsIgnoreCase("Past transaction")) {
            //JOptionPane.showMessageDialog();
            bankingAppDialog.addPastTransComponents();
        }

        //bankingAppDialog.setVisible(true);
    }

     */
}
