import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:hive_flutter/hive_flutter.dart';
import '../services/auth_service.dart';
import '../l10n/strings.dart';
import 'home_screen.dart';
import '../data/models/user.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final _formKey = GlobalKey<FormState>();
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  bool _isLoading = false;
  bool _isLoginMode = true;

  @override
  void dispose() {
    _emailController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  String? _validateEmail(String? value) {
    if (value == null || value.isEmpty) {
      return Strings.campoObrigatorio;
    }
    if (!value.contains('@') || !value.contains('.')) {
      return Strings.emailInvalido;
    }
    return null;
  }

  String? _validatePassword(String? value) {
    if (value == null || value.isEmpty) {
      return Strings.campoObrigatorio;
    }
    if (value.length < 6) {
      return Strings.senhaMinima;
    }
    return null;
  }

  Future<void> _handleSubmit() async {
    if (!_formKey.currentState!.validate()) {
      return;
    }

    setState(() {
      _isLoading = true;
    });

    final email = _emailController.text.trim();
    final password = _passwordController.text;
    final authService = Provider.of<AuthService>(context, listen: false);

    bool success;
    String message;

    if (_isLoginMode) {
      success = await authService.login(email, password);
      message = success ? Strings.entrar : Strings.loginInvalido;
    } else {
      success = await authService.createAccount(email, password);
      message = success ? Strings.contaCriada : Strings.erroCriarConta;
    }

    setState(() {
      _isLoading = false;
    });

    if (!mounted) return;

    if (success) {
      // Navigation será feita automaticamente pelo Consumer no App widget
      // Mas podemos fazer manualmente para feedback imediato
      Navigator.of(context).pushReplacement(
        MaterialPageRoute(builder: (_) => const HomeScreen()),
      );
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(message)),
      );
    }
  }

  void _showSavedUsersPicker() {
    final usersBox = Hive.box<User>('users');
    final users = usersBox.values.toList();
    if (users.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Nenhum usuário salvo')),
      );
      return;
    }

    showModalBottomSheet(
      context: context,
      builder: (context) => SafeArea(
        child: ListView(
          padding: const EdgeInsets.all(8),
          shrinkWrap: true,
          children: users
              .map((u) => ListTile(
                    leading: const Icon(Icons.person_outline),
                    title: Text(u.email),
                    onTap: () {
                      Navigator.of(context).pop();
                      _emailController.text = u.email;
                    },
                  ))
              .toList(),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Center(
          child: SingleChildScrollView(
            padding: const EdgeInsets.all(24.0),
            child: Form(
              key: _formKey,
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  const Icon(
                    Icons.lock_outline,
                    size: 80,
                    color: Colors.blue,
                  ),
                  const SizedBox(height: 32),
                  Text(
                    _isLoginMode ? Strings.loginTitle : Strings.criarConta,
                    style: Theme.of(context).textTheme.headlineMedium,
                    textAlign: TextAlign.center,
                  ),
                  const SizedBox(height: 48),
                  TextFormField(
                    controller: _emailController,
                    decoration: InputDecoration(
                      labelText: Strings.email,
                      prefixIcon: const Icon(Icons.email),
                      border: const OutlineInputBorder(),
                      suffixIcon: IconButton(
                        icon: const Icon(Icons.expand_more),
                        tooltip: 'Selecionar usuário salvo',
                        onPressed: _showSavedUsersPicker,
                      ),
                    ),
                    keyboardType: TextInputType.emailAddress,
                    autocorrect: false,
                    validator: _validateEmail,
                  ),
                  const SizedBox(height: 16),
                  TextFormField(
                    controller: _passwordController,
                    decoration: const InputDecoration(
                      labelText: Strings.senha,
                      prefixIcon: Icon(Icons.lock),
                      border: OutlineInputBorder(),
                    ),
                    obscureText: true,
                    validator: _validatePassword,
                  ),
                  const SizedBox(height: 32),
                  ElevatedButton(
                    onPressed: _isLoading ? null : _handleSubmit,
                    style: ElevatedButton.styleFrom(
                      padding: const EdgeInsets.symmetric(vertical: 16),
                    ),
                    child: _isLoading
                        ? const CircularProgressIndicator()
                        : Text(
                            _isLoginMode ? Strings.entrar : Strings.criarConta,
                            style: const TextStyle(fontSize: 16),
                          ),
                  ),
                  const SizedBox(height: 16),
                  TextButton(
                    onPressed: _isLoading
                        ? null
                        : () {
                            setState(() {
                              _isLoginMode = !_isLoginMode;
                            });
                          },
                    child: Text(
                      _isLoginMode
                          ? 'Não tem conta? ${Strings.criarConta}'
                          : 'Já tem conta? ${Strings.entrar}',
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}

