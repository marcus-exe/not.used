import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import '../screens/debug_tools_screen.dart';

mixin DebugScaffoldMixin {
  Widget buildScaffold(BuildContext context, Widget Function(BuildContext) scaffoldBuilder) {
    if (!kDebugMode) {
      return scaffoldBuilder(context);
    }

    final scaffold = scaffoldBuilder(context);
    
    if (scaffold is Scaffold) {
      return Scaffold(
        appBar: scaffold.appBar,
        body: scaffold.body,
        floatingActionButton: _buildStackedFab(context, scaffold.floatingActionButton),
        floatingActionButtonLocation: scaffold.floatingActionButtonLocation,
        bottomNavigationBar: scaffold.bottomNavigationBar,
        drawer: scaffold.drawer,
        endDrawer: scaffold.endDrawer,
        backgroundColor: scaffold.backgroundColor,
        resizeToAvoidBottomInset: scaffold.resizeToAvoidBottomInset,
        bottomSheet: scaffold.bottomSheet,
        persistentFooterButtons: scaffold.persistentFooterButtons,
      );
    }

    return scaffold;
  }

  Widget? _buildStackedFab(BuildContext context, Widget? existingFab) {
    if (!kDebugMode) return existingFab;

    final debugFab = FloatingActionButton(
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
    );

    if (existingFab == null) {
      return debugFab;
    }

    // Se já existe um FAB, empilha ambos
    return Stack(
      alignment: Alignment.bottomRight,
      children: [
        Positioned(
          bottom: 0,
          right: 0,
          child: existingFab,
        ),
        Positioned(
          bottom: 0,
          right: 70,
          child: debugFab,
        ),
      ],
    );
  }
}

