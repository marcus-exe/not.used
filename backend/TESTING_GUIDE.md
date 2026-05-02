# BDD Testing Guide with Docker

This guide explains how to verify and run your Gherkin BDD tests using Docker.

## Quick Start

### 1. Verify Test Setup

Run the verification script to check if everything is configured correctly:

```bash
cd backend
./scripts/verify-tests.sh
```

This script will:
- ✅ Check if Docker is running
- ✅ Check if .NET SDK is installed
- ✅ Verify test project exists
- ✅ Count feature files
- ✅ Start test database
- ✅ Build test project
- ✅ Run a sample test
- ✅ Clean up

### 2. Run All Tests

#### Option A: Using the Script (Recommended)

**macOS/Linux:**
```bash
cd backend
./scripts/run-tests.sh
```

**Windows (PowerShell):**
```powershell
cd backend
.\scripts\run-tests.ps1
```

#### Option B: Manual Steps

```bash
# 1. Start test database
docker-compose -f docker-compose.test.yml up -d test-db

# 2. Wait for database (check status)
docker exec techknowledgepills-test-db pg_isready -U postgres

# 3. Set environment variables
export USE_DOCKER_DB=true
export TEST_DB_CONNECTION="Host=localhost;Port=5433;Database=techknowledgepills_test;Username=postgres;Password=postgres"

# 4. Run tests
dotnet test TechKnowledgePills.Tests/TechKnowledgePills.Tests.csproj --verbosity normal

# 5. Clean up
docker-compose -f docker-compose.test.yml down -v
```

## Test Database Configuration

The test database runs on **port 5433** to avoid conflicts with your main database (port 5432).

- **Container name**: `techknowledgepills-test-db`
- **Database**: `techknowledgepills_test`
- **User**: `postgres`
- **Password**: `postgres`
- **Port**: `5433` (host) → `5432` (container)

## Running Specific Tests

### Run a Single Feature

```bash
export USE_DOCKER_DB=true
dotnet test TechKnowledgePills.Tests/TechKnowledgePills.Tests.csproj \
  --filter "FullyQualifiedName~UserRegistration" \
  --verbosity normal
```

### Run Tests Matching a Pattern

```bash
export USE_DOCKER_DB=true
dotnet test TechKnowledgePills.Tests/TechKnowledgePills.Tests.csproj \
  --filter "FullyQualifiedName~Login" \
  --verbosity normal
```

## Troubleshooting

### Docker Not Running

```bash
# Check Docker status
docker info

# Start Docker Desktop if needed
```

### Database Connection Issues

```bash
# Check if database container is running
docker ps | grep test-db

# Check database logs
docker logs techknowledgepills-test-db

# Restart database
docker-compose -f docker-compose.test.yml restart test-db
```

### Port Already in Use

If port 5433 is already in use, you can change it in `docker-compose.test.yml`:

```yaml
ports:
  - "5434:5432"  # Change to a different port
```

And update the connection string:
```bash
export TEST_DB_CONNECTION="Host=localhost;Port=5434;Database=techknowledgepills_test;Username=postgres;Password=postgres"
```

### Tests Fail with Database Errors

1. Make sure the database is fully started:
   ```bash
   docker exec techknowledgepills-test-db pg_isready -U postgres
   ```

2. Clean up and restart:
   ```bash
   docker-compose -f docker-compose.test.yml down -v
   docker-compose -f docker-compose.test.yml up -d test-db
   sleep 5
   ```

3. Check database connectivity:
   ```bash
   docker exec -it techknowledgepills-test-db psql -U postgres -d techknowledgepills_test -c "SELECT 1;"
   ```

## Test Output

When tests run successfully, you should see:

```
✅ Test run successful
  Passed!  - Failed:     0, Passed:    23, Skipped:     0, Total:    23
```

## Feature Files Overview

1. **UserRegistration.feature** - 4 scenarios
2. **UserLogin.feature** - 4 scenarios  
3. **ContentManagement.feature** - 6 scenarios
4. **StressIndicator.feature** - 5 scenarios
5. **Recommendations.feature** - 4 scenarios

**Total: 23 test scenarios**

## Environment Variables

- `USE_DOCKER_DB=true` - Enables Docker PostgreSQL (default: in-memory)
- `TEST_DB_CONNECTION` - Custom database connection string

## CI/CD Integration

For CI/CD pipelines, you can use:

```yaml
# Example GitHub Actions
- name: Start test database
  run: docker-compose -f docker-compose.test.yml up -d test-db

- name: Wait for database
  run: |
    until docker exec techknowledgepills-test-db pg_isready -U postgres; do
      sleep 1
    done

- name: Run tests
  env:
    USE_DOCKER_DB: true
  run: dotnet test TechKnowledgePills.Tests/TechKnowledgePills.Tests.csproj

- name: Cleanup
  if: always()
  run: docker-compose -f docker-compose.test.yml down -v
```

## Next Steps

1. ✅ Run `./scripts/verify-tests.sh` to verify setup
2. ✅ Run `./scripts/run-tests.sh` to execute all tests
3. ✅ Review test results and fix any failures
4. ✅ Add more scenarios as your API grows

