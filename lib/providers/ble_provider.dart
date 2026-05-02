import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import '../services/ble_service.dart';
import '../models/ble_log_entry.dart';
import '../utils/hex_utils.dart';

/// Provider for managing BLE state and operations
class BleProvider extends ChangeNotifier {
  final BleService _bleService = BleService();
  
  // State
  List<ScanResult> _scanResults = [];
  bool _isScanning = false;
  BluetoothDevice? _connectedDevice;
  BluetoothConnectionState _connectionState = BluetoothConnectionState.disconnected;
  List<BluetoothService> _services = [];
  BluetoothCharacteristic? _selectedCharacteristic;
  final List<BleLogEntry> _logs = [];
  StreamSubscription<List<int>>? _notificationSubscription;
  final List<StreamSubscription<List<int>>> _allNotificationSubscriptions = [];
  
  // Getters
  List<ScanResult> get scanResults => _scanResults;
  bool get isScanning => _isScanning;
  BluetoothDevice? get connectedDevice => _connectedDevice;
  BluetoothConnectionState get connectionState => _connectionState;
  List<BluetoothService> get services => _services;
  BluetoothCharacteristic? get selectedCharacteristic => _selectedCharacteristic;
  List<BleLogEntry> get logs => _logs;
  bool get isConnected => _connectionState == BluetoothConnectionState.connected;
  
  BleProvider() {
    _initialize();
  }
  
  void _initialize() {
    // Listen to scan results
    _bleService.scanResults.listen((results) {
      _scanResults = results;
      notifyListeners();
    });
    
    // Listen to connection state changes
    _bleService.connectionState.listen((state) {
      _connectionState = state;
      if (state == BluetoothConnectionState.disconnected) {
        _connectedDevice = null;
        _services = [];
        _selectedCharacteristic = null;
        _notificationSubscription?.cancel();
        addLog('Disconnected from device', LogType.info);
      }
      notifyListeners();
    });
  }
  
  /// Start scanning for devices
  Future<void> startScan() async {
    try {
      _isScanning = true;
      notifyListeners();
      
      addLog('Started scanning for devices...', LogType.info);
      await _bleService.startScan(timeout: const Duration(seconds: 10));
      
      // Wait for scan to complete
      await Future.delayed(const Duration(seconds: 10));
      _isScanning = false;
      addLog('Scan completed. Found ${_scanResults.length} devices.', LogType.info);
      notifyListeners();
    } catch (e) {
      _isScanning = false;
      addLog('Error scanning: $e', LogType.error);
      notifyListeners();
      rethrow;
    }
  }
  
  /// Stop scanning
  Future<void> stopScan() async {
    try {
      await _bleService.stopScan();
      _isScanning = false;
      addLog('Stopped scanning', LogType.info);
      notifyListeners();
    } catch (e) {
      addLog('Error stopping scan: $e', LogType.error);
    }
  }
  
  /// Connect to a device
  Future<void> connectToDevice(BluetoothDevice device) async {
    try {
      addLog('Connecting to ${device.platformName}...', LogType.info);
      await _bleService.connect(device);
      _connectedDevice = device;
      _connectionState = BluetoothConnectionState.connected;
      addLog('Connected to ${device.platformName}', LogType.info);
      
      // Discover services automatically
      await discoverServices();

      // Auto-subscribe to all notifiable characteristics to capture any responses
      _subscribeToAllNotifiableCharacteristics();
      
      notifyListeners();
    } catch (e) {
      addLog('Error connecting: $e', LogType.error);
      rethrow;
    }
  }
  
  /// Disconnect from device
  Future<void> disconnect() async {
    try {
      await _bleService.disconnect();
      _connectedDevice = null;
      _services = [];
      _selectedCharacteristic = null;
      _notificationSubscription?.cancel();
      for (final sub in _allNotificationSubscriptions) {
        await sub.cancel();
      }
      _allNotificationSubscriptions.clear();
      notifyListeners();
    } catch (e) {
      addLog('Error disconnecting: $e', LogType.error);
    }
  }
  
  /// Discover services
  Future<void> discoverServices() async {
    try {
      addLog('Discovering services...', LogType.info);
      _services = await _bleService.discoverServices();
      addLog('Found ${_services.length} services', LogType.info);
      notifyListeners();
    } catch (e) {
      addLog('Error discovering services: $e', LogType.error);
      rethrow;
    }
  }
  
  /// Select a characteristic for communication
  void selectCharacteristic(BluetoothCharacteristic characteristic) {
    _selectedCharacteristic = characteristic;
    addLog(
      'Selected characteristic: ${characteristic.uuid.toString()}',
      LogType.info,
    );
    
    // Subscribe to notifications if supported
    if (characteristic.properties.notify || characteristic.properties.indicate) {
      subscribeToNotifications(characteristic);
    }
    
    notifyListeners();
  }
  
  /// Subscribe to notifications from a characteristic
  Future<void> subscribeToNotifications(BluetoothCharacteristic characteristic) async {
    try {
      _notificationSubscription?.cancel();
      
      final stream = await _bleService.subscribeToCharacteristic(characteristic);
      _notificationSubscription = stream.listen((data) {
        addLog(
          'Received: ${HexUtils.formatBytesForDisplay(data)}',
          LogType.received,
          data,
        );
      });
      
      addLog('Subscribed to notifications', LogType.info);
    } catch (e) {
      addLog('Error subscribing to notifications: $e', LogType.error);
    }
  }

  void _subscribeToAllNotifiableCharacteristics() async {
    if (_services.isEmpty) return;
    for (final service in _services) {
      for (final characteristic in service.characteristics) {
        if (characteristic.properties.notify || characteristic.properties.indicate) {
          try {
            final stream = await _bleService.subscribeToCharacteristic(characteristic);
            final sub = stream.listen((data) {
              addLog(
                'Received: ${HexUtils.formatBytesForDisplay(data)} (char ${characteristic.uuid})',
                LogType.received,
                data,
              );
            });
            _allNotificationSubscriptions.add(sub);
          } catch (e) {
            addLog('Failed to subscribe on ${characteristic.uuid}: $e', LogType.warning);
          }
        }
      }
    }
  }
  
  /// Send hex data to the selected characteristic
  Future<void> sendHexData(String hexString) async {
    if (_selectedCharacteristic == null) {
      addLog('No characteristic selected', LogType.error);
      return;
    }
    
    try {
      final bytes = HexUtils.hexStringToBytes(hexString);
      await _bleService.writeCharacteristic(_selectedCharacteristic!, bytes);
      addLog(
        'Sent: ${HexUtils.formatBytesForDisplay(bytes)}',
        LogType.sent,
        bytes,
      );
      notifyListeners();
    } catch (e) {
      addLog('Error sending data: $e', LogType.error);
      rethrow;
    }
  }
  
  /// Read data from the selected characteristic
  Future<List<int>?> readData() async {
    if (_selectedCharacteristic == null) {
      addLog('No characteristic selected', LogType.error);
      return null;
    }
    
    try {
      final data = await _bleService.readCharacteristic(_selectedCharacteristic!);
      addLog(
        'Read: ${HexUtils.formatBytesForDisplay(data)}',
        LogType.received,
        data,
      );
      notifyListeners();
      return data;
    } catch (e) {
      addLog('Error reading data: $e', LogType.error);
      return null;
    }
  }
  
  /// Add a log entry
  void addLog(String message, LogType type, [List<int>? data]) {
    _logs.add(BleLogEntry(
      timestamp: DateTime.now(),
      message: message,
      type: type,
      data: data,
    ));
    notifyListeners();
  }
  
  /// Clear logs
  void clearLogs() {
    _logs.clear();
    notifyListeners();
  }
  
  @override
  void dispose() {
    _notificationSubscription?.cancel();
    super.dispose();
  }
}

