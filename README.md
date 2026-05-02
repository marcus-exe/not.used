# BLE Smart Ring Flutter App

A Flutter application for testing and debugging BLE (Bluetooth Low Energy) communication with smart ring devices. This app allows you to scan for BLE devices, connect to them, explore their services and characteristics, and send predefined hexadecimal commands.

## Features

- 📡 **BLE Device Scanner**: Discover nearby Bluetooth Low Energy devices with signal strength indicators
- 🔌 **Device Connection**: Connect to your smart ring or any BLE device
- 🔍 **Service Discovery**: Automatically discover and display all services and characteristics
- 🎯 **Debug Console**: Send predefined hexadecimal commands and monitor responses
- 📊 **Communication Log**: View timestamped logs of all sent and received data
- 🔔 **Notification Support**: Automatically subscribe to characteristic notifications

## Prerequisites

- Flutter SDK (3.9.2 or higher)
- Android device with Bluetooth Low Energy support
- Android SDK 21 or higher

## Installation

1. Navigate to the project directory:
```bash
cd ble_smart_ring
```

2. Get dependencies:
```bash
flutter pub get
```

3. Connect your Android device via USB or start an Android emulator

4. Run the app:
```bash
flutter run
```

## Usage

### 1. Scanning for Devices

- Launch the app
- Grant Bluetooth and Location permissions when prompted
- Tap "Scan for Devices" button
- Wait for nearby BLE devices to appear in the list

### 2. Connecting to a Device

- Tap on your smart ring device from the scan results
- The app will automatically connect and discover services

### 3. Selecting a Characteristic

- Browse through the discovered services and characteristics
- Tap "Use" on the characteristic you want to communicate with
- The app will navigate to the Debug Console

### 4. Sending Commands

The Debug Console has **predefined hexadecimal commands** that you can modify in the code.

**To customize your hex commands:**

Open `lib/screens/debug_screen.dart` and edit the `_hexCommands` map around line 11:

```dart
static const Map<String, String> _hexCommands = {
  'Command 1': '0A1B2C',           // Your custom hex values
  'Command 2': 'FF00AA',
  'Command 3': '010203040506',
  'Test Command': 'AABBCCDD',
  'Read Status': '01',
  'Reset': '00',
};
```

Simply modify the keys (button labels) and values (hex data) to match your needs.

### 5. Viewing Logs

- All communication is logged in real-time
- Sent data appears with an upward arrow (🟢 green)
- Received data appears with a downward arrow (🟠 orange)
- Errors and info messages are color-coded
- Tap "Clear Logs" to reset the log history

## Project Structure

```
lib/
├── main.dart                      # App entry point
├── models/
│   └── ble_log_entry.dart        # Log entry data model
├── providers/
│   └── ble_provider.dart         # State management
├── screens/
│   ├── scanner_screen.dart       # BLE device scanner
│   ├── device_detail_screen.dart # Service/characteristic explorer
│   └── debug_screen.dart         # Debug console with commands
├── services/
│   └── ble_service.dart          # BLE operations layer
└── utils/
    └── hex_utils.dart            # Hex conversion utilities
```

## Permissions

The app requires the following Android permissions (automatically configured):

- `BLUETOOTH_SCAN` - To scan for BLE devices
- `BLUETOOTH_CONNECT` - To connect to BLE devices
- `ACCESS_FINE_LOCATION` - Required for BLE scanning on Android
- `ACCESS_COARSE_LOCATION` - Fallback location permission

## Troubleshooting

### Connection Issues

- Ensure your smart ring is powered on and in range
- Try turning Bluetooth off and on
- Restart the app and try scanning again

### Permission Denied

- Go to Android Settings → Apps → BLE Smart Ring
- Grant all required permissions manually

### No Devices Found

- Ensure location services are enabled on your Android device
- Make sure Bluetooth is enabled
- Move closer to your smart ring device

## Technical Details

### Dependencies

- **flutter_blue_plus**: BLE communication library
- **permission_handler**: Runtime permission handling
- **provider**: State management solution

### Supported Features

- ✅ BLE scanning with RSSI
- ✅ Device connection/disconnection
- ✅ Service and characteristic discovery
- ✅ Write operations (with and without response)
- ✅ Read operations
- ✅ Notification/Indication subscriptions
- ✅ Real-time data logging

## Development

This app is designed for testing and study purposes. Key customization points:

1. **Hex Commands**: Modify `_hexCommands` in `lib/screens/debug_screen.dart`
2. **Logging**: All BLE operations are logged through `BleProvider.addLog()`
3. **UI Theme**: Customize colors in `lib/main.dart`

## Notes

- This is a debug/testing tool - all commands are hardcoded for safety
- The app automatically subscribes to notifications when selecting a characteristic
- Connection state is monitored continuously
- All timestamps are in HH:MM:SS.MS format

## License

This project is for study and testing purposes.
