import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:flutter_blue_plus/flutter_blue_plus.dart';
import '../providers/ble_provider.dart';
import '../models/ble_log_entry.dart';

class DeviceDetailScreen extends StatefulWidget {
  const DeviceDetailScreen({super.key});

  @override
  State<DeviceDetailScreen> createState() => _DeviceDetailScreenState();
}

class _DeviceDetailScreenState extends State<DeviceDetailScreen> {
  bool _showServices = false;
  bool _isInitialized = false;
  
  // Hex command to send
  static const String _hexCommand = '6901000000000000000000000000006A';

  @override
  void initState() {
    super.initState();
    // Auto-select a writable characteristic
    WidgetsBinding.instance.addPostFrameCallback((_) {
      _autoSelectCharacteristic();
    });
  }

  void _autoSelectCharacteristic() {
    final bleProvider = Provider.of<BleProvider>(context, listen: false);
    
    if (_isInitialized) return;
    
    // Find first characteristic that has WRITE and NOTIFY properties
    for (var service in bleProvider.services) {
      for (var char in service.characteristics) {
        if (char.properties.write && char.properties.notify) {
          bleProvider.selectCharacteristic(char);
          _isInitialized = true;
          return;
        }
      }
    }
    
    // If no WRITE+NOTIFY found, just find any writable characteristic
    for (var service in bleProvider.services) {
      for (var char in service.characteristics) {
        if (char.properties.write) {
          bleProvider.selectCharacteristic(char);
          _isInitialized = true;
          return;
        }
      }
    }
  }

  Future<void> _sendCommand(BleProvider bleProvider) async {
    if (!bleProvider.isConnected) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Not connected to device'),
          backgroundColor: Colors.red,
        ),
      );
      return;
    }

    if (bleProvider.selectedCharacteristic == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('No characteristic selected'),
          backgroundColor: Colors.red,
        ),
      );
      return;
    }

    try {
      await bleProvider.sendHexData(_hexCommand);
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('Command sent!'),
            duration: Duration(milliseconds: 800),
            backgroundColor: Colors.green,
          ),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Error: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }

  String _getLatestReceivedData(BleProvider bleProvider) {
    // Find the most recent received data
    final receivedLogs = bleProvider.logs
        .where((log) => log.type == LogType.received && log.data != null)
        .toList();
    
    if (receivedLogs.isEmpty) {
      return 'Waiting for data...';
    }
    
    final latestLog = receivedLogs.last;
    return latestLog.data!
        .map((b) => b.toRadixString(16).toUpperCase().padLeft(2, '0'))
        .join(' ');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Device Control'),
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        actions: [
          IconButton(
            icon: Icon(_showServices ? Icons.visibility_off : Icons.visibility),
            onPressed: () {
              setState(() {
                _showServices = !_showServices;
              });
            },
            tooltip: _showServices ? 'Hide Services' : 'Show Services',
          ),
          Consumer<BleProvider>(
            builder: (context, bleProvider, child) {
              return IconButton(
                icon: const Icon(Icons.power_settings_new),
                onPressed: () async {
                  await bleProvider.disconnect();
                  if (context.mounted) {
                    Navigator.pop(context);
                  }
                },
                tooltip: 'Disconnect',
              );
            },
          ),
        ],
      ),
      body: Consumer<BleProvider>(
        builder: (context, bleProvider, child) {
          if (bleProvider.services.isEmpty) {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }

          return Column(
            children: [
              // Connection status card
              Card(
                margin: const EdgeInsets.all(16),
                color: bleProvider.isConnected
                    ? Colors.green.shade50
                    : Colors.red.shade50,
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Row(
                    children: [
                      Icon(
                        bleProvider.isConnected
                            ? Icons.bluetooth_connected
                            : Icons.bluetooth_disabled,
                        color: bleProvider.isConnected
                            ? Colors.green
                            : Colors.red,
                        size: 32,
                      ),
                      const SizedBox(width: 16),
                      Expanded(
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              bleProvider.connectedDevice?.platformName ??
                                  'Unknown Device',
                              style: const TextStyle(
                                fontSize: 18,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            Text(
                              bleProvider.isConnected
                                  ? 'Connected'
                                  : 'Disconnected',
                              style: TextStyle(
                                color: bleProvider.isConnected
                                    ? Colors.green
                                    : Colors.red,
                              ),
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              ),

              // Send Command Button
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 16),
                child: SizedBox(
                  width: double.infinity,
                  height: 60,
                  child: ElevatedButton.icon(
                    onPressed: bleProvider.isConnected
                        ? () => _sendCommand(bleProvider)
                        : null,
                    icon: const Icon(Icons.send, size: 28),
                    label: const Text(
                      'Send Command',
                      style: TextStyle(fontSize: 18),
                    ),
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.blue,
                      foregroundColor: Colors.white,
                      disabledBackgroundColor: Colors.grey,
                      disabledForegroundColor: Colors.white70,
                    ),
                  ),
                ),
              ),

              const SizedBox(height: 8),

              // Command info
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 16),
                child: Text(
                  'Command: $_hexCommand',
                  style: const TextStyle(
                    fontSize: 12,
                    fontFamily: 'monospace',
                    color: Colors.grey,
                  ),
                ),
              ),

              const SizedBox(height: 16),

              // Latest Response Display
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 16),
                child: Container(
                  width: double.infinity,
                  padding: const EdgeInsets.all(16),
                  decoration: BoxDecoration(
                    color: Colors.black87,
                    borderRadius: BorderRadius.circular(8),
                    border: Border.all(color: Colors.green, width: 2),
                  ),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Row(
                        children: [
                          Icon(
                            Icons.sensors,
                            color: Colors.greenAccent,
                            size: 16,
                          ),
                          SizedBox(width: 8),
                          Text(
                            'LATEST RESPONSE',
                            style: TextStyle(
                              color: Colors.greenAccent,
                              fontSize: 12,
                              fontWeight: FontWeight.bold,
                              letterSpacing: 1.2,
                            ),
                          ),
                        ],
                      ),
                      const SizedBox(height: 12),
                      Text(
                        _getLatestReceivedData(bleProvider),
                        style: const TextStyle(
                          color: Colors.greenAccent,
                          fontSize: 16,
                          fontFamily: 'monospace',
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ],
                  ),
                ),
              ),

              const SizedBox(height: 16),

              // Received Data Section
              Expanded(
                child: Column(
                  children: [
                    Container(
                      width: double.infinity,
                      padding: const EdgeInsets.all(12),
                      color: Colors.blue.shade50,
                      child: Row(
                        children: [
                          const Icon(Icons.arrow_downward, size: 20, color: Colors.blue),
                          const SizedBox(width: 8),
                          const Text(
                            'Device Responses',
                            style: TextStyle(
                              fontSize: 16,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          const SizedBox(width: 8),
                          // Message counter
                          Container(
                            padding: const EdgeInsets.symmetric(
                              horizontal: 6,
                              vertical: 2,
                            ),
                            decoration: BoxDecoration(
                              color: Colors.blue.shade700,
                              borderRadius: BorderRadius.circular(10),
                            ),
                            child: Text(
                              '${bleProvider.logs.where((log) => log.type == LogType.received).length}',
                              style: const TextStyle(
                                fontSize: 11,
                                color: Colors.white,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ),
                          const Spacer(),
                          // Live indicator
                          if (bleProvider.logs.where((log) => 
                              log.type == LogType.received).isNotEmpty)
                            Container(
                              padding: const EdgeInsets.symmetric(
                                horizontal: 8,
                                vertical: 4,
                              ),
                              decoration: BoxDecoration(
                                color: Colors.green,
                                borderRadius: BorderRadius.circular(12),
                              ),
                              child: const Row(
                                mainAxisSize: MainAxisSize.min,
                                children: [
                                  Icon(
                                    Icons.circle,
                                    size: 8,
                                    color: Colors.white,
                                  ),
                                  SizedBox(width: 4),
                                  Text(
                                    'LIVE',
                                    style: TextStyle(
                                      fontSize: 10,
                                      color: Colors.white,
                                      fontWeight: FontWeight.bold,
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          const SizedBox(width: 8),
                          // Clear logs button
                          IconButton(
                            icon: const Icon(Icons.delete_sweep, size: 20),
                            onPressed: () {
                              bleProvider.clearLogs();
                            },
                            tooltip: 'Clear Logs',
                            padding: EdgeInsets.zero,
                            constraints: const BoxConstraints(),
                          ),
                        ],
                      ),
                    ),
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
                                    'No data received yet',
                                    style: TextStyle(
                                      fontSize: 16,
                                      color: Colors.grey[600],
                                    ),
                                  ),
                                  const SizedBox(height: 8),
                                  Text(
                                    'Send a command to see responses',
                                    style: TextStyle(
                                      fontSize: 14,
                                      color: Colors.grey[500],
                                    ),
                                  ),
                                ],
                              ),
                            )
                          : ListView.builder(
                              reverse: true,
                              padding: const EdgeInsets.all(8),
                              itemCount: bleProvider.logs.length,
                              itemBuilder: (context, index) {
                                final log = bleProvider.logs[
                                    bleProvider.logs.length - 1 - index];
                                return _LogTile(log: log);
                              },
                            ),
                    ),
                  ],
                ),
              ),

              // Show services section if toggled
              if (_showServices)
                Expanded(
                  child: Column(
                    children: [
                      Container(
                        width: double.infinity,
                        padding: const EdgeInsets.all(12),
                        color: Colors.grey.shade200,
                        child: const Text(
                          'Services & Characteristics',
                          style: TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                      Expanded(
                        child: ListView.builder(
                          itemCount: bleProvider.services.length,
                          itemBuilder: (context, index) {
                            final service = bleProvider.services[index];
                            return _ServiceCard(service: service);
                          },
                        ),
                      ),
                    ],
                  ),
                ),
            ],
          );
        },
      ),
    );
  }
}

class _LogTile extends StatelessWidget {
  final BleLogEntry log;

  const _LogTile({required this.log});

  @override
  Widget build(BuildContext context) {
    // Compact view for received data
    if (log.type == LogType.received && log.data != null) {
      return Container(
        margin: const EdgeInsets.symmetric(vertical: 2, horizontal: 8),
        padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
        decoration: BoxDecoration(
          color: Colors.orange.shade50,
          border: Border(
            left: BorderSide(color: Colors.orange, width: 3),
          ),
        ),
        child: Row(
          children: [
            Icon(Icons.arrow_downward, size: 14, color: Colors.orange.shade700),
            const SizedBox(width: 8),
            Text(
              log.formattedTime,
              style: const TextStyle(
                fontSize: 10,
                fontFamily: 'monospace',
                color: Colors.grey,
              ),
            ),
            const SizedBox(width: 12),
            Expanded(
              child: Text(
                log.data!
                    .map((b) => b.toRadixString(16).toUpperCase().padLeft(2, '0'))
                    .join(' '),
                style: const TextStyle(
                  fontSize: 12,
                  fontFamily: 'monospace',
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
          ],
        ),
      );
    }
    
    // Regular view for other log types
    return Card(
      margin: const EdgeInsets.symmetric(vertical: 4, horizontal: 8),
      color: _getBackgroundColor(),
      child: Padding(
        padding: const EdgeInsets.all(12),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                Icon(_getIcon(), size: 18, color: _getColor()),
                const SizedBox(width: 8),
                Text(
                  log.formattedTime,
                  style: const TextStyle(
                    fontSize: 12,
                    fontFamily: 'monospace',
                    fontWeight: FontWeight.bold,
                  ),
                ),
                const Spacer(),
                Text(
                  _getTypeLabel(),
                  style: TextStyle(
                    fontSize: 11,
                    color: _getColor(),
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 8),
            Text(
              log.message,
              style: const TextStyle(fontSize: 14),
            ),
            if (log.data != null && log.data!.isNotEmpty) ...[
              const SizedBox(height: 8),
              Container(
                width: double.infinity,
                padding: const EdgeInsets.all(8),
                decoration: BoxDecoration(
                  color: Colors.black87,
                  borderRadius: BorderRadius.circular(4),
                ),
                child: Text(
                  log.data!
                      .map((b) => b.toRadixString(16).toUpperCase().padLeft(2, '0'))
                      .join(' '),
                  style: const TextStyle(
                    fontSize: 13,
                    fontFamily: 'monospace',
                    color: Colors.greenAccent,
                  ),
                ),
              ),
            ],
          ],
        ),
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
      case LogType.sent:
        return Colors.green.shade50;
      case LogType.received:
        return Colors.orange.shade50;
      case LogType.error:
        return Colors.red.shade50;
      case LogType.warning:
        return Colors.amber.shade50;
      default:
        return Colors.grey.shade50;
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

  String _getTypeLabel() {
    switch (log.type) {
      case LogType.info:
        return 'INFO';
      case LogType.sent:
        return 'SENT';
      case LogType.received:
        return 'RECEIVED';
      case LogType.error:
        return 'ERROR';
      case LogType.warning:
        return 'WARNING';
    }
  }
}

class _ServiceCard extends StatefulWidget {
  final BluetoothService service;

  const _ServiceCard({required this.service});

  @override
  State<_ServiceCard> createState() => _ServiceCardState();
}

class _ServiceCardState extends State<_ServiceCard> {
  bool _isExpanded = false;

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 4),
      child: Column(
        children: [
          ListTile(
            leading: const Icon(Icons.settings_input_antenna),
            title: const Text(
              'Service',
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 12),
            ),
            subtitle: Text(
              widget.service.uuid.toString(),
              style: const TextStyle(fontSize: 10, fontFamily: 'monospace'),
            ),
            trailing: IconButton(
              icon: Icon(
                _isExpanded ? Icons.expand_less : Icons.expand_more,
              ),
              onPressed: () {
                setState(() {
                  _isExpanded = !_isExpanded;
                });
              },
            ),
          ),
          if (_isExpanded)
            Column(
              children: widget.service.characteristics
                  .map((char) => _CharacteristicTile(characteristic: char))
                  .toList(),
            ),
        ],
      ),
    );
  }
}

class _CharacteristicTile extends StatelessWidget {
  final BluetoothCharacteristic characteristic;

  const _CharacteristicTile({required this.characteristic});

  @override
  Widget build(BuildContext context) {
    final bleProvider = Provider.of<BleProvider>(context, listen: false);
    
    return Container(
      decoration: BoxDecoration(
        border: Border(
          top: BorderSide(color: Colors.grey.shade300),
        ),
      ),
      child: ListTile(
        contentPadding: const EdgeInsets.only(left: 32, right: 16),
        leading: const Icon(Icons.scatter_plot, size: 16),
        title: const Text(
          'Characteristic',
          style: TextStyle(fontSize: 11),
        ),
        subtitle: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              characteristic.uuid.toString(),
              style: const TextStyle(fontSize: 9, fontFamily: 'monospace'),
            ),
            const SizedBox(height: 4),
            Wrap(
              spacing: 4,
              children: [
                if (characteristic.properties.read)
                  _PropertyChip('R', Colors.blue),
                if (characteristic.properties.write)
                  _PropertyChip('W', Colors.green),
                if (characteristic.properties.notify)
                  _PropertyChip('N', Colors.orange),
                if (characteristic.properties.indicate)
                  _PropertyChip('I', Colors.purple),
              ],
            ),
          ],
        ),
        trailing: ElevatedButton(
          onPressed: () {
            bleProvider.selectCharacteristic(characteristic);
            ScaffoldMessenger.of(context).showSnackBar(
              const SnackBar(
                content: Text('Characteristic selected!'),
                duration: Duration(seconds: 1),
              ),
            );
          },
          style: ElevatedButton.styleFrom(
            padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
          ),
          child: const Text('Select', style: TextStyle(fontSize: 10)),
        ),
      ),
    );
  }
}

class _PropertyChip extends StatelessWidget {
  final String label;
  final Color color;

  const _PropertyChip(this.label, this.color);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 2),
      decoration: BoxDecoration(
        color: color.withValues(alpha: 0.2),
        borderRadius: BorderRadius.circular(3),
        border: Border.all(color: color, width: 1),
      ),
      child: Text(
        label,
        style: TextStyle(
          fontSize: 9,
          color: color,
          fontWeight: FontWeight.bold,
        ),
      ),
    );
  }
}

