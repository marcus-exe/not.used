package com.fiap.br.utils;

public class Formatters {
    public static String formatCpf(String cpfInput) {
        String cpfNumbersOnly = cpfInput.replaceAll("\\D", "");

        if (cpfNumbersOnly.length() != 11) {
            throw new IllegalArgumentException("O CPF deve conter 11 dígitos.");
        }
        return cpfNumbersOnly.substring(0, 3) + "." +
                cpfNumbersOnly.substring(3, 6) + "." +
                cpfNumbersOnly.substring(6, 9) + "-" +
                cpfNumbersOnly.substring(9, 11);
    }

    public static String formatPhoneNumber(String phoneNumber) {

        if (phoneNumber.length() != 11) {
            throw new IllegalArgumentException("Invalid phone number. Please enter 11 digits.");
        }

        String areaCode = phoneNumber.substring(0, 2); // DD
        String firstPart = phoneNumber.substring(2, 7); // XXXXX
        String secondPart = phoneNumber.substring(7, 11); // XXXX

        return String.format("(%s) %s-%s", areaCode, firstPart, secondPart);
    }
    public static boolean validateEmail(String emailInput) {
        return emailInput.endsWith("@gmail.com");
    }

    public static boolean validatePassword(String passwordInput) {
        return passwordInput.length() > 10;
    }




}
