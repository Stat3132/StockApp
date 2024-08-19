/**
 * @author eboyle
 * @createdOn 8/15/2024 at 5:24 PM
 * @projectName StockApp
 * @packageName csc180.perez.diego.stockappjavafx.Controller;
 */
package csc180.perez.diego.stockappjavafx.Controller;

import csc180.perez.diego.stockappjavafx.Model.Stock;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class StockSearchController {
    @FXML
    private Button btnBack;

    @FXML
    private Button btnBuy;

    @FXML
    private Button btnSell;

    @FXML
    private Label lblStockHistory;

    @FXML
    private Label lblStockName;

    @FXML
    private Label lblStockPrice;

    static String stockData[];
    double[] stockHistoryInDouble = new double[6];
    @FXML
    void initialize() {
        lblStockName.setText(stockData[0]);
        lblStockPrice.setText(stockData[5]);
        int oneValueBefore = 0;
        for (int i = 1; i < stockHistoryInDouble.length + 1; i++) {
            double convertedValues = Double.parseDouble(stockData[i]);
            if (oneValueBefore == 5) {
                stockHistoryInDouble[oneValueBefore] = Long.parseLong(stockData[i]);
                break;
            }
            stockHistoryInDouble[oneValueBefore] = convertedValues;
            oneValueBefore++;
        }
        Stock stockHistory = new Stock(stockData[0], stockHistoryInDouble[0], stockHistoryInDouble[1], stockHistoryInDouble[2], stockHistoryInDouble[3], stockHistoryInDouble[4], (long)stockHistoryInDouble[5]);
        lblStockHistory.setText(stockHistory.toString());
    }
    @FXML
    void onBackClick(MouseEvent event) throws IOException {
        ChangeScene.changeScene(event, "MainMenu.fxml");
    }

    @FXML
    void onBuyClick(MouseEvent event) {
        DatabaseController.createUserStockRelationship(MainMenuController.userName, stockData[0], stockHistoryInDouble[4]);
    }

    @FXML
    void onSellClick(MouseEvent event) {

    }
}

