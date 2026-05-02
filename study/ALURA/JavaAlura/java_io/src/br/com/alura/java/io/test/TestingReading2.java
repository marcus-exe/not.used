package br.com.alura.java.io.test;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;

public class TestingReading2 {

	public static void main(String[] args) throws Exception {

		Scanner scanner = new Scanner(new File("accounts.csv"));
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			//System.out.println(line);

			Scanner lineScanner = new Scanner(line);
			lineScanner.useLocale(Locale.US);
			lineScanner.useDelimiter(",");

			String type = lineScanner.next();
			int agency = lineScanner.nextInt();
			int number = lineScanner.nextInt();
			String barrer = lineScanner.next();
			double balance = lineScanner.nextDouble();

			 
			String formatedvalue = String.format(new Locale("pt", "BR"),"%s - %04d-%08d, %s: %08.2f", type, agency, number, barrer, balance);
			System.out.println(formatedvalue);

			
			lineScanner.close();

//			String[] values = line.split(",");
//			System.out.println(Arrays.toString(values));
//			System.out.println(values[3]);

		}
		scanner.close();
	}

}
