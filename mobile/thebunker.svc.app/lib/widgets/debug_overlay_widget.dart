import 'package:flutter/foundation.dart';
import 'package:flutter/scheduler.dart';
import 'package:flutter/material.dart';
import '../screens/debug_tools_screen.dart';

class DebugOverlayWidget extends StatefulWidget {
  final Widget child;

  const DebugOverlayWidget({
    super.key,
    required this.child,
  });

  @override
  State<DebugOverlayWidget> createState() => _DebugOverlayWidgetState();
}

class _DebugOverlayWidgetState extends State<DebugOverlayWidget> {
  OverlayEntry? _overlayEntry;

  @override
  void initState() {
    super.initState();
    if (kDebugMode) {
      SchedulerBinding.instance.addPostFrameCallback((_) {
        _addDebugButton();
      });
    }
  }

  @override
  void dispose() {
    _removeDebugButton();
    super.dispose();
  }

  void _addDebugButton() {
    if (!kDebugMode || !mounted) return;

    // Tenta obter o overlay, se não estiver disponível ainda, agenda novamente
    final overlay = Overlay.maybeOf(context);
    if (overlay == null) {
      // Tenta novamente no próximo frame se o overlay não estiver disponível
      SchedulerBinding.instance.addPostFrameCallback((_) {
        if (mounted) _addDebugButton();
      });
      return;
    }

    _removeDebugButton();

    _overlayEntry = OverlayEntry(
      builder: (context) => Positioned(
        bottom: 16,
        right: 16,
        child: FloatingActionButton(
          mini: true,
          backgroundColor: Colors.orange.shade700,
          onPressed: () {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (_) => const DebugToolsScreen(),
              ),
            );
          },
          child: const Icon(Icons.bug_report, color: Colors.white),
        ),
      ),
    );

    overlay.insert(_overlayEntry!);
  }

  void _removeDebugButton() {
    _overlayEntry?.remove();
    _overlayEntry = null;
  }

  @override
  Widget build(BuildContext context) {
    return widget.child;
  }
}

