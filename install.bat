@echo off

echo ==================================
echo Secure File Encrypter Installer
echo ==================================

java -version >nul 2>&1

if errorlevel 1 (
    echo Java is not installed.
    echo Please install Java 17 or later.
    pause
    exit /b
)

mkdir C:\tools 2>nul

copy /Y SecureFileEncrypter.jar C:\tools\SecureFileEncrypter.jar >nul

(
echo @echo off
echo java -jar "C:\tools\SecureFileEncrypter.jar" %%*
) > C:\tools\sfe.bat

echo.
echo Installation complete.
echo.
echo Run:
echo C:\tools\sfe.bat --help
echo.

pause