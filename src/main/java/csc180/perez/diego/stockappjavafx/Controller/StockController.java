package csc180.perez.diego.stockappjavafx.Controller;

public class StockController {
    public void startUp(){
        StockApi stockApi = new StockApi();
        stockApi.accessingAPI();
    }
}
