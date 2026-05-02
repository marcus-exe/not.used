# Testing the Android App

This guide will help you test the Tech Knowledge Pills Android app on both emulator and physical devices.

## Prerequisites

1. **Backend Running**: Ensure the backend API is running via Docker Compose
   ```bash
   cd backend
   docker compose ps
   ```
   Both `techknowledgepills-api` and `techknowledgepills-db` should be running.

2. **Android Studio**: Latest version with Android SDK installed
3. **Device/Emulator**: Android device (API 24+) or emulator

## Testing on Android Emulator

### Step 1: Start the Emulator

1. Open Android Studio
2. Go to **Tools > Device Manager**
3. Create a new virtual device or use an existing one
4. Start the emulator

### Step 2: Configure API Connection

The app is already configured for emulator testing:
- Base URL: `http://10.0.2.2:5001/`
- Port 5001 is the default (avoids macOS AirPlay conflict on port 5000)

### Step 3: Build and Run

1. Open the `android` folder in Android Studio
2. Wait for Gradle sync to complete
3. Click **Run** (green play button) or press `Shift+F10`
4. Select your emulator from the device list
5. The app will install and launch

### Step 4: Test Features

1. **Registration**
   - Tap "Don't have an account? Register"
   - Enter email and password
   - Confirm password
   - Tap "Register"
   - Should navigate to Home screen on success

2. **Login**
   - Enter registered email and password
   - Tap "Login"
   - Should navigate to Home screen

3. **Home Dashboard**
   - View current stress level (if available)
   - Browse recommended content
   - Navigate to different sections

4. **Stress Indicators**
   - Tap on stress indicator card or navigate to Stress screen
   - Tap "Generate Mock Data" to create 30 days of test data
   - View stress level history

5. **Content Browsing**
   - Tap "Browse All Content" from home
   - View list of all knowledge pills
   - Tap any content item to view details

6. **Recommendations**
   - Navigate to Recommendations screen
   - View personalized content suggestions
   - Tap items to view details

## Testing on Physical Device

### Step 1: Find Your Machine's IP Address

**On macOS:**
```bash
ifconfig | grep "inet " | grep -v 127.0.0.1
```

**On Windows:**
```bash
ipconfig
```

**On Linux:**
```bash
hostname -I
```

Look for your local network IP (usually starts with `192.168.x.x` or `10.x.x.x`)

### Step 2: Update API Base URL

1. Open `android/app/src/main/java/com/techknowledgepills/data/api/RetrofitModule.kt`
2. Find the `BASE_URL` constant
3. Replace with your machine's IP address:

```kotlin
private const val BASE_URL = "http://192.168.1.100:5001/" // Replace with your IP
```

**Important**: 
- Use your actual IP address (not `192.168.1.100`)
- Ensure your device and computer are on the same Wi-Fi network
- Port 5001 must match your backend configuration

### Step 3: Enable USB Debugging (Physical Device)

1. On your Android device:
   - Go to **Settings > About Phone**
   - Tap **Build Number** 7 times to enable Developer Options
   - Go back to **Settings > Developer Options**
   - Enable **USB Debugging**

2. Connect device via USB
3. Allow USB debugging when prompted on device

### Step 4: Build and Run

1. In Android Studio, click **Run**
2. Select your physical device from the list
3. The app will install and launch

### Step 5: Verify Connection

If you see connection errors:
1. Verify backend is running: `docker compose ps`
2. Test API from device's browser: `http://YOUR_IP:5001/swagger`
3. Check firewall settings (may need to allow port 5001)
4. Verify device and computer are on same network

## Testing Checklist

### Authentication Flow
- [ ] Register new user
- [ ] Login with registered credentials
- [ ] Error handling for invalid credentials
- [ ] Token persistence (app remembers login after restart)

### Stress Indicators
- [ ] Generate mock stress data
- [ ] View stress level history
- [ ] Display latest stress level on home screen
- [ ] Stress level colors (Low=Green, Medium=Yellow, High=Orange, Critical=Red)

### Content Management
- [ ] Browse all content
- [ ] View article content
- [ ] View video content (URL display)
- [ ] View quiz content
- [ ] Content type filtering

### Recommendations
- [ ] View personalized recommendations
- [ ] Recommendations based on stress level
- [ ] Navigate to content from recommendations

### Navigation
- [ ] Navigate between all screens
- [ ] Back button works correctly
- [ ] Deep linking to content details

## Troubleshooting

### App Won't Connect to API

**Symptoms**: Network errors, timeout, or "Failed to fetch" messages

**Solutions**:
1. **Check backend is running**:
   ```bash
   cd backend
   docker compose ps
   docker compose logs api
   ```

2. **Test API from browser**:
   - Emulator: `http://10.0.2.2:5001/swagger`
   - Physical device: `http://YOUR_IP:5001/swagger`

3. **Verify network**:
   - Emulator: Should work automatically
   - Physical device: Must be on same Wi-Fi network

4. **Check firewall**:
   - macOS: System Settings > Network > Firewall
   - Allow incoming connections on port 5001

5. **Verify CORS settings**:
   - Check `backend/TechKnowledgePills.API/Program.cs`
   - Ensure your IP/URL is in allowed origins

### Build Errors

**Gradle Sync Fails**:
```bash
cd android
./gradlew clean
./gradlew build
```

**Missing Dependencies**:
- File > Invalidate Caches / Restart
- File > Sync Project with Gradle Files

### App Crashes on Launch

1. Check Logcat in Android Studio
2. Look for error messages
3. Verify all dependencies are correctly configured
4. Check AndroidManifest.xml for required permissions

### Authentication Issues

**Token Not Persisting**:
- Check DataStore permissions in AndroidManifest
- Verify TokenManager is working (check logs)

**401 Unauthorized Errors**:
- Token may have expired
- Try logging out and logging back in
- Check token format in API requests

## API Testing with Swagger

Before testing the app, you can test the API directly:

1. Open Swagger UI: http://localhost:5001/swagger
2. Test endpoints:
   - `POST /api/auth/register` - Create test user
   - `POST /api/auth/login` - Get JWT token
   - `GET /api/content` - View content (requires auth)
   - `POST /api/stressindicator/generate-mock` - Generate test data

## Network Debugging

### View Network Requests

1. Enable network logging in RetrofitModule (already enabled)
2. Check Logcat in Android Studio
3. Filter by "OkHttp" to see API requests/responses

### Test API Manually

**Using curl from terminal**:
```bash
# Register user
curl -X POST http://localhost:5001/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}'

# Login
curl -X POST http://localhost:5001/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}'

# Get content (replace TOKEN with actual token)
curl -X GET http://localhost:5001/api/content \
  -H "Authorization: Bearer TOKEN"
```

## Performance Testing

1. **Load Testing**:
   - Generate large amounts of mock data
   - Test scrolling performance
   - Check memory usage in Android Studio Profiler

2. **Network Testing**:
   - Test with slow network (Android Studio Network Profiler)
   - Test offline behavior
   - Test reconnection after network loss

## Next Steps

After basic testing:
1. Add sample content via API or Swagger
2. Test with multiple users
3. Test edge cases (empty data, large datasets)
4. Test error scenarios (network failures, invalid data)

## Getting Help

If you encounter issues:
1. Check Android Studio Logcat for errors
2. Check backend logs: `docker compose logs api`
3. Verify API is accessible from browser
4. Check network connectivity
5. Review this troubleshooting section

