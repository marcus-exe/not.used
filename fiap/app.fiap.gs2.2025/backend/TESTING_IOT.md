# Testing IoT Health Data Integration

This guide will help you test the IoT health data integration system.

## Step 1: Start All Services

```bash
cd backend
docker-compose up -d
```

This will start:
- PostgreSQL database (`db`)
- Backend API (`api`)
- IoT Simulator (`iot-simulator`)

## Step 2: Verify Services Are Running

```bash
docker-compose ps
```

You should see all three services with status "Up":
```
NAME                          STATUS
techknowledgepills-api         Up
techknowledgepills-db          Up
techknowledgepills-iot-simulator  Up
```

## Step 3: Check IoT Simulator Logs

```bash
docker-compose logs -f iot-simulator
```

You should see output like:
```
2025-01-15 10:30:00 - INFO - Starting IoT Health Data Simulator
2025-01-15 10:30:00 - INFO -   Device ID: fitness-tracker-001
2025-01-15 10:30:00 - INFO -   User ID: 1
2025-01-15 10:30:00 - INFO -   API Endpoint: http://api:8080/api/HealthMetric/iot
2025-01-15 10:30:05 - INFO - ✓ Health data sent successfully: HR=75, Steps=5420, HRV=45
```

**If you see connection errors**, wait a few seconds for the API to fully start, then check again.

## Step 4: Verify API is Receiving Data

Check the API logs:
```bash
docker-compose logs -f api | grep -i "health\|iot"
```

You should see log entries indicating health metrics are being received and processed.

## Step 5: Test the API Endpoints

### 5.1. Check if Health Metrics are Being Stored

First, you need to authenticate. Get a JWT token by logging in:

```bash
# Register a test user (if not already exists)
curl -X POST http://localhost:5001/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "Test123!"
  }'

# Login to get JWT token
TOKEN=$(curl -s -X POST http://localhost:5001/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "Test123!"
  }' | jq -r '.token')

echo "Token: $TOKEN"
```

### 5.2. Get Health Metrics (for user ID 1)

```bash
curl -X GET http://localhost:5001/api/healthmetric \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" | jq
```

### 5.3. Get Latest Health Metric

```bash
curl -X GET http://localhost:5001/api/healthmetric/latest \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" | jq
```

### 5.4. Verify Stress Indicators Were Created

```bash
curl -X GET http://localhost:5001/api/stressindicator \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" | jq
```

You should see stress indicators with notes like:
```json
{
  "id": 1,
  "userId": 1,
  "stressLevel": 2,
  "timestamp": "2025-01-15T10:30:00Z",
  "notes": "Auto-generated from health metrics: HR=75, Sleep=7.5h, HRV=45ms"
}
```

## Step 6: Manually Test IoT Endpoint

You can manually send health data to test the endpoint:

```bash
curl -X POST http://localhost:5001/api/HealthMetric/iot \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "heartRate": 95,
    "steps": 8500,
    "sleepHours": 6.5,
    "heartRateVariability": 25,
    "bodyTemperature": 36.8,
    "deviceId": "manual-test-device",
    "deviceType": "fitness_tracker"
  }' | jq
```

This should return the created health metric and automatically create a stress indicator.

## Step 7: Check Database Directly (Optional)

```bash
# Connect to PostgreSQL
docker-compose exec db psql -U postgres -d techknowledgepills

# Check health metrics
SELECT * FROM "HealthMetrics" ORDER BY "Timestamp" DESC LIMIT 5;

# Check stress indicators
SELECT * FROM "StressIndicators" ORDER BY "Timestamp" DESC LIMIT 5;

# Exit
\q
```

## Step 8: Test Stress Simulation

To test how the system handles elevated stress levels:

1. Stop the current simulator:
```bash
docker-compose stop iot-simulator
```

2. Update `docker-compose.yml` to enable stress simulation:
```yaml
iot-simulator:
  environment:
    - SIMULATE_STRESS=true
```

3. Restart the simulator:
```bash
docker-compose up -d iot-simulator
```

4. Watch the logs - you should see higher heart rates and lower HRV:
```bash
docker-compose logs -f iot-simulator
```

5. Check stress indicators - they should show higher stress levels:
```bash
curl -X GET http://localhost:5001/api/stressindicator/latest \
  -H "Authorization: Bearer $TOKEN" | jq
```

## Troubleshooting

### IoT Simulator Not Sending Data

1. **Check if API is ready:**
```bash
curl http://localhost:5001/api/healthmetric/iot
# Should return 400 (Bad Request) not 404 (Not Found)
```

2. **Check network connectivity:**
```bash
docker-compose exec iot-simulator ping -c 3 api
```

3. **Restart the simulator:**
```bash
docker-compose restart iot-simulator
```

### No Data in Database

1. **Verify user exists:**
```bash
docker-compose exec db psql -U postgres -d techknowledgepills -c "SELECT id, email FROM \"Users\";"
```

2. **Check if IoT simulator is using correct user ID:**
```bash
docker-compose logs iot-simulator | grep "User ID"
```

3. **Manually test the endpoint** (see Step 6)

### Stress Indicators Not Being Created

1. **Check API logs for errors:**
```bash
docker-compose logs api | grep -i error
```

2. **Verify HealthAnalysisService is registered:**
Check that `Program.cs` includes:
```csharp
builder.Services.AddScoped<IHealthAnalysisService, HealthAnalysisService>();
```

3. **Test stress calculation manually:**
Send health data with known stress indicators (high HR, low sleep, low HRV) and verify stress level is calculated correctly.

## Quick Test Script

Save this as `test-iot.sh`:

```bash
#!/bin/bash

echo "=== Testing IoT Integration ==="

# Check services
echo "1. Checking services..."
docker-compose ps

# Check IoT logs
echo -e "\n2. Recent IoT simulator logs:"
docker-compose logs --tail=10 iot-simulator

# Test API endpoint
echo -e "\n3. Testing IoT endpoint..."
curl -s -X POST http://localhost:5001/api/HealthMetric/iot \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "heartRate": 85,
    "steps": 5000,
    "sleepHours": 7.5,
    "heartRateVariability": 45,
    "bodyTemperature": 36.6,
    "deviceId": "test-device",
    "deviceType": "fitness_tracker"
  }' | jq

echo -e "\n=== Test Complete ==="
```

Make it executable and run:
```bash
chmod +x test-iot.sh
./test-iot.sh
```

## Expected Results

After running for a few minutes, you should have:

✅ Health metrics in the database (one every 5 minutes by default)  
✅ Stress indicators automatically created from health metrics  
✅ Logs showing successful data transmission  
✅ API endpoints returning data  

## Next Steps

- View data in Swagger UI: http://localhost:5001/swagger
- Check the mobile app - stress indicators should appear automatically
- Adjust IoT simulator settings in `docker-compose.yml` for different test scenarios

