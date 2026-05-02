/// Utilities for working with hexadecimal data
class HexUtils {
  /// Convert a hex string to a list of bytes
  /// Example: "0A1B2C" -> [10, 27, 44]
  static List<int> hexStringToBytes(String hex) {
    // Remove any spaces or special characters
    hex = hex.replaceAll(RegExp(r'[^0-9A-Fa-f]'), '');
    
    if (hex.length % 2 != 0) {
      throw ArgumentError('Hex string must have an even number of characters');
    }
    
    List<int> bytes = [];
    for (int i = 0; i < hex.length; i += 2) {
      String byteHex = hex.substring(i, i + 2);
      bytes.add(int.parse(byteHex, radix: 16));
    }
    
    return bytes;
  }
  
  /// Convert a list of bytes to a hex string
  /// Example: [10, 27, 44] -> "0A1B2C"
  static String bytesToHexString(List<int> bytes) {
    return bytes.map((b) => b.toRadixString(16).toUpperCase().padLeft(2, '0')).join('');
  }
  
  /// Validate if a string is valid hexadecimal
  static bool isValidHex(String hex) {
    hex = hex.replaceAll(RegExp(r'[^0-9A-Fa-f]'), '');
    return hex.isNotEmpty && hex.length % 2 == 0;
  }
  
  /// Format bytes for display with spaces between each byte
  /// Example: [10, 27, 44] -> "0A 1B 2C"
  static String formatBytesForDisplay(List<int> bytes) {
    return bytes.map((b) => b.toRadixString(16).toUpperCase().padLeft(2, '0')).join(' ');
  }
}

