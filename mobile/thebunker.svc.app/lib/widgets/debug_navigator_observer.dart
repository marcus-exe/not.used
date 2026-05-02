import 'package:flutter/foundation.dart';
import 'package:flutter/scheduler.dart';
import 'package:flutter/material.dart';
import '../screens/debug_tools_screen.dart';

class DebugNavigatorObserver extends NavigatorObserver {
  OverlayEntry? _overlayEntry;

  @override
  void didPush(Route<dynamic> route, Route<dynamic>? previousRoute) {
    _scheduleAddDebugButton(route.navigator?.context);
  }

  @override
  void didPop(Route<dynamic> route, Route<dynamic>? previousRoute) {
    _scheduleAddDebugButton(route.navigator?.context ?? previousRoute?.navigator?.context);
  }

  @override
  void didReplace({Route<dynamic>? newRoute, Route<dynamic>? oldRoute}) {
    _scheduleAddDebugButton(newRoute?.navigator?.context);
  }

  void _scheduleAddDebugButton(BuildContext? context) {
    if (!kDebugMode || context == null) {
      _removeDebugButton();
      return;
    }

    // Agenda para depois do frame atual ser construído
    SchedulerBinding.instance.addPostFrameCallback((_) {
      _addDebugButton(context);
    });
  }

  void _addDebugButton(BuildContext context) {
    if (!kDebugMode) {
      _removeDebugButton();
      return;
    }

    _removeDebugButton();

    // Verifica se o overlay está disponível
    final overlay = Overlay.maybeOf(context);
    if (overlay == null) return;

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

  void dispose() {
    _removeDebugButton();
  }

  @override
  void didRemove(Route<dynamic> route, Route<dynamic>? previousRoute) {
    _scheduleAddDebugButton(previousRoute?.navigator?.context);
  }
}

