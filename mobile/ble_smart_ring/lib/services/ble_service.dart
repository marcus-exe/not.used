// ignore_for_file: avoid_print

import 'dart:async';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

/// Service for managing BLE operations
class BleService {
  // Singleton pattern
  static final BleService _instance = BleService._internal();
  factory BleService() => _instance;
  BleService._internal();
  
  // Stream controllers for reactive updates
  final _scanResultsController = StreamController<List<ScanResult>>.broadcast();
  final _connectionStateController = StreamController<BluetoothConnectionState>.broadcast();
  
  Stream<List<ScanResult>> get scanResults => _scanResultsController.stream;
  Stream<BluetoothConnectionState> get connectionState => _connectionStateController.stream;
  
  final List<ScanResult> _scanResultsList = [];
  BluetoothDevice? _connectedDevice;
  StreamSubscription<BluetoothConnectionState>? _connectionStateSubscription;
  
  BluetoothDevice? get connectedDevice => _connectedDevice;
  bool get isScanning => FlutterBluePlus.isScanningNow;
  
  /// Start scanning for BLE devices
  Future<void> startScan({Duration timeout = const Duration(seconds: 4)}) async {
    try {
      // Clear previous results
      _scanResultsList.clear();
      _scanResultsController.add(_scanResultsList);
      
      // Start scanning
      await FlutterBluePlus.startScan(timeout: timeout);
      
      // Listen to scan results
      FlutterBluePlus.scanResults.listen((results) {
        _scanResultsList.clear();
        _scanResultsList.addAll(results);
        _scanResultsController.add(_scanResultsList);
      });
    } catch (e) {
      print('Error starting scan: $e');
      rethrow;
    }
  }
  
  /// Stop scanning
  Future<void> stopScan() async {
    try {
      await FlutterBluePlus.stopScan();
    } catch (e) {
      print('Error stopping scan: $e');
    }
  }
  
  /// Connect to a BLE device
  Future<void> connect(BluetoothDevice device) async {
    try {
      // Disconnect from any previously connected device
      if (_connectedDevice != null) {
        await disconnect();
      }
      
      // Connect to the new device
      await device.connect(timeout: const Duration(seconds: 15));
      _connectedDevice = device;
      
      // Listen to connection state changes
      _connectionStateSubscription?.cancel();
      _connectionStateSubscription = device.connectionState.listen((state) {
        _connectionStateController.add(state);
        if (state == BluetoothConnectionState.disconnected) {
          _connectedDevice = null;
        }
      });

      // Request higher MTU and connection priority (Android only)
      try {
        await device.requestMtu(247);
      } catch (_) {
        // ignore - not supported on all platforms
      }
      try {
        await device.requestConnectionPriority(
          connectionPriorityRequest: ConnectionPriority.high,
        );
      } catch (_) {
        // ignore - not supported on all platforms
      }
      
    } catch (e) {
      print('Error connecting to device: $e');
      rethrow;
    }
  }
  
  /// Disconnect from the current device
  Future<void> disconnect() async {
    try {
      if (_connectedDevice != null) {
        await _connectedDevice!.disconnect();
        _connectionStateSubscription?.cancel();
        _connectedDevice = null;
      }
    } catch (e) {
      print('Error disconnecting: $e');
    }
  }
  
  /// Discover services and characteristics
  Future<List<BluetoothService>> discoverServices() async {
    if (_connectedDevice == null) {
      throw Exception('No device connected');
    }
    
    try {
      return await _connectedDevice!.discoverServices();
    } catch (e) {
      print('Error discovering services: $e');
      rethrow;
    }
  }
  
  /// Write data to a characteristic
  Future<void> writeCharacteristic(
    BluetoothCharacteristic characteristic,
    List<int> data,
  ) async {
    try {
      // Try the most permissive path first: withoutResponse if supported
      if (characteristic.properties.writeWithoutResponse) {
        try {
          await characteristic.write(data, withoutResponse: true);
          return;
        } catch (_) {
          // fall back to with response
        }
      }
      // Fallback to write with response
      await characteristic.write(data, withoutResponse: false);
    } catch (e) {
      print('Error writing to characteristic: $e');
      rethrow;
    }
  }
  
  /// Read data from a characteristic
  Future<List<int>> readCharacteristic(BluetoothCharacteristic characteristic) async {
    try {
      return await characteristic.read();
    } catch (e) {
      print('Error reading from characteristic: $e');
      rethrow;
    }
  }
  
  /// Subscribe to notifications from a characteristic
  Future<Stream<List<int>>> subscribeToCharacteristic(
    BluetoothCharacteristic characteristic,
  ) async {
    try {
      await characteristic.setNotifyValue(true);
      return characteristic.onValueReceived;
    } catch (e) {
      print('Error subscribing to characteristic: $e');
      rethrow;
    }
  }
  
  /// Unsubscribe from notifications
  Future<void> unsubscribeFromCharacteristic(
    BluetoothCharacteristic characteristic,
  ) async {
    try {
      await characteristic.setNotifyValue(false);
    } catch (e) {
      print('Error unsubscribing from characteristic: $e');
    }
  }
  
  /// Check if Bluetooth is available and enabled
  Future<bool> isBluetoothAvailable() async {
    try {
      return await FlutterBluePlus.isSupported;
    } catch (e) {
      return false;
    }
  }
  
  /// Dispose resources
  void dispose() {
    _scanResultsController.close();
    _connectionStateController.close();
    _connectionStateSubscription?.cancel();
  }
}

