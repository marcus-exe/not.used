import 'package:geolocator/geolocator.dart';
import '../data/models/geo_point.dart';
import '../l10n/strings.dart';

class LocationService {
  Future<bool> checkPermission() async {
    bool serviceEnabled = await Geolocator.isLocationServiceEnabled();
    if (!serviceEnabled) {
      return false;
    }

    LocationPermission permission = await Geolocator.checkPermission();
    if (permission == LocationPermission.denied) {
      permission = await Geolocator.requestPermission();
      if (permission == LocationPermission.denied) {
        return false;
      }
    }

    if (permission == LocationPermission.deniedForever) {
      return false;
    }

    return true;
  }

  Future<GeoPoint?> getCurrentLocation() async {
    try {
      // Verificar se o serviço de localização está habilitado
      bool serviceEnabled = await Geolocator.isLocationServiceEnabled();
      if (!serviceEnabled) {
        return null;
      }

      // Verificar permissão
      LocationPermission permission = await Geolocator.checkPermission();
      
      if (permission == LocationPermission.denied) {
        // Pedir permissão
        permission = await Geolocator.requestPermission();
        if (permission == LocationPermission.denied) {
          return null;
        }
      }

      if (permission == LocationPermission.deniedForever) {
        return null;
      }

      // Tentar obter a localização com timeout maior e precisão mais baixa como fallback
      Position? position;
      
      try {
        position = await Geolocator.getCurrentPosition(
          desiredAccuracy: LocationAccuracy.high,
          timeLimit: const Duration(seconds: 15),
        );
      } catch (e) {
        // Se falhar com precisão alta, tenta com precisão mais baixa
        try {
          position = await Geolocator.getCurrentPosition(
            desiredAccuracy: LocationAccuracy.medium,
            timeLimit: const Duration(seconds: 10),
          );
        } catch (e2) {
          // Última tentativa com precisão baixa
          position = await Geolocator.getLastKnownPosition();
          if (position == null) {
            return null;
          }
        }
      }

      if (position == null) {
        return null;
      }

      return GeoPoint(
        latitude: position.latitude,
        longitude: position.longitude,
        accuracy: position.accuracy,
      );
    } catch (e) {
      // Se tudo falhar, tenta última localização conhecida
      try {
        final lastPosition = await Geolocator.getLastKnownPosition();
        if (lastPosition != null) {
          return GeoPoint(
            latitude: lastPosition.latitude,
            longitude: lastPosition.longitude,
            accuracy: lastPosition.accuracy,
          );
        }
      } catch (e2) {
        // Ignora erro da última tentativa
      }
      return null;
    }
  }

  Future<void> openLocationSettings() async {
    await Geolocator.openLocationSettings();
  }
}

