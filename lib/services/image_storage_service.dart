import 'dart:io';
import 'package:path_provider/path_provider.dart';
import 'package:path/path.dart' as path;

class ImageStorageService {
  static final ImageStorageService _instance = ImageStorageService._internal();
  factory ImageStorageService() => _instance;
  ImageStorageService._internal();

  Directory? _imagesDirectory;

  /// Get or create the images directory
  Future<Directory> getImagesDirectory() async {
    if (_imagesDirectory != null && await _imagesDirectory!.exists()) {
      return _imagesDirectory!;
    }

    final appDocumentsDir = await getApplicationDocumentsDirectory();
    final imagesDir = Directory(path.join(appDocumentsDir.path, 'form_images'));
    
    if (!await imagesDir.exists()) {
      await imagesDir.create(recursive: true);
    }

    _imagesDirectory = imagesDir;
    print('[ImageStorage] Images directory initialized: ${imagesDir.path}');
    return imagesDir;
  }
  
  /// Resolve a relative or absolute image path to an absolute path
  Future<String> resolveImagePath(String imagePath) async {
    try {
      // If it's already a relative path (starts with form_images/)
      if (imagePath.startsWith('form_images/') || !path.isAbsolute(imagePath)) {
        final imagesDir = await getImagesDirectory();
        final fileName = path.basename(imagePath);
        return path.join(imagesDir.path, fileName);
      }
      
      // If it's an absolute path, check if it's in our directory
      final imagesDir = await getImagesDirectory();
      if (imagePath.startsWith(imagesDir.path)) {
        return imagePath;
      }
      
      // Try to extract filename from absolute path and resolve
      final fileName = path.basename(imagePath);
      return path.join(imagesDir.path, fileName);
    } catch (e) {
      print('[ImageStorage] Error resolving path $imagePath: $e');
      return imagePath; // Return original if resolution fails
    }
  }

  /// Check if a path is in the persistent images directory (or is a relative path)
  Future<bool> isPersistentPath(String imagePath) async {
    try {
      // Relative paths are considered persistent
      if (imagePath.startsWith('form_images/') || !path.isAbsolute(imagePath)) {
        return true;
      }
      
      final imagesDir = await getImagesDirectory();
      // Use path normalization to compare
      final normalizedImagePath = path.normalize(imagePath);
      final normalizedImagesDir = path.normalize(imagesDir.path);
      return normalizedImagePath.startsWith(normalizedImagesDir);
    } catch (e) {
      return false;
    }
  }

  /// Copy an image file to persistent storage and return the new path
  /// ALWAYS copies to ensure file exists in persistent storage
  Future<String> saveImage(String sourcePath) async {
    try {
      // Get images directory first
      final imagesDir = await getImagesDirectory();
      print('[ImageStorage] Images directory: ${imagesDir.path}');
      
      final sourceFile = File(sourcePath);
      
      // Check if source file exists
      final sourceExists = await sourceFile.exists();
      print('[ImageStorage] Source file exists: $sourceExists, path: $sourcePath');
      
      if (!sourceExists) {
        // Check if it's already in persistent storage and exists there
        if (sourcePath.startsWith(imagesDir.path)) {
          final persistentFile = File(sourcePath);
          if (await persistentFile.exists()) {
            print('[ImageStorage] File already exists in persistent storage: $sourcePath');
            return sourcePath;
          }
        }
        throw Exception('Source image file does not exist: $sourcePath');
      }

      // Check if source is already in persistent directory or is a relative path
      if (sourcePath.startsWith('form_images/') || !path.isAbsolute(sourcePath)) {
        // It's a relative path, return as-is
        print('[ImageStorage] Path is already relative: $sourcePath');
        return sourcePath;
      }
      
      if (sourcePath.startsWith(imagesDir.path)) {
        // Already in persistent storage and exists, convert to relative path
        final fileName = path.basename(sourcePath);
        final relativePath = path.join('form_images', fileName);
        print('[ImageStorage] Converting absolute to relative: $relativePath');
        return relativePath;
      }

      // Copy to persistent storage with a new unique filename
      final fileName = path.basename(sourcePath);
      final timestamp = DateTime.now().millisecondsSinceEpoch;
      final random = (timestamp % 10000).toString().padLeft(4, '0');
      final extension = path.extension(fileName).isEmpty ? '.jpg' : path.extension(fileName);
      final baseName = path.basenameWithoutExtension(fileName);
      final safeBaseName = baseName.replaceAll(RegExp(r'[^a-zA-Z0-9]'), '_');
      final newFileName = '${timestamp}_${random}_$safeBaseName$extension';
      
      // Store only the relative path (filename) to avoid UUID issues on iOS
      // We'll resolve the full path when needed
      final relativePath = path.join('form_images', newFileName);
      
      final destinationPath = path.join(imagesDir.path, newFileName);

      print('[ImageStorage] Copying from $sourcePath to $destinationPath');
      
      // Copy the file
      await sourceFile.copy(destinationPath);
      
      // Verify the copy succeeded
      final destFile = File(destinationPath);
      if (!await destFile.exists()) {
        throw Exception('File copy failed - destination file does not exist: $destinationPath');
      }
      
      final sourceSize = await sourceFile.length();
      final destSize = await destFile.length();
      if (sourceSize != destSize) {
        throw Exception('File copy size mismatch: source=$sourceSize, dest=$destSize');
      }

      print('[ImageStorage] Successfully saved image: $destinationPath (${destSize} bytes)');
      print('[ImageStorage] Returning relative path: $relativePath');
      
      // Return relative path instead of absolute path to avoid UUID issues
      return relativePath;
    } catch (e, stackTrace) {
      print('[ImageStorage] Error saving image from $sourcePath: $e');
      print('[ImageStorage] Stack trace: $stackTrace');
      rethrow;
    }
  }

  /// Check if an image file exists at the given path
  Future<bool> imageExists(String imagePath) async {
    try {
      final file = File(imagePath);
      return await file.exists();
    } catch (e) {
      return false;
    }
  }

  /// Get the file for a stored image path
  File? getImageFile(String imagePath) {
    try {
      final file = File(imagePath);
      if (file.existsSync()) {
        return file;
      }
      return null;
    } catch (e) {
      return null;
    }
  }

  /// Delete an image file (e.g., when removing from form)
  Future<bool> deleteImage(String imagePath) async {
    try {
      final file = File(imagePath);
      if (await file.exists()) {
        await file.delete();
        return true;
      }
      return false;
    } catch (e) {
      return false;
    }
  }

  /// Clean up old images that are no longer referenced
  /// This can be called periodically to free up space
  Future<void> cleanupUnusedImages(Set<String> usedImagePaths) async {
    try {
      final imagesDir = await getImagesDirectory();
      if (!await imagesDir.exists()) {
        return;
      }

      final files = imagesDir.listSync();
      for (final file in files) {
        if (file is File) {
          final filePath = file.path;
          if (!usedImagePaths.contains(filePath)) {
            try {
              await file.delete();
            } catch (e) {
              // Ignore errors when deleting files
            }
          }
        }
      }
    } catch (e) {
      // Ignore cleanup errors
    }
  }
}

