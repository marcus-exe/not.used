import 'package:flutter/material.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:provider/provider.dart';
import 'package:intl/intl.dart';
import '../data/models/form_entry.dart';
import '../services/form_loader.dart';
import '../services/auth_service.dart';
import 'form_screen.dart';
import 'entry_details_screen.dart';
import 'login_screen.dart';
import '../l10n/strings.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  String? _filterFormKey;
  final Set<String> _expandedEntries = {};

  void _showDefaultFormPicker(BuildContext context) {
    final formLoader = Provider.of<FormLoader>(context, listen: false);
    final forms = formLoader.formsList.where((f) => !f.oculto).toList();

    if (forms.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Nenhum formulário disponível')),
      );
      return;
    }

    showModalBottomSheet(
      context: context,
      builder: (context) => Container(
        padding: const EdgeInsets.all(16),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              '${Strings.escolherFormulario} (padrão)',
              style: Theme.of(context).textTheme.titleLarge,
            ),
            const SizedBox(height: 16),
            ...forms.map((form) => ListTile(
                  title: Text(form.titulo),
                  subtitle: form.descricao != null ? Text(form.descricao!) : null,
                  trailing: Consumer<FormLoader>(
                    builder: (context, loader, _) => loader.selectedFormKey == form.key
                        ? const Icon(Icons.check, color: Colors.green)
                        : const SizedBox.shrink(),
                  ),
                  onTap: () {
                    formLoader.setSelectedForm(form.key);
                    Navigator.of(context).pop();
                  },
                )),
          ],
        ),
      ),
    );
  }

  void _showFormSelectionDialog(BuildContext context) {
    final formLoader = Provider.of<FormLoader>(context, listen: false);
    final forms = formLoader.formsList.where((f) => !f.oculto).toList();

    if (forms.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Nenhum formulário disponível')),
      );
      return;
    }

    if (forms.length == 1) {
      Navigator.of(context).push(
        MaterialPageRoute(
          builder: (_) => FormScreen(formDefinition: forms.first),
        ),
      );
      return;
    }

    showModalBottomSheet(
      context: context,
      builder: (context) => Container(
        padding: const EdgeInsets.all(16),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              Strings.escolherFormulario,
              style: Theme.of(context).textTheme.titleLarge,
            ),
            const SizedBox(height: 16),
            ...forms.map((form) => ListTile(
                  title: Text(form.titulo),
                  subtitle: form.descricao != null ? Text(form.descricao!) : null,
                  onTap: () {
                    Navigator.of(context).pop();
                    Navigator.of(context).push(
                      MaterialPageRoute(
                        builder: (_) => FormScreen(formDefinition: form),
                      ),
                    );
                  },
                )),
          ],
        ),
      ),
    );
  }

  void _showFilterDialog(BuildContext context) {
    final formLoader = Provider.of<FormLoader>(context, listen: false);
    final forms = formLoader.formsList.where((f) => !f.oculto).toList();

    showModalBottomSheet(
      context: context,
      builder: (context) => Container(
        padding: const EdgeInsets.all(16),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              'Filtrar por formulário',
              style: Theme.of(context).textTheme.titleLarge,
            ),
            const SizedBox(height: 16),
            ListTile(
              title: const Text('Todos'),
              trailing: _filterFormKey == null
                  ? const Icon(Icons.check, color: Colors.green)
                  : null,
              onTap: () {
                setState(() {
                  _filterFormKey = null;
                });
                Navigator.of(context).pop();
              },
            ),
            ...forms.map((form) => ListTile(
                  title: Text(form.titulo),
                  subtitle: form.descricao != null ? Text(form.descricao!) : null,
                  trailing: _filterFormKey == form.key
                      ? const Icon(Icons.check, color: Colors.green)
                      : null,
                  onTap: () {
                    setState(() {
                      _filterFormKey = form.key;
                    });
                    Navigator.of(context).pop();
                  },
                )),
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: const Icon(Icons.menu),
          onPressed: () => _showDefaultFormPicker(context),
          tooltip: '${Strings.escolherFormulario} (padrão)',
        ),
        title: const Text(Strings.homeTitle),
        actions: [
          IconButton(
            icon: const Icon(Icons.filter_list),
            onPressed: () => _showFilterDialog(context),
            tooltip: 'Filtrar',
          ),
          IconButton(
            icon: const Icon(Icons.logout),
            onPressed: () {
              final authService = Provider.of<AuthService>(context, listen: false);
              authService.logout();
              // Substituir toda a pilha de navegação pela tela de login
              Navigator.of(context).pushAndRemoveUntil(
                MaterialPageRoute(builder: (_) => const LoginScreen()),
                (route) => false,
              );
            },
            tooltip: 'Sair',
          ),
        ],
      ),
      body: ValueListenableBuilder<Box<FormEntry>>(
        valueListenable: Hive.box<FormEntry>('entries').listenable(),
        builder: (context, box, _) {
          final formLoader = Provider.of<FormLoader>(context, listen: false);
          final currentUserId = Provider.of<AuthService>(context, listen: false).currentUser?.id;
          String? mapEntryFormKeyToCanonical(String formKey) {
            // If stored key matches a known key, keep it
            if (formLoader.getForm(formKey) != null) return formKey;
            // Try match by title to support legacy entries saved with titles
            final matches = formLoader.formsList.where((f) => f.titulo == formKey);
            if (matches.isNotEmpty) {
              return matches.first.key;
            }
            return formKey;
          }

          final allEntries = box.values
              .where((e) => e.userId == currentUserId || e.userId == null)
              .toList();

          // If filtering by form, include both matching entries AND their children
          List<FormEntry> filteredEntries;
          if (_filterFormKey != null) {
            final canonicalFilterKey = mapEntryFormKeyToCanonical(_filterFormKey!);
            // Find entries matching the filter
            final matchingEntries = allEntries
                .where((e) {
                  final canonical = mapEntryFormKeyToCanonical(e.formKey);
                  return canonical == canonicalFilterKey;
                })
                .toList();
            
            // Find all child entries that have a parent in the matching entries
            final matchingEntryIds = matchingEntries.map((e) => e.id).toSet();
            final childEntries = allEntries
                .where((e) => 
                    e.parentFormId != null && 
                    e.parentFormId!.isNotEmpty &&
                    matchingEntryIds.contains(e.parentFormId))
                .toList();
            
            // Combine matching entries with their children
            filteredEntries = [...matchingEntries, ...childEntries];
          } else {
            filteredEntries = allEntries;
          }

          // Only show root entries (entries without parentFormId) from the filtered set
          final rootEntries = filteredEntries
              .where((e) => e.parentFormId == null || e.parentFormId!.isEmpty)
              .toList()
            ..sort((a, b) => b.createdAt.compareTo(a.createdAt));

          if (rootEntries.isEmpty && allEntries.isEmpty) {
            return Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Icon(Icons.inbox_outlined, size: 64, color: Colors.grey),
                  const SizedBox(height: 16),
                  Text(
                    Strings.semEntradas,
                    style: Theme.of(context).textTheme.titleMedium?.copyWith(
                          color: Colors.grey,
                        ),
                  ),
                ],
              ),
            );
          }

          // Helper function to build entry card with nested children
          Widget buildEntryCard(FormEntry entry, int indentLevel) {
            final isChild = indentLevel > 0;
            // Get children from the filtered entries
            final children = filteredEntries
                .where((e) => e.parentFormId == entry.id)
                .toList()
              ..sort((a, b) => a.createdAt.compareTo(b.createdAt));

            final hasChildren = children.isNotEmpty;
            final isExpanded = _expandedEntries.contains(entry.id);

            Widget entryTile = ListTile(
              leading: isChild
                  ? Icon(Icons.subdirectory_arrow_right, color: Colors.blue.shade300, size: 18)
                  : null,
              title: Row(
                children: [
                  Expanded(
                    child: Consumer<FormLoader>(
                      builder: (context, loader, _) {
                        final byKey = loader.getForm(entry.formKey)?.titulo;
                        String formTitle;
                        if (byKey != null) {
                          formTitle = byKey;
                        } else {
                          // Try by title (legacy)
                          final byTitle = loader.formsList.firstWhere(
                            (f) => f.titulo == entry.formKey,
                            orElse: () => null as dynamic,
                          );
                          formTitle = byTitle?.titulo ?? entry.formKey;
                        }
                        return Text(
                          formTitle,
                          overflow: TextOverflow.ellipsis,
                        );
                      },
                    ),
                  ),
                  if (isChild)
                    Chip(
                      label: const Text('Filho', style: TextStyle(fontSize: 9)),
                      padding: EdgeInsets.zero,
                      visualDensity: VisualDensity.compact,
                      materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
                    ),
                  if (hasChildren && !isChild)
                    Padding(
                      padding: const EdgeInsets.only(left: 8),
                      child: Chip(
                        label: Text('${children.length}', style: const TextStyle(fontSize: 9)),
                        padding: EdgeInsets.zero,
                        visualDensity: VisualDensity.compact,
                        materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
                        backgroundColor: Colors.blue.shade50,
                      ),
                    ),
                ],
              ),
              subtitle: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    DateFormat('dd/MM/yyyy HH:mm').format(entry.createdAt),
                    style: const TextStyle(fontSize: 12),
                  ),
                  if (entry.location != null)
                    Text(
                      'Lat: ${entry.location!.latitude.toStringAsFixed(4)}, Lng: ${entry.location!.longitude.toStringAsFixed(4)}',
                      style: const TextStyle(fontSize: 12, color: Colors.grey),
                    ),
                ],
              ),
              trailing: IconButton(
                icon: const Icon(Icons.chevron_right),
                onPressed: () {
                  Navigator.of(context).push(
                    MaterialPageRoute(
                      builder: (_) => EntryDetailsScreen(entry: entry),
                    ),
                  );
                },
              ),
              onTap: hasChildren && !isChild
                  ? () {
                      // Toggle expansion
                      setState(() {
                        if (isExpanded) {
                          _expandedEntries.remove(entry.id);
                        } else {
                          _expandedEntries.add(entry.id);
                        }
                      });
                    }
                  : () {
                      // Navigate to details for entries without children
                      Navigator.of(context).push(
                        MaterialPageRoute(
                          builder: (_) => EntryDetailsScreen(entry: entry),
                        ),
                      );
                    },
            );

            return Column(
              children: [
                Padding(
                  padding: EdgeInsets.only(left: indentLevel * 16.0),
                  child: Card(
                    margin: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
                    child: Container(
                      decoration: isChild
                          ? BoxDecoration(
                              border: Border(
                                left: BorderSide(color: Colors.blue.shade300, width: 3),
                              ),
                            )
                          : null,
                      child: entryTile,
                    ),
                  ),
                ),
                // Add child entries recursively (only if expanded)
                if (hasChildren && isExpanded)
                  ...children.map((child) => buildEntryCard(child, indentLevel + 1)),
              ],
            );
          }

          return Column(
            children: [
              if (_filterFormKey != null)
                Container(
                  width: double.infinity,
                  padding: const EdgeInsets.fromLTRB(16, 12, 8, 12),
                  child: Row(
                    children: [
                      Expanded(
                        child: Consumer<FormLoader>(
                          builder: (context, loader, _) {
                            final label = loader.getForm(_filterFormKey!)?.titulo ?? _filterFormKey!;
                            return Text('Filtrado por: $label');
                          },
                        ),
                      ),
                      TextButton.icon(
                        onPressed: () => setState(() => _filterFormKey = null),
                        icon: const Icon(Icons.clear),
                        label: const Text('Limpar'),
                      ),
                    ],
                  ),
                ),
              Expanded(
                child: ListView(
                  padding: const EdgeInsets.all(8),
                  children: rootEntries.map((entry) => buildEntryCard(entry, 0)).toList(),
                ),
              ),
            ],
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          final loader = Provider.of<FormLoader>(context, listen: false);
          final selected = loader.selectedForm;
          // Only use selected form if it's not hidden
          if (selected != null && !selected.oculto) {
            Navigator.of(context).push(
              MaterialPageRoute(
                builder: (_) => FormScreen(formDefinition: selected),
              ),
            );
          } else {
            _showFormSelectionDialog(context);
          }
        },
        child: const Icon(Icons.add),
      ),
    );
  }
}
