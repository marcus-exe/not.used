import 'package:flutter/foundation.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'dart:convert';
import 'package:crypto/crypto.dart';
import '../data/models/user.dart';
import '../l10n/strings.dart';

class AuthService extends ChangeNotifier {
  final Box<User> _usersBox = Hive.box<User>('users');
  User? _currentUser;
  bool _isAuthenticated = false;

  bool get isAuthenticated => _isAuthenticated;
  User? get currentUser => _currentUser;

  String _hashPassword(String password) {
    final bytes = utf8.encode(password);
    final digest = sha256.convert(bytes);
    return digest.toString();
  }

  Future<bool> login(String email, String password) async {
    try {
      final user = _usersBox.values.firstWhere(
        (u) => u.email == email,
        orElse: () => throw Exception(Strings.loginInvalido),
      );

      final passwordHash = _hashPassword(password);
      if (user.passwordHash != passwordHash) {
        throw Exception(Strings.loginInvalido);
      }

      _currentUser = user;
      _isAuthenticated = true;
      notifyListeners();
      return true;
    } catch (e) {
      return false;
    }
  }

  Future<bool> createAccount(String email, String password) async {
    try {
      if (_usersBox.values.any((u) => u.email == email)) {
        throw Exception('E-mail já cadastrado');
      }

      final user = User(
        id: DateTime.now().millisecondsSinceEpoch.toString(),
        email: email,
        passwordHash: _hashPassword(password),
      );

      await _usersBox.put(user.id, user);
      
      _currentUser = user;
      _isAuthenticated = true;
      notifyListeners();
      return true;
    } catch (e) {
      return false;
    }
  }

  void logout() {
    _currentUser = null;
    _isAuthenticated = false;
    notifyListeners();
  }
}

