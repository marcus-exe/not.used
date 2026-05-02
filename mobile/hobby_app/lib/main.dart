import 'dart:convert';
import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:hobby_app/app_router.dart';
import 'package:hobby_app/data/models/hobby_model.dart';
import 'package:path_provider/path_provider.dart';

Future<Uint8List> _getHiveEncryptionKey() async {
  const secureStorage = FlutterSecureStorage();
  const keyName = 'hive_key';
  String? key = await secureStorage.read(key: keyName);

  if (key == null) {
    final newKey = Hive.generateSecureKey();
    key = base64UrlEncode(newKey);
    await secureStorage.write(key: keyName, value: key);
  }
  return base64Url.decode(key);
}

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  final appDocumentDir = await getApplicationDocumentsDirectory();
  Hive.init(appDocumentDir.path);

  final encryptionKey = await _getHiveEncryptionKey();

  Hive.registerAdapter(HobbyAdapter());
  
  await Hive.openBox<Hobby>('hobbies', encryptionCipher: HiveAesCipher(encryptionKey));
  
  runApp(const ProviderScope(child: MyApp()));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp.router(
      title: 'Hobby App',
      theme: ThemeData(
        primarySwatch: Colors.teal,
        visualDensity: VisualDensity.adaptivePlatformDensity,
        fontFamily: 'Inter',
        useMaterial3: true,
      ),
      routerConfig: appRouter,
    );
  }
}