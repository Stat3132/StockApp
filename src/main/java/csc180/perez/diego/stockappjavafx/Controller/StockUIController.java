package csc180.perez.diego.stockappjavafx.Controller;

import com.sun.tools.javac.Main;
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
    protected void  onLogInClick(MouseEvent event) throws IOException {
        while (true) {
            if (!txtUserName.getText().isEmpty() && !txtPassword.getText().isEmpty()){
                String[] databasePassword = DatabaseController.loginUser(txtUserName.getText());
                if (txtPassword.getText().equals(databasePassword[0])) {
                    MainMenuController.userName = databasePassword[1];
                    StockSearchController.userName = databasePassword[1];
                    MainMenuController.userCurrentBalance = Double.parseDouble(databasePassword[2]);
                    ChangeScene.changeScene(event, "MainMenu.fxml");
                    return;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
    }
}
