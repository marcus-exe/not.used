# IoT Health Data Integration

This document describes the IoT health data integration system that feeds health metrics into the backend, which are then automatically converted to stress indicators for the mobile app.

## Architecture Overview

```
IoT Device/Simulator → Backend API → Health Metrics Storage → Stress Analysis → Stress Indicators → Mobile App
```

## Components

### 1. HealthMetric Entity
Stores raw health data from IoT devices:
- Heart Rate (BPM)
- Steps (daily count)
- Sleep Hours
- Heart Rate Variability (HRV)
- Body Temperature
- Device ID and Type

### 2. HealthMetricController
REST API endpoints:
- `POST /api/HealthMetric/iot` - IoT devices submit health data (public endpoint)
- `GET /api/HealthMetric` - Users retrieve their health metrics (authenticated)
- `GET /api/HealthMetric/latest` - Get latest health metric (authenticated)

### 3. HealthAnalysisService
Automatically analyzes health metrics and creates stress indicators:
- Calculates stress level based on multiple health factors
- Creates `StressIndicator` records linked to users
- Uses scoring algorithm considering:
  - Heart rate abnormalities
  - Sleep deprivation
  - Low HRV (indicates stress)
  - Body temperature variations
  - Low activity levels

### 4. IoT Simulator Container
Python-based container that simulates health monitoring devices:
- Generates realistic health data
- Configurable intervals and stress simulation
- Automatically sends data to backend API
- See `iot-simulator/README.md` for details

## Data Flow

1. **IoT Device/Simulator** sends health data to `/api/HealthMetric/iot`
2. **Backend** stores the `HealthMetric` in the database
3. **HealthAnalysisService** analyzes the metrics:
   - Calculates stress score from multiple factors
   - Determines `StressLevel` (Low, Medium, High, Critical)
4. **Backend** creates a `StressIndicator` record
5. **Mobile App** can retrieve stress indicators via existing `/api/StressIndicator` endpoints

## Stress Level Calculation

The system uses a scoring algorithm:

| Factor | Condition | Score |
|--------|-----------|-------|
| Heart Rate | < 60 BPM | +1 |
| Heart Rate | > 100 BPM | +2 |
| Heart Rate | > 120 BPM | +3 |
| Sleep | < 6 hours | +2 |
| Sleep | < 7 hours | +1 |
| Sleep | > 9 hours | +1 |
| HRV | < 20ms | +3 |
| HRV | < 30ms | +2 |
| HRV | < 40ms | +1 |
| Temperature | Abnormal | +1 |
| Steps | < 3000 | +1 |

**Stress Level Mapping:**
- Score 0-1: **Low**
- Score 2-3: **Medium**
- Score 4-5: **High**
- Score 6+: **Critical**

## Usage

### Starting the IoT Simulator

```bash
cd backend
docker-compose up -d
```

The IoT simulator will automatically start and begin sending health data every 5 minutes (configurable).

### Configuring the Simulator

Set environment variables in `docker-compose.yml`:

```yaml
iot-simulator:
  environment:
    - USER_ID=1                    # User to associate data with
    - DEVICE_ID=fitness-tracker-001 # Device identifier
    - INTERVAL_SECONDS=300          # Send data every 5 minutes
    - SIMULATE_STRESS=true          # Enable stress simulation
```

### Testing IoT Data Submission

You can manually test the IoT endpoint:

```bash
curl -X POST http://localhost:5001/api/HealthMetric/iot \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "heartRate": 85,
    "steps": 5420,
    "sleepHours": 7.5,
    "heartRateVariability": 45,
    "bodyTemperature": 36.6,
    "deviceId": "test-device-001",
    "deviceType": "fitness_tracker"
  }'
```

### Viewing Health Metrics

Authenticated users can retrieve their health metrics:

```bash
# Get all health metrics
curl -X GET http://localhost:5001/api/HealthMetric \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"

# Get latest health metric
curl -X GET http://localhost:5001/api/HealthMetric/latest \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Database Schema

### HealthMetric Table
```sql
CREATE TABLE "HealthMetrics" (
    "Id" SERIAL PRIMARY KEY,
    "UserId" INTEGER NOT NULL,
    "Timestamp" TIMESTAMP NOT NULL,
    "HeartRate" INTEGER,
    "Steps" INTEGER,
    "SleepHours" DOUBLE PRECISION,
    "HeartRateVariability" INTEGER,
    "BodyTemperature" DOUBLE PRECISION,
    "DeviceId" TEXT,
    "DeviceType" TEXT,
    FOREIGN KEY ("UserId") REFERENCES "Users" ("Id") ON DELETE CASCADE
);

CREATE INDEX "IX_HealthMetrics_DeviceId" ON "HealthMetrics" ("DeviceId");
CREATE INDEX "IX_HealthMetrics_UserId_Timestamp" ON "HealthMetrics" ("UserId", "Timestamp");
```

## Integration with Mobile App

The mobile app already has endpoints to retrieve stress indicators:
- `GET /api/StressIndicator` - Get all user's stress indicators
- `GET /api/StressIndicator/latest` - Get latest stress indicator

Stress indicators created from IoT health data will automatically appear in these endpoints, so no mobile app changes are required!

## Security Considerations

Currently, the `/api/HealthMetric/iot` endpoint is public (AllowAnonymous). For production:

1. **API Key Authentication**: Add API key validation for IoT devices
2. **Device Registration**: Require devices to register before sending data
3. **Rate Limiting**: Prevent abuse with rate limiting
4. **User Validation**: Verify the userId exists and device is authorized for that user

Example API key middleware:
```csharp
// In HealthMetricController
[HttpPost("iot")]
[ApiKeyAuth] // Custom attribute
public async Task<ActionResult> SubmitIoTData(...)
```

## Monitoring

Check IoT simulator logs:
```bash
docker-compose logs -f iot-simulator
```

Check API logs for health data processing:
```bash
docker-compose logs -f api
```

## Future Enhancements

- [ ] Real-time WebSocket updates to mobile app
- [ ] Machine learning for more accurate stress prediction
- [ ] Multiple device support per user
- [ ] Historical trend analysis
- [ ] Alerts for critical health conditions
- [ ] Integration with real fitness trackers (Fitbit, Apple Watch APIs)

