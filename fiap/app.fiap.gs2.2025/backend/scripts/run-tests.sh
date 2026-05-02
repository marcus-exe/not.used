#!/bin/bash

# Script to run BDD tests with Docker
set -e

echo "🚀 Starting TechKnowledgePills BDD Tests with Docker"
echo "=================================================="

# Navigate to backend directory
cd "$(dirname "$0")/.."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker and try again."
    exit 1
fi

# Start test database
echo "📦 Starting test database container..."
docker-compose -f docker-compose.test.yml up -d test-db

# Wait for database to be ready
echo "⏳ Waiting for database to be ready..."
timeout=30
counter=0
until docker exec techknowledgepills-test-db pg_isready -U postgres > /dev/null 2>&1; do
    if [ $counter -ge $timeout ]; then
        echo "❌ Database failed to start within $timeout seconds"
        docker-compose -f docker-compose.test.yml down
        exit 1
    fi
    counter=$((counter + 1))
    sleep 1
done

echo "✅ Database is ready!"

# Set environment variable to use Docker database
export USE_DOCKER_DB=true
export TEST_DB_CONNECTION="Host=localhost;Port=5433;Database=techknowledgepills_test;Username=postgres;Password=postgres"

# Run tests
echo "🧪 Running BDD tests..."
echo ""

if command -v dotnet > /dev/null 2>&1; then
    dotnet test TechKnowledgePills.Tests/TechKnowledgePills.Tests.csproj --verbosity minimal
    test_exit_code=$?
else
    echo "❌ dotnet CLI not found. Please install .NET SDK 8.0"
    docker-compose -f docker-compose.test.yml down
    exit 1
fi

# Cleanup
echo ""
echo "🧹 Cleaning up test database..."
docker-compose -f docker-compose.test.yml down -v

if [ $test_exit_code -eq 0 ]; then
    echo ""
    echo "✅ All tests passed!"
else
    echo ""
    echo "❌ Some tests failed. Exit code: $test_exit_code"
fi

exit $test_exit_code

