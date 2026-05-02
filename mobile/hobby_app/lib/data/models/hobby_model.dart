import 'package:flutter/material.dart';
import 'package:hive_flutter/hive_flutter.dart';

part 'hobby_model.g.dart';

@HiveType(typeId: 0)
class Hobby {
  @HiveField(0)
  final String name;

  @HiveField(1)
  final String description;

  @HiveField(2)
  final IconData icon;

  const Hobby({
    required this.name,
    required this.description,
    required this.icon,
  });
}