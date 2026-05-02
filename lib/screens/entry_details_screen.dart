import 'dart:io';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';
import '../data/models/form_entry.dart';
import '../data/models/form_schema.dart';
import '../services/form_loader.dart';
import '../widgets/image_gallery_widget.dart';
import '../services/image_storage_service.dart';

class EntryDetailsScreen extends StatelessWidget {
  final FormEntry entry;

  const EntryDetailsScreen({super.key, required this.entry});

  String _formatValue(dynamic value, {String? fieldType}) {
    if (value == null) return '-';
    if (value is DateTime) {
      return DateFormat('dd/MM/yyyy HH:mm').format(value);
    }
    if (value is bool) {
      return value ? 'Sim' : 'Não';
    }
    return value.toString();
  }

  @override
  Widget build(BuildContext context) {
    final loader = Provider.of<FormLoader>(context, listen: false);
    // Try find definition by key first; fallback by comparing titles
    FormDefinition? definition = loader.getForm(entry.formKey);
    definition ??= loader.formsList.firstWhere(
      (f) => f.titulo == entry.formKey,
      orElse: () => null as dynamic,
    );

    final formTitle = definition?.titulo ?? entry.formKey;

    return Scaffold(
      appBar: AppBar(
        title: Text(formTitle),
      ),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          Row(
            children: [
              const Icon(Icons.description_outlined),
              const SizedBox(width: 8),
              Text(
                'Detalhes do formulário',
                style: Theme.of(context).textTheme.titleMedium,
              ),
            ],
          ),
          const SizedBox(height: 12),
          Card(
            child: Padding(
              padding: const EdgeInsets.all(12),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    children: [
                      const Icon(Icons.event_note, size: 18),
                      const SizedBox(width: 8),
                      Text(DateFormat('dd/MM/yyyy HH:mm').format(entry.createdAt)),
                    ],
                  ),
                  const SizedBox(height: 8),
                  if (entry.location != null)
                    Row(
                      children: [
                        const Icon(Icons.location_on_outlined, size: 18),
                        const SizedBox(width: 8),
                        Text('Lat: ${entry.location!.latitude.toStringAsFixed(5)}, Lng: ${entry.location!.longitude.toStringAsFixed(5)}'),
                      ],
                    ),
                ],
              ),
            ),
          ),
          const SizedBox(height: 16),
          Text('Respostas', style: Theme.of(context).textTheme.titleMedium),
          const SizedBox(height: 8),
          ...entry.answers.entries.map((e) {
            final field = definition?.campos.firstWhere(
              (f) => f.id == e.key,
              orElse: () => null as dynamic,
            );
            final label = field?.rotulo ?? e.key;
            final fieldType = field?.tipo;
            
            // Check if this is an image field
            if (fieldType == 'imagem' || fieldType == 'galeria') {
              return _buildImageFieldCard(context, label, e.value);
            }
            
            final formatted = _formatValue(e.value, fieldType: fieldType);
            return Card(
              child: ListTile(
                title: Text(label),
                subtitle: Text(formatted),
              ),
            );
          }).toList(),
          const SizedBox(height: 24),
        ],
      ),
    );
  }

  Widget _buildImageFieldCard(BuildContext context, String label, dynamic value) {
    // Extract image paths - can be a single string or a list of strings
    List<String> imagePaths = [];
    
    if (value == null) {
      return Card(
        child: ListTile(
          title: Text(label),
          subtitle: const Text('-'),
        ),
      );
    }
    
    if (value is List) {
      // Multiple images
      imagePaths = value
          .where((item) => item != null && item.toString().isNotEmpty)
          .map((item) => item.toString())
          .toList();
    } else if (value is String && value.isNotEmpty) {
      // Single image
      imagePaths = [value];
    } else {
      // Invalid format
      return Card(
        child: ListTile(
          title: Text(label),
          subtitle: const Text('Formato de imagem inválido'),
        ),
      );
    }

    if (imagePaths.isEmpty) {
      return Card(
        child: ListTile(
          title: Text(label),
          subtitle: const Text('-'),
        ),
      );
    }

    // ImageGalleryWidget will handle path resolution
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(12),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                const Icon(Icons.image, size: 20),
                const SizedBox(width: 8),
                Text(
                  label,
                  style: Theme.of(context).textTheme.titleSmall,
                ),
                const Spacer(),
                if (imagePaths.length > 1)
                  Text(
                    '${imagePaths.length} imagens',
                    style: Theme.of(context).textTheme.bodySmall?.copyWith(
                          color: Colors.grey[600],
                        ),
                  ),
              ],
            ),
            const SizedBox(height: 12),
            ImageGalleryWidget(
              imagePaths: imagePaths,
              height: 200,
              width: 250,
            ),
          ],
        ),
      ),
    );
  }
}


