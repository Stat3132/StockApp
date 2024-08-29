/**
 * @author eboyle
 * @createdOn 8/15/2024 at 4:46 PM
 * @projectName StockApp
 * @packageName csc180.perez.diego.stockappjavafx.Controller;
 */
package csc180.perez.diego.stockappjavafx.Controller;

import csc180.perez.diego.stockappjavafx.UTIL.StockTicker;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private Label lblHelloUser, NVDAshare, LUMNshare, INTCshare, PLTRshare, LYFTshare, UserCash;
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
                NVDAshare.setText("Total " + stockInfo[1] + ":" + stockInfo[0] + " shares");
            } else if (i == 1 && stockInfo != null) {
                LUMNshare.setText("Total " + stockInfo[1] + ":" + stockInfo[0] + " shares");
            } else if (i == 2 && stockInfo != null) {
                INTCshare.setText("Total " + stockInfo[1] + ":" + stockInfo[0] + " shares");
            } else if (i == 3 && stockInfo != null) {
                PLTRshare.setText("Total " + stockInfo[1] + ":" + stockInfo[0] + " shares");
            } else if (i == 4 && stockInfo != null) {
                LYFTshare.setText("Total " + stockInfo[1] + ":" + stockInfo[0] + " shares");
            }
        }
    }

    @FXML
    void onExitClick() {
        Platform.exit();
    }

    @FXML
    void onSearchClick(MouseEvent event) throws IOException {
        String searchInputStringConversion = txtStockSearch.getText();
        for (String tickerName : tickerNames) {
            if (searchInputStringConversion.equals("") || !searchInputStringConversion.equals(tickerName)) {
                txtStockSearch.setText("Invalid stock");
                return;
            }
        }

        Task<Void> databaseRetrieval = new Task<>() {
            @Override
            protected Void call() throws Exception {
                for (StockTicker enumeratedEnum : StockTicker.values()) {
                    if (searchInputStringConversion.equalsIgnoreCase(enumeratedEnum.toString())) {
                        StockSearchController.stockData = DatabaseController.stockInfo(searchInputStringConversion);
                        System.out.println("Correct search");
                        return null;
                    }
                }
                throw new Exception("INVALID STOCK");
            }
        };
        new Thread(databaseRetrieval).start();
        ChangeScene.changeSceneWithLoadingScreen(event, "StockSearch.fxml", databaseRetrieval);
    }


}
