package com.stocktrading.database;

import com.stocktrading.models.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * Database manager using JDBC and SQLite
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:stocktrading.db";
    private Connection connection;
    
    public DatabaseManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
            initializeTables();
        } catch (Exception e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }
    
    private void initializeTables() throws SQLException {
        Statement stmt = connection.createStatement();
        
        // Users table
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT UNIQUE NOT NULL, " +
            "password TEXT NOT NULL, " +
            "name TEXT NOT NULL, " +
            "email TEXT NOT NULL)"
        );
        
        // Portfolios table
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS portfolios (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "user_id INTEGER NOT NULL, " +
            "cash_balance REAL NOT NULL, " +
            "FOREIGN KEY(user_id) REFERENCES users(id))"
        );
        
        // Holdings table
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS holdings (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "portfolio_id INTEGER NOT NULL, " +
            "symbol TEXT NOT NULL, " +
            "quantity INTEGER NOT NULL, " +
            "average_price REAL NOT NULL, " +
            "FOREIGN KEY(portfolio_id) REFERENCES portfolios(id))"
        );
        
        // Transactions table
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS transactions (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "portfolio_id INTEGER NOT NULL, " +
            "type TEXT NOT NULL, " +
            "symbol TEXT NOT NULL, " +
            "quantity INTEGER NOT NULL, " +
            "price REAL NOT NULL, " +
            "timestamp TEXT NOT NULL, " +
            "FOREIGN KEY(portfolio_id) REFERENCES portfolios(id))"
        );
        
        stmt.close();
    }
    
    // User operations
    public int createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, name, email) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, user.getUsername());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
        pstmt.executeUpdate();
        
        ResultSet rs = pstmt.getGeneratedKeys();
        int userId = rs.next() ? rs.getInt(1) : -1;
        pstmt.close();
        
        // Create portfolio for user
        if (userId > 0) {
            createPortfolio(userId, 10000.0);
        }
        
        return userId;
    }
    
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        
        User user = null;
        if (rs.next()) {
            user = new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email")
            );
        }
        
        pstmt.close();
        return user;
    }
    
    // Portfolio operations
    public int createPortfolio(int userId, double initialCash) throws SQLException {
        String sql = "INSERT INTO portfolios (user_id, cash_balance) VALUES (?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, userId);
        pstmt.setDouble(2, initialCash);
        pstmt.executeUpdate();
        
        ResultSet rs = pstmt.getGeneratedKeys();
        int portfolioId = rs.next() ? rs.getInt(1) : -1;
        pstmt.close();
        
        return portfolioId;
    }
    
    public Portfolio getPortfolioByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM portfolios WHERE user_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, userId);
        ResultSet rs = pstmt.executeQuery();
        
        Portfolio portfolio = null;
        if (rs.next()) {
            portfolio = new Portfolio(rs.getInt("id"), rs.getDouble("cash_balance"));
            loadHoldings(portfolio);
            loadTransactions(portfolio);
        }
        
        pstmt.close();
        return portfolio;
    }
    
    public void updatePortfolioCash(int portfolioId, double cashBalance) throws SQLException {
        String sql = "UPDATE portfolios SET cash_balance = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setDouble(1, cashBalance);
        pstmt.setInt(2, portfolioId);
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    private void loadHoldings(Portfolio portfolio) throws SQLException {
        String sql = "SELECT * FROM holdings WHERE portfolio_id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, portfolio.getId());
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            portfolio.addHolding(
                rs.getString("symbol"),
                rs.getInt("quantity"),
                rs.getDouble("average_price")
            );
        }
        
        pstmt.close();
    }
    
    private void loadTransactions(Portfolio portfolio) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE portfolio_id = ? ORDER BY timestamp DESC LIMIT 50";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, portfolio.getId());
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            Transaction transaction = new Transaction(
                rs.getInt("id"),
                rs.getString("type"),
                rs.getString("symbol"),
                rs.getInt("quantity"),
                rs.getDouble("price"),
                LocalDateTime.parse(rs.getString("timestamp"))
            );
            portfolio.addTransaction(transaction);
        }
        
        pstmt.close();
    }
    
    public void saveHolding(int portfolioId, String symbol, int quantity, double avgPrice) throws SQLException {
        // Check if holding exists
        String checkSql = "SELECT id, quantity, average_price FROM holdings WHERE portfolio_id = ? AND symbol = ?";
        PreparedStatement checkStmt = connection.prepareStatement(checkSql);
        checkStmt.setInt(1, portfolioId);
        checkStmt.setString(2, symbol);
        ResultSet rs = checkStmt.executeQuery();
        
        if (rs.next()) {
            // Update existing holding
            int existingQty = rs.getInt("quantity");
            double existingAvg = rs.getDouble("average_price");
            int newQty = existingQty + quantity;
            double newAvg = ((existingQty * existingAvg) + (quantity * avgPrice)) / newQty;
            
            String updateSql = "UPDATE holdings SET quantity = ?, average_price = ? WHERE portfolio_id = ? AND symbol = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateSql);
            updateStmt.setInt(1, newQty);
            updateStmt.setDouble(2, newAvg);
            updateStmt.setInt(3, portfolioId);
            updateStmt.setString(4, symbol);
            updateStmt.executeUpdate();
            updateStmt.close();
        } else {
            // Insert new holding
            String insertSql = "INSERT INTO holdings (portfolio_id, symbol, quantity, average_price) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertSql);
            insertStmt.setInt(1, portfolioId);
            insertStmt.setString(2, symbol);
            insertStmt.setInt(3, quantity);
            insertStmt.setDouble(4, avgPrice);
            insertStmt.executeUpdate();
            insertStmt.close();
        }
        
        checkStmt.close();
    }
    
    public void updateHolding(int portfolioId, String symbol, int newQuantity) throws SQLException {
        if (newQuantity <= 0) {
            String deleteSql = "DELETE FROM holdings WHERE portfolio_id = ? AND symbol = ?";
            PreparedStatement pstmt = connection.prepareStatement(deleteSql);
            pstmt.setInt(1, portfolioId);
            pstmt.setString(2, symbol);
            pstmt.executeUpdate();
            pstmt.close();
        } else {
            String updateSql = "UPDATE holdings SET quantity = ? WHERE portfolio_id = ? AND symbol = ?";
            PreparedStatement pstmt = connection.prepareStatement(updateSql);
            pstmt.setInt(1, newQuantity);
            pstmt.setInt(2, portfolioId);
            pstmt.setString(3, symbol);
            pstmt.executeUpdate();
            pstmt.close();
        }
    }
    
    public void saveTransaction(int portfolioId, Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (portfolio_id, type, symbol, quantity, price, timestamp) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, portfolioId);
        pstmt.setString(2, transaction.getType());
        pstmt.setString(3, transaction.getSymbol());
        pstmt.setInt(4, transaction.getQuantity());
        pstmt.setDouble(5, transaction.getPrice());
        pstmt.setString(6, transaction.getTimestamp().toString());
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database: " + e.getMessage());
        }
    }
}
