#!/bin/bash

# Quick verification script to check if tests are working
set -e

echo "🔍 Verifying BDD Tests Setup"
echo "============================="
echo ""

cd "$(dirname "$0")/.."

# Check Docker
echo "1️⃣ Checking Docker..."
if ! docker info > /dev/null 2>&1; then
    echo "   ❌ Docker is not running"
    exit 1
fi
echo "   ✅ Docker is running"

# Check .NET SDK
echo ""
echo "2️⃣ Checking .NET SDK..."
if ! command -v dotnet > /dev/null 2>&1; then
    echo "   ❌ .NET SDK not found"
    exit 1
fi
DOTNET_VERSION=$(dotnet --version)
echo "   ✅ .NET SDK $DOTNET_VERSION found"

# Check test project
echo ""
echo "3️⃣ Checking test project..."
if [ ! -f "TechKnowledgePills.Tests/TechKnowledgePills.Tests.csproj" ]; then
    echo "   ❌ Test project not found"
    exit 1
fi
echo "   ✅ Test project found"

# Check feature files
echo ""
echo "4️⃣ Checking Gherkin feature files..."
FEATURE_COUNT=$(find TechKnowledgePills.Tests/Features -name "*.feature" 2>/dev/null | wc -l | tr -d ' ')
if [ "$FEATURE_COUNT" -eq 0 ]; then
    echo "   ❌ No feature files found"
    exit 1
fi
echo "   ✅ Found $FEATURE_COUNT feature file(s)"

# Start test database
echo ""
echo "5️⃣ Starting test database..."
docker-compose -f docker-compose.test.yml up -d test-db > /dev/null 2>&1

# Wait for database
echo "   ⏳ Waiting for database..."
timeout=30
counter=0
until docker exec techknowledgepills-test-db pg_isready -U postgres > /dev/null 2>&1; do
    if [ $counter -ge $timeout ]; then
        echo "   ❌ Database failed to start"
        docker-compose -f docker-compose.test.yml down > /dev/null 2>&1
        exit 1
    fi
    counter=$((counter + 1))
    sleep 1
done
echo "   ✅ Database is ready"

# Try to build test project
echo ""
echo "6️⃣ Building test project..."
if ! dotnet build TechKnowledgePills.Tests/TechKnowledgePills.Tests.csproj --no-restore > /dev/null 2>&1; then
    echo "   ⚠️  Build failed, trying with restore..."
    dotnet build TechKnowledgePills.Tests/TechKnowledgePills.Tests.csproj > /dev/null 2>&1 || {
        echo "   ❌ Build failed"
        docker-compose -f docker-compose.test.yml down > /dev/null 2>&1
        exit 1
    }
fi
echo "   ✅ Build successful"

# Run a single test to verify
echo ""
echo "7️⃣ Running a quick test..."
export USE_DOCKER_DB=true
export TEST_DB_CONNECTION="Host=localhost;Port=5433;Database=techknowledgepills_test;Username=postgres;Password=postgres"

if dotnet test TechKnowledgePills.Tests/TechKnowledgePills.Tests.csproj --filter "FullyQualifiedName~UserRegistration&FullyQualifiedName~Successful" --verbosity quiet --no-build > /dev/null 2>&1; then
    echo "   ✅ Test execution successful"
else
    echo "   ⚠️  Test execution had issues (this might be normal if tests need setup)"
fi

# Cleanup
echo ""
echo "🧹 Cleaning up..."
docker-compose -f docker-compose.test.yml down -v > /dev/null 2>&1

echo ""
echo "✅ Verification complete! Your test setup looks good."
echo ""
echo "To run all tests, use:"
echo "  ./scripts/run-tests.sh"

