import 'package:flutter/material.dart';
import 'dart:async';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:provider/provider.dart';
import 'app.dart';
import 'data/models/user.dart';
import 'data/models/form_entry.dart';
import 'data/models/geo_point.dart';
import 'services/auth_service.dart';
import 'services/form_loader.dart';
import 'services/encryption_service.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  
  // Inicializar Hive
  await Hive.initFlutter();
  
  // Registrar adapters
  Hive.registerAdapter(UserAdapter());
  Hive.registerAdapter(FormEntryAdapter());
  Hive.registerAdapter(GeoPointAdapter());
  
  // Obter cipher de criptografia
  final encryptionCipher = await EncryptionService.getCipher();
  
  // Verificar se boxes já existem antes de tentar abrir
  final usersBoxExists = await Hive.boxExists('users');
  final entriesBoxExists = await Hive.boxExists('entries');
  
  // Abrir box de usuários
  if (usersBoxExists) {
    // Box existe, tenta abrir com criptografia
    try {
      await Hive.openBox<User>('users', encryptionCipher: encryptionCipher);
    } catch (e) {
      // Se falhar, pode ser box antigo sem criptografia
      // Deleta e cria novo criptografado (apenas na primeira vez após migração)
      try {
        if (Hive.isBoxOpen('users')) {
          await Hive.box('users').close();
        }
        await Hive.deleteBoxFromDisk('users');
      } catch (_) {}
      await Hive.openBox<User>('users', encryptionCipher: encryptionCipher);
    }
  } else {
    // Box não existe, cria novo com criptografia
    await Hive.openBox<User>('users', encryptionCipher: encryptionCipher);
  }
  
  // Abrir box de entradas
  if (entriesBoxExists) {
    // Box existe, tenta abrir com criptografia
    try {
      await Hive.openBox<FormEntry>('entries', encryptionCipher: encryptionCipher);
    } catch (e) {
      // Se falhar, pode ser box antigo sem criptografia
      // Deleta e cria novo criptografado (apenas na primeira vez após migração)
      try {
        if (Hive.isBoxOpen('entries')) {
          await Hive.box('entries').close();
        }
        await Hive.deleteBoxFromDisk('entries');
      } catch (_) {}
      await Hive.openBox<FormEntry>('entries', encryptionCipher: encryptionCipher);
    }
  } else {
    // Box não existe, cria novo com criptografia
    await Hive.openBox<FormEntry>('entries', encryptionCipher: encryptionCipher);
  }
  
  // Inicializar serviços
  final authService = AuthService();
  final formLoader = FormLoader();
  // Carrega formulários em background para permitir mostrar tela de carregamento no app
  unawaited(formLoader.loadForms());
  
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => authService),
        ChangeNotifierProvider.value(value: formLoader),
      ],
      child: const App(),
    ),
  );
}

