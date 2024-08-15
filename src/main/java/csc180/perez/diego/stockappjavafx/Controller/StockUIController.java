package csc180.perez.diego.stockappjavafx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class StockUIController {

    @FXML
    private Button btnCreateAccount;

    @FXML
    private Button btnLogIn;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void onCreateAccountClick(MouseEvent event) throws IOException {
        ChangeScene.changeScene(event, "StockSimCreateAccount.fxml");
    }

    @FXML
    void onLogInClick(MouseEvent event) throws IOException {
        ChangeScene.changeScene(event, "MainMenu.fxml");
    }

}
