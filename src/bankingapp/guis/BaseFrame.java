package bankingapp.guis;

import bankingapp.dao.User;

import javax.swing.*;

public abstract class BaseFrame extends JFrame {

    protected User user;
    public BaseFrame (String title){
        initialaze(title);
    }


    public BaseFrame(String title, User user){
        this.user = user;
        initialaze(title);
    }
    private void initialaze(String title) {
        setTitle(title);
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        addGuiComponents();
    }

    protected abstract void addGuiComponents();
}
