import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import '../screens/debug_tools_screen.dart';

class DebugRouteObserver extends NavigatorObserver {
  OverlayEntry? _overlayEntry;
  BuildContext? _currentContext;
  Offset? _buttonPosition; // Posição do botão, null = usar posição padrão (canto inferior direito)
  
  // Métodos públicos para acesso externo
  void setContext(BuildContext context) {
    _currentContext = context;
    _scheduleAddButton();
  }

  @override
  void didPush(Route<dynamic> route, Route<dynamic>? previousRoute) {
    super.didPush(route, previousRoute);
    if (kDebugMode) {
      _currentContext = route.navigator?.context;
      _scheduleAddButton();
    }
  }

  @override
  void didPop(Route<dynamic> route, Route<dynamic>? previousRoute) {
    super.didPop(route, previousRoute);
    if (kDebugMode) {
      // Quando volta, usa o contexto do Navigator que ainda está ativo
      _currentContext = route.navigator?.context;
      // Aguarda mais tempo para a rota anterior estar totalmente renderizada
      SchedulerBinding.instance.addPostFrameCallback((_) {
        Future.delayed(const Duration(milliseconds: 300), () {
          if (_currentContext != null) {
            _scheduleAddButton();
          }
        });
      });
    }
  }

  @override
  void didReplace({Route<dynamic>? newRoute, Route<dynamic>? oldRoute}) {
    super.didReplace(newRoute: newRoute, oldRoute: oldRoute);
    _currentContext = newRoute?.navigator?.context;
    _scheduleAddButton();
  }

  void _scheduleAddButton() {
    if (!kDebugMode || _currentContext == null) {
      _removeDebugButton();
      return;
    }

    // Agenda para adicionar após o frame (com delay adicional para garantir)
    SchedulerBinding.instance.addPostFrameCallback((_) {
      Future.delayed(const Duration(milliseconds: 200), () {
        // Verifica novamente o contexto antes de adicionar
        if (_currentContext != null) {
          _addDebugButton();
        }
      });
    });
  }

  void _addDebugButton() {
    if (!kDebugMode || _currentContext == null) return;

    try {
      final overlay = Overlay.maybeOf(_currentContext!);
      if (overlay == null) {
        // Tenta novamente no próximo frame
        SchedulerBinding.instance.addPostFrameCallback((_) {
          if (_currentContext != null) _addDebugButton();
        });
        return;
      }

      // Sempre remove o antigo antes de adicionar um novo
      _removeDebugButton();

      // Obtém o tamanho da tela para calcular limites
      final screenSize = MediaQuery.of(_currentContext!).size;
      const buttonSize = 40.0; // Tamanho do mini FAB
      const margin = 16.0; // Margem mínima das bordas

      _overlayEntry = OverlayEntry(
        builder: (context) {
          // Obtém o safe area para evitar Dynamic Island e notches
          final safeArea = MediaQuery.of(context).padding;
          // O bottom máximo é limitado para não ir muito alto (próximo ao Dynamic Island)
          // Quanto maior o bottom, mais alto na tela (mais longe do fundo)
          final maxTopSafeBottom = screenSize.height - safeArea.top - margin - buttonSize;
          
          // Calcula limites da tela
          final maxLeft = screenSize.width - buttonSize - margin;
          final maxBottom = screenSize.height - buttonSize - margin;
          final minBottom = margin; // Distância mínima do fundo da tela
          
          // Define posição inicial se não tiver sido arrastado ainda (canto superior direito, mas respeitando safe area)
          if (_buttonPosition == null) {
            // Usa o limite máximo seguro ou o canto superior direito (menor dos dois)
            _buttonPosition = Offset(maxLeft, maxTopSafeBottom.clamp(minBottom, maxBottom));
          }
          
          // Garante que a posição não saia dos limites
          // Limita o bottom para não ultrapassar a safe area no topo
          final effectiveMaxBottom = maxTopSafeBottom < maxBottom ? maxTopSafeBottom : maxBottom;
          double left = _buttonPosition!.dx.clamp(margin, maxLeft);
          double bottom = _buttonPosition!.dy.clamp(minBottom, effectiveMaxBottom);
          
          // Atualiza a posição se foi ajustada pelos limites
          if (left != _buttonPosition!.dx || bottom != _buttonPosition!.dy) {
            _buttonPosition = Offset(left, bottom);
          }

          return Positioned(
            left: left,
            bottom: bottom,
            child: GestureDetector(
              onPanUpdate: (details) {
                // Obtém o tamanho atualizado da tela e safe area
                final currentScreenSize = MediaQuery.of(context).size;
                final currentSafeArea = MediaQuery.of(context).padding;
                // Limita o bottom máximo para não ir muito alto (próximo ao Dynamic Island)
                final currentMaxTopSafeBottom = currentScreenSize.height - currentSafeArea.top - margin - buttonSize;
                
                final currentMaxLeft = currentScreenSize.width - buttonSize - margin;
                final currentMaxBottom = currentScreenSize.height - buttonSize - margin;
                final currentMinBottom = margin;
                
                // Calcula nova posição baseada no toque
                final newLeft = (details.globalPosition.dx - buttonSize / 2);
                final newBottom = currentScreenSize.height - details.globalPosition.dy - buttonSize / 2;
                
                // Aplica limites com margem e safe area
                final effectiveMaxBottom = currentMaxTopSafeBottom < currentMaxBottom ? currentMaxTopSafeBottom : currentMaxBottom;
                final clampedLeft = newLeft.clamp(margin, currentMaxLeft);
                final clampedBottom = newBottom.clamp(currentMinBottom, effectiveMaxBottom);
                
                _buttonPosition = Offset(clampedLeft, clampedBottom);
                _overlayEntry?.markNeedsBuild();
              },
              child: FloatingActionButton(
                mini: true,
                backgroundColor: Colors.orange.shade700,
                heroTag: "debug_fab_unique",
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
        },
      );

      overlay.insert(_overlayEntry!);
    } catch (e) {
      // Ignora erros e tenta novamente
      SchedulerBinding.instance.addPostFrameCallback((_) {
        if (_currentContext != null) _addDebugButton();
      });
    }
  }

  void _removeDebugButton() {
    try {
      _overlayEntry?.remove();
    } catch (e) {
      // Ignora erros ao remover
    }
    _overlayEntry = null;
  }

  void dispose() {
    _removeDebugButton();
  }
}
