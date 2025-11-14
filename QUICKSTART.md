# Quick Start Guide

## Get Running in 3 Steps

### For macOS/Linux:

```bash
cd ~/StockTradingApp
./compile.sh
./run.sh
```

### For Windows:

```cmd
cd StockTradingApp
compile.bat
run.bat
```

## First Time Use

1. **Register Account**
   - Click "Register" button
   - Username: `demo`
   - Password: `demo123`
   - Email: `demo@example.com`

2. **Login**
   - Use credentials above
   - You'll start with $10,000

3. **Trade Stocks**
   - View stocks in "Market" tab
   - Select AAPL (Apple)
   - Click "Buy Stock"
   - Enter quantity: `5`
   - Check "Portfolio" tab to see your holdings

## Key Features to Try

### Stock Prediction
1. Go to "Market" tab
2. Select any stock
3. Click "View Prediction Details"
4. See trend prediction (UP/DOWN/NEUTRAL) and confidence score

### Portfolio Management
1. Buy different stocks with various quantities
2. Go to "Portfolio" tab to see all holdings
3. Select a stock and click "Sell Stock"
4. Watch your cash balance update

### Transaction History
1. Go to "Transactions" tab
2. View all your buy/sell activity
3. See prices and timestamps

## Topics Demonstrated

✅ **Threads**: Stock data fetched concurrently using thread pools  
✅ **Inheritance**: User extends Person abstract class  
✅ **RESTful APIs**: HTTP client for stock data (mock implementation)  
✅ **Packages**: Organized code structure with imports  
✅ **Interfaces**: AuthService, PortfolioService, StockPredictor  
✅ **Collections**: List, Map, Set used throughout  
✅ **Generics**: Type-safe collections everywhere  

## Code Locations for Review

| Topic | File Location |
|-------|--------------|
| Threads | `src/com/stocktrading/api/StockAPIClient.java` |
| Inheritance | `src/com/stocktrading/models/Person.java` and `User.java` |
| Interfaces | `src/com/stocktrading/interfaces/` |
| Collections | `src/com/stocktrading/models/Portfolio.java` |
| Database (JDBC) | `src/com/stocktrading/database/DatabaseManager.java` |
| GUI | `src/com/stocktrading/gui/` |
| Prediction | `src/com/stocktrading/services/TrendPredictionService.java` |

## Troubleshooting

**Issue**: `sqlite-jdbc.jar not found`  
**Solution**: Run script downloads it automatically. If fails, download from:  
https://github.com/xerial/sqlite-jdbc/releases/download/3.43.0.0/sqlite-jdbc-3.43.0.0.jar

**Issue**: Compilation error  
**Solution**: Ensure JDK 11+ installed: `java -version`

**Issue**: GUI doesn't appear  
**Solution**: Make sure DISPLAY is set on Linux, or you're on graphical environment

## Project Statistics

- **Total Files**: 17 Java classes
- **Lines of Code**: ~2,500+
- **Packages**: 6 (models, services, interfaces, api, database, gui)
- **Design Patterns**: Interface, Service Layer, DAO
- **Technologies**: Java Swing, JDBC, SQLite, Threads

## Next Steps

1. Explore the code to understand implementations
2. Try modifying prediction algorithms
3. Add new stock symbols to the list
4. Implement real API integration
5. Enhance the GUI with charts/graphs

Happy Trading! 📈
