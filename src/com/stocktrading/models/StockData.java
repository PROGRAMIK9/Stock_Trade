package com.stocktrading.models;

public class StockData {
    // Attributes - the data we're storing
    private String symbol;
    private String date;
    private double open;
    private double close;
    private double high;
    private double low;
    private long volume;
}

    // constructor
    public StockData(String symbol, String date, double open, double high, double low, double close, long volume) {
        this.symbol = symbol;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public double getClose() {
        return close;
    }
    public String getSymbol() {
        return symbol;
    }
    public String getDate() {
        return date;
    }
    public double getOpen() {
        return open;
    }
    public double getHigh() {
        return high;
    }
    public double getLow() {
        return low;
    }
    public double getclose() {
        return close;
    }
    public long getVolume() {
        return volume;
    }