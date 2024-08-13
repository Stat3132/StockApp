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
        stockList.addAll(Arrays.asList(StockTicker.values()));
        for (int i = 0; i < 5; i++) {
            HttpGet request = new HttpGet("https://api.polygon.io/v2/aggs/ticker/" + stockList.get(i) + "/range/1/day/" + mostRecentStockTime + "?apiKey=6yr9w0ir7v0gGtri_v4LXi1ehRoAMpQd");
            CloseableHttpClient httpClient = HttpClients.createDefault();
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                ObjectMapper stockData = new ObjectMapper();
                Map<String, Object> data = stockData.readValue(responseBody, Map.class);
                List<Map<String, Object>> results = (List<Map<String, Object>>) data.get("results");
                if (results == null) {
                    currentDayMinusOne = currentDayMinusOne.minusDays(2);
                    mostRecentStockTime = currentDayMinusOne + "/" + currentDayMinusOne;
                    i--;
                    continue;
                }
                double doubledOpeningPrice;
                double doubledLowestPrice;
                double doubledHighestPrice;
                double doubledClosingPrice;
                double doubledVolume;
                long longTransactionalValue;
                if (results.getFirst().get("o") == Integer.class) {
                    Number openingPrice = (Number) results.getFirst().get("o");
                    doubledOpeningPrice = openingPrice.doubleValue();
                } else {
                    doubledOpeningPrice = (double) results.getFirst().get("o");
                }
                if (results.getFirst().get("l").getClass() == Integer.class) {
                    Number lowestPrice = (Number) results.getFirst().get("l");
                    doubledLowestPrice = lowestPrice.doubleValue();
                } else {
                    doubledLowestPrice = (double) results.getFirst().get("l");
                }
                if (results.getFirst().get("h").getClass() == Integer.class) {
                    Number lowestPrice = (Number) results.getFirst().get("h");
                    doubledHighestPrice = lowestPrice.doubleValue();
                } else {
                    doubledHighestPrice = (double) results.getFirst().get("h");
                }
                if (results.getFirst().get("c").getClass() == Integer.class) {
                    Number lowestPrice = (Number) results.getFirst().get("c");
                    doubledClosingPrice = lowestPrice.doubleValue();
                } else {
                   doubledClosingPrice = (double) results.getFirst().get("c");
                }
                if (results.getFirst().get("v").getClass() == Integer.class) {
                    Number lowestPrice = (Number) results.getFirst().get("v");
                    doubledVolume = lowestPrice.doubleValue();
                } else {
                    doubledVolume = (double) results.getFirst().get("v");
                }
                if (results.getFirst().get("t").getClass() == Integer.class){
                    Number transactions = (Number) results.getFirst().get("t");
                    longTransactionalValue = transactions.longValue();
                } else {
                   longTransactionalValue = (long) results.getFirst().get("t");
                }
                Stock newStock = new Stock(
                        stockList.get(i).toString(),
                        doubledLowestPrice,
                        doubledHighestPrice,
                        doubledClosingPrice,
                        doubledVolume,
                        doubledOpeningPrice,
                        longTransactionalValue);
                stockMap.put(stockList.get(i).toString(), newStock);
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
