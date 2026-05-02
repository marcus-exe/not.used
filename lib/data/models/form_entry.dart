import 'package:hive/hive.dart';
import 'geo_point.dart';

part 'form_entry.g.dart';

@HiveType(typeId: 1)
class FormEntry extends HiveObject {
  @HiveField(0)
  String id;

  @HiveField(1)
  String formKey;

  @HiveField(2)
  Map<String, dynamic> answers;

  @HiveField(3)
  DateTime createdAt;

  @HiveField(4)
  GeoPoint? location;

  // Usuário que criou esta entrada
  @HiveField(5)
  String? userId;

  // ID da entrada do formulário pai (se este foi criado a partir de outro formulário)
  @HiveField(6)
  String? parentFormId;

  FormEntry({
    required this.id,
    required this.formKey,
    required this.answers,
    DateTime? createdAt,
    this.location,
    this.userId,
    this.parentFormId,
  }) : createdAt = createdAt ?? DateTime.now();
}

