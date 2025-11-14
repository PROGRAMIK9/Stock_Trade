#!/bin/bash

# Compile script for Stock Trading Application

echo "Compiling Stock Trading Application..."

# Create bin directory if it doesn't exist
mkdir -p bin

# Compile all Java files
javac -d bin -sourcepath src src/com/stocktrading/*.java \
    src/com/stocktrading/models/*.java \
    src/com/stocktrading/interfaces/*.java \
    src/com/stocktrading/services/*.java \
    src/com/stocktrading/api/*.java \
    src/com/stocktrading/database/*.java \
    src/com/stocktrading/gui/*.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "To run the application, use: ./run.sh"
else
    echo "Compilation failed!"
    exit 1
fi
