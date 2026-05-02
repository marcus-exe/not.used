package br.com.Alura.bytebank.bank.test;

public class TestingString {
	public static void main(String[] args) {

		String name = "Alura"; // literal object
		// String name = new String("alura");

		String name2 = name.replace("A", "a");
//		System.out.println(name2);
//		System.out.println(name.charAt(0));
//		System.out.println(name.substring(1));
//		System.out.println(name.length());
		System.out.println(name.trim().isEmpty());
//		System.out.println();

		for (int i = 0; i < name.length(); i++) {
			System.out.println(name.charAt(i));

		}

	}
}