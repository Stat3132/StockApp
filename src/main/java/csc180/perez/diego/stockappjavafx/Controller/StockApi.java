package csc180.perez.diego.stockappjavafx.Controller;

import csc180.perez.diego.stockappjavafx.Model.Stock;
import csc180.perez.diego.stockappjavafx.UTIL.StockTicker;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class StockApi {

    LocalDate currentDay = LocalDate.now();
    LocalDate currentDayMinusOne = currentDay.minusDays(1);
    String mostRecentStockTime = currentDayMinusOne + "/" + currentDayMinusOne;
    Map<String, Stock> stockMap = new HashMap<>();
    ArrayList<StockTicker> stockList = new ArrayList<>();

    public void accessingAPI() {
        System.out.println("Test to Change");
        stockList.addAll(Arrays.asList(StockTicker.values()));
        for (int currentStockBeingIteratedOver = 0; currentStockBeingIteratedOver < 2; currentStockBeingIteratedOver++) {
            HttpGet request = new HttpGet("https://api.polygon.io/v2/aggs/ticker/" + stockList.get(currentStockBeingIteratedOver) + "/range/1/day/" + mostRecentStockTime + "?apiKey=6yr9w0ir7v0gGtri_v4LXi1ehRoAMpQd");
            CloseableHttpClient httpClient = HttpClients.createDefault();
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                ObjectMapper stockData = new ObjectMapper();
                Map<String, Object> mappedStockData = stockData.readValue(responseBody, Map.class);
                List<Map<String, Object>> listOfStockData = (List<Map<String, Object>>) mappedStockData.get("results");
                if (listOfStockData == null) {
                    currentDayMinusOne = currentDayMinusOne.minusDays(2);
                    mostRecentStockTime = currentDayMinusOne + "/" + currentDayMinusOne;
                    currentStockBeingIteratedOver--;
                    continue;
                }
                double doubledOpeningPrice;
                double doubledLowestPrice;
                double doubledHighestPrice;
                double doubledClosingPrice;
                double doubledVolume;
                long longTransactionalValue;
                if (listOfStockData.getFirst().get("o") == Integer.class) {
                    Number openingPrice = (Number) listOfStockData.getFirst().get("o");
                    doubledOpeningPrice = openingPrice.doubleValue();
                } else {
                    doubledOpeningPrice = (double) listOfStockData.getFirst().get("o");
                }
                if (listOfStockData.getFirst().get("l").getClass() == Integer.class) {
                    Number lowestPrice = (Number) listOfStockData.getFirst().get("l");
                    doubledLowestPrice = lowestPrice.doubleValue();
                } else {
                    doubledLowestPrice = (double) listOfStockData.getFirst().get("l");
                }
                if (listOfStockData.getFirst().get("h").getClass() == Integer.class) {
                    Number lowestPrice = (Number) listOfStockData.getFirst().get("h");
                    doubledHighestPrice = lowestPrice.doubleValue();
                } else {
                    doubledHighestPrice = (double) listOfStockData.getFirst().get("h");
                }
                if (listOfStockData.getFirst().get("c").getClass() == Integer.class) {
                    Number lowestPrice = (Number) listOfStockData.getFirst().get("c");
                    doubledClosingPrice = lowestPrice.doubleValue();
                } else {
                   doubledClosingPrice = (double) listOfStockData.getFirst().get("c");
                }
                if (listOfStockData.getFirst().get("v").getClass() == Integer.class) {
                    Number lowestPrice = (Number) listOfStockData.getFirst().get("v");
                    doubledVolume = lowestPrice.doubleValue();
                } else {
                    doubledVolume = (double) listOfStockData.getFirst().get("v");
                }
                if (listOfStockData.getFirst().get("t").getClass() == Integer.class){
                    Number transactions = (Number) listOfStockData.getFirst().get("t");
                    longTransactionalValue = transactions.longValue();
                } else {
                   longTransactionalValue = (long) listOfStockData.getFirst().get("t");
                }
                Stock newStock = new Stock(
                        stockList.get(currentStockBeingIteratedOver).toString(),
                        doubledLowestPrice,
                        doubledHighestPrice,
                        doubledClosingPrice,
                        doubledVolume,
                        doubledOpeningPrice,
                        longTransactionalValue);
                stockMap.put(stockList.get(currentStockBeingIteratedOver).toString(), newStock);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Stock stock : stockMap.values()) {
            DatabaseController.createStocks(stock);
        }
    }

    public void stockSearch(String stockName) {
    }

}
