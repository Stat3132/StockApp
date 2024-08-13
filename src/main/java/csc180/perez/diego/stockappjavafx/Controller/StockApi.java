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
                double doubledOpeningPrice = (double) results.get(i).get("o");
                double doubledLowestPrice = (double) results.get(i).get("l");
                double doubledHighestPrice = (double) results.get(i).get("h");
                double doubledClosingPrice = (double) results.get(i).get("c");
                double doubledVolume = (double) results.get(i).get("v");
                if (results.get(i).get("o") == Integer.class) {
                    Number openingPrice = (Number) results.get(0).get("o");
                    doubledOpeningPrice = openingPrice.doubleValue();
                }
                if (results.get(i).get("l") == Integer.class) {
                    Number lowestPrice = (Number) results.get(0).get("l");
                    doubledLowestPrice = lowestPrice.doubleValue();
                }
                if (results.get(i).get("h") == Integer.class) {
                    Number lowestPrice = (Number) results.get(0).get("h");
                    doubledHighestPrice = lowestPrice.doubleValue();
                }
                if (results.get(i).get("c") == Integer.class) {
                    Number lowestPrice = (Number) results.get(0).get("c");
                    doubledClosingPrice = lowestPrice.doubleValue();
                }
                if (results.get(i).get("v") == Integer.class) {
                    Number lowestPrice = (Number) results.get(0).get("v");
                    doubledVolume = lowestPrice.doubleValue();
                }
                Stock newStock = new Stock(
                        stockList.get(i).toString(),
                        doubledLowestPrice,
                        doubledHighestPrice,
                        doubledClosingPrice,
                        doubledVolume,
                        doubledOpeningPrice,
                        (long) results.get(i).get("t"));
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
