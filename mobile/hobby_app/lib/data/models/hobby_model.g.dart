// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'hobby_model.dart';

// **************************************************************************
// TypeAdapterGenerator
// **************************************************************************

class HobbyAdapter extends TypeAdapter<Hobby> {
  @override
  final int typeId = 0;

  @override
  Hobby read(BinaryReader reader) {
    final numOfFields = reader.readByte();
    final fields = <int, dynamic>{
      for (int i = 0; i < numOfFields; i++) reader.readByte(): reader.read(),
    };
    return Hobby(
      name: fields[0] as String,
      description: fields[1] as String,
      // Correctly read the integer code point and create a new IconData object.
      icon: IconData(
        fields[2] as int,
        fontFamily: 'MaterialIcons', // Assuming MaterialIcons is used.
      ),
    );
  }

  @override
  void write(BinaryWriter writer, Hobby obj) {
    writer
      ..writeByte(3)
      ..writeByte(0)
      ..write(obj.name)
      ..writeByte(1)
      ..write(obj.description)
      // Correctly write the integer code point.
      ..writeByte(2)
      ..write(obj.icon.codePoint);
  }

  @override
  int get hashCode => typeId.hashCode;

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is HobbyAdapter &&
          runtimeType == other.runtimeType &&
          typeId == other.typeId;
}