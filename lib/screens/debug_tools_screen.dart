import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:intl/intl.dart';
import '../data/models/user.dart';
import '../data/models/form_entry.dart';
import '../l10n/strings.dart';

class DebugToolsScreen extends StatefulWidget {
  const DebugToolsScreen({super.key});

  @override
  State<DebugToolsScreen> createState() => _DebugToolsScreenState();
}

class _DebugToolsScreenState extends State<DebugToolsScreen> {

  Future<void> _clearUsersBox() async {
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Confirmar Exclusão'),
        content: const Text('Tem certeza que deseja deletar TODOS os usuários?'),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(false),
            child: const Text(Strings.cancelar),
          ),
          TextButton(
            onPressed: () => Navigator.of(context).pop(true),
            style: TextButton.styleFrom(foregroundColor: Colors.red),
            child: const Text('Deletar'),
          ),
        ],
      ),
    );

    if (      confirmed == true) {
      await Hive.box<User>('users').clear();
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Usuários deletados')),
        );
      }
    }
  }

  Future<void> _clearEntriesBox() async {
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Confirmar Exclusão'),
        content: const Text('Tem certeza que deseja deletar TODAS as entradas?'),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(false),
            child: const Text(Strings.cancelar),
          ),
          TextButton(
            onPressed: () => Navigator.of(context).pop(true),
            style: TextButton.styleFrom(foregroundColor: Colors.red),
            child: const Text('Deletar'),
          ),
        ],
      ),
    );

    if (      confirmed == true) {
      await Hive.box<FormEntry>('entries').clear();
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Entradas deletadas')),
        );
      }
    }
  }

  List<Widget> _buildNestedEntriesList(Box<FormEntry> entriesBox, Box<User> usersBox) {
    // Find root entries (those without parentFormId) and build tree structure
    final allEntries = entriesBox.values.toList();
    final rootEntries = allEntries.where((e) => e.parentFormId == null || e.parentFormId!.isEmpty).toList();
    
    // Debug: Print relationships for debugging
    if (kDebugMode) {
      debugPrint('[DebugTools] Total entries: ${allEntries.length}');
      debugPrint('[DebugTools] Root entries: ${rootEntries.length}');
      for (final entry in allEntries) {
        debugPrint('[DebugTools] Entry ${entry.id} (${entry.formKey}) - parentFormId: ${entry.parentFormId}');
        if (entry.parentFormId != null && entry.parentFormId!.isNotEmpty) {
          final parentExists = allEntries.any((e) => e.id == entry.parentFormId);
          debugPrint('[DebugTools] Entry ${entry.id} (${entry.formKey}) has parent ${entry.parentFormId} - Parent exists: $parentExists');
        }
      }
    }
    
    // Sort by creation date (newest first)
    rootEntries.sort((a, b) => b.createdAt.compareTo(a.createdAt));
    
    final widgets = <Widget>[];
    final displayedEntryIds = <String>{};
    
    // Helper function to recursively build entry tree
    void addEntryWithChildren(FormEntry entry, int indentLevel) {
      if (displayedEntryIds.contains(entry.id)) return; // Avoid duplicates
      
      widgets.add(_buildEntryWidget(entry, entriesBox, usersBox, indentLevel));
      displayedEntryIds.add(entry.id);
      
      // Find and add child entries
      final children = allEntries
          .where((e) => e.parentFormId != null && 
                      e.parentFormId!.isNotEmpty &&
                      e.parentFormId == entry.id &&
                      !displayedEntryIds.contains(e.id))
          .toList();
      children.sort((a, b) => a.createdAt.compareTo(b.createdAt));
      
      for (final child in children) {
        addEntryWithChildren(child, indentLevel + 1);
      }
    }
    
    // Add root entries and their children (limit to first 10 roots for display)
    for (final rootEntry in rootEntries.take(10)) {
      addEntryWithChildren(rootEntry, 0);
    }
    
    // Handle entries that have a parentFormId but the parent doesn't exist (orphaned)
    final orphanedEntries = allEntries
        .where((e) => e.parentFormId != null && 
                     e.parentFormId!.isNotEmpty &&
                     !displayedEntryIds.contains(e.id) &&
                     !allEntries.any((parent) => parent.id == e.parentFormId))
        .toList();
    
    if (orphanedEntries.isNotEmpty) {
      widgets.add(const Padding(
        padding: EdgeInsets.all(8.0),
        child: Text(
          'Entradas órfãs (pai não encontrado):',
          style: TextStyle(fontWeight: FontWeight.bold, fontSize: 12),
        ),
      ));
      for (final orphan in orphanedEntries.take(5)) {
        if (!displayedEntryIds.contains(orphan.id)) {
          widgets.add(_buildEntryWidget(orphan, entriesBox, usersBox, 0));
          displayedEntryIds.add(orphan.id);
        }
      }
    }
    
    return widgets;
  }

  Widget _buildEntryWidget(FormEntry entry, Box<FormEntry> entriesBox, Box<User> usersBox, int indentLevel) {
    final hasLocation = entry.location != null;
    final isChild = indentLevel > 0;
    
    return Padding(
      padding: EdgeInsets.only(left: indentLevel * 24.0),
      child: Container(
        margin: const EdgeInsets.symmetric(vertical: 2),
        decoration: isChild
            ? BoxDecoration(
                border: Border(
                  left: BorderSide(color: Colors.blue.shade300, width: 2),
                ),
              )
            : null,
        child: ListTile(
          leading: isChild
              ? Icon(Icons.subdirectory_arrow_right, color: Colors.blue.shade300, size: 20)
              : null,
          title: Row(
            children: [
              Flexible(
                child: Text(
                  'Formulário: ${entry.formKey}',
                  overflow: TextOverflow.ellipsis,
                ),
              ),
              if (isChild) ...[
                const SizedBox(width: 8),
                Chip(
                  label: const Text('Filho', style: TextStyle(fontSize: 10)),
                  padding: EdgeInsets.zero,
                  visualDensity: VisualDensity.compact,
                  materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
                ),
              ],
              if (entry.parentFormId != null && entry.parentFormId!.isNotEmpty) ...[
                const SizedBox(width: 4),
                Tooltip(
                  message: 'Pai: ${entry.parentFormId}',
                  child: Icon(Icons.link, size: 16, color: Colors.grey.shade600),
                ),
              ],
            ],
          ),
          subtitle: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'ID: ${entry.id}\nData: ${DateFormat('dd/MM/yyyy HH:mm').format(entry.createdAt)}',
              ),
              if (entry.parentFormId != null && entry.parentFormId!.isNotEmpty)
                Text(
                  'Pai: ${entry.parentFormId}',
                  style: TextStyle(
                    fontSize: 11,
                    color: Colors.blue.shade700,
                    fontStyle: FontStyle.italic,
                  ),
                ),
              if (hasLocation)
                Text(
                  'Localização: ${entry.location!.latitude.toStringAsFixed(4)}, ${entry.location!.longitude.toStringAsFixed(4)}',
                  style: const TextStyle(
                    fontSize: 12,
                    color: Colors.grey,
                  ),
                ),
              const SizedBox(height: 4),
              // Associação com usuário criador
              Builder(
                builder: (context) {
                  if (entry.userId == null || entry.userId!.isEmpty) {
                    return const Text(
                      'Usuário: N/A',
                      style: TextStyle(fontSize: 12, color: Colors.black54),
                    );
                  }

                  final user = usersBox.get(entry.userId);
                  
                  if (user == null) {
                    try {
                      final foundUser = usersBox.values.firstWhere(
                        (u) => u.id == entry.userId,
                      );
                      return Text(
                        'Usuário: ${foundUser.email} (id: ${foundUser.id})',
                        style: const TextStyle(fontSize: 12, color: Colors.black54),
                      );
                    } catch (e) {
                      return Text(
                        'Usuário: Não encontrado (id: ${entry.userId})',
                        style: const TextStyle(fontSize: 12, color: Colors.orange),
                      );
                    }
                  }
                  return Text(
                    'Usuário: ${user.email} (id: ${user.id})',
                    style: const TextStyle(fontSize: 12, color: Colors.black54),
                  );
                },
              ),
              const SizedBox(height: 2),
              Text(
                'Respostas: ${entry.answers.length} campo(s)',
                style: const TextStyle(
                  fontSize: 12,
                  fontStyle: FontStyle.italic,
                ),
              ),
            ],
          ),
          isThreeLine: true,
          trailing: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              IconButton(
                icon: const Icon(Icons.delete, color: Colors.red),
                onPressed: () async {
                  final confirmed = await showDialog<bool>(
                    context: context,
                    builder: (context) => AlertDialog(
                      title: const Text('Confirmar Exclusão'),
                      content: Text('Deletar entrada do formulário ${entry.formKey}?'),
                      actions: [
                        TextButton(
                          onPressed: () => Navigator.of(context).pop(false),
                          child: const Text(Strings.cancelar),
                        ),
                        TextButton(
                          onPressed: () => Navigator.of(context).pop(true),
                          style: TextButton.styleFrom(foregroundColor: Colors.red),
                          child: const Text('Deletar'),
                        ),
                      ],
                    ),
                  );
                  if (confirmed == true && mounted) {
                    await entriesBox.delete(entry.id);
                    if (mounted) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(content: Text('Entrada deletada')),
                      );
                    }
                  }
                },
              ),
              IconButton(
                icon: const Icon(Icons.visibility),
                onPressed: () {
                  final hasLocation = entry.location != null;
                  showDialog(
                    context: context,
                    builder: (context) => AlertDialog(
                      title: Text('Entrada: ${entry.formKey}'),
                      content: SingleChildScrollView(
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          mainAxisSize: MainAxisSize.min,
                          children: [
                            Text(
                              'ID: ${entry.id}\nData: ${DateFormat('dd/MM/yyyy HH:mm:ss').format(entry.createdAt)}',
                            ),
                            if (entry.parentFormId != null && entry.parentFormId!.isNotEmpty) ...[
                              const SizedBox(height: 8),
                              Text(
                                'Formulário Pai ID: ${entry.parentFormId}',
                                style: TextStyle(
                                  fontWeight: FontWeight.bold,
                                  color: Colors.blue.shade700,
                                ),
                              ),
                            ],
                            if (hasLocation) ...[
                              const SizedBox(height: 8),
                              Text(
                                'Localização:\nLat: ${entry.location!.latitude}\nLng: ${entry.location!.longitude}\nPrecisão: ${entry.location!.accuracy != null ? entry.location!.accuracy!.toStringAsFixed(2) : 'N/A'}m',
                              ),
                            ],
                            const SizedBox(height: 16),
                            const Text(
                              'Respostas:',
                              style: TextStyle(
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            ...entry.answers.entries.map((e) {
                              String formatValue(dynamic v) {
                                if (v is DateTime) {
                                  return DateFormat('dd/MM/yyyy').format(v);
                                }
                                if (v is String) {
                                  try {
                                    final parsed = DateTime.tryParse(v);
                                    if (parsed != null) {
                                      return DateFormat('dd/MM/yyyy').format(parsed);
                                    }
                                  } catch (_) {}
                                  return v;
                                }
                                return '$v';
                              }

                              final valueText = formatValue(e.value);
                              return Padding(
                                padding: const EdgeInsets.only(top: 4),
                                child: Text(
                                  '${e.key}: $valueText',
                                  style: const TextStyle(
                                    fontSize: 12,
                                    fontFamily: 'monospace',
                                  ),
                                ),
                              );
                            }),
                          ],
                        ),
                      ),
                      actions: [
                        TextButton(
                          onPressed: () => Navigator.of(context).pop(),
                          child: const Text(Strings.ok),
                        ),
                      ],
                    ),
                  );
                },
              ),
            ],
          ),
        ),
      ),
    );
  }

  Future<void> _clearAllBoxes() async {
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Confirmar Exclusão Total'),
        content: const Text(
          'Tem certeza que deseja deletar TODOS os dados (usuários e entradas)?\n\nEsta ação não pode ser desfeita.',
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(false),
            child: const Text(Strings.cancelar),
          ),
          TextButton(
            onPressed: () => Navigator.of(context).pop(true),
            style: TextButton.styleFrom(foregroundColor: Colors.red),
            child: const Text('Deletar Tudo'),
          ),
        ],
      ),
    );

    if (      confirmed == true) {
      await Hive.box<User>('users').clear();
      await Hive.box<FormEntry>('entries').clear();
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Todos os dados foram deletados')),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    if (!kDebugMode) {
      return Scaffold(
        appBar: AppBar(title: const Text('Debug Tools')),
        body: const Center(
          child: Text('Esta ferramenta só está disponível em modo debug'),
        ),
      );
    }

    return Scaffold(
      appBar: AppBar(
        title: const Text('Debug Tools - Hive'),
        backgroundColor: Colors.orange.shade700,
      ),
      body: ValueListenableBuilder<Box<User>>(
        valueListenable: Hive.box<User>('users').listenable(),
        builder: (context, usersBox, _) {
          return ValueListenableBuilder<Box<FormEntry>>(
            valueListenable: Hive.box<FormEntry>('entries').listenable(),
            builder: (context, entriesBox, _) {
              final usersCount = usersBox.length;
              final entriesCount = entriesBox.length;
              
              return ListView(
                padding: const EdgeInsets.all(16),
                children: [
                  Card(
                    color: Colors.red.shade50,
                    child: Padding(
                      padding: const EdgeInsets.all(16),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Row(
                            children: [
                              Icon(Icons.warning, color: Colors.red.shade700),
                              const SizedBox(width: 8),
                              Text(
                                'Modo Debug',
                                style: TextStyle(
                                  fontSize: 18,
                                  fontWeight: FontWeight.bold,
                                  color: Colors.red.shade700,
                                ),
                              ),
                            ],
                          ),
                          const SizedBox(height: 8),
                          const Text(
                            'Esta ferramenta é apenas para desenvolvimento. Use com cuidado!',
                            style: TextStyle(fontSize: 12),
                          ),
                        ],
                      ),
                    ),
                  ),
                  const SizedBox(height: 24),
                  // Box de Usuários
                  Card(
                    child: ExpansionTile(
                      leading: const Icon(Icons.people),
                      title: const Text('Box: users'),
                      subtitle: Text('$usersCount usuário(s)'),
                      children: [
                        if (usersBox.isEmpty)
                          const Padding(
                            padding: EdgeInsets.all(16),
                            child: Text('Nenhum usuário cadastrado'),
                          )
                        else
                          ...usersBox.values.map((user) => ListTile(
                                title: Text(user.email),
                                subtitle: Text(
                                  'ID: ${user.id}\nCriado em: ${DateFormat('dd/MM/yyyy HH:mm').format(user.createdAt)}',
                                ),
                                isThreeLine: true,
                                trailing: IconButton(
                                  icon: const Icon(Icons.delete, color: Colors.red),
                                  onPressed: () async {
                                    // Impedir exclusão se houver entradas associadas
                                    final hasAssociatedEntries = entriesBox.values.any((e) => e.userId == user.id);
                                    if (hasAssociatedEntries) {
                                      await showDialog<void>(
                                        context: context,
                                        builder: (context) => AlertDialog(
                                          title: const Text('Não é possível excluir'),
                                          content: const Text(
                                            'Para deletar um usuário, primeiro delete todas as entradas (exames) associadas a ele.',
                                          ),
                                          actions: [
                                            TextButton(
                                              onPressed: () => Navigator.of(context).pop(),
                                              child: const Text(Strings.ok),
                                            ),
                                          ],
                                        ),
                                      );
                                      return;
                                    }

                                    final confirmed = await showDialog<bool>(
                                      context: context,
                                      builder: (context) => AlertDialog(
                                        title: const Text('Confirmar Exclusão'),
                                        content: Text('Deletar usuário ${user.email}?'),
                                        actions: [
                                          TextButton(
                                            onPressed: () => Navigator.of(context).pop(false),
                                            child: const Text(Strings.cancelar),
                                          ),
                                          TextButton(
                                            onPressed: () => Navigator.of(context).pop(true),
                                            style: TextButton.styleFrom(foregroundColor: Colors.red),
                                            child: const Text('Deletar'),
                                          ),
                                        ],
                                      ),
                                    );
                                    if (confirmed == true && mounted) {
                                      await usersBox.delete(user.id);
                                      ScaffoldMessenger.of(context).showSnackBar(
                                        SnackBar(content: Text('Usuário ${user.email} deletado')),
                                      );
                                    }
                                  },
                                ),
                              )),
                        const Divider(),
                        Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: SizedBox(
                            width: double.infinity,
                            child: OutlinedButton.icon(
                              onPressed: _clearUsersBox,
                              icon: const Icon(Icons.delete, color: Colors.red),
                              label: const Text('Deletar Todos os Usuários'),
                              style: OutlinedButton.styleFrom(
                                foregroundColor: Colors.red,
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(height: 16),
                  // Box de Entradas
                  Card(
                    child: ExpansionTile(
                      leading: const Icon(Icons.description),
                      title: const Text('Box: entries'),
                      subtitle: Text('$entriesCount entrada(s)'),
                      children: [
                        if (entriesBox.isEmpty)
                          const Padding(
                            padding: EdgeInsets.all(16),
                            child: Text('Nenhuma entrada cadastrada'),
                          )
                        else
                          ..._buildNestedEntriesList(entriesBox, usersBox),
                        if (entriesBox.length > 10)
                          Padding(
                            padding: const EdgeInsets.all(16),
                            child: Text(
                              'Mostrando 10 de ${entriesBox.length} entradas',
                              style: const TextStyle(
                                fontStyle: FontStyle.italic,
                                color: Colors.grey,
                              ),
                            ),
                          ),
                        const Divider(),
                        Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: SizedBox(
                            width: double.infinity,
                            child: OutlinedButton.icon(
                              onPressed: _clearEntriesBox,
                              icon: const Icon(Icons.delete, color: Colors.red),
                              label: const Text('Deletar Todas as Entradas'),
                              style: OutlinedButton.styleFrom(
                                foregroundColor: Colors.red,
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(height: 24),
                  // Botão para deletar tudo
                  Card(
                    color: Colors.red.shade50,
                    child: Padding(
                      padding: const EdgeInsets.all(16),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          const Text(
                            'Ações Destrutivas',
                            style: TextStyle(
                              fontSize: 16,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          const SizedBox(height: 16),
                          SizedBox(
                            width: double.infinity,
                            child: ElevatedButton.icon(
                              onPressed: _clearAllBoxes,
                              icon: const Icon(Icons.delete_forever),
                              label: const Text('Deletar TODOS os Dados'),
                              style: ElevatedButton.styleFrom(
                                backgroundColor: Colors.red,
                                foregroundColor: Colors.white,
                                padding: const EdgeInsets.symmetric(vertical: 16),
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                  const SizedBox(height: 24),
                  // Informações do Hive
                  Card(
                    child: Padding(
                      padding: const EdgeInsets.all(16),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          const Text(
                            'Informações do Hive',
                            style: TextStyle(
                              fontSize: 16,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          const SizedBox(height: 8),
                          Text('Users (itens): $usersCount'),
                          Text('Entries (itens): $entriesCount'),
                          Text('Encrypted: Sim (AES-256)'),
                        ],
                      ),
                    ),
                  ),
                ],
              );
            },
          );
        },
      ),
    );
  }
}

