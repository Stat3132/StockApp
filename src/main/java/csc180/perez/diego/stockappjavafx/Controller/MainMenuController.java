/**
 * @author eboyle
 * @createdOn 8/15/2024 at 4:46 PM
 * @projectName StockApp
 * @packageName csc180.perez.diego.stockappjavafx.Controller;
 */
package csc180.perez.diego.stockappjavafx.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnSearch;

    @FXML
    private Label lblHelloUser;

    @FXML
    private Label lblUserPortfolio;

    @FXML
    private TextField txtStockSearch;

    @FXML
    void onExitClick(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void onSearchClick(MouseEvent event) throws IOException {
        ChangeScene.changeScene(event, "StockSearch.fxml");
    }

}
