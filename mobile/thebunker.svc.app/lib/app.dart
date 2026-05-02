import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'services/form_loader.dart';
import 'screens/login_screen.dart';
import 'screens/home_screen.dart';
import 'services/auth_service.dart';
import 'widgets/debug_route_observer.dart';

class App extends StatefulWidget {
  const App({super.key});

  @override
  State<App> createState() => _AppState();
}

class _AppState extends State<App> {
  final _debugObserver = DebugRouteObserver();
  final GlobalKey<NavigatorState> _navigatorKey = GlobalKey<NavigatorState>();

  @override
  void dispose() {
    _debugObserver.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      navigatorKey: _navigatorKey,
      title: 'svs form',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.blue),
        useMaterial3: true,
      ),
      navigatorObservers: [_debugObserver],
      home: Consumer2<AuthService, FormLoader>(
        builder: (context, authService, formLoader, _) {
          // Trigger para adicionar o botão quando a home é carregada
          WidgetsBinding.instance.addPostFrameCallback((_) {
            _debugObserver.setContext(context);
          });

          if (formLoader.isLoading) {
            return Scaffold(
              body: Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: const [
                    CircularProgressIndicator(),
                    SizedBox(height: 16),
                    Text('Carregando formulários...'),
                  ],
                ),
              ),
            );
          }

          if (authService.isAuthenticated) {
            return const HomeScreen();
          }
          return const LoginScreen();
        },
      ),
    );
  }
}

