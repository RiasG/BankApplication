package bankingapp.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Transaction {
    private final int userId;
    private final String transactionType;
    private final BigDecimal transactionAmount;
    private final LocalDate transactionDate;

    public Transaction( int userId, String transactionType, BigDecimal transactionAmount, LocalDate transactionDate) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
    }

    public int getUserId() {
        return userId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }
}
