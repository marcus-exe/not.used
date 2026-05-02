# IoT Health Data Simulator

This container simulates IoT health monitoring devices (fitness trackers, smartwatches) that continuously send health metrics to the backend API.

## Features

- **Realistic Health Data Generation**:
  - Heart Rate (BPM)
  - Daily Steps
  - Sleep Hours
  - Heart Rate Variability (HRV)
  - Body Temperature

- **Stress Simulation**: Can simulate elevated stress levels that affect health metrics
- **Configurable**: Environment variables control behavior
- **Automatic Stress Indicator Creation**: Backend automatically converts health metrics to stress indicators

## Configuration

The simulator can be configured using environment variables:

| Variable | Default | Description |
|----------|---------|-------------|
| `API_BASE_URL` | `http://api:8080` | Base URL of the backend API |
| `USER_ID` | `1` | User ID to associate health data with |
| `DEVICE_ID` | `fitness-tracker-001` | Unique identifier for the IoT device |
| `DEVICE_TYPE` | `fitness_tracker` | Type of device (e.g., `fitness_tracker`, `smartwatch`, `health_monitor`) |
| `INTERVAL_SECONDS` | `300` | Seconds between data submissions (default: 5 minutes) |
| `SIMULATE_STRESS` | `false` | If `true`, simulates elevated stress affecting health metrics |

## Health Metrics Explained

### Heart Rate (BPM)
- **Normal resting**: 60-100 BPM
- **During activity**: 100-150 BPM
- **High stress**: 95-120+ BPM

### Steps
- Increments throughout the day based on time of day
- Morning: 100-500 steps/hour
- Afternoon: 200-800 steps/hour
- Evening: 100-400 steps/hour
- Night: 0-100 steps/hour

### Sleep Hours
- Only tracked during night/morning hours (10 PM - 8 AM)
- **Normal**: 7-9 hours
- **Stressed**: 4.5-6.5 hours

### Heart Rate Variability (HRV)
- Lower HRV indicates higher stress
- **Normal**: 40-60ms
- **Stressed**: 25-35ms
- **Very stressed**: 15-25ms

### Body Temperature
- **Normal range**: 36.1-37.2°C
- Slight variations throughout the day

## How It Works

1. The simulator generates realistic health data based on the current time and stress level
2. Data is sent to `/api/HealthMetric/iot` endpoint
3. The backend automatically:
   - Stores the health metrics
   - Analyzes the data
   - Creates corresponding stress indicators
   - Links everything to the user

## Running the Simulator

### Using Docker Compose

The simulator is automatically started with the backend:

```bash
cd backend
docker-compose up -d
```

### Running Manually

```bash
cd backend/iot-simulator
docker build -t iot-simulator .
docker run --network techknowledgepills_app-network \
  -e API_BASE_URL=http://api:8080 \
  -e USER_ID=1 \
  -e DEVICE_ID=my-device-001 \
  -e INTERVAL_SECONDS=300 \
  iot-simulator
```

### Testing with Stress Simulation

To test stress detection, enable stress simulation:

```bash
docker-compose up -d
docker-compose exec iot-simulator sh -c 'export SIMULATE_STRESS=true && python iot_simulator.py'
```

Or set in `docker-compose.yml`:
```yaml
environment:
  - SIMULATE_STRESS=true
```

## API Endpoint

The simulator sends POST requests to:
```
POST http://api:8080/api/HealthMetric/iot
Content-Type: application/json

{
  "userId": 1,
  "timestamp": "2025-01-15T10:30:00Z",
  "deviceId": "fitness-tracker-001",
  "deviceType": "fitness_tracker",
  "heartRate": 75,
  "steps": 5420,
  "sleepHours": 7.5,
  "heartRateVariability": 45,
  "bodyTemperature": 36.6
}
```

## Monitoring

Check the simulator logs:

```bash
docker-compose logs -f iot-simulator
```

You should see messages like:
```
2025-01-15 10:30:00 - INFO - ✓ Health data sent successfully: HR=75, Steps=5420, HRV=45
```

## Troubleshooting

### Connection Errors
- Ensure the API container is running: `docker-compose ps`
- Check network connectivity: `docker-compose exec iot-simulator ping api`
- Verify API is accessible: `curl http://api:8080/api/HealthMetric/iot`

### No Data Appearing
- Check user ID exists in the database
- Verify API endpoint is working
- Check API logs: `docker-compose logs api`

