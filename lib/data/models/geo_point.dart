import 'package:hive/hive.dart';

part 'geo_point.g.dart';

@HiveType(typeId: 2)
class GeoPoint extends HiveObject {
  @HiveField(0)
  double latitude;

  @HiveField(1)
  double longitude;

  @HiveField(2)
  double? accuracy;

  @HiveField(3)
  DateTime timestamp;

  GeoPoint({
    required this.latitude,
    required this.longitude,
    this.accuracy,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();

  Map<String, dynamic> toJson() {
    return {
      'latitude': latitude,
      'longitude': longitude,
      'accuracy': accuracy,
      'timestamp': timestamp.toIso8601String(),
    };
  }

  factory GeoPoint.fromJson(Map<String, dynamic> json) {
    return GeoPoint(
      latitude: json['latitude'] as double,
      longitude: json['longitude'] as double,
      accuracy: json['accuracy'] as double?,
      timestamp: DateTime.parse(json['timestamp'] as String),
    );
  }
}

