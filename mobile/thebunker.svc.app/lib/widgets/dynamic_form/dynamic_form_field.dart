import 'dart:io';
import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart' show kIsWeb;
import 'package:intl/intl.dart';
import 'package:image_picker/image_picker.dart';
import '../../data/models/form_schema.dart';
import '../../l10n/strings.dart';
import '../../services/image_storage_service.dart';

class DynamicFormField extends StatelessWidget {
  final FieldDefinition field;
  final dynamic value;
  final String? error;
  final ValueChanged<dynamic> onChanged;
  final VoidCallback? onFormButtonTap;

  const DynamicFormField({
    super.key,
    required this.field,
    required this.value,
    this.error,
    required this.onChanged,
    this.onFormButtonTap,
  });

  @override
  Widget build(BuildContext context) {
    switch (field.tipo) {
      case 'texto':
        return _buildTextField(context);
      case 'multilinha':
        return _buildMultilineTextField(context);
      case 'numero':
        return _buildNumberField(context);
      case 'data':
        return _buildDateField(context);
      case 'hora':
        return _buildTimeField(context);
      case 'selecao':
        return _buildDropdownField(context);
      case 'checkbox':
        return _buildCheckboxField(context);
      case 'formulario':
        return _buildFormButtonField(context);
      case 'imagem':
      case 'galeria':
        return _buildImageField(context);
      default:
        return Text('Tipo de campo não suportado: ${field.tipo}');
    }
  }

  Widget _buildTextField(BuildContext context) {
    return TextFormField(
      initialValue: value?.toString(),
      decoration: InputDecoration(
        labelText: field.rotulo,
        errorText: error,
        border: const OutlineInputBorder(),
      ),
      onChanged: (val) => onChanged(val),
      maxLength: field.maxLength,
    );
  }

  Widget _buildMultilineTextField(BuildContext context) {
    return TextFormField(
      initialValue: value?.toString(),
      decoration: InputDecoration(
        labelText: field.rotulo,
        errorText: error,
        border: const OutlineInputBorder(),
      ),
      maxLines: 4,
      maxLength: field.maxLength,
      onChanged: (val) => onChanged(val),
    );
  }

  Widget _buildNumberField(BuildContext context) {
    return TextFormField(
      initialValue: value?.toString(),
      decoration: InputDecoration(
        labelText: field.rotulo,
        errorText: error,
        border: const OutlineInputBorder(),
      ),
      keyboardType: TextInputType.number,
      onChanged: (val) {
        final numValue = num.tryParse(val);
        onChanged(numValue);
      },
    );
  }

  Widget _buildDateField(BuildContext context) {
    return InkWell(
      onTap: () async {
        final picked = await showDatePicker(
          context: context,
          initialDate: value as DateTime? ?? DateTime.now(),
          firstDate: DateTime(1900),
          lastDate: DateTime(2100),
        );
        if (picked != null) {
          onChanged(picked);
        }
      },
      child: InputDecorator(
        decoration: InputDecoration(
          labelText: field.rotulo,
          errorText: error,
          border: const OutlineInputBorder(),
          suffixIcon: const Icon(Icons.calendar_today),
        ),
        child: Text(
          value != null
              ? DateFormat('dd/MM/yyyy').format(value as DateTime)
              : Strings.selecionarData,
        ),
      ),
    );
  }

  Widget _buildTimeField(BuildContext context) {
    return InkWell(
      onTap: () async {
        final picked = await showTimePicker(
          context: context,
          initialTime: value != null
              ? TimeOfDay.fromDateTime(value as DateTime)
              : TimeOfDay.now(),
        );
        if (picked != null) {
          final now = DateTime.now();
          final dateTime = DateTime(
            now.year,
            now.month,
            now.day,
            picked.hour,
            picked.minute,
          );
          onChanged(dateTime);
        }
      },
      child: InputDecorator(
        decoration: InputDecoration(
          labelText: field.rotulo,
          errorText: error,
          border: const OutlineInputBorder(),
          suffixIcon: const Icon(Icons.access_time),
        ),
        child: Text(
          value != null
              ? DateFormat('HH:mm').format(value as DateTime)
              : Strings.selecionarHora,
        ),
      ),
    );
  }

  Widget _buildDropdownField(BuildContext context) {
    if (field.opcoes == null || field.opcoes!.isEmpty) {
      return const Text('Campo de seleção sem opções definidas');
    }

    String? selectedValue = value?.toString();

    return DropdownButtonFormField<String>(
      value: selectedValue,
      decoration: InputDecoration(
        labelText: field.rotulo,
        errorText: error,
        border: const OutlineInputBorder(),
      ),
      items: field.opcoes!.map((option) {
        return DropdownMenuItem<String>(
          value: option,
          child: Text(option),
        );
      }).toList(),
      onChanged: (val) => onChanged(val),
    );
  }

  Widget _buildCheckboxField(BuildContext context) {
    return CheckboxListTile(
      title: Text(field.rotulo),
      subtitle: error != null
          ? Text(
              error!,
              style: const TextStyle(color: Colors.red),
            )
          : null,
      value: value as bool? ?? false,
      onChanged: (val) => onChanged(val ?? false),
    );
  }

  Widget _buildFormButtonField(BuildContext context) {
    if (field.formKey == null) {
      return Text(
        'Campo de formulário sem formKey definido',
        style: TextStyle(color: Colors.red[700]),
      );
    }

    return ElevatedButton.icon(
      onPressed: onFormButtonTap,
      icon: const Icon(Icons.arrow_forward),
      label: Text(field.rotulo),
      style: ElevatedButton.styleFrom(
        minimumSize: const Size(double.infinity, 48),
        padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
      ),
    );
  }

  Widget _buildImageField(BuildContext context) {
    final List<String> imagePaths = value is List
        ? List<String>.from(value)
        : value != null
            ? [value.toString()]
            : [];
    
    final maxImages = field.max ?? 1;
    final canAddMore = imagePaths.length < maxImages.toInt();

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          field.rotulo,
          style: Theme.of(context).textTheme.labelLarge,
        ),
        if (error != null) ...[
          const SizedBox(height: 4),
          Text(
            error!,
            style: TextStyle(color: Theme.of(context).colorScheme.error),
          ),
        ],
        const SizedBox(height: 8),
        // Display selected images
        if (imagePaths.isNotEmpty) ...[
          Wrap(
            spacing: 8,
            runSpacing: 8,
            children: imagePaths.asMap().entries.map((entry) {
              final index = entry.key;
              final imagePath = entry.value;
              return Stack(
                children: [
                  Container(
                    width: 100,
                    height: 100,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(8),
                      border: Border.all(color: Colors.grey.shade300),
                    ),
                    child: ClipRRect(
                      borderRadius: BorderRadius.circular(8),
                      child: imagePath.startsWith('http://') || imagePath.startsWith('https://')
                          ? Image.network(
                              imagePath,
                              fit: BoxFit.cover,
                              errorBuilder: (context, error, stackTrace) {
                                return const Icon(Icons.broken_image);
                              },
                            )
                          : FutureBuilder<String>(
                              future: ImageStorageService().resolveImagePath(imagePath),
                              builder: (context, snapshot) {
                                if (!snapshot.hasData) {
                                  return Container(
                                    color: Colors.grey.shade200,
                                    child: const Center(
                                      child: CircularProgressIndicator(strokeWidth: 2),
                                    ),
                                  );
                                }
                                final resolvedPath = snapshot.data!;
                                return _buildFormImagePreview(resolvedPath);
                              },
                            ),
                    ),
                  ),
                  Positioned(
                    top: 4,
                    right: 4,
                    child: GestureDetector(
                      onTap: () {
                        final newPaths = List<String>.from(imagePaths);
                        newPaths.removeAt(index);
                        onChanged(newPaths.isEmpty ? null : (maxImages.toInt() == 1 ? newPaths.first : newPaths));
                      },
                      child: Container(
                        decoration: const BoxDecoration(
                          color: Colors.red,
                          shape: BoxShape.circle,
                        ),
                        padding: const EdgeInsets.all(4),
                        child: const Icon(
                          Icons.close,
                          color: Colors.white,
                          size: 16,
                        ),
                      ),
                    ),
                  ),
                ],
              );
            }).toList(),
          ),
          const SizedBox(height: 12),
        ],
        // Add image button
        if (canAddMore)
          OutlinedButton.icon(
            onPressed: () => _showImageSourceDialog(context),
            icon: const Icon(Icons.add_photo_alternate),
            label: Text(maxImages.toInt() == 1 ? 'Adicionar Foto' : 'Adicionar Foto (${imagePaths.length}/${maxImages.toInt()})'),
            style: OutlinedButton.styleFrom(
              minimumSize: const Size(double.infinity, 48),
            ),
          ),
      ],
    );
  }

  Future<void> _showImageSourceDialog(BuildContext context) async {
    final maxImages = (field.max ?? 1).toInt();
    final currentImages = value is List
        ? List<String>.from(value)
        : value != null
            ? [value.toString()]
            : [];
    final remaining = maxImages - currentImages.length;

    final source = await showModalBottomSheet<ImageSource>(
      context: context,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
      ),
      builder: (context) => SafeArea(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            ListTile(
              leading: const Icon(Icons.photo_library),
              title: Text(maxImages == 1 
                  ? 'Escolher da Galeria'
                  : 'Escolher da Galeria${remaining > 1 ? " ($remaining restantes)" : ""}'),
              onTap: () => Navigator.pop(context, ImageSource.gallery),
            ),
            // Show camera option only on mobile platforms (not web)
            if (!kIsWeb) ...[
              ListTile(
                leading: const Icon(Icons.camera_alt),
                title: Text(maxImages == 1
                    ? 'Tirar Foto'
                    : 'Tirar Foto${remaining > 1 ? " ($remaining restantes)" : ""}'),
                onTap: () => Navigator.pop(context, ImageSource.camera),
              ),
            ],
            const Divider(height: 1),
            ListTile(
              leading: const Icon(Icons.cancel),
              title: const Text('Cancelar'),
              onTap: () => Navigator.pop(context),
            ),
          ],
        ),
      ),
    );

    if (source != null) {
      await _pickImage(context, source, maxImages: maxImages == 1 ? 1 : remaining);
    }
  }

  Future<void> _pickImage(BuildContext context, ImageSource source, {int maxImages = 1}) async {
    try {
      final ImagePicker picker = ImagePicker();
      final List<XFile> pickedFiles;

      // Camera can only take one photo at a time, so always use pickImage
      // Gallery can pick multiple if maxImages > 1
      if (source == ImageSource.camera) {
        final pickedFile = await picker.pickImage(source: source);
        pickedFiles = pickedFile != null ? [pickedFile] : [];
      } else if (maxImages > 1) {
        // For gallery with multiple images allowed
        pickedFiles = await picker.pickMultiImage();
      } else {
        // For gallery with single image
        final pickedFile = await picker.pickImage(source: source);
        pickedFiles = pickedFile != null ? [pickedFile] : [];
      }

      if (pickedFiles.isEmpty) return;

      // Limit to remaining slots
      final filesToAdd = pickedFiles.take(maxImages).toList();

      // Show saving indicator
      if (context.mounted) {
        showDialog(
          context: context,
          barrierDismissible: false,
          builder: (context) => const Center(
            child: CircularProgressIndicator(),
          ),
        );
      }

      // Save images to persistent storage
      final imageStorage = ImageStorageService();
      final List<String> savedPaths = [];

      try {
        for (final file in filesToAdd) {
          try {
            print('[FormField] Saving image from temporary path: ${file.path}');
            final persistentPath = await imageStorage.saveImage(file.path);
            print('[FormField] Image saved to persistent path: $persistentPath');
            savedPaths.add(persistentPath);
            
            // Verify the file exists
            final savedFile = File(persistentPath);
            if (!await savedFile.exists()) {
              print('[FormField] WARNING: Persistent file does not exist after saving: $persistentPath');
            }
          } catch (e) {
            print('[FormField] Error saving individual image ${file.path}: $e');
            // Continue with other images
            if (context.mounted) {
              ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(content: Text('Erro ao salvar uma das imagens: $e')),
              );
            }
          }
        }
        
        if (savedPaths.isEmpty) {
          throw Exception('Nenhuma imagem foi salva com sucesso');
        }
      } catch (e) {
        if (context.mounted) {
          Navigator.of(context).pop(); // Close loading dialog
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text('Erro ao salvar imagens: $e')),
          );
        }
        return;
      }

      if (context.mounted) {
        Navigator.of(context).pop(); // Close loading dialog
      }

      // Get current images
      final currentImages = value is List
          ? List<String>.from(value)
          : value != null
              ? [value.toString()]
              : [];

      // Verify all saved paths are actually in persistent storage
      final imagesDir = await imageStorage.getImagesDirectory();
      
      for (final savedPath in savedPaths) {
        if (!savedPath.startsWith(imagesDir.path)) {
          print('[FormField] WARNING: Saved path is not in persistent directory!');
          print('[FormField] Expected to start with: ${imagesDir.path}');
          print('[FormField] Actual path: $savedPath');
        } else {
          print('[FormField] Verified persistent path: $savedPath');
          // Verify file actually exists
          final file = File(savedPath);
          if (!await file.exists()) {
            print('[FormField] ERROR: Persistent path exists but file does not: $savedPath');
          } else {
            final size = await file.length();
            print('[FormField] File verified: $savedPath (${size} bytes)');
          }
        }
      }

      // Add new persistent image paths
      final newPaths = [...currentImages, ...savedPaths];
      
      print('[FormField] Updating form data with paths:');
      for (final path in newPaths) {
        print('[FormField]   - $path');
      }

      // Update value based on max images
      if (field.max != null && (field.max as num).toInt() == 1) {
        final finalPath = newPaths.first;
        print('[FormField] Setting single image path: $finalPath');
        onChanged(finalPath);
      } else {
        print('[FormField] Setting multiple image paths: ${newPaths.length} images');
        onChanged(newPaths);
      }
    } catch (e) {
      if (context.mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Erro ao selecionar imagem: $e')),
        );
      }
    }
  }

  Widget _buildFormImagePreview(String imagePath) {
    final file = File(imagePath);
    if (!file.existsSync()) {
      return Container(
        color: Colors.grey.shade200,
        child: const Icon(Icons.broken_image, size: 40, color: Colors.grey),
      );
    }
    
    return Image.file(
      file,
      fit: BoxFit.cover,
      errorBuilder: (context, error, stackTrace) {
        return Container(
          color: Colors.grey.shade200,
          child: const Icon(Icons.broken_image, size: 40, color: Colors.grey),
        );
      },
    );
  }
}

