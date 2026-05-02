package br.com.Alura.bytebank.bank.util;

import java.util.ArrayList;
import java.util.List;

public class TestingOtherWrappers {
	public static void main(String[] args) {
		
		Integer ageRef = Integer.valueOf(29);//autoboxing
		System.out.println(ageRef.doubleValue());//unboxing
		
		Double dRef = Double.valueOf(3.2);//autoboxing
		System.out.println(dRef.doubleValue());//unboxing
		
		Boolean bRef = Boolean.FALSE;//autoboxing
		System.out.println(bRef.booleanValue());//unboxing
		
		//Number as Class Mother 
		Number number = Float.valueOf(29.0f);
		List<Number> list = new ArrayList<>();
		list.add(10);
		list.add(32.6);
		list.add(25.6f);
		
	}
}
