package br.com.Alura.bytebank.bank.util;

import java.util.ArrayList;
import java.util.List;

public class TestingWrapperInteger {

	public static void main(String[] args) {

		int age = 29;// Integer
		// Integer ageRef = new Integer(29); //deprecated
		Integer ageRef = Integer.valueOf(29);// autoboxing

		System.out.println(ageRef.doubleValue());

		System.out.println(Integer.MAX_VALUE);
		System.out.println(Integer.MIN_VALUE);
		System.out.println(Integer.SIZE);
		System.out.println(Integer.BYTES);

		System.out.println(ageRef.intValue());// (unboxing)

		// Converting from String to Int and making some operations
		String s = args[0];
		// Integer number = Integer.valueOf(s); //not really used
		int number = Integer.parseInt(s);
		System.out.println(number + 10);

		List numbers = new ArrayList();
		numbers.add(age);

	}

}
