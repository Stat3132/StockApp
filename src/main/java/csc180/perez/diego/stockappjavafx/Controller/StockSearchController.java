/**
 * @author eboyle
 * @createdOn 8/15/2024 at 5:24 PM
 * @projectName StockApp
 * @packageName csc180.perez.diego.stockappjavafx.Controller;
 */
package csc180.perez.diego.stockappjavafx.Controller;

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

    @FXML
    void onBackClick(MouseEvent event) throws IOException {
        ChangeScene.changeScene(event, "MainMenu.fxml");
    }

    @FXML
    void onBuyClick(MouseEvent event) {

    }

    @FXML
    void onSellClick(MouseEvent event) {

    }

    static String stockData[];

    @FXML
    void initialize() {
        lblStockName.setText(stockData[0]);
        lblStockPrice.setText(stockData[6]);
    }

}

