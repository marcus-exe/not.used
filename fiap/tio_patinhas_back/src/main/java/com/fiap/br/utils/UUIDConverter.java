package com.fiap.br.utils;

import java.util.UUID;

public class UUIDConverter {
    /**
     * Converts a valid UUID string into a UUID object.
     *
     * @param uuidString The string representation of the UUID.
     * @return The UUID object if the string is valid, otherwise null.
     */
    public static UUID stringToUUID(String uuidString) {
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID string: " + uuidString);
            return null; // or throw an exception if you prefer
        }
    }
}
