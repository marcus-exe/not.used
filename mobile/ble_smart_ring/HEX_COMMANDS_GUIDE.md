# Hex Commands Quick Reference

This guide shows you how to customize the hexadecimal commands in your BLE app.

## Where to Edit

**File:** `lib/screens/debug_screen.dart`

**Location:** Around line 11

## Example Configuration

```dart
static const Map<String, String> _hexCommands = {
  'Command 1': '0A1B2C',           // Button label : Hex data
  'Command 2': 'FF00AA',
  'Command 3': '010203040506',
  'Test Command': 'AABBCCDD',
  'Read Status': '01',
  'Reset': '00',
};
```

## How to Modify

### Adding a New Command

```dart
static const Map<String, String> _hexCommands = {
  'Command 1': '0A1B2C',
  'My New Command': '1234ABCD',  // Add your new command here
};
```

### Removing a Command

Simply delete or comment out the line:

```dart
static const Map<String, String> _hexCommands = {
  'Command 1': '0A1B2C',
  // 'Command 2': 'FF00AA',  // This command is now hidden
  'Command 3': '010203040506',
};
```

### Renaming a Command

Change the text before the colon:

```dart
static const Map<String, String> _hexCommands = {
  'Power On': '0A1B2C',    // Changed from 'Command 1'
  'Power Off': 'FF00AA',   // Changed from 'Command 2'
};
```

### Changing Hex Values

Change the hex string after the colon:

```dart
static const Map<String, String> _hexCommands = {
  'Command 1': '123456',   // Changed from '0A1B2C'
};
```

## Hex String Format

- Use **0-9** and **A-F** (or a-f)
- **Even number** of characters (each byte = 2 hex digits)
- No spaces or special characters needed
- Examples:
  - ✅ `'01'` - Single byte
  - ✅ `'0A1B'` - Two bytes
  - ✅ `'FF00AA'` - Three bytes
  - ✅ `'0102030405060708'` - Eight bytes
  - ❌ `'1'` - Must be even length
  - ❌ `'0G'` - Invalid hex character

## Example Use Cases

### Smart Ring Commands

```dart
static const Map<String, String> _hexCommands = {
  'Get Battery': '01',
  'Get Steps': '02',
  'Get Heart Rate': '03',
  'Start Monitoring': '04',
  'Stop Monitoring': '05',
  'Sync Time': '0A',
  'Factory Reset': 'FF',
};
```

### Testing Commands

```dart
static const Map<String, String> _hexCommands = {
  'Test 1 Byte': 'AA',
  'Test 2 Bytes': 'AABB',
  'Test 4 Bytes': 'AABBCCDD',
  'Test 8 Bytes': 'AABBCCDD11223344',
  'All Zeros': '000000',
  'All Ones': 'FFFFFF',
};
```

## After Making Changes

1. Save the file
2. Hot reload the app (press `r` in terminal or use IDE hot reload)
3. The new commands will appear as buttons in the Debug Console

## Tips

- Keep command names short and descriptive
- Group related commands together
- Test with simple 1-byte commands first
- Use comments to document what each command does

```dart
static const Map<String, String> _hexCommands = {
  // Device Control
  'Power On': '01',
  'Power Off': '00',
  
  // Data Reading
  'Read Battery': '10',
  'Read Sensor': '11',
  
  // Configuration
  'Reset': 'FF',
};
```

