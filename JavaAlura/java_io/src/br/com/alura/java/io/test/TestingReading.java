package br.com.alura.java.io.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestingReading {
	public static void main(String[] args) throws IOException {
		//File Input Stream
		InputStream fis = new FileInputStream("lorem.txt");//it has bits as output
		InputStreamReader isr = new InputStreamReader(fis);//it has chars as output
		BufferedReader br = new BufferedReader(isr);//it convert in lines
		String line = br.readLine();//it reads the line but only one at a time
		
		while (line != null) {
			System.out.println(line);
			line = br.readLine();
		}
		
		br.close();//I always need to close the buffer
		
		
	}
}
