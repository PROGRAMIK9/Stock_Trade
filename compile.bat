@echo off
REM Compile script for Stock Trading Application (Windows)

echo Compiling Stock Trading Application...

REM Create bin directory if it doesn't exist
if not exist bin mkdir bin

REM Compile all Java files
javac -d bin -sourcepath src src\com\stocktrading\*.java ^
    src\com\stocktrading\models\*.java ^
    src\com\stocktrading\interfaces\*.java ^
    src\com\stocktrading\services\*.java ^
    src\com\stocktrading\api\*.java ^
    src\com\stocktrading\database\*.java ^
    src\com\stocktrading\gui\*.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
    echo To run the application, use: run.bat
) else (
    echo Compilation failed!
    exit /b 1
)
