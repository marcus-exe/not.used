import 'package:go_router/go_router.dart';
import 'package:hobby_app/presentation/pages/add_hobby_page.dart';
import 'package:hobby_app/presentation/pages/details_page.dart';
import 'package:hobby_app/data/models/hobby_model.dart';
import 'package:hobby_app/presentation/pages/home_page.dart';

final appRouter = GoRouter(
  routes: [
    GoRoute(
      path: '/',
      builder: (context, state) => const HomePage(),
      routes: [
        GoRoute(
          path: 'detail',
          builder: (context, state) {
            final hobby = state.extra as Hobby;
            return DetailPage(hobby: hobby);
          },
        ),
        GoRoute(
          path: 'add-hobby',
          builder: (context, state) => const ManageHobbyPage(),
        ),
        GoRoute(
          path: 'edit-hobby',
          builder: (context, state) {
            final hobby = state.extra as Hobby;
            return ManageHobbyPage(hobby: hobby);
          },
        ),
      ],
    ),
  ],
);