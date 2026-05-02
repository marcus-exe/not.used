/// Represents a single log entry in the BLE communication log
class BleLogEntry {
  final DateTime timestamp;
  final String message;
  final LogType type;
  final List<int>? data;
  
  BleLogEntry({
    required this.timestamp,
    required this.message,
    required this.type,
    this.data,
  });
  
  /// Format the timestamp for display
  String get formattedTime {
    return '${timestamp.hour.toString().padLeft(2, '0')}:'
           '${timestamp.minute.toString().padLeft(2, '0')}:'
           '${timestamp.second.toString().padLeft(2, '0')}.'
           '${(timestamp.millisecond ~/ 10).toString().padLeft(2, '0')}';
  }
}

/// Types of log entries
enum LogType {
  info,      // General information
  sent,      // Data sent to device
  received,  // Data received from device
  error,     // Error messages
  warning,   // Warning messages
}

