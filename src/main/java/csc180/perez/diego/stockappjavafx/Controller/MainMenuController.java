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
    private Label NVDAshare, LUMNshare, INTCshare, PLTRshare, LYFTshare;
    @FXML
    private Label UserCash;
    @FXML
    private TextField txtStockSearch;
    static String userName;
    static double userCurrentBalance;
    String[] tickerNames = {"NVDA", "LUMN", "INTC", "PLTR", "LYFT"};
    String[] stockInfo;

    @FXML
    void initialize() {
        lblHelloUser.setText("Hello " + userName);
        UserCash.setText("Current Balance: $" + userCurrentBalance);
        for (int i = 0; i < tickerNames.length; i++) {
            stockInfo = DatabaseController.getUserStockAmount(userName, tickerNames[i]);
            if (i == 0 && stockInfo != null) {
                NVDAshare.setText("Most recent " + stockInfo[1] + ":" + stockInfo[0] + " shares");
            } else if (i == 1 && stockInfo != null) {
                LUMNshare.setText("Most recent " + stockInfo[1] + ":" + stockInfo[0] + " shares");
            } else if (i == 2 && stockInfo != null) {
                INTCshare.setText("Most recent " + stockInfo[1] + ":" + stockInfo[0] + " shares");
            } else if (i == 3 && stockInfo != null) {
                PLTRshare.setText("Most recent " + stockInfo[1] + ":" + stockInfo[0] + " shares");
            } else if (i == 4 && stockInfo != null) {
                LYFTshare.setText("Most recent " + stockInfo[1] + ":" + stockInfo[0] + " shares");
            }
        }

    }

    @FXML
    void onExitClick() {
        Platform.exit();
    }

    @FXML
    void onSearchClick(MouseEvent event) throws IOException {
        for (StockTicker enumeratedEnum : StockTicker.values()) {
            if (txtStockSearch.getText().equalsIgnoreCase(enumeratedEnum.toString())) {
                String[] stockInfo = DatabaseController.stockInfo(txtStockSearch);
                StockSearchController.stockData = stockInfo;
                ChangeScene.changeScene(event, "StockSearch.fxml");
                return;
            } else {
                txtStockSearch.setText("");
                txtStockSearch.setPromptText("INVALID STOCK");
            }
        }
    }
}
