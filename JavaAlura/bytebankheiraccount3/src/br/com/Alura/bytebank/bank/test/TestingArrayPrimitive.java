package br.com.Alura.bytebank.bank.test;

public class TestingArrayPrimitive {
	
	//Array[]
	public static void main(String[] args) {
		
	int[] ages = new int[5];
	//it creates an array with 5 slots for int kind of variable
	//besides, it starts the array with the default values
	
	for(int i=0;i<ages.length;i++) {
		ages[i]= i +254;
		System.out.println(ages[i]);
	}
	
	
		
		
	}
}	
