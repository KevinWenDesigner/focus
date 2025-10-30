# HIS Invoice System - PowerShell Startup Script
# ==================================================

Write-Host ""
Write-Host "================================================" -ForegroundColor Cyan
Write-Host "  HIS Invoice System - Quick Start" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan
Write-Host ""

# Refresh environment variables
Write-Host "[Info] Refreshing environment variables..." -ForegroundColor Yellow
$env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" + [System.Environment]::GetEnvironmentVariable("Path","User")

# Check for Maven
$mavenCmd = Get-Command mvn -ErrorAction SilentlyContinue

if (-not $mavenCmd) {
    # Try Chocolatey Maven location
    $chocoMaven = "C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.11\bin\mvn.cmd"
    if (Test-Path $chocoMaven) {
        Write-Host "[OK] Found Maven (Chocolatey installation)" -ForegroundColor Green
        $mavenCmd = $chocoMaven
    } else {
        Write-Host "[ERROR] Maven not found!" -ForegroundColor Red
        Write-Host ""
        Write-Host "Please install Maven first:" -ForegroundColor Yellow
        Write-Host "  choco install maven -y"
        Write-Host ""
        Write-Host "Or download from: https://maven.apache.org/download.cgi"
        Write-Host ""
        pause
        exit 1
    }
} else {
    Write-Host "[OK] Maven found" -ForegroundColor Green
    $mavenCmd = "mvn"
}

# Display Maven version
Write-Host ""
& $mavenCmd -version
Write-Host ""

# Set Oracle charset environment
Write-Host "[Config] Setting Oracle database charset..." -ForegroundColor Yellow
$env:NLS_LANG = "AMERICAN_AMERICA.ZHS16GBK"
$env:JAVA_TOOL_OPTIONS = "-Dfile.encoding=GBK -Doracle.jdbc.defaultNChar=false -Duser.language=zh -Duser.country=CN"

Write-Host "  NLS_LANG = $env:NLS_LANG" -ForegroundColor Gray
Write-Host "  Note: Using GBK encoding for Oracle database" -ForegroundColor Gray
Write-Host ""

# Start Spring Boot application
Write-Host "[Start] Starting Spring Boot application..." -ForegroundColor Green
Write-Host ""
Write-Host "================================================" -ForegroundColor Cyan
Write-Host "  Application Starting" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Please wait... first run may take several minutes" -ForegroundColor Yellow
Write-Host "Maven will download dependencies automatically" -ForegroundColor Yellow
Write-Host ""
Write-Host "Watch the console output for:" -ForegroundColor Yellow
Write-Host "  - Maven downloading dependencies"
Write-Host "  - Compiling Java source code"
Write-Host "  - Database connection status"
Write-Host "  - 'Started InvoiceApplication' message"
Write-Host ""
Write-Host "When you see 'Started InvoiceApplication', the server is ready!" -ForegroundColor Green
Write-Host "Access: http://localhost:8080" -ForegroundColor Cyan
Write-Host ""
Write-Host "Press Ctrl+C to stop the application" -ForegroundColor Yellow
Write-Host ""

# Run Maven
& $mavenCmd clean spring-boot:run



