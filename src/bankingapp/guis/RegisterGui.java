package bankingapp.guis;

import bankingapp.dao.BankJDBC;
import bankingapp.dao.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterGui extends BaseFrame {
    public RegisterGui() {
        super("Banking App Register");
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

        JLabel rePasswordLabel = new JLabel("Re-type Password");
        rePasswordLabel.setBounds(20, 290, getWidth() - 50, 40);
        rePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(rePasswordLabel);


        JTextField rePasswordTextField = new JPasswordField();
        rePasswordTextField.setBounds(20, 330, getWidth() - 50, 40);
        rePasswordTextField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(rePasswordTextField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(20, 400, getWidth() - 50, 40);
        loginButton.setFont(new Font("Dialog", Font.PLAIN, 28));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = userTextField.getText();
                String password = passwordTextField.getText();
                String rePassword = rePasswordTextField.getText();

                if (check(name, password, rePassword)) {
                    if (BankJDBC.register(name, password)) {
                        System.out.println("Register new");
                        new BankingGui(BankJDBC.validateLogin(name,password)).setVisible(true);
                        RegisterGui.this.dispose();


                    } else {
                        JOptionPane.showMessageDialog(RegisterGui.this, "Register failed");
                    }
                } else {
                    JOptionPane.showMessageDialog(RegisterGui.this, "Register failed");
                }

            }
        });


        add(loginButton);

        JLabel registerLabel = new JLabel("<html><a href=\"#\">Have an account? Sign-in here</a></html>");
        registerLabel.setBounds(0, 500,

                getWidth() - 10, 40);
        registerLabel.setFont(new

                Font("Dialog", Font.PLAIN, 15));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterGui.this.dispose();
                new LoginGui().setVisible(true);
            }
        });

        add(registerLabel);

    }

    private boolean check(String name, String pass, String rePass) {
        if (name.length() > 0 && pass.length() > 0 && pass.equals(rePass)) {
            return true;
        }

        return false;
    }
}
