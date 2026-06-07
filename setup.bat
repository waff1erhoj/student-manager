@echo off
chcp 65001 >nul
echo ========================================
echo   Student Manager - Windows Setup
echo ========================================
echo.

REM Check Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java not found. Please install JDK 17+ first.
    echo         Download: https://adoptium.net
    pause
    exit /b 1
)
echo [OK] Java detected

REM Check Maven
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Maven not found. Please install Maven first.
    echo         Download: https://maven.apache.org/download.cgi
    pause
    exit /b 1
)
echo [OK] Maven detected

REM Check MySQL
mysql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] MySQL not found. Please install MySQL first.
    echo         Download: https://dev.mysql.com/downloads/mysql
    pause
    exit /b 1
)
echo [OK] MySQL detected

echo.
echo [INFO] Initializing database...
echo Please enter your MySQL root password when prompted.

mysql -u root -p < sql\init.sql
if %errorlevel% neq 0 (
    echo [WARN] Database init may have failed. Check your root password.
)

echo.
echo [INFO] Creating application user...
mysql -u root -p -e "CREATE USER IF NOT EXISTS 'student_app'@'localhost' IDENTIFIED BY 'student123'; GRANT ALL PRIVILEGES ON student_manager.* TO 'student_app'@'localhost'; FLUSH PRIVILEGES;"

echo.
echo [INFO] Building project (first time downloads dependencies, please wait)...
mvn clean package -q
if %errorlevel% neq 0 (
    echo [ERROR] Build failed.
    pause
    exit /b 1
)
echo [OK] Build successful

echo.
echo ========================================
echo   Starting Tomcat server...
echo   Open: http://localhost:8080/student-manager/students?action=list
echo   Press Ctrl+C to stop
echo ========================================
echo.

mvn tomcat7:run
