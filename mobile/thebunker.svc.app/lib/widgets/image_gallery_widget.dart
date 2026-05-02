import 'dart:io';
import 'package:flutter/material.dart';
import '../services/image_storage_service.dart';

class ImageGalleryWidget extends StatelessWidget {
  final List<String> imagePaths;
  final double? height;
  final double? width;

  const ImageGalleryWidget({
    super.key,
    required this.imagePaths,
    this.height,
    this.width,
  });

  @override
  Widget build(BuildContext context) {
    if (imagePaths.isEmpty) {
      return const SizedBox.shrink();
    }

    return SizedBox(
      height: height ?? 200,
      child: ListView.builder(
        scrollDirection: Axis.horizontal,
        itemCount: imagePaths.length,
        itemBuilder: (context, index) {
          final imagePath = imagePaths[index];
          return _buildImageItem(context, imagePath, index);
        },
      ),
    );
  }

  Widget _buildImageItem(BuildContext context, String imagePath, int index) {
    final isNetworkImage = imagePath.startsWith('http://') || 
                           imagePath.startsWith('https://');
    
    return Container(
      margin: const EdgeInsets.only(right: 8),
      child: GestureDetector(
        onTap: () => _showFullScreenImage(context, imagePath, index),
        child: ClipRRect(
          borderRadius: BorderRadius.circular(8),
          child: Container(
            decoration: BoxDecoration(
              border: Border.all(color: Colors.grey.shade300),
              borderRadius: BorderRadius.circular(8),
            ),
            width: width ?? 200,
            child: Stack(
              fit: StackFit.expand,
              children: [
                isNetworkImage
                    ? Image.network(
                        imagePath,
                        fit: BoxFit.cover,
                        loadingBuilder: (context, child, loadingProgress) {
                          if (loadingProgress == null) return child;
                          return Center(
                            child: CircularProgressIndicator(
                              value: loadingProgress.expectedTotalBytes != null
                                  ? loadingProgress.cumulativeBytesLoaded /
                                      loadingProgress.expectedTotalBytes!
                                  : null,
                            ),
                          );
                        },
                        errorBuilder: (context, error, stackTrace) {
                          return _buildErrorWidget();
                        },
                      )
                    : FutureBuilder<String>(
                        future: ImageStorageService().resolveImagePath(imagePath),
                        builder: (context, snapshot) {
                          if (!snapshot.hasData) {
                            return const Center(
                              child: CircularProgressIndicator(strokeWidth: 2),
                            );
                          }
                          final resolvedPath = snapshot.data!;
                          final file = File(resolvedPath);
                          if (!file.existsSync()) {
                            print('[ImageGallery] Image file does not exist: $resolvedPath (from $imagePath)');
                            return _buildErrorWidget();
                          }
                          print('[ImageGallery] Loading image from: $resolvedPath');
                          return Image.file(
                            file,
                            fit: BoxFit.cover,
                            errorBuilder: (context, error, stackTrace) {
                              print('[ImageGallery] Error loading image $resolvedPath: $error');
                              return _buildErrorWidget();
                            },
                          );
                        },
                      ),
                // Image counter badge
                if (imagePaths.length > 1)
                  Positioned(
                    top: 8,
                    right: 8,
                    child: Container(
                      padding: const EdgeInsets.symmetric(
                        horizontal: 8,
                        vertical: 4,
                      ),
                      decoration: BoxDecoration(
                        color: Colors.black54,
                        borderRadius: BorderRadius.circular(12),
                      ),
                      child: Text(
                        '${index + 1}/${imagePaths.length}',
                        style: const TextStyle(
                          color: Colors.white,
                          fontSize: 12,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                  ),
              ],
            ),
          ),
        ),
      ),
    );
  }


  Widget _buildErrorWidget() {
    return Container(
      color: Colors.grey.shade200,
      child: const Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(Icons.broken_image, size: 48, color: Colors.grey),
          SizedBox(height: 8),
          Text(
            'Imagem não encontrada',
            style: TextStyle(color: Colors.grey, fontSize: 12),
          ),
        ],
      ),
    );
  }

  void _showFullScreenImage(BuildContext context, String imagePath, int index) {
    final isNetworkImage = imagePath.startsWith('http://') || 
                           imagePath.startsWith('https://');
    
    Navigator.of(context).push(
      MaterialPageRoute(
        builder: (context) => _FullScreenImagePage(
          imagePath: imagePath,
          isNetworkImage: isNetworkImage,
          currentIndex: index,
          allImages: imagePaths,
        ),
      ),
    );
  }
}

class _FullScreenImagePage extends StatefulWidget {
  final String imagePath;
  final bool isNetworkImage;
  final int currentIndex;
  final List<String> allImages;

  const _FullScreenImagePage({
    required this.imagePath,
    required this.isNetworkImage,
    required this.currentIndex,
    required this.allImages,
  });

  @override
  State<_FullScreenImagePage> createState() => _FullScreenImagePageState();
}

class _FullScreenImagePageState extends State<_FullScreenImagePage> {
  late PageController _pageController;
  late int _currentIndex;

  @override
  void initState() {
    super.initState();
    _currentIndex = widget.currentIndex;
    _pageController = PageController(initialPage: widget.currentIndex);
  }

  @override
  void dispose() {
    _pageController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.black,
      appBar: AppBar(
        backgroundColor: Colors.black,
        iconTheme: const IconThemeData(color: Colors.white),
        title: widget.allImages.length > 1
            ? Text(
                '${_currentIndex + 1} de ${widget.allImages.length}',
                style: const TextStyle(color: Colors.white),
              )
            : null,
      ),
      body: widget.allImages.length > 1
          ? PageView.builder(
              controller: _pageController,
              onPageChanged: (index) {
                setState(() {
                  _currentIndex = index;
                });
              },
              itemCount: widget.allImages.length,
              itemBuilder: (context, index) {
                final imagePath = widget.allImages[index];
                final isNetwork = imagePath.startsWith('http://') || 
                                 imagePath.startsWith('https://');
                return _buildFullScreenImage(imagePath, isNetwork);
              },
            )
          : _buildFullScreenImage(widget.imagePath, widget.isNetworkImage),
    );
  }

  Widget _buildFullScreenImage(String imagePath, bool isNetwork) {
    return Center(
      child: InteractiveViewer(
        minScale: 0.5,
        maxScale: 3.0,
        child: isNetwork
            ? Image.network(
                imagePath,
                fit: BoxFit.contain,
                loadingBuilder: (context, child, loadingProgress) {
                  if (loadingProgress == null) return child;
                  return const Center(
                    child: CircularProgressIndicator(color: Colors.white),
                  );
                },
                errorBuilder: (context, error, stackTrace) {
                  return const Center(
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Icon(Icons.broken_image, size: 64, color: Colors.white54),
                        SizedBox(height: 16),
                        Text(
                          'Erro ao carregar imagem',
                          style: TextStyle(color: Colors.white54),
                        ),
                      ],
                    ),
                  );
                },
              )
              : FutureBuilder<String>(
                  future: ImageStorageService().resolveImagePath(imagePath),
                  builder: (context, snapshot) {
                    if (!snapshot.hasData) {
                      return const Center(
                        child: CircularProgressIndicator(color: Colors.white),
                      );
                    }
                    final resolvedPath = snapshot.data!;
                    return _buildFullScreenLocalImage(resolvedPath);
                  },
                ),
      ),
    );
  }

  Widget _buildFullScreenLocalImage(String imagePath) {
    final file = File(imagePath);
    final exists = file.existsSync();
    
    if (!exists) {
      print('[ImageGallery] Full-screen image file does not exist: $imagePath');
      return const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.broken_image, size: 64, color: Colors.white54),
            SizedBox(height: 16),
            Text(
              'Imagem não encontrada',
              style: TextStyle(color: Colors.white54),
            ),
          ],
        ),
      );
    }

    print('[ImageGallery] Loading full-screen image from: $imagePath');
    return Image.file(
      file,
      fit: BoxFit.contain,
      errorBuilder: (context, error, stackTrace) {
        print('[ImageGallery] Error loading full-screen image $imagePath: $error');
        return const Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Icon(Icons.broken_image, size: 64, color: Colors.white54),
              SizedBox(height: 16),
              Text(
                'Erro ao carregar imagem',
                style: TextStyle(color: Colors.white54),
              ),
            ],
          ),
        );
      },
    );
  }
}

