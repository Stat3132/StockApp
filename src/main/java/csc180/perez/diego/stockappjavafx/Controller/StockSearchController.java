/**
 * @author eboyle
 * @createdOn 8/15/2024 at 5:24 PM
 * @projectName StockApp
 * @packageName csc180.perez.diego.stockappjavafx.Controller;
 */
package csc180.perez.diego.stockappjavafx.Controller;

import csc180.perez.diego.stockappjavafx.Model.Stock;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class StockSearchController {
    @FXML
    public Button btnStockHistory;
    @FXML
    private Label lblStockHistory, lblStockName, lblStockPrice, outcome, sellOutcome;
    @FXML
    private TextField amountToBuy;
    @FXML
    private TextField amountToSell = null;
    static String userName;
    static String[] stockData;
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
        Stock stockHistory = new Stock(stockData[0], stockHistoryInDouble[0], stockHistoryInDouble[1], stockHistoryInDouble[2], stockHistoryInDouble[3], stockHistoryInDouble[4], (long) stockHistoryInDouble[5]);
        lblStockHistory.setText(stockHistory.toString());
    }

    @FXML
    void onBackClick(MouseEvent event) throws IOException {
        Task<Void> databaseRetrieval = new Task<>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("Back Clicked");
                return null;
            }
        };
        ChangeScene.changeSceneWithLoadingScreen(event, "MainMenu.fxml", databaseRetrieval);
    }

    @FXML
    void onBuyClick() {
        if (amountToBuy.getText() != null) {
            double fullyCalculatedAmount;
            double amountBought = Double.parseDouble(amountToBuy.getText());
            double totalAmountBought = stockHistoryInDouble[4] * amountBought;
            System.out.println(totalAmountBought);
            double amountOfPerson = DatabaseController.getPersonCurrentMoney(userName);

            if (amountOfPerson < totalAmountBought) {
                outcome.setText("YOU DO NOT HAVE ENOUGH \n MONEY TO BUY THIS STOCK");
                return;
            }
            fullyCalculatedAmount = amountOfPerson - totalAmountBought;
            outcome.setText("YOU HAVE BOUGHT A STOCK");
            DatabaseController.updatePersonTotalAmount(userName, fullyCalculatedAmount);
            MainMenuController.userCurrentBalance = fullyCalculatedAmount;
            DatabaseController.createUserStockRelationship(MainMenuController.userName, stockData[0], totalAmountBought);
        }
    }

    @FXML
    void onSellClick() {
        if (amountToSell.getText() != null) {
            double totalShareOfStock = DatabaseController.getStockValueFromStock(userName, stockData[0]);
            double amountSelling = Double.parseDouble(amountToSell.getText());
            if (amountSelling < totalShareOfStock) {
                double amountOfPerson = DatabaseController.getPersonCurrentMoney(userName);
                double newPersonAmount = amountOfPerson + amountSelling;
                MainMenuController.userCurrentBalance = newPersonAmount;
                double newStockAmount = totalShareOfStock - amountSelling;
                DatabaseController.updatePersonTotalAmount(userName, newPersonAmount);
                DatabaseController.updatingStockValueFromStock(userName, stockData[0], newStockAmount);
                sellOutcome.setText("YOU HAVE SOLD A STOCK");
                return;
            }
            sellOutcome.setText("YOU DO NOT HAVE ENOUGH STOCK TO SELL");
        }

    }

    @FXML
    void onStockHistoryClicked(MouseEvent event) throws IOException {
        ChangeScene.changeScene(event, "HIstoricalChart.fxml");
    }
}

