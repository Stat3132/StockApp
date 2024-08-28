package csc180.perez.diego.stockappjavafx.Controller;

import csc180.perez.diego.stockappjavafx.Model.Stock;
import csc180.perez.diego.stockappjavafx.UTIL.StockTicker;
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

    //region stockApiVariables
    private static final LocalDate currentDay = LocalDate.now();
    private static LocalDate currentDayMinusOne = currentDay.minusDays(1);
    private static String mostRecentStockTime = currentDayMinusOne + "/" + currentDayMinusOne;
    private static Map<String, Stock> stockValuesMap = new HashMap<>();
    private static ArrayList<StockTicker> enumOfStockNames = new ArrayList<>();
    private static Map<String, List<Map<String, Stock>>> historicalStockValueMap = new HashMap<>();
    private static String timePeriod = "";
    //endregion
    public static Map<String, List<Map<String, Stock>>> getHistoricalStockValueMap() {
        return historicalStockValueMap;
    }
    public static void clearHistoricalStockValueMap(){
        historicalStockValueMap.clear();
    }

    //region original accessingApi
    public static void  accessingAPI() {
        enumOfStockNames.addAll(Arrays.asList(StockTicker.values()));
        for (int currentStock = 0; currentStock < 5; currentStock++) {
            HttpGet stockAPIRequest = new HttpGet("https://api.polygon.io/v2/aggs/ticker/" + enumOfStockNames.get(currentStock) + "/range/1/day/" + mostRecentStockTime + "?apiKey=6yr9w0ir7v0gGtri_v4LXi1ehRoAMpQd");
            CloseableHttpClient httpClient = HttpClients.createDefault();
            try (CloseableHttpResponse response = httpClient.execute(stockAPIRequest)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                ObjectMapper stockData = new ObjectMapper();
                Map<String, Object> mappedStockData = stockData.readValue(responseBody, Map.class);
                List<Map<String, Object>> listOfStockData = (List<Map<String, Object>>) mappedStockData.get("results");
                if (listOfStockData == null) {
                    currentDayMinusOne = currentDayMinusOne.minusDays(2);
                    mostRecentStockTime = currentDayMinusOne + "/" + currentDayMinusOne;
                    currentStock--;
                    continue;
                }
                buildStockMap(enumOfStockNames.get(currentStock), listOfStockData, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Stock stockValues: stockValuesMap.values()) {
            DatabaseController.createStocks(stockValues);
            System.out.println(stockValues.toString());
        }
    }
    //endregion

    //region overLoaded accessingAPI
    public static void accessingAPI(StockTicker stockTicker, String timeSpan, int timeBeforeCurrentDay){
        //todo one week, 1 month, 3 months, six months, one year, two years
        LocalDate timePeriodBefore;
        switch(timeSpan.toLowerCase()){
            case "month" -> {
                if (timeBeforeCurrentDay == 6) {
                    timePeriodBefore = currentDay.minusMonths(timeBeforeCurrentDay);
                }else if (timeBeforeCurrentDay == 3){
                    timePeriodBefore = currentDay.minusMonths(3);
                }else{
                    timePeriodBefore = currentDay.minusMonths(1);
                }
            }
            case "week" ->
                    timePeriodBefore = currentDay.minusWeeks(1);
            case "year" -> {
                if(timeBeforeCurrentDay == 1){
                    timePeriodBefore = currentDay.minusYears(1);
                }else if(timeBeforeCurrentDay == 2){
                    timePeriodBefore = currentDay.minusYears(2);
                }else{
                    timePeriodBefore = currentDay.minusYears(1);
                }
            }
            default -> {
                System.out.println("timespan must be month, week, or year");
                return;
            }
        }
        String formattedApi = String.format("https://api.polygon.io/v2/aggs/ticker/%s/range/1/%s/%s/%s?apiKey=6yr9w0ir7v0gGtri_v4LXi1ehRoAMpQd", stockTicker.toString(),timeSpan.toLowerCase(),timePeriodBefore,currentDayMinusOne.toString());
        HttpGet apiRequest = new HttpGet(formattedApi);
        CloseableHttpClient apiClient = HttpClients.createDefault();
        try (CloseableHttpResponse response = apiClient.execute(apiRequest)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            ObjectMapper stockData = new ObjectMapper();
            Map<String, Object> mappedStockData = stockData.readValue(responseBody, Map.class);
            List<Map<String, Object>> listOfStockData = (List<Map<String, Object>>) mappedStockData.get("results");
            timePeriod = timeSpan.toLowerCase();
            buildStockMap(stockTicker, listOfStockData, true);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region stock builder
    public static void buildStockMap(StockTicker stock, List<Map<String, Object>> listOfStockData, boolean makeHistoricalData){
        Stock newStock = null;
        int period = 0;
        Map<String, Stock> stockPrices = new HashMap<>();
        List<Map<String, Stock>> periodStockList = new ArrayList<>();
        for (Map<String, Object> stockData : listOfStockData) {
            double doubledOpeningPrice = 0;
            double doubledLowestPrice = 0;
            double doubledHighestPrice = 0;
            double doubledClosingPrice = 0;
            double doubledVolume = 0;
            long longTransactionalValue = 0;
            for (Map.Entry<String, Object> entry : stockData.entrySet()) {
                switch (entry.getKey().toLowerCase()) {
                    case "v" -> {
                        doubledVolume = checkIfInteger(entry);
                    }
                    case "o" -> {
                        doubledOpeningPrice = checkIfInteger(entry);
                    }
                    case "l" -> {
                        doubledLowestPrice = checkIfInteger(entry);
                    }
                    case "h" -> {
                        doubledHighestPrice = checkIfInteger(entry);
                    }
                    case "c" -> {
                        doubledClosingPrice = checkIfInteger(entry);
                    }
                    case "n" -> {
                        if (entry.getValue().getClass() == Integer.class) {
                            Number transactions = (Number) entry.getValue();
                            longTransactionalValue = transactions.longValue();
                        } else {
                            longTransactionalValue = (long) entry.getValue();
                        }
                    }
                    default -> {

                    }
                }
            }
            if(makeHistoricalData){
                period++;
                newStock = new Stock(
                        stock.toString(),
                        doubledLowestPrice,
                        doubledHighestPrice,
                        doubledClosingPrice,
                        doubledVolume,
                        doubledOpeningPrice,
                        longTransactionalValue);
                stockPrices.put(String.format("%s: %s", timePeriod.toLowerCase(), period), newStock);

            }else{
                newStock = new Stock(
                        stock.toString(),
                        doubledLowestPrice,
                        doubledHighestPrice,
                        doubledClosingPrice,
                        doubledVolume,
                        doubledOpeningPrice,
                        longTransactionalValue);
                stockValuesMap.put(stock.toString(), newStock);
            }
        }
        if(makeHistoricalData) {
            periodStockList.add(stockPrices);
            historicalStockValueMap.put(stock.toString(), periodStockList);
        }
    }
    //endregion

    //region checkIfIntegerValue
    public static double checkIfInteger(Map.Entry<String, Object> entry){
        if(entry.getValue().getClass() == Integer.class){
            Number entryValue = (Number) entry.getValue();
            return entryValue.doubleValue();
        }else{
            return (double) entry.getValue();
        }
    }
    //endregion

    public void stockSearch(String stockName) {
    }
}

