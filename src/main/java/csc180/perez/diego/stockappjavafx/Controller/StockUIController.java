package csc180.perez.diego.stockappjavafx.Controller;

import javafx.event.ActionEvent;
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
    protected void  onLoginClick(ActionEvent event) throws IOException {
        while (true) {
            if (!txtUserName.getText().isEmpty() && !txtPassword.getText().isEmpty()){
                String[] databasePassword = DatabaseController.loginUser(txtUserName.getText());
                if (txtPassword.getText().equals(databasePassword[0])) {
                    ChangeScene.changeScene(event, "MainMenu.fxml");
                } else {
                    break;
                }
            } else {
                break;
            }
        }
    }
}
