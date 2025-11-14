@echo off
REM Run script for Stock Trading Application (Windows)

echo Starting Stock Trading Application...

REM Check if compiled
if not exist bin (
    echo Application not compiled. Running compile script...
    call compile.bat
)

REM Download SQLite JDBC driver if not present
if not exist sqlite-jdbc.jar (
    echo Downloading SQLite JDBC driver...
    echo Please download manually from:
    echo https://github.com/xerial/sqlite-jdbc/releases/download/3.43.0.0/sqlite-jdbc-3.43.0.0.jar
    echo Place it in the project root directory and run this script again.
    pause
    exit /b 1
)

REM Run the application
java -cp "bin;sqlite-jdbc.jar" com.stocktrading.Main
