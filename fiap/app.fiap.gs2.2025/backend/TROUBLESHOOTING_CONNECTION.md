# Troubleshooting Android App Connection Issues

## Problem: "Failed to connect to /10.0.2.2 (port 5001)"

This error means the Android emulator cannot reach the backend API.

## Quick Fixes

### 1. Verify Backend is Running

```bash
cd backend
docker-compose ps
```

You should see:
- `techknowledgepills-api` - Status: Up
- `techknowledgepills-db` - Status: Up (healthy)

### 2. Test API from Host Machine

```bash
# Test if API responds
curl http://localhost:5001/swagger

# Test login endpoint
curl -X POST http://localhost:5001/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"test"}'
```

If this works, the API is running correctly.

### 3. Test from Android Emulator Browser

1. Open browser in Android emulator
2. Navigate to: `http://10.0.2.2:5001/swagger`
3. If this doesn't load, there's a network issue

### 4. Check Port Binding

```bash
# Check if port 5001 is listening
lsof -i :5001

# Or on Linux
netstat -tuln | grep 5001
```

You should see the Docker container listening on port 5001.

### 5. Restart Services

```bash
cd backend
docker-compose restart api
# Wait 10 seconds
docker-compose logs -f api
```

### 6. Check Docker Network

The API should be accessible from the host. Verify:

```bash
# Check if API container can be reached
docker-compose exec api curl http://localhost:8080/swagger

# Check port mapping
docker-compose ps api
```

Should show: `0.0.0.0:5001->8080/tcp`

## Common Issues and Solutions

### Issue 1: Port Already in Use

**Error**: Port 5001 is already in use

**Solution**:
```bash
# Find what's using port 5001
lsof -i :5001

# Kill the process or change port in docker-compose.yml
# Update API_PORT in .env or docker-compose.yml
```

### Issue 2: Firewall Blocking Connection

**macOS**: 
- System Settings > Network > Firewall
- Allow incoming connections for Docker/OrbStack

**Solution**: Temporarily disable firewall to test, or add exception for port 5001

### Issue 3: Docker Not Exposing Port Correctly

**Check docker-compose.yml**:
```yaml
api:
  ports:
    - "${API_PORT:-5001}:8080"  # Should map host:container
```

**Solution**: Ensure the port mapping is correct and restart:
```bash
docker-compose down
docker-compose up -d
```

### Issue 4: Android Emulator Network Issue

**Solution**: 
1. Restart Android emulator
2. Cold boot the emulator (Wipe Data)
3. Try using a different emulator

### Issue 5: API Not Binding to All Interfaces

The API should listen on `0.0.0.0` inside the container. Check `Program.cs`:
```csharp
ASPNETCORE_URLS=http://+:8080  // Correct - listens on all interfaces
```

## Testing Steps

### Step 1: Test from Host
```bash
curl http://localhost:5001/api/auth/login \
  -X POST \
  -H "Content-Type: application/json" \
  -d '{"email":"test","password":"test"}'
```

### Step 2: Test from Emulator Browser
1. Open browser in emulator
2. Go to `http://10.0.2.2:5001/swagger`
3. Should see Swagger UI

### Step 3: Check Android App Logs
In Android Studio:
1. Open Logcat
2. Filter by your app package
3. Look for network errors
4. Check Retrofit/OkHttp logs

### Step 4: Enable Network Security Config (if needed)

If you see cleartext traffic errors, ensure `AndroidManifest.xml` has:
```xml
android:usesCleartextTraffic="true"
```

## Alternative: Use Physical Device

If emulator continues to have issues:

1. Find your machine's IP:
```bash
# macOS
ifconfig | grep "inet " | grep -v 127.0.0.1

# Or use the script
cd android
./find-ip.sh
```

2. Update `RetrofitModule.kt`:
```kotlin
private const val BASE_URL = "http://YOUR_IP:5001/"
```

3. Ensure device and computer are on same Wi-Fi network

## Debugging Commands

```bash
# Check all services
docker-compose ps

# View API logs
docker-compose logs -f api

# Check if API is responding
curl -v http://localhost:5001/swagger

# Test specific endpoint
curl -X POST http://localhost:5001/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"test"}'

# Check Docker network
docker network inspect backend_app-network

# Restart everything
docker-compose down
docker-compose up -d
```

## Still Not Working?

1. **Check API logs for errors**:
   ```bash
   docker-compose logs api | grep -i error
   ```

2. **Verify database is healthy**:
   ```bash
   docker-compose ps db
   ```

3. **Try accessing API from emulator's browser**:
   - Open browser in emulator
   - Go to `http://10.0.2.2:5001/swagger`
   - If this doesn't work, it's a network/Docker issue

4. **Check Android app configuration**:
   - Verify `BASE_URL` in `RetrofitModule.kt`
   - Check `AndroidManifest.xml` has internet permission
   - Verify cleartext traffic is allowed

5. **Test with a simple curl from emulator** (if you have adb shell access):
   ```bash
   adb shell
   curl http://10.0.2.2:5001/swagger
   ```

