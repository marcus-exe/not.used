#!/bin/bash

echo "=== IoT Integration Test Script ==="
echo ""

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if docker-compose is available
if ! command -v docker-compose &> /dev/null; then
    echo -e "${RED}Error: docker-compose not found${NC}"
    exit 1
fi

# Check if services are running
echo "1. Checking if services are running..."
if ! docker-compose ps | grep -q "Up"; then
    echo -e "${YELLOW}Services are not running. Starting them...${NC}"
    docker-compose up -d
    echo "Waiting 10 seconds for services to start..."
    sleep 10
else
    echo -e "${GREEN}✓ Services are running${NC}"
fi

# Check IoT simulator logs
echo -e "\n2. Checking IoT simulator status..."
IOT_LOGS=$(docker-compose logs --tail=5 iot-simulator 2>&1)
if echo "$IOT_LOGS" | grep -q "Health data sent successfully"; then
    echo -e "${GREEN}✓ IoT simulator is sending data${NC}"
    echo "Recent logs:"
    echo "$IOT_LOGS" | tail -3
else
    echo -e "${YELLOW}⚠ IoT simulator may not be sending data yet${NC}"
    echo "Logs:"
    echo "$IOT_LOGS" | tail -5
fi

# Test API endpoint
echo -e "\n3. Testing IoT API endpoint..."
API_RESPONSE=$(curl -s -w "\n%{http_code}" -X POST http://localhost:5001/api/HealthMetric/iot \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "heartRate": 85,
    "steps": 5000,
    "sleepHours": 7.5,
    "heartRateVariability": 45,
    "bodyTemperature": 36.6,
    "deviceId": "test-script-device",
    "deviceType": "fitness_tracker"
  }' 2>&1)

HTTP_CODE=$(echo "$API_RESPONSE" | tail -1)
BODY=$(echo "$API_RESPONSE" | head -n -1)

if [ "$HTTP_CODE" = "200" ]; then
    echo -e "${GREEN}✓ API endpoint is working${NC}"
    echo "Response:"
    echo "$BODY" | jq . 2>/dev/null || echo "$BODY"
else
    echo -e "${RED}✗ API endpoint returned status: $HTTP_CODE${NC}"
    echo "Response: $BODY"
fi

# Check if we can query health metrics (requires auth)
echo -e "\n4. Testing authenticated endpoints..."
echo "   (Skipping - requires JWT token. See TESTING_IOT.md for full test)"

# Summary
echo -e "\n=== Test Summary ==="
echo "To view IoT simulator logs: docker-compose logs -f iot-simulator"
echo "To view API logs: docker-compose logs -f api"
echo "To access Swagger UI: http://localhost:5001/swagger"
echo ""
echo "For complete testing instructions, see: TESTING_IOT.md"

