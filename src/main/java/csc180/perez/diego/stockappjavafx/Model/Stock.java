package csc180.perez.diego.stockappjavafx.Model;

public class Stock {
    private String ticket;
    private double lowestPrice;
    private double highestPrice;
    private double currentClosingPrice;
    private double volume;
    private double openingPrice; //What I will choose as the value people buy stocks at.

    public Stock(String ticket, double lowestPrice, double highestPrice, double currentClosingPrice, double volume, double openingPrice) {
        this.ticket = ticket;
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
        this.currentClosingPrice = currentClosingPrice;
        this.volume = volume;
        this.openingPrice = openingPrice;
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
}