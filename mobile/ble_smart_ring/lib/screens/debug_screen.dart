import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/ble_provider.dart';
import '../models/ble_log_entry.dart';

class DebugScreen extends StatelessWidget {
  const DebugScreen({super.key});

  // DEFINE YOUR HARDCODED HEX COMMANDS HERE
  static const Map<String, String> _hexCommands = {
    'Send Command': '6901000000000000000000000000006A',
  };

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Debug Console'),
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        actions: [
          Consumer<BleProvider>(
            builder: (context, bleProvider, child) {
              return IconButton(
                icon: const Icon(Icons.delete_sweep),
                onPressed: () {
                  bleProvider.clearLogs();
                },
                tooltip: 'Clear Logs',
              );
            },
          ),
        ],
      ),
      body: Consumer<BleProvider>(
        builder: (context, bleProvider, child) {
          return Column(
            children: [
              // Connection status banner
              Container(
                width: double.infinity,
                padding: const EdgeInsets.all(12),
                color: bleProvider.isConnected
                    ? Colors.green.shade100
                    : Colors.red.shade100,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Icon(
                      bleProvider.isConnected
                          ? Icons.check_circle
                          : Icons.error,
                      color: bleProvider.isConnected
                          ? Colors.green.shade700
                          : Colors.red.shade700,
                      size: 20,
                    ),
                    const SizedBox(width: 8),
                    Text(
                      bleProvider.isConnected
                          ? 'Connected to ${bleProvider.connectedDevice?.platformName ?? "Device"}'
                          : 'Disconnected',
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: bleProvider.isConnected
                            ? Colors.green.shade700
                            : Colors.red.shade700,
                      ),
                    ),
                  ],
                ),
              ),

              // Selected characteristic info
              if (bleProvider.selectedCharacteristic != null)
                Container(
                  width: double.infinity,
                  padding: const EdgeInsets.all(12),
                  color: Colors.blue.shade50,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text(
                        'Selected Characteristic:',
                        style: TextStyle(
                          fontSize: 12,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      const SizedBox(height: 4),
                      Text(
                        bleProvider.selectedCharacteristic!.uuid.toString(),
                        style: const TextStyle(
                          fontSize: 11,
                          fontFamily: 'monospace',
                        ),
                      ),
                    ],
                  ),
                ),

              // Command buttons
              Container(
                padding: const EdgeInsets.all(16),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      'Predefined Commands:',
                      style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const SizedBox(height: 12),
                    Wrap(
                      spacing: 8,
                      runSpacing: 8,
                      children: _hexCommands.entries.map((entry) {
                        return _CommandButton(
                          label: entry.key,
                          hexData: entry.value,
                          onPressed: bleProvider.isConnected &&
                                  bleProvider.selectedCharacteristic != null
                              ? () async {
                                  try {
                                    await bleProvider.sendHexData(entry.value);
                                    if (context.mounted) {
                                      ScaffoldMessenger.of(context)
                                          .showSnackBar(
                                        SnackBar(
                                          content: Text(
                                            'Sent: ${entry.value}',
                                          ),
                                          duration:
                                              const Duration(milliseconds: 500),
                                          backgroundColor: Colors.green,
                                        ),
                                      );
                                    }
                                  } catch (e) {
                                    if (context.mounted) {
                                      ScaffoldMessenger.of(context)
                                          .showSnackBar(
                                        SnackBar(
                                          content: Text('Error: $e'),
                                          backgroundColor: Colors.red,
                                        ),
                                      );
                                    }
                                  }
                                }
                              : null,
                        );
                      }).toList(),
                    ),
                  ],
                ),
              ),

              const Divider(height: 1),

              // Log header
              Padding(
                padding: const EdgeInsets.all(16),
                child: Row(
                  children: [
                    const Icon(Icons.description, size: 20),
                    const SizedBox(width: 8),
                    const Text(
                      'Communication Log',
                      style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const Spacer(),
                    Text(
                      '${bleProvider.logs.length} entries',
                      style: TextStyle(
                        fontSize: 12,
                        color: Colors.grey[600],
                      ),
                    ),
                  ],
                ),
              ),

              // Log entries
              Expanded(
                child: bleProvider.logs.isEmpty
                    ? Center(
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Icon(
                              Icons.info_outline,
                              size: 48,
                              color: Colors.grey[400],
                            ),
                            const SizedBox(height: 16),
                            Text(
                              'No log entries yet',
                              style: TextStyle(
                                fontSize: 16,
                                color: Colors.grey[600],
                              ),
                            ),
                            const SizedBox(height: 8),
                            Text(
                              'Send a command to see logs',
                              style: TextStyle(
                                fontSize: 14,
                                color: Colors.grey[500],
                              ),
                            ),
                          ],
                        ),
                      )
                    : ListView.builder(
                        reverse: true, // Show newest at bottom
                        itemCount: bleProvider.logs.length,
                        itemBuilder: (context, index) {
                          final log =
                              bleProvider.logs[bleProvider.logs.length - 1 - index];
                          return _LogEntry(log: log);
                        },
                      ),
              ),
            ],
          );
        },
      ),
    );
  }
}

class _CommandButton extends StatelessWidget {
  final String label;
  final String hexData;
  final VoidCallback? onPressed;

  const _CommandButton({
    required this.label,
    required this.hexData,
    required this.onPressed,
  });

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      onPressed: onPressed,
      style: ElevatedButton.styleFrom(
        padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
        backgroundColor: onPressed != null ? Colors.blue : Colors.grey,
        foregroundColor: Colors.white,
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisSize: MainAxisSize.min,
        children: [
          Text(
            label,
            style: const TextStyle(fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 2),
          Text(
            hexData,
            style: const TextStyle(
              fontSize: 10,
              fontFamily: 'monospace',
            ),
          ),
        ],
      ),
    );
  }
}

class _LogEntry extends StatelessWidget {
  final BleLogEntry log;

  const _LogEntry({required this.log});

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
      decoration: BoxDecoration(
        border: Border(
          bottom: BorderSide(color: Colors.grey.shade200),
        ),
        color: _getBackgroundColor(),
      ),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // Timestamp
          SizedBox(
            width: 80,
            child: Text(
              log.formattedTime,
              style: const TextStyle(
                fontSize: 11,
                fontFamily: 'monospace',
                color: Colors.grey,
              ),
            ),
          ),
          const SizedBox(width: 8),
          
          // Icon
          Icon(
            _getIcon(),
            size: 16,
            color: _getColor(),
          ),
          const SizedBox(width: 8),
          
          // Message
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  log.message,
                  style: TextStyle(
                    fontSize: 13,
                    color: _getColor(),
                    fontWeight: log.type == LogType.error
                        ? FontWeight.bold
                        : FontWeight.normal,
                  ),
                ),
                if (log.data != null && log.data!.isNotEmpty)
                  Padding(
                    padding: const EdgeInsets.only(top: 4),
                    child: Text(
                      'Data: ${log.data!.map((b) => b.toRadixString(16).toUpperCase().padLeft(2, '0')).join(' ')}',
                      style: const TextStyle(
                        fontSize: 11,
                        fontFamily: 'monospace',
                        color: Colors.black54,
                      ),
                    ),
                  ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Color _getColor() {
    switch (log.type) {
      case LogType.info:
        return Colors.blue;
      case LogType.sent:
        return Colors.green;
      case LogType.received:
        return Colors.orange;
      case LogType.error:
        return Colors.red;
      case LogType.warning:
        return Colors.amber;
    }
  }

  Color _getBackgroundColor() {
    switch (log.type) {
      case LogType.error:
        return Colors.red.shade50;
      case LogType.warning:
        return Colors.amber.shade50;
      case LogType.sent:
        return Colors.green.shade50;
      case LogType.received:
        return Colors.orange.shade50;
      default:
        return Colors.transparent;
    }
  }

  IconData _getIcon() {
    switch (log.type) {
      case LogType.info:
        return Icons.info_outline;
      case LogType.sent:
        return Icons.arrow_upward;
      case LogType.received:
        return Icons.arrow_downward;
      case LogType.error:
        return Icons.error_outline;
      case LogType.warning:
        return Icons.warning_outlined;
    }
  }
}

