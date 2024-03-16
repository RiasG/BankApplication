package bankingapp.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class User {
    private final int id;
    private final String name, password;

    private BigDecimal currentBalance;

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(int id, String name, String password, BigDecimal currentBalance) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.currentBalance = currentBalance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance.setScale(2, RoundingMode.FLOOR);
    }
}
