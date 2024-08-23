/**
 * @author eboyle
 * @createdOn 8/15/2024 at 4:46 PM
 * @projectName StockApp
 * @packageName csc180.perez.diego.stockappjavafx.Controller;
 */
package csc180.perez.diego.stockappjavafx.Controller;

import csc180.perez.diego.stockappjavafx.UTIL.StockTicker;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.xml.crypto.Data;
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
    private Label UserCash;

    @FXML
    private TextField txtStockSearch;
    static String userName;
    static double userCurrentBalance;

    @FXML
    void initialize() {
        lblHelloUser.setText("Hello " + userName);
        UserCash.setText("Current Balance: $" + userCurrentBalance);
        String[] stockInfo = DatabaseController.getUserStockAmount(userName, "NVDA");
        lblUserPortfolio.setText(stockInfo[1] + ":" +stockInfo[0] + " shares");

    }
    @FXML
    void onExitClick(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void onSearchClick(MouseEvent event) throws IOException {
        if (txtStockSearch.getText().equalsIgnoreCase("NVDA")|| txtStockSearch.getText().equalsIgnoreCase("LUMN") || txtStockSearch.getText().equalsIgnoreCase("INTC") || txtStockSearch.getText().equalsIgnoreCase("PLTR") || txtStockSearch.getText().equalsIgnoreCase("LYFT")) {
            String[] stockInfo = DatabaseController.stockInfo(txtStockSearch);
            StockSearchController.stockData = stockInfo;
            ChangeScene.changeScene(event, "StockSearch.fxml");
        } else {
            txtStockSearch.setText("");
            txtStockSearch.setPromptText("INVALID STOCK");
        }
    }

    @FXML
    void onBuyClicked(MouseEvent event) {

    }

}
