# PowerShell script to run BDD tests with Docker

Write-Host "🚀 Starting TechKnowledgePills BDD Tests with Docker" -ForegroundColor Cyan
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host ""

# Navigate to backend directory
$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location (Join-Path $scriptPath "..")

# Check if Docker is running
try {
    docker info | Out-Null
} catch {
    Write-Host "❌ Docker is not running. Please start Docker and try again." -ForegroundColor Red
    exit 1
}

# Start test database
Write-Host "📦 Starting test database container..." -ForegroundColor Yellow
docker-compose -f docker-compose.test.yml up -d test-db

# Wait for database to be ready
Write-Host "⏳ Waiting for database to be ready..." -ForegroundColor Yellow
$timeout = 30
$counter = 0
$ready = $false

while ($counter -lt $timeout) {
    try {
        docker exec techknowledgepills-test-db pg_isready -U postgres | Out-Null
        $ready = $true
        break
    } catch {
        Start-Sleep -Seconds 1
        $counter++
    }
}

if (-not $ready) {
    Write-Host "❌ Database failed to start within $timeout seconds" -ForegroundColor Red
    docker-compose -f docker-compose.test.yml down
    exit 1
}

Write-Host "✅ Database is ready!" -ForegroundColor Green
Write-Host ""

# Set environment variables to use Docker database
$env:USE_DOCKER_DB = "true"
$env:TEST_DB_CONNECTION = "Host=localhost;Port=5433;Database=techknowledgepills_test;Username=postgres;Password=postgres"

# Run tests
Write-Host "🧪 Running BDD tests..." -ForegroundColor Yellow
Write-Host ""

if (Get-Command dotnet -ErrorAction SilentlyContinue) {
    dotnet test TechKnowledgePills.Tests/TechKnowledgePills.Tests.csproj --verbosity normal
    $testExitCode = $LASTEXITCODE
} else {
    Write-Host "❌ dotnet CLI not found. Please install .NET SDK 8.0" -ForegroundColor Red
    docker-compose -f docker-compose.test.yml down
    exit 1
}

# Cleanup
Write-Host ""
Write-Host "🧹 Cleaning up test database..." -ForegroundColor Yellow
docker-compose -f docker-compose.test.yml down -v

if ($testExitCode -eq 0) {
    Write-Host ""
    Write-Host "✅ All tests passed!" -ForegroundColor Green
} else {
    Write-Host ""
    Write-Host "❌ Some tests failed. Exit code: $testExitCode" -ForegroundColor Red
}

exit $testExitCode

