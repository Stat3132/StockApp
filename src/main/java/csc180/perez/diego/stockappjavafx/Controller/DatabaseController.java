package csc180.perez.diego.stockappjavafx.Controller;

import csc180.perez.diego.stockappjavafx.Model.Person;
import csc180.perez.diego.stockappjavafx.Model.Stock;
import javafx.scene.control.TextField;


import java.sql.*;

public class DatabaseController {
//    static String url = "jdbc:mysql://localhost:3306/";
//    static String user = "root";
//    static String password = "test";
    static String url = "jdbc:mysql://stockaws.cxquk06g8ywu.us-east-2.rds.amazonaws.com:3306/";
    static String user = "admin";
    static String password = "password";


    //region DATABASE CONNECTION


    //endregion
    //region CREATING LOGIC FOR DATABASE
    public static void createDatabase() {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            String sql = "create database if not exists stock;";
            st.executeUpdate(sql);
            sql = "use stock;";
            st.executeUpdate(sql);
            url = url + "stock";
            Connection conn = DriverManager.getConnection(url, user, password);
            st = conn.createStatement();
            sql = "create table if not exists people (" +
                    "`Id` INT NOT NULL AUTO_INCREMENT," +
                    "FirstName VARCHAR(45) NULL," +
                    "LastName VARCHAR(45) NULL," +
                    "Email VARCHAR(45) NULL," +
                    "PhoneNumber VARCHAR(45) NULL," +
                    "Age INT NULL," +
                    "Username VARCHAR(45) NULL," +
                    "Password VARCHAR(45) NULL," +
                    "CurrentBalance Double NULL," +
                    "PRIMARY KEY (`Id`));";
            st.executeUpdate(sql);
            sql = "CREATE TABLE if not exists stocks(" +
                    "idstocks INT NOT NULL AUTO_INCREMENT," +
                    "ticket VARCHAR(45) NULL," +
                    "lowestPrice DOUBLE NULL," +
                    "highestPrice DOUBLE NULL," +
                    "currentClosingPrice DOUBLE NULL," +
                    "volume DOUBLE NULL," +
                    "openingPrice DOUBLE NULL," +
                    "amountOfTransactions BIGINT NULL," +
                    "PRIMARY KEY (`idstocks`));";
            st.executeUpdate(sql);
            sql = "CREATE TABLE if not exists userstocks(" +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    "personId int not null," +
                    "stockId int not null," +
                    "amountOwned double null," +
                    "PRIMARY KEY (`id`));";
            st.executeUpdate(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void createPerson(Person person) {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO people (FirstName, LastName, Email, PhoneNumber, Age, Username, Password, CurrentBalance) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, person.getFirstName());
            pst.setString(2, person.getLastName());
            pst.setString(3, person.getEmail());
            pst.setString(4, person.getPhoneNumber());
            if (person.getAge() < 18 || person.getAge() > 130) {
                System.exit(1);
            } else {
                pst.setInt(5, person.getAge());
            }
            pst.setString(6, person.getUserName());
            pst.setString(7, person.getPassword());
            pst.setDouble(8, person.getAmountOfMoney());

            pst.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void createUserStockRelationship(String username, String ticket, double stockAmountOwned) {
        int personId = getPersonId(username);
        int stockId = getStockId(ticket);
        double newStockAmount = 0;
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            if (personId != -1 && stockId != -1) {
                String checkIfRelationshipExists = "select * from userstocks where personId = ? and stockId = ?";
                PreparedStatement preparedStatementCheckRelationships = con.prepareStatement(checkIfRelationshipExists);
                preparedStatementCheckRelationships.setInt(1, personId);
                preparedStatementCheckRelationships.setInt(2, stockId);
                ResultSet resultSetRelationship = preparedStatementCheckRelationships.executeQuery();
                if (resultSetRelationship.next()){
                    String currentStockAmount = resultSetRelationship.getString("amountOwned");
                    newStockAmount = Double.parseDouble(currentStockAmount) + stockAmountOwned;
                    String updateUserStockRelationship = "update userstocks set amountOwned = ? where personId = ? and stockId = ?";
                    PreparedStatement preparedUpdateUserStockRelationships = con.prepareStatement(updateUserStockRelationship);
                    preparedUpdateUserStockRelationships.setDouble(1,  newStockAmount);
                    preparedUpdateUserStockRelationships.setInt(2, personId);
                    preparedUpdateUserStockRelationships.setInt(3, stockId);
                    preparedUpdateUserStockRelationships.executeUpdate();
                    System.out.println("updated user stock relationship");
                } else {
                    String insertUserStockRelationship = "INSERT INTO userstocks (personId, stockId, amountOwned) VALUES (?, ?, ?);";
                    PreparedStatement pst = con.prepareStatement(insertUserStockRelationship);

                    pst.setInt(1, personId);
                    pst.setInt(2, stockId);
                    pst.setDouble(3, stockAmountOwned);
                    pst.executeUpdate();
                    System.out.println("Made user relationship");
                }
            } else {
                if (personId == -1) {
                    System.out.println("error in username or user does not exist");
                }
                if (stockId == -1) {
                    System.out.println("error in ticket or ticket does not exist");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void createStocks(Stock stock) {

        try {
            Connection con = DriverManager.getConnection(url, user, password);
            String testIfStockExists = "select * from stocks where ticket = ?";
            PreparedStatement preparedStatement = con.prepareStatement(testIfStockExists);
            preparedStatement.setString(1, stock.getTicket());
            ResultSet ifStockExistsResults = preparedStatement.executeQuery();
            if (ifStockExistsResults.isBeforeFirst()) {
                String updateStock = "update stocks set lowestPrice = ?, highestPrice = ?, currentClosingPrice = ?, volume = ?, openingPrice = ?, amountOfTransactions = ? where ticket = ?";
                preparedStatement = con.prepareStatement(updateStock);
                preparedStatement.setDouble(1, stock.getLowestPrice());
                preparedStatement.setDouble(2, stock.getHighestPrice());
                preparedStatement.setDouble(3, stock.getCurrentClosingPrice());
                preparedStatement.setDouble(4, stock.getVolume());
                preparedStatement.setDouble(5, stock.getOpeningPrice());
                preparedStatement.setLong(6, stock.getAmountOfTransactions());
                preparedStatement.setString(7, stock.getTicket());
                preparedStatement.executeUpdate();
                System.out.println("Stock Updated");
            } else {
                String sql = "INSERT INTO stocks (ticket, lowestPrice, highestPrice, currentClosingPrice, volume, openingPrice, amountOfTransactions) Values (?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement pst = con.prepareStatement(sql);
                if (pst != null) {
                    pst.setString(1, stock.getTicket());
                    pst.setDouble(2, stock.getLowestPrice());
                    pst.setDouble(3, stock.getHighestPrice());
                    pst.setDouble(4, stock.getCurrentClosingPrice());
                    pst.setDouble(5, stock.getVolume());
                    pst.setDouble(6, stock.getOpeningPrice());
                    pst.setDouble(7, stock.getAmountOfTransactions());
                    pst.executeUpdate();
                    System.out.println("Stock Created");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static double getStockValueFromStock(String username, String ticket) {
        int personId = getPersonId(username);
        int stockId = getStockId(ticket);
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String checkIfRelationshipExists = "select amountOwned from stock.userstocks where personId = ? and stockId = ?";
            PreparedStatement preparedStatementCheckRelationships = connection.prepareStatement(checkIfRelationshipExists);
            preparedStatementCheckRelationships.setInt(1, personId);
            preparedStatementCheckRelationships.setInt(2, stockId);
            ResultSet resultSetRelationship = preparedStatementCheckRelationships.executeQuery();
            if (resultSetRelationship.next()) {
                return resultSetRelationship.getDouble("amountOwned");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    public static void updatingStockValueFromStock(String username, String ticket, double stockAmountBeingRemoved) {
        int personId = getPersonId(username);
        int stockId = getStockId(ticket);
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String checkIfRelationshipExists = "update userstocks set amountowned = ? where personId = ? and stockId = ?";
            PreparedStatement preparedStatementCheckRelationships = connection.prepareStatement(checkIfRelationshipExists);
            preparedStatementCheckRelationships.setDouble(1, stockAmountBeingRemoved);
            preparedStatementCheckRelationships.setInt(2, personId);
            preparedStatementCheckRelationships.setInt(3, stockId);
            preparedStatementCheckRelationships.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    //endregion
    //region getting Info from Database
    public static String[] getUserStockAmount(String username, String ticket) {
        int personId = getPersonId(username);
        int stockId = getStockId(ticket);
        String[] stockInfo = new String[2];
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String checkIfRelationshipExists = "select stockid, amountOwned from stock.userstocks where personId = ? and stockId = ?";
            PreparedStatement preparedStatementCheckRelationships = connection.prepareStatement(checkIfRelationshipExists);
            preparedStatementCheckRelationships.setInt(1, personId);
            preparedStatementCheckRelationships.setInt(2, stockId);
            ResultSet resultSetRelationship = preparedStatementCheckRelationships.executeQuery();
            if (resultSetRelationship.next()) {
                double amountOwned = resultSetRelationship.getDouble("amountOwned");
                String amountOwnedString = amountOwned + "";
                stockInfo[0] = amountOwnedString;
                String grabStockName = "select ticket from stock.stocks where idstocks = ?";
                PreparedStatement preparedStatementGrabStockName = connection.prepareStatement(grabStockName);
                preparedStatementGrabStockName.setInt(1, resultSetRelationship.getInt(1));
                ResultSet resultSetStockName = preparedStatementGrabStockName.executeQuery();
                if (resultSetStockName.next()) {
                    String ticker = resultSetStockName.getString("ticket");
                    stockInfo[1] = ticker;
                    return stockInfo;
                }
                return null;
            } else {
                if (personId == -1) {
                    System.out.println("error in username or user does not exist");
                }
                if (stockId == -1) {
                    System.out.println("error in ticket or ticket does not exist");
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static String[] stockInfo(String ticker) {
        String sql = "SELECT lowestPrice, highestPrice, currentClosingPrice, volume, openingPrice, amountOfTransactions from stock.stocks where ticket = ?";
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, ticker);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                String lowestPrice = result.getString("lowestPrice");
                String highestPrice = result.getString("highestPrice");
                String currentClosingPrice = result.getString("currentClosingPrice");
                String volume = result.getString("volume");
                String openingPrice = result.getString("openingPrice");
                String amountOfTransactions = result.getString("amountOfTransactions");
                String[] stockInfo = {ticker, lowestPrice, highestPrice, currentClosingPrice, volume, openingPrice, amountOfTransactions};
                return stockInfo;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return null;
    }

    public static int getPersonId(String username) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "select * from stock.people where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getStockId(String ticket) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "select * from stock.stocks where ticket = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ticket);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //endregion
    //region user login
    public static String[] loginUser(String username) {
        String sql = "SELECT Password, Username, CurrentBalance from stock.people where Username = ?";
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            ResultSet result = pst.executeQuery();
            if (result.next()) {
                String password = result.getString("Password");
                double currentUserBalance = result.getDouble("CurrentBalance");
                String[] userInfo = {password, username, currentUserBalance + ""};
                return userInfo;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return null;
    }

    public boolean isUsernameAvailable(String username) {
        return getPersonId(username) == -1;
    }

    //endregion
    //region get person info
    public static double getPersonCurrentMoney(String username) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "select * from people where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Double currentBalance = resultSet.getDouble("CurrentBalance");
                return currentBalance;
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return 0;
    }

    public static void updatePersonTotalAmount(String username, double fullyCalculatedAmountBought) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "update people set CurrentBalance = ? where username = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, fullyCalculatedAmountBought);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
            System.out.println("STOCK HAS BEEN BOUGHT");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion
}

