package csc180.perez.diego.stockappjavafx.Model;

public class Stock {
    private String ticket;
    private double lowestPrice;
    private double highestPrice;
    private double currentClosingPrice;
    private double volume;
    private double openingPrice; //What I will choose as the value people buy stocks at.
    private long amountOfTransactions;

    public Stock(String ticket, double lowestPrice, double highestPrice, double currentClosingPrice, double volume, double openingPrice, long amountOfTransactions) {
        this.ticket = ticket;
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
        this.currentClosingPrice = currentClosingPrice;
        this.volume = volume;
        this.openingPrice = openingPrice;
        this.amountOfTransactions = amountOfTransactions;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        if (ticket != null || !ticket.isEmpty()) {
            this.ticket = ticket;
        } else {
            throw new IllegalArgumentException("Ticket cannot be null or empty");
        }
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(double highestPrice) {
        this.highestPrice = highestPrice;
    }

    public double getCurrentClosingPrice() {
        return currentClosingPrice;
    }

    public void setCurrentClosingPrice(double currentClosingPrice) {
        this.currentClosingPrice = currentClosingPrice;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(double openingPrice) {
        this.openingPrice = openingPrice;
    }

    public long getAmountOfTransactions() {
        return amountOfTransactions;
    }

    public void setAmountOfTransactions(long amountOfTransactions) {
        this.amountOfTransactions = amountOfTransactions;
    }

    @Override
    public String toString() {
        StringBuilder stockStringBuilder = new StringBuilder();
        stockStringBuilder.append("     Ticket: ").append(ticket).append("\n");
        stockStringBuilder.append("Lowest price: ").append(lowestPrice).append("\n");
        stockStringBuilder.append("Highest price: ").append(highestPrice).append("\n");
        stockStringBuilder.append("Current closing price: ").append(currentClosingPrice).append("\n");
        stockStringBuilder.append("Volume: ").append(volume).append("\n");
        stockStringBuilder.append("Opening price: ").append(openingPrice).append("\n");
        stockStringBuilder.append("Amount of transactions: ").append(amountOfTransactions).append("\n");
        return stockStringBuilder.toString();
    }
}