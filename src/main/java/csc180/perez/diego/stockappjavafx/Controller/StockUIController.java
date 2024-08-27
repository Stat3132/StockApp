package csc180.perez.diego.stockappjavafx.Controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLOutput;

public class StockUIController {

    @FXML
    private TextField txtPassword, txtUserName;

    @FXML
    void onCreateAccountClick(MouseEvent event) throws IOException {
        ChangeScene.changeScene(event, "StockSimCreateAccount.fxml");
    }

    @FXML
    protected void onLogInClick(MouseEvent event) throws IOException {
        if (!txtUserName.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
            Task<Void> databaseRetrieval = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    String[] databasePassword = DatabaseController.loginUser(txtUserName.getText());
                    if (txtPassword.getText().equals(databasePassword[0])) {
                        MainMenuController.userName = databasePassword[1];
                        StockSearchController.userName = databasePassword[1];
                        MainMenuController.userCurrentBalance = Double.parseDouble(databasePassword[2]);
                    } else {
                        throw new Exception("INVALID LOGIN");
                    }
                    return null;
                }
            };
            new Thread(databaseRetrieval).start();
            ChangeScene.changeSceneWithLoadingScreen(event, "MainMenu.fxml", databaseRetrieval);
        }
    }
}