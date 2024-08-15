/**
 * @author eboyle
 * @createdOn 8/8/2024 at 6:21 PM
 * @projectName StockApps
 * @packageName CSC180.Perez.Diego.Controller;
 */
package csc180.perez.diego.stockappjavafx.Controller;

import csc180.perez.diego.stockappjavafx.Model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;

public class CreateAccountController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnFinish;

    @FXML
    private Label lblErrorMessage;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtUserName;

    @FXML
    void onBackClick(MouseEvent event) throws IOException {
        ChangeScene.changeScene(event, "StartingScreen.fxml");
    }

    @FXML
    void onFinishClick(MouseEvent event){
        DatabaseController databaseController = new DatabaseController();
        if(databaseController.isUsernameAvailable(txtUserName.getText())) {
            Person person = new Person(txtFirstName.getText(), txtLastName.getText(), txtPhoneNumber.getText(), Integer.parseInt(txtAge.getText()), txtEmail.getText(), txtPassword.getText(), txtUserName.getText());
            databaseController.createPerson(person);
            System.out.println("Made Account");
        }
        else {
            if(lblErrorMessage != null)
            lblErrorMessage.setVisible(true);
            System.out.println("error creating user");
        }
    }

}