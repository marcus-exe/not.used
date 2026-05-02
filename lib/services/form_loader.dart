import 'dart:convert';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import '../data/models/form_schema.dart';

class FormLoader extends ChangeNotifier {
  final Map<String, FormDefinition> _forms = {};
  String? _selectedFormKey;
  bool _isLoading = false;

  Map<String, FormDefinition> get forms => _forms;
  List<FormDefinition> get formsList => _forms.values.toList();
  FormDefinition? get selectedForm => _selectedFormKey != null ? _forms[_selectedFormKey] : null;
  String? get selectedFormKey => _selectedFormKey;
  bool get isLoading => _isLoading;

  Future<void> loadForms() async {
    try {
      _isLoading = true;
      notifyListeners();
      debugPrint('[FormLoader] Lendo AssetManifest.json...');
      final manifestContent = await rootBundle.loadString('AssetManifest.json');
      final Map<String, dynamic> manifestMap = json.decode(manifestContent);
      debugPrint('[FormLoader] Total de entradas no manifest: ${manifestMap.length}');
      
      final formAssets = manifestMap.keys
          .where((key) => key.startsWith('assets/forms/') && key.endsWith('.json'))
          .toList();
      debugPrint('[FormLoader] Assets de formulários encontrados: ${formAssets.length}');
      for (final a in formAssets) {
        debugPrint('[FormLoader] - $a');
      }

      for (final assetPath in formAssets) {
        try {
          final jsonString = await rootBundle.loadString(assetPath);
          final jsonData = json.decode(jsonString) as Map<String, dynamic>;
          final formDef = FormDefinition.fromJson(jsonData);
          _forms[formDef.key] = formDef;
          debugPrint('[FormLoader] Form carregado: key="${formDef.key}", titulo="${formDef.titulo}"');
        } catch (e) {
          debugPrint('[FormLoader] Erro ao carregar formulário $assetPath: $e');
        }
      }

      // Define um formulário padrão se nenhum estiver selecionado
      if (_selectedFormKey == null && _forms.isNotEmpty) {
        _selectedFormKey = _forms.values.first.key;
      }

      debugPrint('[FormLoader] Total de formulários carregados: ${_forms.length}');
      _isLoading = false;
      notifyListeners();
    } catch (e) {
      debugPrint('[FormLoader] Erro ao carregar formulários: $e');
      _isLoading = false;
      notifyListeners();
    }
  }

  FormDefinition? getForm(String key) {
    return _forms[key];
  }

  void setSelectedForm(String key) {
    if (_forms.containsKey(key)) {
      _selectedFormKey = key;
      notifyListeners();
    }
  }
}

