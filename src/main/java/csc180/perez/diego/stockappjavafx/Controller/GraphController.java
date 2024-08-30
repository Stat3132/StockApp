/**
 * @author dpontious
 * @createdOn 8/21/2024 at 6:18 PM
 * @projectName JavaFx
 * @packageName csc180.perez.diego.stockappjavafx.Controller;
 */
package csc180.perez.diego.stockappjavafx.Controller;

import csc180.perez.diego.stockappjavafx.Model.Stock;
import csc180.perez.diego.stockappjavafx.UTIL.StockTicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GraphController {
    @FXML
    public TextField txtFieldSearch;

    @FXML
    private LineChart<String, BigInteger> chartHistoricalData;

    @FXML
    void exit(ActionEvent event) throws IOException {
        ChangeScene.changeScene(event, "StockSearch.fxml");
    }

    @FXML
    void oneMonth(ActionEvent event) {

    }

    @FXML
    void oneWeek(ActionEvent event) {

    }

    @FXML
    void oneYear(ActionEvent event) {
        StockTicker stockTicker = getSearchStock();
        bulidHistoricalDataChart(stockTicker, "year", 1);
    }

    @FXML
    void sixMonths(ActionEvent event) {
        StockTicker stockTicker = getSearchStock();
        bulidHistoricalDataChart(stockTicker, "month", 6);
    }

    @FXML
    void threeMonths(ActionEvent event) {
        StockTicker stockTicker = getSearchStock();
        bulidHistoricalDataChart(stockTicker, "month", 3);
    }

    @FXML
    void twoYear(ActionEvent event) {
        StockTicker stockTicker = getSearchStock();
        bulidHistoricalDataChart(stockTicker, "year", 2);
    }

    private void bulidHistoricalDataChart(StockTicker stockTicker, String timespan, int timePeriodBefore){
        chartHistoricalData.getData().clear();
        StockApi.accessingAPI(stockTicker, timespan, timePeriodBefore);
        Map<String, List<Map<String, Stock>>> histMapOfStockValues = StockApi.getHistoricalStockValueMap();
//        CategoryAxis xAxis = new CategoryAxis();
//        NumberAxis yAxis = new NumberAxis();
//        xAxis.setLabel("Period");
//        yAxis.setLabel("Price");
        XYChart.Series<String,BigInteger> series = new XYChart.Series<>();
        int period = 0;
        for(Map.Entry<String, List<Map<String, Stock>>> entry : histMapOfStockValues.entrySet()){
            for(Map<String, Stock> stockData : entry.getValue()){
                for(Map.Entry<String, Stock> stock : stockData.entrySet()){
                    ++period;
                    BigDecimal decimalOfVolume = BigDecimal.valueOf(stock.getValue().getVolume());
                    BigInteger bigIntegerOfVolume = decimalOfVolume.toBigInteger();
                    series.getData().add(new XYChart.Data<>("" + period, bigIntegerOfVolume));
                    //System.out.println(bigIntegerOfVolume);
                    break;
                }
            }
        }
        chartHistoricalData.getData().add(series);
        StockApi.clearHistoricalStockValueMap();
    }

    private StockTicker getSearchStock(){
        String stockToSearch = txtFieldSearch.getText();
       ArrayList<StockTicker> enumOfStocks = new ArrayList<>(Arrays.asList(StockTicker.values()));
       for(StockTicker stock : enumOfStocks){
           if(stock.toString().equals(stockToSearch)){
               return stock;
           }
       }
       return StockTicker.NVDA;
    }

}


