package bankingapp.guis;

import bankingapp.dao.BankJDBC;
import bankingapp.dao.Transaction;
import bankingapp.dao.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BankingAppDialog extends JDialog implements ActionListener {
    private User user;
    private BankingGui bankingGui;
    private JLabel balanceLabel, enterAmountLabel, enterUserLabel;
    private JTextField enterAmountField, enterUserField;
    private JButton actionButton;
    private JPanel pastTransactionPanel;
    private ArrayList<Transaction> pastTransactions;

    public BankingAppDialog(BankingGui bankingGui, User user) {
        this.user = user;
        this.bankingGui = bankingGui;

        setSize(400, 400);
        setModal(true);
        setLocationRelativeTo(bankingGui);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        //System.out.println(bankingGui.getCurrentBalanceTextField().getText() + " init bankDialog");

    }

    public void addCurrentBalanceAndAmount() {
        balanceLabel = new JLabel("Balance: $" + user.getCurrentBalance());
        balanceLabel.setBounds(0, 10, getWidth() - 20, 20);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        enterAmountLabel = new JLabel("Enter amount: ");
        enterAmountLabel.setBounds(0, 50, getWidth() - 20, 20);
        enterAmountLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountLabel);

        enterAmountField = new JTextField();
        enterAmountField.setBounds(15, 80, getWidth() - 40, 40);
        enterAmountField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterAmountField.setHorizontalAlignment(SwingConstants.RIGHT);
        add(enterAmountField);

    }

    public void addActionButton(String actionType) {
        actionButton = new JButton(actionType);
        actionButton.setBounds(15, 300, getWidth() - 40, 40);
        actionButton.setFont(new Font("Dialog", Font.BOLD, 20));
        actionButton.addActionListener(this);
        add(actionButton);
    }

    public void addUserField() {
        enterUserLabel = new JLabel("Enter user: ");

        enterUserLabel.setBounds(0, 160, getWidth() - 20, 20);
        enterUserLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserLabel);

        enterUserField = new JTextField();
        enterUserField.setBounds(15, 190, getWidth() - 50, 40);
        enterUserField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterUserField.setHorizontalAlignment(SwingConstants.RIGHT);
        add(enterUserField);

    }

    public void addPastTransComponents() {
        pastTransactionPanel = new JPanel();
        pastTransactionPanel.setLayout(new BoxLayout(pastTransactionPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(pastTransactionPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 20, getWidth() - 15, getHeight() - 60);

        pastTransactions = BankJDBC.getPasTransactions(user);
        for (int i = 0; i < pastTransactions.size(); i++) {
            JPanel pastTransContainer = new JPanel();
            pastTransContainer.setLayout(new BorderLayout());

            Transaction transaction = pastTransactions.get(i);

            JLabel pastTransLabel = new JLabel(transaction.getTransactionType());
            pastTransLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            JLabel amountTransLabel = new JLabel(String.valueOf(transaction.getTransactionAmount()));
            amountTransLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            JLabel dateTransLabel = new JLabel(String.valueOf(transaction.getTransactionDate()));
            dateTransLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            pastTransContainer.add(pastTransLabel, BorderLayout.WEST);
            pastTransContainer.add(amountTransLabel, BorderLayout.EAST);
            pastTransContainer.add(dateTransLabel, BorderLayout.SOUTH);

            pastTransContainer.setBackground(Color.WHITE);
            pastTransactionPanel.add(pastTransContainer);
        }
        add(scrollPane);
    }

    private void handleTransaction(String transactionType, float amountVal) {
        Transaction transaction;
        if (transactionType.equalsIgnoreCase("Deposit")) {
            user.setCurrentBalance(user.getCurrentBalance().add(new BigDecimal(amountVal)));
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(amountVal), LocalDate.now());
        } else {
            user.setCurrentBalance(user.getCurrentBalance().subtract(new BigDecimal(amountVal)));
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(-amountVal), LocalDate.now());
        }

        if (BankJDBC.addTransactionToDatabase(transaction) && BankJDBC.updateCurrentBalance(user)) {
            JOptionPane.showMessageDialog(this, transactionType + " Successfully");
            resetFieldsAndUpdateBalance();
        } else {
            JOptionPane.showMessageDialog(this, transactionType + " Failed");
        }

    }

    private void resetFieldsAndUpdateBalance() {
        enterAmountField.setText("");
        if (enterUserField != null) {
            enterUserField.setText("");
        }

        balanceLabel.setText("Balance: $" + user.getCurrentBalance());
        bankingGui.getCurrentBalanceTextField().setText("");
        bankingGui.getCurrentBalanceTextField().setText("$" + user.getCurrentBalance());


    }

    private void handleTransfer(User user, String transferredUser, float amount) {
        if (BankJDBC.transfer(user, transferredUser, amount)) {
            JOptionPane.showMessageDialog(this, "Transfer success");
            resetFieldsAndUpdateBalance();
        } else {
            JOptionPane.showMessageDialog(this, "Transfer failed");
        }


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPress = e.getActionCommand();
        float amountVal = Float.parseFloat(enterAmountField.getText());

        if (buttonPress.equalsIgnoreCase("Deposit")) {
            handleTransaction(buttonPress, amountVal);
            return;
        } else {
            int result = user.getCurrentBalance().compareTo(BigDecimal.valueOf(amountVal));
            if (result < 0) {
                JOptionPane.showMessageDialog(this, "Error: Input value is more than current balance");
                return;
            }

        }

        if (buttonPress.equalsIgnoreCase("Withdraw")) {

            handleTransaction(buttonPress, amountVal);
        } else {
            String transferredUser = enterUserField.getText();
            //System.out.println(transferredUser);
            handleTransfer(user, transferredUser, amountVal);
        }
    }

}
