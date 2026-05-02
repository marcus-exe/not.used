import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:hobby_app/data/models/hobby_model.dart';

class HobbyViewModel extends AsyncNotifier<List<Hobby>> {
  late final Box<Hobby> _hobbyBox;

  @override
  Future<List<Hobby>> build() async {
    _hobbyBox = await Hive.openBox<Hobby>('hobbies', encryptionCipher: null);

    if (_hobbyBox.isEmpty) {
      final defaultHobbies = [
        const Hobby(
          name: 'Gardening',
          description: 'Cultivating plants, flowers, and vegetables.',
          icon: Icons.grass_rounded,
        ),
        const Hobby(
          name: 'Painting',
          description: 'Creating art using various mediums.',
          icon: Icons.format_paint_rounded,
        ),
        const Hobby(
          name: 'Baking',
          description: 'Preparing food in an oven, often with delicious results.',
          icon: Icons.bakery_dining_rounded,
        ),
        const Hobby(
          name: 'Hiking',
          description: 'Going for long walks, especially in the countryside or woods.',
          icon: Icons.hiking_rounded,
        ),
        const Hobby(
          name: 'Photography',
          description: 'The art of creating images with a camera.',
          icon: Icons.photo_camera_rounded,
        ),
      ];
      await _hobbyBox.addAll(defaultHobbies);
    }
    
    return _hobbyBox.values.toList();
  }

  Future<void> addHobby(Hobby newHobby) async {
    await _hobbyBox.add(newHobby);
    state = AsyncData(_hobbyBox.values.toList());
  }

  Future<void> deleteHobby(Hobby hobby) async {
    final hobbyIndex = _hobbyBox.values.toList().indexOf(hobby);
    if (hobbyIndex != -1) {
      await _hobbyBox.deleteAt(hobbyIndex);
    }
    state = AsyncData(_hobbyBox.values.toList());
  }

  Future<void> updateHobby(Hobby originalHobby, Hobby updatedHobby) async {
    final hobbyIndex = _hobbyBox.values.toList().indexOf(originalHobby);
    if (hobbyIndex != -1) {
      await _hobbyBox.putAt(hobbyIndex, updatedHobby);
    }
    state = AsyncData(_hobbyBox.values.toList());
  }
}