import 'dart:io';
import 'package:path_provider/path_provider.dart';
import 'package:path/path.dart' as path;
import 'image_storage_service.dart';

/// Service to migrate and validate image paths in form entries
class ImageMigrationService {
  static final ImageMigrationService _instance = ImageMigrationService._internal();
  factory ImageMigrationService() => _instance;
  ImageMigrationService._internal();

  final ImageStorageService _imageStorage = ImageStorageService();

  /// Validate and migrate image paths in form data
  /// Returns a new map with validated/migrated paths
  Future<Map<String, dynamic>> migrateFormData(Map<String, dynamic> formData) async {
    final migratedData = Map<String, dynamic>.from(formData);
    
    for (final entry in migratedData.entries) {
      final key = entry.key;
      final value = entry.value;
      
      // Check if this is an image value (string or list of strings)
      if (value is String && _isImagePath(value)) {
        final migratedPath = await _migrateImagePath(value);
        if (migratedPath != null) {
          migratedData[key] = migratedPath;
        }
      } else if (value is List) {
        final migratedList = <dynamic>[];
        bool hasChanges = false;
        
        for (final item in value) {
          if (item is String && _isImagePath(item)) {
            final migratedPath = await _migrateImagePath(item);
            migratedList.add(migratedPath ?? item);
            if (migratedPath != null && migratedPath != item) {
              hasChanges = true;
            }
          } else {
            migratedList.add(item);
          }
        }
        
        if (hasChanges) {
          migratedData[key] = migratedList;
        }
      }
    }
    
    return migratedData;
  }

  /// Check if a path looks like an image path
  bool _isImagePath(String path) {
    if (path.isEmpty) return false;
    if (path.startsWith('http://') || path.startsWith('https://')) return false;
    
    final extension = path.split('.').last.toLowerCase();
    return ['jpg', 'jpeg', 'png', 'gif', 'webp', 'bmp'].contains(extension) ||
           path.contains('/image') ||
           path.contains('/photo') ||
           path.contains('/picture');
  }

  /// Migrate a single image path if needed
  /// Returns the migrated path or null if migration isn't needed/failed
  Future<String?> _migrateImagePath(String imagePath) async {
    try {
      // Check if it's already a persistent path
      final imagesDir = await _imageStorage.getImagesDirectory();
      if (imagePath.startsWith(imagesDir.path)) {
        // Already persistent, just verify it exists
        final file = File(imagePath);
        if (await file.exists()) {
          return null; // Already correct
        }
        // File doesn't exist, can't recover
        print('[ImageMigration] Persistent path doesn't exist: $imagePath');
        return null;
      }

      // Check if temporary file exists
      final tempFile = File(imagePath);
      if (await tempFile.exists()) {
        // File exists, migrate it to persistent storage
        print('[ImageMigration] Migrating temporary image: $imagePath');
        try {
          final persistentPath = await _imageStorage.saveImage(imagePath);
          print('[ImageMigration] Migrated to: $persistentPath');
          
          // Delete temporary file (optional, but saves space)
          try {
            await tempFile.delete();
          } catch (e) {
            // Ignore deletion errors
          }
          
          return persistentPath;
        } catch (e) {
          print('[ImageMigration] Failed to migrate $imagePath: $e');
          return null;
        }
      } else {
        // Temporary file doesn't exist - can't migrate
        print('[ImageMigration] Temporary file doesn't exist: $imagePath');
        return null;
      }
    } catch (e) {
      print('[ImageMigration] Error migrating image path $imagePath: $e');
      return null;
    }
  }
}

