package csc180.perez.diego.stockappjavafx.Controller;

import csc180.perez.diego.stockappjavafx.Model.Stock;
import javafx.scene.control.TextField;

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
            Connection conn = DriverManager.getConnection(url + "stock", user, password);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createStocks(Stock stock){
            String sql = "INSERT INTO stock (ticket, lowestPrice, highestPrice, currentClosingPrice, volume, openingPrice)Values (?, ?, ?, ?, ?, ?)";
            try {
                Connection con = DriverManager.getConnection(url, user, password);
                PreparedStatement pst = con.prepareStatement(sql);
                if (pst != null) {
                    pst.setString(1, stock.getTicket());
                    pst.setDouble(2, stock.getLowestPrice());
                    pst.setDouble(3, stock.getHighestPrice());
                    pst.setDouble(4, stock.getCurrentClosingPrice());
                    pst.setDouble(5, stock.getVolume());
                    pst.setDouble(6, stock.getOpeningPrice());
                    pst.executeUpdate();
                    System.out.println("User created correctly");
                }
                else {
                    System.out.println("Failed to establish connection");
                }
                } catch(SQLException e){
                    throw new RuntimeException(e);
                }

        }
    }


