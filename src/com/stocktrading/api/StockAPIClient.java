package com.stocktrading.api;

import com.stocktrading.models.Stock;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

/**
 * RESTful API Client demonstrating Threads and API integration
 * Uses free API (Alpha Vantage alternative - using mock data for simplicity)
 */
public class StockAPIClient {
    private static final String API_KEY = "demo"; // Use your API key
    private ExecutorService executorService;
    
    public StockAPIClient() {
        // Thread pool for concurrent API requests
        this.executorService = Executors.newFixedThreadPool(5);
    }
    
    /**
     * Fetch stock data using threads for concurrent requests
     */
    public Map<String, Stock> fetchMultipleStocks(List<String> symbols) {
        Map<String, Stock> stockMap = new ConcurrentHashMap<>();
        List<Future<Stock>> futures = new ArrayList<>();
        
        // Submit tasks to thread pool
        for (String symbol : symbols) {
            Future<Stock> future = executorService.submit(() -> fetchStockData(symbol));
            futures.add(future);
        }
        
        // Collect results from threads
        for (Future<Stock> future : futures) {
            try {
                Stock stock = future.get(5, TimeUnit.SECONDS);
                if (stock != null) {
                    stockMap.put(stock.getSymbol(), stock);
                }
            } catch (TimeoutException e) {
                System.err.println("Request timed out");
            } catch (Exception e) {
                System.err.println("Error fetching stock: " + e.getMessage());
            }
        }
        
        return stockMap;
    }
    
    /**
     * Fetch single stock data - can be called in thread
     */
    public Stock fetchStockData(String symbol) {
        try {
            // For demo purposes, using mock data
            // In production, you would call a real API like Alpha Vantage or Yahoo Finance
            Stock stock = generateMockStockData(symbol);
            
            // Simulate API delay
            Thread.sleep(100);
            
            return stock;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
    
    /**
     * Fetch historical data for prediction
     */
    public List<Double> fetchHistoricalPrices(String symbol, int days) {
        List<Double> prices = new ArrayList<>();
        Random random = new Random(symbol.hashCode());
        
        double basePrice = 100 + random.nextDouble() * 400;
        
        for (int i = 0; i < days; i++) {
            double change = (random.nextDouble() - 0.5) * 10;
            basePrice += change;
            prices.add(Math.max(1, basePrice));
        }
        
        return prices;
    }
    
    /**
     * Real API call example (commented out)
     */
    private String makeAPICall(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        
        return response.toString();
    }
    
    /**
     * Generate mock stock data for demo
     */
    private Stock generateMockStockData(String symbol) {
        Random random = new Random(symbol.hashCode());
        double basePrice = 50 + random.nextDouble() * 450;
        
        Stock stock = new Stock(symbol, getCompanyName(symbol), basePrice);
        stock.setOpenPrice(basePrice * (0.95 + random.nextDouble() * 0.1));
        stock.setHighPrice(basePrice * (1.0 + random.nextDouble() * 0.05));
        stock.setLowPrice(basePrice * (0.95 + random.nextDouble() * 0.05));
        stock.setVolume((long)(1000000 + random.nextInt(9000000)));
        
        // Add historical prices
        List<Double> historical = fetchHistoricalPrices(symbol, 30);
        stock.setHistoricalPrices(historical);
        
        return stock;
    }
    
    private String getCompanyName(String symbol) {
        Map<String, String> companies = new HashMap<>();
        companies.put("AAPL", "Apple Inc.");
        companies.put("GOOGL", "Alphabet Inc.");
        companies.put("MSFT", "Microsoft Corporation");
        companies.put("AMZN", "Amazon.com Inc.");
        companies.put("TSLA", "Tesla Inc.");
        companies.put("META", "Meta Platforms Inc.");
        companies.put("NVDA", "NVIDIA Corporation");
        companies.put("JPM", "JPMorgan Chase & Co.");
        companies.put("V", "Visa Inc.");
        companies.put("WMT", "Walmart Inc.");
        
        return companies.getOrDefault(symbol, symbol + " Corporation");
    }
    
    public void shutdown() {
        executorService.shutdown();
    }
}
