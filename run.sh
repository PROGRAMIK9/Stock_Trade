#!/bin/bash

# Run script for Stock Trading Application

echo "Starting Stock Trading Application..."

# Check if compiled
if [ ! -d "bin" ]; then
    echo "Application not compiled. Running compile script..."
    ./compile.sh
fi

# Download SQLite JDBC driver if not present
if [ ! -f "sqlite-jdbc.jar" ]; then
    echo "Downloading SQLite JDBC driver..."
    curl -L -o sqlite-jdbc.jar https://github.com/xerial/sqlite-jdbc/releases/download/3.43.0.0/sqlite-jdbc-3.43.0.0.jar
fi

# Run the application
java -cp bin:sqlite-jdbc.jar com.stocktrading.Main
