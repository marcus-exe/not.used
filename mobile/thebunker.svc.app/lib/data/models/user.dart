import 'package:hive/hive.dart';

part 'user.g.dart';

@HiveType(typeId: 0)
class User extends HiveObject {
  @HiveField(0)
  String id;

  @HiveField(1)
  String email;

  @HiveField(2)
  String passwordHash;

  @HiveField(3)
  DateTime createdAt;

  User({
    required this.id,
    required this.email,
    required this.passwordHash,
    DateTime? createdAt,
  }) : createdAt = createdAt ?? DateTime.now();
}

