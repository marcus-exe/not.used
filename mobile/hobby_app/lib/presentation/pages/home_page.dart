import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:hobby_app/data/providers/providers.dart';

class HomePage extends ConsumerWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final hobbiesAsyncValue = ref.watch(hobbyViewModelProvider);

    return Scaffold(
      appBar: AppBar(
        title: const Text('My Hobby Hub'),
        centerTitle: true,
        backgroundColor: Colors.teal,
      ),
      body: hobbiesAsyncValue.when(
        data: (hobbies) {
          return ListView.builder(
            itemCount: hobbies.length,
            itemBuilder: (context, index) {
              final hobby = hobbies[index];
              return Card(
                margin: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
                elevation: 4,
                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
                child: ListTile(
                  leading: Icon(hobby.icon, color: Colors.teal),
                  title: Text(
                    hobby.name,
                    style: const TextStyle(fontWeight: FontWeight.bold),
                  ),
                  subtitle: Text(hobby.description),
                  trailing: const Icon(Icons.chevron_right_rounded),
                  onTap: () {
                    context.go('/detail', extra: hobby);
                  },
                ),
              );
            },
          );
        },
        loading: () => const Center(child: CircularProgressIndicator()),
        error: (error, stack) => Center(child: Text('Error: $error')),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          context.go('/add-hobby');
        },
        backgroundColor: Colors.teal,
        foregroundColor: Colors.white,
        tooltip: 'Add new hobby',
        child: const Icon(Icons.add),
      ),
    );
  }
}