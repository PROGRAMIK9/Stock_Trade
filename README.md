# Stock Trading Application

A comprehensive Java-based stock trading application with GUI, demonstrating key Java programming concepts.

## Project Overview

This application is a stock trading simulator that allows users to:
- Create accounts and authenticate
- View real-time stock prices
- Buy and sell stocks with virtual money ($10,000 starting balance)
- Get AI-powered stock predictions based on historical trends
- Track portfolio performance and transaction history

## Java Concepts Demonstrated

### 1. **Threads**
- Location: `StockAPIClient.java`
- Implementation: Uses `ExecutorService` and thread pools to fetch multiple stock prices concurrently
- Demonstrates: Thread creation, thread pools, `Future`, `Callable`, concurrent data structures

### 2. **Inheritance**
- Base Class: `Person.java` (abstract)
- Derived Class: `User.java` extends `Person`
- Demonstrates: Class hierarchy, `extends` keyword, `super()`, method overriding

### 3. **RESTful APIs**
- Location: `StockAPIClient.java`
- Implementation: HTTP connections using `HttpURLConnection`
- Features: Mock data generation, API call structure ready for real APIs

### 4. **Packages with Import Statements**
- Package structure: `com.stocktrading.*`
- Subpackages: `models`, `services`, `interfaces`, `api`, `database`, `gui`
- Import statements used throughout all classes

### 5. **Interfaces**
- `AuthService.java`: Authentication operations contract
- `PortfolioService.java`: Portfolio management contract
- `StockPredictor.java`: Stock prediction contract
- Implemented by service classes demonstrating polymorphism

### 6. **Collections**
- `List<T>`: Used for historical prices, transactions, stock symbols
- `Map<K,V>`: Used for stock holdings, available stocks
- `Set<T>`: Used for tracking authenticated users
- `ConcurrentHashMap`: Thread-safe collections in API client

### 7. **Generics**
- Generic collections throughout: `List<Stock>`, `Map<String, Stock>`, `Set<String>`
- Generic methods in service classes
- Type safety in data structures

## Project Structure

```
StockTradingApp/
├── src/com/stocktrading/
│   ├── Main.java                           # Application entry point
│   ├── models/                             # Data models
│   │   ├── Person.java                     # Base class (Inheritance)
│   │   ├── User.java                       # User model extends Person
│   │   ├── Stock.java                      # Stock data model
│   │   ├── Portfolio.java                  # Portfolio with Collections
│   │   └── Transaction.java                # Transaction model
│   ├── interfaces/                         # Interface definitions
│   │   ├── AuthService.java                # Authentication interface
│   │   ├── PortfolioService.java           # Portfolio interface
│   │   └── StockPredictor.java             # Prediction interface
│   ├── services/                           # Service implementations
│   │   ├── AuthenticationService.java      # Auth service impl
│   │   ├── PortfolioManagementService.java # Portfolio service impl
│   │   └── TrendPredictionService.java     # Prediction service impl
│   ├── api/                                # API client
│   │   └── StockAPIClient.java             # REST API + Threads
│   ├── database/                           # Database layer
│   │   └── DatabaseManager.java            # JDBC with SQLite
│   └── gui/                                # GUI components
│       ├── LoginFrame.java                 # Login screen
│       └── DashboardFrame.java             # Main dashboard
├── compile.sh                              # Compilation script
├── run.sh                                  # Run script
└── README.md                               # This file
```

## Features

### 1. User Authentication
- Register new accounts with username, password, and email
- Login with credentials
- Secure password storage (can be enhanced with hashing)
- Session management

### 2. Portfolio Management
- Starting balance: $10,000 virtual money
- Buy stocks at current market prices
- Sell stocks from holdings
- Track average purchase price
- View current portfolio value

### 3. Stock Prediction
- **Moving Average Analysis**: Uses short-term (5-day) and long-term (10-day) moving averages
- **Trend Detection**: Golden Cross (bullish) and Death Cross (bearish) patterns
- **Momentum Calculation**: Rate of price change analysis
- **Confidence Score**: Based on trend consistency
- **Predictions**: UP, DOWN, or NEUTRAL trend forecasts

### 4. Database Persistence
- SQLite database for local storage
- Tables: users, portfolios, holdings, transactions
- JDBC for database operations
- Automatic schema creation

### 5. GUI Features
- Clean Swing-based interface
- Three main tabs:
  - **Market**: View stocks, predictions, and buy
  - **Portfolio**: View holdings and sell stocks
  - **Transactions**: Complete transaction history
- Real-time updates
- Color-coded buttons for actions

## How to Run

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- SQLite JDBC driver (automatically downloaded by run script)

### Steps

1. **Make scripts executable:**
```bash
chmod +x compile.sh run.sh
```

2. **Compile the application:**
```bash
./compile.sh
```

3. **Run the application:**
```bash
./run.sh
```

### Alternative: Manual Compilation

```bash
# Create bin directory
mkdir -p bin

# Compile all Java files
javac -d bin -sourcepath src src/com/stocktrading/*.java \
    src/com/stocktrading/models/*.java \
    src/com/stocktrading/interfaces/*.java \
    src/com/stocktrading/services/*.java \
    src/com/stocktrading/api/*.java \
    src/com/stocktrading/database/*.java \
    src/com/stocktrading/gui/*.java

# Download SQLite JDBC driver
curl -L -o sqlite-jdbc.jar https://github.com/xerial/sqlite-jdbc/releases/download/3.43.0.0/sqlite-jdbc-3.43.0.0.jar

# Run
java -cp bin:sqlite-jdbc.jar com.stocktrading.Main
```

## Usage Guide

### First Time Setup
1. Launch the application
2. Click "Register" to create a new account
3. Fill in username, password, and email
4. Click "Login" with your credentials

### Trading Stocks
1. Go to "Market" tab to view available stocks
2. Select a stock to see its prediction
3. Click "Buy Stock" to purchase shares
4. Go to "Portfolio" tab to view your holdings
5. Select a holding and click "Sell Stock" to sell

### Understanding Predictions
- **UP**: Stock is predicted to rise (Short MA > Long MA)
- **DOWN**: Stock is predicted to fall (Short MA < Long MA)
- **NEUTRAL**: No clear trend
- Confidence score indicates reliability of prediction

## Technical Details

### Stock Prediction Algorithm
The application uses technical analysis techniques:
1. **Simple Moving Average (SMA)**: Average price over a period
2. **Golden Cross**: Short-term MA crosses above long-term MA (bullish signal)
3. **Death Cross**: Short-term MA crosses below long-term MA (bearish signal)
4. **Momentum**: Rate of change in stock price
5. **RSI (Optional)**: Relative Strength Index for overbought/oversold conditions

### Thread Management
- Uses `ExecutorService` with fixed thread pool (5 threads)
- Concurrent API calls for multiple stock symbols
- `Future` objects for result collection
- Timeout handling (5 seconds per request)

### Database Schema
```sql
users (id, username, password, name, email)
portfolios (id, user_id, cash_balance)
holdings (id, portfolio_id, symbol, quantity, average_price)
transactions (id, portfolio_id, type, symbol, quantity, price, timestamp)
```

## Extending the Application

### Adding Real API Integration
1. Get an API key from [Alpha Vantage](https://www.alphavantage.co/) or [Yahoo Finance](https://finance.yahoo.com/)
2. Modify `StockAPIClient.java`:
   - Replace `generateMockStockData()` calls with `makeAPICall()`
   - Parse JSON responses (add Gson or Jackson library)
   - Update API endpoints

### Enhancing Predictions
1. Add more indicators (RSI, MACD, Bollinger Bands)
2. Implement machine learning models
3. Use longer historical data periods
4. Add sentiment analysis from news

### Security Improvements
1. Hash passwords (use BCrypt)
2. Add input validation
3. Implement session tokens
4. Add HTTPS for API calls

## Known Limitations

1. **Mock Data**: Currently uses simulated stock prices
2. **Simplified Prediction**: Basic moving average strategy
3. **Local Database**: SQLite for single-user usage
4. **No Real-time Updates**: Manual refresh required

## Educational Value

This project covers essential Java concepts:
- ✅ Object-Oriented Programming
- ✅ Multithreading and Concurrency
- ✅ GUI Development with Swing
- ✅ Database Operations with JDBC
- ✅ API Integration patterns
- ✅ Design Patterns (Interface, Factory, Service Layer)
- ✅ Collections Framework
- ✅ Exception Handling
- ✅ File I/O and Persistence

## License

This project is created for educational purposes.

## Credits

Developed as a class project demonstrating comprehensive Java programming concepts.
