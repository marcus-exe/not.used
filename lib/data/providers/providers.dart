import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:hobby_app/data/models/hobby_model.dart';
import 'package:hobby_app/presentation/view_models/hobby_view_models.dart';

final hobbyViewModelProvider =
    AsyncNotifierProvider<HobbyViewModel, List<Hobby>>(() {
  return HobbyViewModel();
});