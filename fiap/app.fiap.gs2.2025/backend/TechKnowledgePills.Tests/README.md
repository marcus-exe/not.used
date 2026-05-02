# TechKnowledgePills BDD Tests

This project contains Gherkin-based BDD (Behavior Driven Development) tests using SpecFlow for the TechKnowledgePills API.

## Overview

The test suite includes **5 feature files** with multiple scenarios covering:

1. **User Registration** - Testing user registration with various validation scenarios
2. **User Login** - Testing authentication and login flows
3. **Content Management** - Testing CRUD operations for knowledge pills content
4. **Stress Indicator** - Testing stress level tracking functionality
5. **Recommendations** - Testing personalized content recommendations

## Test Coverage

### Feature Files

- `UserRegistration.feature` - 4 scenarios
- `UserLogin.feature` - 4 scenarios
- `ContentManagement.feature` - 6 scenarios
- `StressIndicator.feature` - 5 scenarios
- `Recommendations.feature` - 4 scenarios

**Total: 23 test scenarios**

## Running the Tests

### Prerequisites

- .NET 8.0 SDK
- Docker Desktop (for database testing)
- Visual Studio 2022 or VS Code with C# extension
- SpecFlow extension (for Visual Studio)

### Run Tests with Docker (Recommended)

The tests are configured to use PostgreSQL in Docker for more realistic testing scenarios.

#### On macOS/Linux:

```bash
cd backend
./scripts/run-tests.sh
```

#### On Windows (PowerShell):

```powershell
cd backend
.\scripts\run-tests.ps1
```

#### Manual Docker Setup:

1. Start the test database:
```bash
docker-compose -f docker-compose.test.yml up -d test-db
```

2. Wait for database to be ready (check with):
```bash
docker exec techknowledgepills-test-db pg_isready -U postgres
```

3. Set environment variables and run tests:
```bash
export USE_DOCKER_DB=true
export TEST_DB_CONNECTION="Host=localhost;Port=5433;Database=techknowledgepills_test;Username=postgres;Password=postgres"
dotnet test TechKnowledgePills.Tests/TechKnowledgePills.Tests.csproj --verbosity normal
```

4. Clean up:
```bash
docker-compose -f docker-compose.test.yml down -v
```

### Run Tests with In-Memory Database (Alternative)

If you don't want to use Docker, tests will automatically fall back to in-memory database:

```bash
cd backend
dotnet test TechKnowledgePills.Tests/TechKnowledgePills.Tests.csproj --verbosity normal
```

### Run Tests in Visual Studio

1. Start Docker Desktop
2. Start the test database: `docker-compose -f docker-compose.test.yml up -d test-db`
3. Set environment variable: `USE_DOCKER_DB=true`
4. Open the solution in Visual Studio
5. Build the solution (Ctrl+Shift+B)
6. Open Test Explorer (Test > Test Explorer)
7. Run all tests or select specific features/scenarios
8. Clean up: `docker-compose -f docker-compose.test.yml down -v`

### Verify Tests Are Working

To quickly verify your tests are set up correctly:

```bash
# 1. Start test database
docker-compose -f docker-compose.test.yml up -d test-db

# 2. Wait for it to be ready
sleep 5

# 3. Run a single feature to test
export USE_DOCKER_DB=true
dotnet test TechKnowledgePills.Tests/TechKnowledgePills.Tests.csproj --filter "FullyQualifiedName~UserRegistration" --verbosity normal

# 4. Clean up
docker-compose -f docker-compose.test.yml down -v
```

## Test Infrastructure

- **TestWebApplicationFactory**: Creates an in-memory test server with in-memory database
- **TestContext**: Shared context for test scenarios
- **Hooks**: Setup and teardown for each scenario
- **Step Definitions**: Implementation of Gherkin steps

## Test Database

Tests can use either:

1. **PostgreSQL in Docker** (default when `USE_DOCKER_DB=true`):
   - More realistic testing environment
   - Uses the same database engine as production
   - Runs on port 5433 to avoid conflicts
   - Automatically cleaned up after tests

2. **In-Memory Database** (fallback):
   - Faster test execution
   - No Docker required
   - Created fresh for each test run
   - Isolated between scenarios

## Gherkin Syntax

The tests use standard Gherkin syntax:
- **Feature**: Describes the feature being tested
- **Scenario**: Describes a specific test case
- **Given**: Sets up the initial state
- **When**: Describes the action being tested
- **Then**: Describes the expected outcome
- **Background**: Steps that run before each scenario in a feature

## Example Scenario

```gherkin
Scenario: Successful user registration
    Given I have a valid email "test@example.com"
    And I have a valid password "Password123"
    When I register with these credentials
    Then the registration should be successful
    And I should receive a JWT token
    And the response should contain my user ID
```

## Notes

- All tests use an in-memory database for isolation
- Authentication tokens are automatically managed in test scenarios
- Each scenario gets a fresh HTTP client instance
- Test data is automatically cleaned up between scenarios

