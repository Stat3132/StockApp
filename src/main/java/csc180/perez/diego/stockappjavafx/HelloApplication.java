package csc180.perez.diego.stockappjavafx;

import csc180.perez.diego.stockappjavafx.Controller.DatabaseController;
import csc180.perez.diego.stockappjavafx.Controller.StockApi;
import csc180.perez.diego.stockappjavafx.Model.Portfolio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.image.DataBuffer;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StartingScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Stock Application!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        DatabaseController.createDatabase();
        StockApi.accessingAPI();
        launch();
//        String[] dataTest = DatabaseController.getStockValueFromStock("Stat19", "NVDA");
//        for (String data : dataTest) {
//            System.out.println(data);
//        }

    }
}