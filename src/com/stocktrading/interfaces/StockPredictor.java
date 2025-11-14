package com.stocktrading.interfaces;

import com.stocktrading.models.Stock;
import java.util.List;

/**
 * Interface for stock prediction algorithms
 */
public interface StockPredictor {
    String predictTrend(List<Double> historicalPrices);
    double predictNextPrice(Stock stock);
    double getConfidenceScore(List<Double> prices);
}
