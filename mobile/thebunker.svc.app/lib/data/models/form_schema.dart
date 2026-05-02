import '../../l10n/strings.dart';

class NextFormConfig {
  final String formKey;
  final NextFormCondition? condicao;
  final Map<String, dynamic>? mapearDados;

  NextFormConfig({
    required this.formKey,
    this.condicao,
    this.mapearDados,
  });

  factory NextFormConfig.fromJson(Map<String, dynamic> json) {
    return NextFormConfig(
      formKey: json['formKey'] as String,
      condicao: json['condicao'] != null
          ? NextFormCondition.fromJson(json['condicao'] as Map<String, dynamic>)
          : null,
      mapearDados: json['mapearDados'] != null
          ? Map<String, dynamic>.from(json['mapearDados'] as Map)
          : null,
    );
  }

  bool evaluateCondition(Map<String, dynamic> formData) {
    if (condicao == null) return true;
    return condicao!.evaluate(formData);
  }
}

class NextFormCondition {
  final String campo;
  final dynamic valor;

  NextFormCondition({
    required this.campo,
    required this.valor,
  });

  factory NextFormCondition.fromJson(Map<String, dynamic> json) {
    return NextFormCondition(
      campo: json['campo'] as String,
      valor: json['valor'],
    );
  }

  bool evaluate(Map<String, dynamic> formData) {
    final fieldValue = formData[campo];
    return fieldValue == valor;
  }
}

class FormDefinition {
  final String key;
  final String titulo;
  final String? descricao;
  final List<FieldDefinition> campos;
  final NextFormConfig? nextForm;
  final bool oculto;

  FormDefinition({
    required this.key,
    required this.titulo,
    this.descricao,
    required this.campos,
    this.nextForm,
    this.oculto = false,
  });

  factory FormDefinition.fromJson(Map<String, dynamic> json) {
    return FormDefinition(
      key: json['key'] as String,
      titulo: json['titulo'] as String,
      descricao: json['descricao'] as String?,
      campos: (json['campos'] as List<dynamic>)
          .map((field) => FieldDefinition.fromJson(field as Map<String, dynamic>))
          .toList(),
      nextForm: json['nextForm'] != null
          ? NextFormConfig.fromJson(json['nextForm'] as Map<String, dynamic>)
          : null,
      oculto: json['oculto'] as bool? ?? false,
    );
  }
}

class FieldDefinition {
  final String id;
  final String tipo;
  final String rotulo;
  final bool obrigatorio;
  final dynamic min;
  final dynamic max;
  final String? regex;
  final int? minLength;
  final int? maxLength;
  final List<String>? opcoes;
  // Form button specific fields
  final String? formKey;
  final Map<String, dynamic>? mapearDados;
  final bool salvarAntes;
  final bool salvarJunto;

  FieldDefinition({
    required this.id,
    required this.tipo,
    required this.rotulo,
    this.obrigatorio = false,
    this.min,
    this.max,
    this.regex,
    this.minLength,
    this.maxLength,
    this.opcoes,
    this.formKey,
    this.mapearDados,
    this.salvarAntes = false,
    this.salvarJunto = false,
  });

  factory FieldDefinition.fromJson(Map<String, dynamic> json) {
    return FieldDefinition(
      id: json['id'] as String,
      tipo: json['tipo'] as String,
      rotulo: json['rotulo'] as String,
      obrigatorio: json['obrigatorio'] as bool? ?? false,
      min: json['min'],
      max: json['max'],
      regex: json['regex'] as String?,
      minLength: json['minLength'] as int?,
      maxLength: json['maxLength'] as int?,
      opcoes: json['opcoes'] != null
          ? List<String>.from(json['opcoes'] as List)
          : null,
      formKey: json['formKey'] as String?,
      mapearDados: json['mapearDados'] != null
          ? Map<String, dynamic>.from(json['mapearDados'] as Map)
          : null,
      salvarAntes: json['salvarAntes'] as bool? ?? false,
      salvarJunto: json['salvarJunto'] as bool? ?? false,
    );
  }

  String? validate(dynamic value) {
    if (obrigatorio && (value == null || value == '' || value == false)) {
      return '${Strings.campoObrigatorio}';
    }

    if (value == null || value == '') {
      return null; // Campos opcionais vazios são válidos
    }

    if (tipo == 'texto' || tipo == 'multilinha') {
      final strValue = value.toString();
      if (minLength != null && strValue.length < minLength!) {
        return '${Strings.caracteresMinimos} $minLength ${Strings.caracteres}';
      }
      if (maxLength != null && strValue.length > maxLength!) {
        return 'Máximo de $maxLength ${Strings.caracteres}';
      }
      if (regex != null && !RegExp(regex!).hasMatch(strValue)) {
        return Strings.regexInvalido;
      }
    }

    if (tipo == 'numero') {
      final numValue = value is num ? value : double.tryParse(value.toString());
      if (numValue == null) {
        return 'Valor numérico inválido';
      }
      if (min != null && numValue < (min as num)) {
        return '${Strings.valorMinimo} $min';
      }
      if (max != null && numValue > (max as num)) {
        return '${Strings.valorMaximo} $max';
      }
    }

    if (tipo == 'imagem' || tipo == 'galeria') {
      // For image fields, value can be a String or List<String>
      if (value is List) {
        final imageList = List.from(value).where((item) => item != null && item != '').toList();
        if (obrigatorio && imageList.isEmpty) {
          return '${Strings.campoObrigatorio}';
        }
        if (max != null && imageList.length > (max as num).toInt()) {
          return 'Máximo de ${(max as num).toInt()} imagens permitidas';
        }
      }
      // Single image path validation is already handled by obrigatorio check at the top
    }

    return null;
  }
}

