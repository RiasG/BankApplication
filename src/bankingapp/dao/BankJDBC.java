package bankingapp.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class BankJDBC {
    //public static final Logger logger = LoggerFactory.getLogger(BankJDBC.class);
    public static final String URL = "jdbc:postgresql://localhost:5432/bank_data";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "88215549";

    public static User validateLogin(String username, String password) {
        try {

            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM bank_user WHERE user_name = ? and user_password = ?");

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                BigDecimal currentBalance = resultSet.getBigDecimal("user_current_balance");
                return new User(userId, username, password, currentBalance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean register(String username, String password) {
        try {

            if (!checkUser(username)) {
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO bank_user (user_name, user_password, user_current_balance) " + " VALUES (?, ?, ?)");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setBigDecimal(3, new BigDecimal(0.0));

                preparedStatement.executeUpdate();
                return true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean checkUser(String name) {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM bank_user WHERE user_name = ?");

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean addTransactionToDatabase(Transaction transaction) {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO bank_transactions (trans_amount, trans_date, trans_type, trans_user_id) " + "VAlUES (?, ?, ?, ?)");

            stmt.setBigDecimal(1, transaction.getTransactionAmount());
            stmt.setDate(2, java.sql.Date.valueOf(transaction.getTransactionDate()));
            stmt.setString(3, transaction.getTransactionType());
            stmt.setInt(4, transaction.getUserId());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateCurrentBalance(User user) {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE bank_user SET user_current_balance = ? WHERE user_id = ?"
            );

            stmt.setBigDecimal(1, user.getCurrentBalance());
            stmt.setInt(2, user.getId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean transfer(User user, String transUsername, float transAmount) {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM bank_user WHERE user_name = ?"
            );
            stmt.setString(1, transUsername);
            //System.out.println(transUsername);
            ResultSet resultSet = stmt.executeQuery();


            while (resultSet.next()) {
                //System.out.println(resultSet.getString("user_name"));
                resultSet.getBigDecimal("user_current_balance");
                User transUser = new User(
                        resultSet.getInt("user_id"),
                        transUsername,
                        resultSet.getString("user_password"),
                        resultSet.getBigDecimal("user_current_balance")
                );

                Transaction transferTrans = new Transaction(
                        user.getId(),
                        "Transfer",
                        new BigDecimal(-transAmount),
                        LocalDate.now()
                );

                Transaction receivedTrans = new Transaction(
                        transUser.getId(),
                        "Transfer",
                        new BigDecimal(transAmount),
                        LocalDate.now()
                );

                //System.out.println(transUser.getCurrentBalance());
                transUser.setCurrentBalance(transUser.getCurrentBalance().add(
                        BigDecimal.valueOf(transAmount)
                ));
                updateCurrentBalance(transUser);

                user.setCurrentBalance(user.getCurrentBalance().subtract(
                        BigDecimal.valueOf(transAmount)
                ));
                updateCurrentBalance(user);

                addTransactionToDatabase(transferTrans);
                addTransactionToDatabase(receivedTrans);

                return true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public static ArrayList<Transaction> getPasTransactions(User user) {
        ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM bank_transactions WHERE trans_user_id = ?"
            );

            stmt.setInt(1, user.getId());

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                transactionList.add(new Transaction(
                        resultSet.getInt("trans_user_id"),
                        resultSet.getString("trans_type"),
                        resultSet.getBigDecimal("trans_amount"),
                        resultSet.getDate("trans_date").toLocalDate())
                );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;

    }
}
