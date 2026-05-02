import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import '../screens/debug_tools_screen.dart';

class DebugFabBuilder extends StatelessWidget {
  final Widget child;

  const DebugFabBuilder({
    super.key,
    required this.child,
  });

  @override
  Widget build(BuildContext context) {
    if (!kDebugMode) {
      return child;
    }

    // Usa MediaQuery para obter o tamanho da tela
    return MediaQuery(
      data: MediaQuery.of(context),
      child: Stack(
        clipBehavior: Clip.none,
        children: [
          child,
          // Usa um widget que consegue acessar o Navigator
          _DebugFloatingButton(),
        ],
      ),
    );
  }
}

class _DebugFloatingButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Positioned(
      bottom: 16,
      right: 16,
      child: FloatingActionButton(
        mini: true,
        backgroundColor: Colors.orange.shade700,
        heroTag: "debug_fab_unique",
        onPressed: () {
          // Tenta encontrar o Navigator mais próximo
          final navigator = Navigator.maybeOf(context, rootNavigator: false);
          if (navigator != null) {
            navigator.push(
              MaterialPageRoute(
                builder: (context) => const DebugToolsScreen(),
              ),
            );
          } else {
            // Se não encontrar, tenta usar rootNavigator
            final rootNavigator = Navigator.maybeOf(context, rootNavigator: true);
            rootNavigator?.push(
              MaterialPageRoute(
                builder: (context) => const DebugToolsScreen(),
              ),
            );
          }
        },
        child: const Icon(Icons.bug_report, color: Colors.white),
      ),
    );
  }
}
