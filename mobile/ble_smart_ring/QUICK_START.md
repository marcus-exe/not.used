# Quick Start Guide

## 🚀 Running the App

### Option 1: Using USB Connected Android Device

1. Enable Developer Options and USB Debugging on your Android device
2. Connect your device via USB
3. Run:
```bash
cd /Users/marcuseduardosena/Documents/Developer/SALCOMP/ble_studies/ble_smart_ring
flutter run
```

### Option 2: Using Android Emulator

1. Start an Android emulator from Android Studio
2. Run:
```bash
cd /Users/marcuseduardosena/Documents/Developer/SALCOMP/ble_studies/ble_smart_ring
flutter run
```

**Note:** BLE testing requires a **physical device** as emulators don't support Bluetooth hardware.

### Option 3: Install Pre-built APK

The debug APK is already built at:
```
build/app/outputs/flutter-apk/app-debug.apk
```

Transfer it to your Android device and install it.

## 📱 App Workflow

1. **Launch App** → Grant Permissions
2. **Scan** → Tap "Scan for Devices"
3. **Connect** → Tap on your smart ring device
4. **Explore** → View services and characteristics
5. **Select** → Tap "Use" on a writable characteristic
6. **Debug** → Send predefined hex commands
7. **Monitor** → View responses in the log

## 🎯 Customizing Hex Commands

Edit this file: `lib/screens/debug_screen.dart`

Find this section around line 11:

```dart
static const Map<String, String> _hexCommands = {
  'Command 1': '0A1B2C',      // ← Change these
  'Command 2': 'FF00AA',      // ← Change these
  'Command 3': '010203040506', // ← Change these
  // Add more commands here...
};
```

After editing:
- Save the file
- Press `r` in the terminal (hot reload)
- Or restart the app

## 🔧 Common Commands

### Check for issues
```bash
flutter analyze
```

### Rebuild the app
```bash
flutter clean
flutter pub get
flutter run
```

### View connected devices
```bash
flutter devices
```

### Build release APK
```bash
flutter build apk --release
```

## 📊 Understanding the UI

### Scanner Screen
- Lists all nearby BLE devices
- Shows device name, MAC address, and signal strength (RSSI)
- Green = strong signal, Orange = medium, Red = weak

### Device Detail Screen
- Shows connection status
- Lists all services (expandable)
- Shows characteristics with properties (READ, WRITE, NOTIFY)
- "Use" button selects characteristic for communication

### Debug Console
- **Top Banner**: Connection status
- **Blue Section**: Selected characteristic UUID
- **Command Buttons**: Tap to send hex data
- **Log Area**: Real-time communication history
  - 🔵 Blue = Info
  - 🟢 Green = Sent data
  - 🟠 Orange = Received data
  - 🔴 Red = Errors

## 💡 Tips

1. **Always grant all permissions** when prompted
2. **Keep device close** during scanning (< 5 meters)
3. **Test with simple commands first** (e.g., single byte like '01')
4. **Watch the logs** for responses from your smart ring
5. **Clear logs** regularly for better readability

## 🐛 Troubleshooting

### "No devices found"
- Enable Bluetooth on phone
- Enable Location Services
- Move closer to device
- Restart app

### "Failed to connect"
- Unpair device from phone Bluetooth settings first
- Restart Bluetooth
- Power cycle the smart ring

### "Permission denied"
- Go to Settings → Apps → BLE Smart Ring
- Grant all permissions
- Restart app

### Build errors
```bash
flutter clean
flutter pub get
flutter run
```

## 📚 Next Steps

1. **Find your device's UUIDs** - Use the device detail screen
2. **Identify the correct characteristic** - Look for WRITE and NOTIFY properties
3. **Customize hex commands** - Edit `debug_screen.dart`
4. **Test communication** - Send commands and monitor responses
5. **Document your findings** - Keep track of which hex values do what

## 🔗 Useful Files

- `README.md` - Complete documentation
- `HEX_COMMANDS_GUIDE.md` - Detailed hex command customization
- `lib/screens/debug_screen.dart` - Where to edit commands
- `lib/providers/ble_provider.dart` - Add custom BLE logic here

Happy testing! 🎉

