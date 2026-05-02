import 'dart:convert';
import 'dart:typed_data';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:hive_flutter/hive_flutter.dart';

class EncryptionService {
  static const String _encryptionKeyName = 'hive_encryption_key';
  static const FlutterSecureStorage _storage = FlutterSecureStorage(
    aOptions: AndroidOptions(
      encryptedSharedPreferences: true,
    ),
  );

  /// Gera ou recupera a chave de criptografia de 32 bytes
  static Future<Uint8List> getEncryptionKey() async {
    try {
      // Tenta recuperar a chave existente
      final existingKey = await _storage.read(key: _encryptionKeyName);
      
      if (existingKey != null && existingKey.isNotEmpty) {
        // Converte a string base64 de volta para bytes
        return base64Decode(existingKey);
      }
      
      // Gera uma nova chave se não existir
      final keyList = Hive.generateSecureKey();
      final key = Uint8List.fromList(keyList);
      
      // Salva a chave de forma segura
      await _storage.write(
        key: _encryptionKeyName,
        value: base64Encode(key),
      );
      
      return key;
    } catch (e) {
      // Em caso de erro, gera uma nova chave (isso causará perda de dados criptografados)
      // Em produção, você pode querer tratar isso de forma diferente
      final keyList = Hive.generateSecureKey();
      final key = Uint8List.fromList(keyList);
      await _storage.write(
        key: _encryptionKeyName,
        value: base64Encode(key),
      );
      return key;
    }
  }

  /// Cria o cipher AES para o Hive
  static Future<HiveAesCipher> getCipher() async {
    final key = await getEncryptionKey();
    return HiveAesCipher(key);
  }

  /// Limpa a chave de criptografia (útil para logout ou reset)
  static Future<void> clearEncryptionKey() async {
    await _storage.delete(key: _encryptionKeyName);
  }
}

