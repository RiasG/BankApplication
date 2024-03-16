package bankingapp.guis;

import bankingapp.dao.BankJDBC;
import bankingapp.dao.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginGui extends BaseFrame {
    public LoginGui() {
        super("Banking and login");
    }

    @Override
    protected void addGuiComponents() {
        JLabel bankingAppLabel = new JLabel("Banking application login");
        bankingAppLabel.setBounds(0, 20, super.getWidth(), 40);
        bankingAppLabel.setFont(new Font("Dialog", Font.BOLD, 27));
        bankingAppLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(bankingAppLabel);

        JLabel userNameLabel = new JLabel("Username");
        userNameLabel.setBounds(20, 120, getWidth() - 30, 25);
        userNameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(userNameLabel);

        JTextField userTextField = new JTextField();
        userTextField.setBounds(20, 160, getWidth() - 50, 40);
        userTextField.setFont(new Font("Dalog", Font.PLAIN, 28));
        add(userTextField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(20, 210, getWidth() - 30, 25);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        JTextField passwordTextField = new JPasswordField();
        passwordTextField.setBounds(20, 240, getWidth() - 50, 40);
        passwordTextField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordTextField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(20, 300, getWidth() - 50, 40);
        loginButton.setFont(new Font("Dialog", Font.PLAIN, 28));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = BankJDBC.validateLogin(userTextField.getText(), String.valueOf(passwordTextField.getText()));
                if (user != null){
                    LoginGui.this.dispose();

                    BankingGui bankingGui = new BankingGui(user);
                    bankingGui.setVisible(true);
                    //JOptionPane.showMessageDialog(bankingGui, "Login successfully");

                }else {
                    JOptionPane.showMessageDialog(LoginGui.this, "Login failed");
                }
            }
        });
        add(loginButton);

        JLabel registerLabel = new JLabel("<html><a href=\"#\">Don't have an account? Register here</a></html>");
        registerLabel.setBounds(0, 500, getWidth() - 10, 40);
        registerLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginGui.this.dispose();
                LoginGui.super.dispose();
                new RegisterGui().setVisible(true);
            }
        });
        add(registerLabel);

    }
}
