import 'dart:io';
import 'package:flutter/material.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:provider/provider.dart';
import '../data/models/form_schema.dart';
import '../data/models/form_entry.dart';
import '../services/location_service.dart';
import '../widgets/dynamic_form/dynamic_form_field.dart';
import '../l10n/strings.dart';
import '../services/auth_service.dart';
import '../services/form_loader.dart';
import '../services/image_storage_service.dart';

class FormScreen extends StatefulWidget {
  final FormDefinition formDefinition;
  final Map<String, dynamic>? initialData;
  final String? parentFormId;
  final bool returnToHomeOnComplete;
  // Accumulated form data from parent forms (when using salvarJunto)
  final Map<String, Map<String, dynamic>>? accumulatedFormsData;

  const FormScreen({
    super.key,
    required this.formDefinition,
    this.initialData,
    this.parentFormId,
    this.returnToHomeOnComplete = false,
    this.accumulatedFormsData,
  });

  @override
  State<FormScreen> createState() => _FormScreenState();
}

class _FormScreenState extends State<FormScreen> {
  final Map<String, dynamic> _formData = {};
  final Map<String, String?> _errors = {};
  final LocationService _locationService = LocationService();
  bool _isSaving = false;
  // Accumulated form data from parent forms (when using salvarJunto)
  late Map<String, Map<String, dynamic>> _accumulatedFormsData;

  @override
  void initState() {
    super.initState();
    // Initialize accumulated forms data
    _accumulatedFormsData = Map<String, Map<String, dynamic>>.from(
      widget.accumulatedFormsData ?? {},
    );
    
    // Pre-populate form data from initialData
    if (widget.initialData != null) {
      _formData.addAll(widget.initialData!);
    }
  }

  void _updateFieldValue(String fieldId, dynamic value) {
    setState(() {
      _formData[fieldId] = value;
      _errors.remove(fieldId);
    });
  }

  Map<String, dynamic> _mapFormData(Map<String, dynamic> sourceData, Map<String, dynamic>? mappingRules) {
    if (mappingRules == null || mappingRules.isEmpty) {
      return {};
    }

    final mappedData = <String, dynamic>{};
    
    mappingRules.forEach((targetKey, sourceValue) {
      if (sourceValue is String && sourceData.containsKey(sourceValue)) {
        // Field-to-field mapping: sourceValue is the source field name, targetKey is the target field
        mappedData[targetKey] = sourceData[sourceValue];
      } else {
        // Literal value: sourceValue is a fixed value
        mappedData[targetKey] = sourceValue;
      }
    });

    return mappedData;
  }

  Future<void> _handleFormButtonTap(FieldDefinition field) async {
    if (field.formKey == null) return;

    final formLoader = Provider.of<FormLoader>(context, listen: false);
    final targetForm = formLoader.getForm(field.formKey!);

    if (targetForm == null) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Formulário "${field.formKey}" não encontrado')),
      );
      return;
    }

    // Map data according to field configuration
    final mappedData = _mapFormData(_formData, field.mapearDados);

    // Handle salvarJunto: accumulate current form data but don't save yet
    Map<String, Map<String, dynamic>> newAccumulatedData = Map.from(_accumulatedFormsData);
    if (field.salvarJunto) {
      // Store current form data in accumulated data (but don't validate yet)
      // We'll validate and save together when the nested form is completed
      newAccumulatedData[widget.formDefinition.key] = Map<String, dynamic>.from(_formData);
    }

    // If salvarAntes is true, save current form first (per-part logic)
    String? currentEntryId;
    if (field.salvarAntes && !field.salvarJunto) {
      // Validate and save without navigating away
      if (!_validateForm()) {
        if (!mounted) return;
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Por favor, corrija os erros antes de continuar')),
        );
        return;
      }

      setState(() {
        _isSaving = true;
      });

      try {
        final location = await _locationService.getCurrentLocation();
        
        final authService = Provider.of<AuthService>(context, listen: false);
        final currentUserId = authService.currentUser?.id;

        // Ensure all images are in persistent storage
        final imageStorage = ImageStorageService();
        final processedFormData = await _ensureImagesPersistent(_formData, imageStorage);
        
        final entry = FormEntry(
          id: DateTime.now().millisecondsSinceEpoch.toString(),
          formKey: widget.formDefinition.key,
          answers: processedFormData,
          location: location,
          userId: currentUserId,
          parentFormId: widget.parentFormId,
        );

        final box = Hive.box<FormEntry>('entries');
        await box.put(entry.id, entry);
        currentEntryId = entry.id; // Store ID for parent relationship

        if (!mounted) return;

        setState(() {
          _isSaving = false;
        });

        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text(Strings.formularioSalvo)),
        );
      } catch (e) {
        if (!mounted) return;
        
        setState(() {
          _isSaving = false;
        });

        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Erro ao salvar: $e')),
        );
        return;
      }
    }

    // Navigate to target form
    // If saving before, replace current form so child form pops to home
    // Otherwise, push so child form returns to current form
    if (mounted) {
      if (field.salvarAntes && !field.salvarJunto) {
        // Per-part saving: save parent first, then navigate
        Navigator.of(context).pushReplacement(
          MaterialPageRoute(
            builder: (_) => FormScreen(
              formDefinition: targetForm,
              initialData: mappedData,
              parentFormId: currentEntryId,
              returnToHomeOnComplete: true,
              accumulatedFormsData: null, // Reset accumulated data
            ),
          ),
        );
      } else if (field.salvarJunto) {
        // Grouped saving: accumulate data, navigate without saving
        Navigator.of(context).push(
          MaterialPageRoute(
            builder: (_) => FormScreen(
              formDefinition: targetForm,
              initialData: mappedData,
              parentFormId: widget.parentFormId,
              returnToHomeOnComplete: widget.returnToHomeOnComplete,
              accumulatedFormsData: newAccumulatedData, // Pass accumulated data
            ),
          ),
        );
      } else {
        // Default: just navigate without saving
        Navigator.of(context).push(
          MaterialPageRoute(
            builder: (_) => FormScreen(
              formDefinition: targetForm,
              initialData: mappedData,
              parentFormId: widget.parentFormId,
              returnToHomeOnComplete: false,
              accumulatedFormsData: null, // Reset accumulated data
            ),
          ),
        );
      }
    }
  }

  bool _validateForm() {
    _errors.clear();
    bool isValid = true;

    for (final field in widget.formDefinition.campos) {
      final value = _formData[field.id];
      final error = field.validate(value);
      if (error != null) {
        _errors[field.id] = error;
        isValid = false;
      }
    }

    setState(() {});
    return isValid;
  }

  Future<void> _saveForm() async {
    // If we have accumulated forms data, validate all forms before saving
    if (_accumulatedFormsData.isNotEmpty) {
      // Validate current form first
      if (!_validateForm()) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Por favor, corrija os erros antes de salvar')),
        );
        return;
      }

      // Add current form to accumulated data
      final allFormsData = Map<String, Map<String, dynamic>>.from(_accumulatedFormsData);
      allFormsData[widget.formDefinition.key] = Map<String, dynamic>.from(_formData);

      // Validate all accumulated forms
      final formLoader = Provider.of<FormLoader>(context, listen: false);
      final validationErrors = <String, String>{};
      
      for (final entry in allFormsData.entries) {
        final formKey = entry.key;
        final formData = entry.value;
        final formDefinition = formLoader.getForm(formKey);
        
        if (formDefinition == null) {
          validationErrors[formKey] = 'Formulário não encontrado';
          continue;
        }

        // Validate each field in the form
        for (final field in formDefinition.campos) {
          final value = formData[field.id];
          final error = field.validate(value);
          if (error != null) {
            validationErrors['$formKey.${field.id}'] = error;
          }
        }
      }

      if (validationErrors.isNotEmpty) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text(
              'Por favor, corrija os erros nos formulários: ${validationErrors.length} erro(s) encontrado(s)',
            ),
          ),
        );
        return;
      }

      // All forms validated, now save them all together
      await _saveAccumulatedForms(allFormsData);
      return;
    }

    // Normal save flow (no accumulated forms)
    if (!_validateForm()) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Por favor, corrija os erros antes de salvar')),
      );
      return;
    }

    setState(() {
      _isSaving = true;
    });

    try {
      // Mostrar carregamento de localização
      showDialog(
        context: context,
        barrierDismissible: false,
        builder: (context) => const Dialog(
          child: Padding(
            padding: EdgeInsets.all(16),
            child: Row(
              mainAxisSize: MainAxisSize.min,
              children: [
                CircularProgressIndicator(),
                SizedBox(width: 12),
                Flexible(child: Text('Adicionando dados da localização...')),
              ],
            ),
          ),
        ),
      );

      // Obter localização (pode ser null em caso de erro, mas tentamos continuar)
      final location = await _locationService.getCurrentLocation();
      if (mounted) {
        Navigator.of(context, rootNavigator: true).pop(); // fecha dialog de carregamento
      }
      
      if (location == null) {
        if (!mounted) return;
        
        // Pergunta se quer continuar sem localização ou tentar configurar
        final response = await showDialog<String>(
          context: context,
          builder: (context) => AlertDialog(
            title: const Text(Strings.permissaoLocalizacao),
            content: const Text(
              'Não foi possível obter a localização atual.\n\n'
              'Você pode:\n'
              '- Configurar as permissões de localização\n'
              '- Salvar o formulário sem localização',
            ),
            actions: [
              TextButton(
                onPressed: () => Navigator.of(context).pop('continue'),
                child: const Text('Continuar sem localização'),
              ),
              TextButton(
                onPressed: () => Navigator.of(context).pop('cancel'),
                child: const Text(Strings.cancelar),
              ),
              TextButton(
                onPressed: () => Navigator.of(context).pop('settings'),
                child: const Text(Strings.configurar),
              ),
            ],
          ),
        );

        if (response == 'settings') {
          await _locationService.openLocationSettings();
          setState(() {
            _isSaving = false;
          });
          return;
        } else if (response == 'cancel') {
          setState(() {
            _isSaving = false;
          });
          return;
        }
        // Se escolheu continuar sem localização, location será null
      }

      // Obter usuário atual
      final authService = Provider.of<AuthService>(context, listen: false);
      final currentUserId = authService.currentUser?.id;
      
      debugPrint('[FormScreen] Criando entrada com userId: $currentUserId');
      debugPrint('[FormScreen] Usuário atual: ${authService.currentUser?.email}');

      // Ensure all images are in persistent storage before saving
      final imageStorage = ImageStorageService();
      final processedFormData = await _ensureImagesPersistent(_formData, imageStorage);

      // Criar entrada
      final entry = FormEntry(
        id: DateTime.now().millisecondsSinceEpoch.toString(),
        formKey: widget.formDefinition.key,
        answers: processedFormData,
        location: location,
        userId: currentUserId,
        parentFormId: widget.parentFormId,
      );
      
      debugPrint('[FormScreen] Entry criado - userId: ${entry.userId}, parentFormId: ${entry.parentFormId}');
      debugPrint('[FormScreen] Form data saved: ${processedFormData.keys}');

      // Salvar no Hive
      final box = Hive.box<FormEntry>('entries');
      await box.put(entry.id, entry);
      
      // Verify what was actually saved
      final savedEntry = box.get(entry.id);
      if (savedEntry != null) {
        debugPrint('[FormScreen] Verified saved entry - answers keys: ${savedEntry.answers.keys}');
        // Log image paths specifically
        for (final key in savedEntry.answers.keys) {
          final value = savedEntry.answers[key];
          if (value is String && (value.contains('/image') || value.contains('.jpg') || value.contains('.png'))) {
            debugPrint('[FormScreen] Image path in saved entry [$key]: $value');
          } else if (value is List) {
            for (final item in value) {
              if (item is String && (item.contains('/image') || item.contains('.jpg') || item.contains('.png'))) {
                debugPrint('[FormScreen] Image path in saved entry [$key]: $item');
              }
            }
          }
        }
      }
      
      // Verificar se foi salvo corretamente
      debugPrint('[FormScreen] Entry salvo - userId após salvar: ${savedEntry?.userId}');
      debugPrint('[FormScreen] Entry salvo - parentFormId após salvar: ${savedEntry?.parentFormId}');

      if (!mounted) return;

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text(Strings.formularioSalvo)),
      );

      // Check for next form configuration
      final nextForm = widget.formDefinition.nextForm;
      if (nextForm != null && nextForm.evaluateCondition(_formData)) {
        // Get the target form
        final formLoader = Provider.of<FormLoader>(context, listen: false);
        final targetForm = formLoader.getForm(nextForm.formKey);
        
        if (targetForm != null) {
          // Map data according to configuration
          final mappedData = _mapFormData(_formData, nextForm.mapearDados);
          
          // If we have accumulated forms data, pass it along
          // Otherwise, create a new entry chain
          Map<String, Map<String, dynamic>>? accumulatedData;
          if (_accumulatedFormsData.isNotEmpty) {
            accumulatedData = Map.from(_accumulatedFormsData);
            accumulatedData[widget.formDefinition.key] = Map<String, dynamic>.from(_formData);
          }
          
          // Navigate to next form with mapped data
          // Replace current form with next form, so when next form pops it goes to home
          if (mounted) {
            Navigator.of(context).pushReplacement(
              MaterialPageRoute(
                builder: (_) => FormScreen(
                  formDefinition: targetForm,
                  initialData: mappedData,
                  parentFormId: accumulatedData != null ? null : entry.id,
                  returnToHomeOnComplete: true,
                  accumulatedFormsData: accumulatedData,
                ),
              ),
            );
          }
          return;
        }
      }

      // If returnToHomeOnComplete is true, pop until we reach home
      if (widget.returnToHomeOnComplete && mounted) {
        Navigator.of(context).popUntil((route) => route.isFirst);
      } else if (mounted) {
        Navigator.of(context).pop();
      }
    } catch (e) {
      if (!mounted) return;
      
      setState(() {
        _isSaving = false;
      });

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('${Strings.erroSalvar}: $e')),
      );
    }
  }

  Future<void> _saveAccumulatedForms(Map<String, Map<String, dynamic>> allFormsData) async {
    setState(() {
      _isSaving = true;
    });

    try {
      // Show location loading dialog
      showDialog(
        context: context,
        barrierDismissible: false,
        builder: (context) => const Dialog(
          child: Padding(
            padding: EdgeInsets.all(16),
            child: Row(
              mainAxisSize: MainAxisSize.min,
              children: [
                CircularProgressIndicator(),
                SizedBox(width: 12),
                Flexible(child: Text('Adicionando dados da localização...')),
              ],
            ),
          ),
        ),
      );

      // Get location once (shared for all forms)
      final location = await _locationService.getCurrentLocation();
      if (mounted) {
        Navigator.of(context, rootNavigator: true).pop(); // Close loading dialog
      }

      if (location == null) {
        if (!mounted) return;

        final response = await showDialog<String>(
          context: context,
          builder: (context) => AlertDialog(
            title: const Text(Strings.permissaoLocalizacao),
            content: const Text(
              'Não foi possível obter a localização atual.\n\n'
              'Você pode:\n'
              '- Configurar as permissões de localização\n'
              '- Salvar os formulários sem localização',
            ),
            actions: [
              TextButton(
                onPressed: () => Navigator.of(context).pop('continue'),
                child: const Text('Continuar sem localização'),
              ),
              TextButton(
                onPressed: () => Navigator.of(context).pop('cancel'),
                child: const Text(Strings.cancelar),
              ),
              TextButton(
                onPressed: () => Navigator.of(context).pop('settings'),
                child: const Text(Strings.configurar),
              ),
            ],
          ),
        );

        if (response == 'settings') {
          await _locationService.openLocationSettings();
          setState(() {
            _isSaving = false;
          });
          return;
        } else if (response == 'cancel') {
          setState(() {
            _isSaving = false;
          });
          return;
        }
      }

      // Get current user
      final authService = Provider.of<AuthService>(context, listen: false);
      final currentUserId = authService.currentUser?.id;

      // Get form loader
      final formLoader = Provider.of<FormLoader>(context, listen: false);

      // Save all forms in sequence, linking them with parentFormId
      final box = Hive.box<FormEntry>('entries');
      String? previousEntryId;

      // Convert accumulated data to a list of entries sorted by form order
      // The first form in the chain is the parent, subsequent ones are children
      final formEntries = <String>[];
      for (final entry in allFormsData.entries) {
        formEntries.add(entry.key);
      }

      // Ensure all images are in persistent storage before saving
      final imageStorage = ImageStorageService();
      
      // Save forms in order
      for (int i = 0; i < formEntries.length; i++) {
        final formKey = formEntries[i];
        var formData = allFormsData[formKey]!;
        
        // Ensure images are persistent
        formData = await _ensureImagesPersistent(formData, imageStorage);
        
        final formDefinition = formLoader.getForm(formKey);

        if (formDefinition == null) continue;

        final entry = FormEntry(
          id: DateTime.now().millisecondsSinceEpoch.toString() + '_$i',
          formKey: formKey,
          answers: formData,
          location: location,
          userId: currentUserId,
          parentFormId: previousEntryId, // Link to previous form
        );

        await box.put(entry.id, entry);
        previousEntryId = entry.id;
      }

      if (!mounted) return;

      setState(() {
        _isSaving = false;
      });

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('${formEntries.length} formulário(s) salvo(s) com sucesso!'),
        ),
      );

      // Navigate back
      if (widget.returnToHomeOnComplete && mounted) {
        Navigator.of(context).popUntil((route) => route.isFirst);
      } else if (mounted) {
        Navigator.of(context).popUntil((route) => route.isFirst || route.settings.name == '/home');
      }
    } catch (e) {
      if (!mounted) return;

      setState(() {
        _isSaving = false;
      });

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('${Strings.erroSalvar}: $e')),
      );
    }
  }

  /// Ensure all image paths in form data are in persistent storage
  Future<Map<String, dynamic>> _ensureImagesPersistent(
    Map<String, dynamic> formData,
    ImageStorageService imageStorage,
  ) async {
    final processedData = <String, dynamic>{};
    
    for (final entry in formData.entries) {
      final key = entry.key;
      final value = entry.value;
      
      if (value is String && _isImagePath(value)) {
        try {
          debugPrint('[FormScreen] Processing image path: $value');
          // Check if already in persistent storage using proper path comparison
          final isPersistent = await imageStorage.isPersistentPath(value);
          if (isPersistent) {
            final imagesDir = await imageStorage.getImagesDirectory();
            // Already persistent, verify file exists
            final file = File(value);
            if (await file.exists()) {
              debugPrint('[FormScreen] Image already in persistent storage and exists: $value');
              processedData[key] = value;
            } else {
              debugPrint('[FormScreen] Persistent path exists but file missing, attempting to migrate: $value');
              // File is missing, try to save from original if we can find it
              // For now, keep the path (will show error in UI)
              processedData[key] = value;
            }
          } else {
            // Not persistent, save it
            final persistentPath = await imageStorage.saveImage(value);
            processedData[key] = persistentPath;
            debugPrint('[FormScreen] Image path saved to persistent: $persistentPath');
          }
        } catch (e) {
          debugPrint('[FormScreen] Error saving image $value: $e');
          // Keep original path even if migration fails
          processedData[key] = value;
        }
      } else if (value is List) {
        final processedList = <dynamic>[];
        for (final item in value) {
          if (item is String && _isImagePath(item)) {
            try {
              debugPrint('[FormScreen] Processing image path in list: $item');
              // Check if already persistent using proper path comparison
              final isPersistent = await imageStorage.isPersistentPath(item);
              if (isPersistent) {
                final file = File(item);
                if (await file.exists()) {
                  debugPrint('[FormScreen] List image already persistent: $item');
                  processedList.add(item);
                } else {
                  debugPrint('[FormScreen] List image persistent path but missing file: $item');
                  processedList.add(item);
                }
              } else {
                // Save to persistent
                final persistentPath = await imageStorage.saveImage(item);
                processedList.add(persistentPath);
                debugPrint('[FormScreen] List image path saved to persistent: $persistentPath');
              }
            } catch (e) {
              debugPrint('[FormScreen] Error saving list image $item: $e');
              processedList.add(item);
            }
          } else {
            processedList.add(item);
          }
        }
        processedData[key] = processedList;
      } else {
        processedData[key] = value;
      }
    }
    
    return processedData;
  }

  bool _isImagePath(String path) {
    if (path.isEmpty) return false;
    if (path.startsWith('http://') || path.startsWith('https://')) return false;
    
    final extension = path.split('.').last.toLowerCase();
    return ['jpg', 'jpeg', 'png', 'gif', 'webp', 'bmp'].contains(extension) ||
           path.contains('/image') ||
           path.contains('/photo') ||
           path.contains('/picture') ||
           path.contains('/cache');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.formDefinition.titulo),
      ),
      body: Form(
        child: Column(
          children: [
            Expanded(
              child: SingleChildScrollView(
                padding: const EdgeInsets.all(16),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    if (widget.formDefinition.descricao != null) ...[
                      Text(
                        widget.formDefinition.descricao!,
                        style: Theme.of(context).textTheme.bodyLarge,
                      ),
                      const SizedBox(height: 24),
                    ],
                    ...widget.formDefinition.campos.map((field) => Padding(
                          padding: const EdgeInsets.only(bottom: 16),
                          child: DynamicFormField(
                            field: field,
                            value: _formData[field.id],
                            error: _errors[field.id],
                            onChanged: (value) => _updateFieldValue(field.id, value),
                            onFormButtonTap: field.tipo == 'formulario'
                                ? () => _handleFormButtonTap(field)
                                : null,
                          ),
                        )),
                  ],
                ),
              ),
            ),
            Container(
              padding: const EdgeInsets.all(16),
              decoration: BoxDecoration(
                color: Theme.of(context).scaffoldBackgroundColor,
                boxShadow: [
                  BoxShadow(
                    color: Colors.black.withOpacity(0.1),
                    blurRadius: 4,
                    offset: const Offset(0, -2),
                  ),
                ],
              ),
              child: Row(
                children: [
                  Expanded(
                    child: OutlinedButton(
                      onPressed: _isSaving ? null : () => Navigator.of(context).pop(),
                      child: const Text(Strings.cancelar),
                    ),
                  ),
                  const SizedBox(width: 16),
                  Expanded(
                    flex: 2,
                    child: ElevatedButton(
                      onPressed: _isSaving ? null : _saveForm,
                      child: _isSaving
                          ? const SizedBox(
                              height: 20,
                              width: 20,
                              child: CircularProgressIndicator(strokeWidth: 2),
                            )
                          : const Text(Strings.salvar),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

