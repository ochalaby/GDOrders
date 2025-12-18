@echo off
REM Stel hier het poortnummer in
SET SERVER_PORT=9090

REM Start de Vaadin applicatie met de embedded JRE
SET JAVA_HOME=%~dp0jre
SET PATH=%JAVA_HOME%\bin;%PATH%

REM Run de runnable JAR in de achtergrond
start "" java -jar "%~dp0${jar.name}" --server.port=%SERVER_PORT%

REM Wacht tot de server op de opgegeven poort luistert
:waitloop
netstat -an | findstr ":%SERVER_PORT%" >nul
IF ERRORLEVEL 1 (
    REM poort nog niet open, 1 seconde wachten en opnieuw checken
    timeout /t 1 /nobreak >nul
    GOTO waitloop
)

REM Open de standaard browser op localhost:<poortnummer>
start "" "http://localhost:%SERVER_PORT%"

