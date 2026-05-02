import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:hobby_app/data/models/hobby_model.dart';
import 'package:hobby_app/data/providers/providers.dart';

class ManageHobbyPage extends ConsumerStatefulWidget {
  final Hobby? hobby;
  const ManageHobbyPage({super.key, this.hobby});

  @override
  ConsumerState<ManageHobbyPage> createState() => _ManageHobbyPageState();
}

class _ManageHobbyPageState extends ConsumerState<ManageHobbyPage> {
  final _formKey = GlobalKey<FormState>();
  late final TextEditingController _nameController;
  late final TextEditingController _descriptionController;
  late IconData _selectedIcon;

  final List<IconData> availableIcons = [
    Icons.sports_soccer_rounded,
    Icons.local_library_rounded,
    Icons.videogame_asset_rounded,
    Icons.music_note_rounded,
    Icons.grass_rounded,
    Icons.format_paint_rounded,
    Icons.bakery_dining_rounded,
    Icons.hiking_rounded,
    Icons.photo_camera_rounded,
  ];

  @override
  void initState() {
    super.initState();
    _nameController = TextEditingController(text: widget.hobby?.name ?? '');
    _descriptionController = TextEditingController(text: widget.hobby?.description ?? '');
    _selectedIcon = widget.hobby?.icon ?? Icons.help;
  }

  void _saveHobby() async {
    if (_formKey.currentState!.validate()) {
      final newHobby = Hobby(
        name: _nameController.text,
        description: _descriptionController.text,
        icon: _selectedIcon,
      );

      final notifier = ref.read(hobbyViewModelProvider.notifier);
      if (widget.hobby != null) {
        await notifier.updateHobby(widget.hobby!, newHobby);
      } else {
        await notifier.addHobby(newHobby);
      }

      if (mounted) {
        context.pop();
      }
    }
  }

  @override
  void dispose() {
    _nameController.dispose();
    _descriptionController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.hobby == null ? 'Add New Hobby' : 'Edit Hobby'),
        centerTitle: true,
        backgroundColor: Colors.teal,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              TextFormField(
                controller: _nameController,
                decoration: InputDecoration(
                  labelText: 'Hobby Name',
                  border: OutlineInputBorder(borderRadius: BorderRadius.circular(12)),
                  prefixIcon: const Icon(Icons.create_rounded),
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter a name for the hobby.';
                  }
                  return null;
                },
              ),
              const SizedBox(height: 16),
              TextFormField(
                controller: _descriptionController,
                decoration: InputDecoration(
                  labelText: 'Description',
                  border: OutlineInputBorder(borderRadius: BorderRadius.circular(12)),
                  prefixIcon: const Icon(Icons.description_rounded),
                ),
                maxLines: 3,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter a description.';
                  }
                  return null;
                },
              ),
              const SizedBox(height: 24),
              ListTile(
                title: const Text('Select an Icon'),
                leading: Icon(_selectedIcon),
                trailing: const Icon(Icons.arrow_drop_down),
                onTap: () {
                  showDialog(
                    context: context,
                    builder: (BuildContext context) {
                      return AlertDialog(
                        title: const Text('Choose an Icon'),
                        content: SingleChildScrollView(
                          child: Wrap(
                            spacing: 10,
                            runSpacing: 10,
                            children: availableIcons.map((icon) {
                              return IconButton(
                                icon: Icon(icon, size: 30),
                                onPressed: () {
                                  setState(() {
                                    _selectedIcon = icon;
                                  });
                                  Navigator.of(context).pop();
                                },
                              );
                            }).toList(),
                          ),
                        ),
                      );
                    },
                  );
                },
                tileColor: Colors.teal.shade50,
                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
              ),
              const SizedBox(height: 32),
              ElevatedButton.icon(
                onPressed: _saveHobby,
                icon: const Icon(Icons.save_rounded),
                label: Text(widget.hobby == null ? 'Add Hobby' : 'Update Hobby'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.teal,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 16),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}