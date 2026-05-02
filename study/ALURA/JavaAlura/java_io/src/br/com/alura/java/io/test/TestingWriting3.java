package br.com.alura.java.io.test;


import java.io.IOException;
import java.io.PrintStream;


//this is the same as reading, but with different methods
public class TestingWriting3 {
	public static void main(String[] args) throws IOException {
		//File Input Stream
//		OutputStream fos = new FileOutputStream("lorem2.txt");//it has bits as input
//		OutputStreamWriter osw = new OutputStreamWriter(fos);//it has chars as input
//		BufferedWriter bw = new BufferedWriter(osw);//it convert in lines
//		FileWriter fw = new FileWriter("lorem2.txt");
//		BufferedWriter bw = new BufferedWriter(fw);//it convert in lines
		
		 
		PrintStream ps = new PrintStream("lorem2.txt");
		ps.println("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod ");
		ps.println();
		ps.println("just testiiiing");
		
		ps.close();//I always need to close the buffer
		
		
	}
}
