# Quick Fix for Physical Device Connection

## Problem
The app shows: "failed to connect to /10.0.2.2 (port 5001)"

## Solution
You're using a **physical device**, not an emulator. Physical devices need your computer's actual IP address.

## Steps to Fix

### 1. Find Your Computer's IP Address

**macOS/Linux:**
```bash
ifconfig | grep "inet " | grep -v 127.0.0.1
```

**Windows:**
```cmd
ipconfig
```

Look for an IP like `192.168.x.x` or `10.x.x.x`

### 2. Update the App Code

The IP has been updated to `192.168.0.137`. If your IP is different:

1. Open: `android/app/src/main/java/com/techknowledgepills/data/api/RetrofitModule.kt`
2. Find line 22:
   ```kotlin
   private const val BASE_URL = "http://192.168.0.137:5001/"
   ```
3. Replace `192.168.0.137` with YOUR computer's IP address

### 3. Rebuild the App

In Android Studio:
- **Build > Clean Project**
- **Build > Rebuild Project**
- **Run** the app again

### 4. Verify Same Network

- Your phone and computer must be on the **same Wi-Fi network**
- Check your phone's Wi-Fi settings
- Check your computer's network connection

### 5. Test from Phone Browser

1. Open browser on your phone
2. Go to: `http://YOUR_IP:5001/swagger`
   - Example: `http://192.168.0.137:5001/swagger`
3. If Swagger UI loads, the network is working!

### 6. Check Firewall

**macOS:**
- System Settings > Network > Firewall
- Allow incoming connections for Docker/OrbStack
- Or temporarily disable firewall to test

**Windows:**
- Windows Defender Firewall
- Allow port 5001 or disable temporarily to test

## Current Configuration

- **App BASE_URL**: `http://192.168.0.137:5001/`
- **Backend CORS**: Updated** to allow your device IP

## Still Not Working?

1. **Verify backend is running:**
   ```bash
   cd backend
   docker-compose ps
   ```

2. **Check API is accessible:**
   ```bash
   curl http://192.168.0.137:5001/swagger
   ```

3. **Restart backend:**
   ```bash
   docker-compose restart api
   ```

4. **Check device and computer are on same Wi-Fi**

5. **Try from phone browser first** - if that doesn't work, it's a network/firewall issue

