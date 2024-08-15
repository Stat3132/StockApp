package csc180.perez.diego.stockappjavafx.Controller;

import csc180.perez.diego.stockappjavafx.Model.Person;
import csc180.perez.diego.stockappjavafx.Model.Stock;


import java.sql.*;

public class DatabaseController {
    static String url = "jdbc:mysql://localhost:3306/";
    static String user = "root";
    static String password = "test";

    public void testConnection() throws SQLException { //uses version to check if the app is connected to the database
        String sql = "SELECT VERSION()";


             Connection  con = DriverManager.getConnection(url, user, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql);


            if (rs.next()) {
                System.out.println(rs.getString(1)); // checks the result set and prints what is in it
            }
        }


    public void createDatabase(){
        try{
            Connection con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            String sql = "create database if not exists stock;";
            st.executeUpdate(sql);
            sql = "use stock;";
            st.executeUpdate(sql);
            url = url + "stock";
            Connection conn = DriverManager.getConnection(url, user, password);
            st = conn.createStatement();
            sql =  "create table if not exists people (" +
            "`Id` INT NOT NULL AUTO_INCREMENT," +
                    "FirstName VARCHAR(45) NULL," +
                    "LastName VARCHAR(45) NULL," +
                    "Email VARCHAR(45) NULL," +
                    "PhoneNumber VARCHAR(45) NULL," +
                    "Age INT NULL," +
                    "PRIMARY KEY (`Id`));";
            st.executeUpdate(sql);
            sql = "CREATE TABLE if not exists stocks("+
                    "idstocks INT NOT NULL AUTO_INCREMENT,"+
                    "ticket VARCHAR(45) NULL," +
                    "lowestPrice DOUBLE NULL,"+
                    "highestPrice DOUBLE NULL,"+
                    "currentClosingPrice DOUBLE NULL,"+
                    "volume DOUBLE NULL,"+
                    "openingPrice DOUBLE NULL,"+
                    "PRIMARY KEY (`idstocks`));";
            st.executeUpdate(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public void createPerson(Person person){
        try{
            Connection con = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO people (FirstName, LastName, Email, PhoneNumber, Age) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, person.getFirstName());
            pst.setString(2, person.getLastName());
            pst.setString(3, person.getEmail());
            pst.setString(4, person.getPhoneNumber());
            pst.setInt(5, person.getAge());

            pst.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void createStock(Stock s){
        try{
            Connection con = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO stocks (ticket, lowestPrice, highestPrice, currentClosingPrice, volume, openingPrice) VALUES (?, ?, ?, ?, ? ,?);";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, s.getTicket());
            pst.setDouble(2, s.getLowestPrice());
            pst.setDouble(3, s.getHighestPrice());
            pst.setDouble(4, s.getCurrentClosingPrice());
            pst.setDouble(5, s.getVolume());
            pst.setDouble(6, s.getOpeningPrice());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createStocks(Stock stock){

            try {
                Connection con = DriverManager.getConnection(url, user, password);
                String testIfStockExists = "select * from stocks where ticket = ?";
                PreparedStatement preparedStatement = con.prepareStatement(testIfStockExists);
                preparedStatement.setString(1, stock.getTicket());
                ResultSet ifStockExistsResults = preparedStatement.executeQuery();
                if(ifStockExistsResults.isBeforeFirst()){
                    String updateStock = "update stocks set lowestPrice = ?, highestPrice = ?, currentClosingPrice = ?, volume = ?, openingPrice = ? where ticket = ?";
                    preparedStatement = con.prepareStatement(updateStock);
                    preparedStatement.setDouble(1, stock.getLowestPrice());
                    preparedStatement.setDouble(2, stock.getHighestPrice());
                    preparedStatement.setDouble(3, stock.getCurrentClosingPrice());
                    preparedStatement.setDouble(4, stock.getVolume());
                    preparedStatement.setDouble(5, stock.getOpeningPrice());
                    preparedStatement.setString(6, stock.getTicket());
                    preparedStatement.executeUpdate();
                    System.out.println("Stock Updated");
                }
                else {
                    String sql = "INSERT INTO stocks (ticket, lowestPrice, highestPrice, currentClosingPrice, volume, openingPrice) Values (?, ?, ?, ?, ?, ?)";

                    PreparedStatement pst = con.prepareStatement(sql);
                    if (pst != null) {
                        pst.setString(1, stock.getTicket());
                        pst.setDouble(2, stock.getLowestPrice());
                        pst.setDouble(3, stock.getHighestPrice());
                        pst.setDouble(4, stock.getCurrentClosingPrice());
                        pst.setDouble(5, stock.getVolume());
                        pst.setDouble(6, stock.getOpeningPrice());
                        pst.executeUpdate();
                        System.out.println("Stock Created");
                }
                }
                } catch(SQLException e){
                    throw new RuntimeException(e);
                }

        }
    }


