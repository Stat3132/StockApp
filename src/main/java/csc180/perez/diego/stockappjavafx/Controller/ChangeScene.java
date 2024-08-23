/**
 * @author eboyle
 * @createdOn 8/14/2024 at 5:54 PM
 * @projectName StockApp
 * @packageName csc180.perez.diego.stockappjavafx.Controller;
 */
package csc180.perez.diego.stockappjavafx.Controller;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ChangeScene {
    private static Stage stage;
    private static Scene scene;

    public static void changeScene(Event event, String strFXMLFileName) throws IOException {
        URL url = new File("src/main/resources/csc180/perez/diego/stockappjavafx/" + strFXMLFileName).toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
