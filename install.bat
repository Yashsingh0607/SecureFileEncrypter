@echo off

echo Installing Secure File Encrypter CLI...

mkdir C:\tools 2>nul

copy SecureFileEncrypter.jar C:\tools\SecureFileEncrypter.jar

echo @echo off > C:\tools\sfe.bat
echo java -jar "C:\tools\SecureFileEncrypter.jar" %%* >> C:\tools\sfe.bat

echo Installation complete!
echo You can now run:
echo sfe encrypt file.txt password