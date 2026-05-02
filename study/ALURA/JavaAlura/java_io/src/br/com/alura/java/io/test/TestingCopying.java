package br.com.alura.java.io.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class TestingCopying {
	public static void main(String[] args) throws IOException {

		//Socket s = new Socket();
		//InputStream fis = s.getInputStream()
		
		//InputStream fis = new FileInputStream("lorem.txt");
		InputStream fis = System.in;
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		
		
		//OutputStream fos = s.getOutputStream; 
		//OutputStream fos = new FileOutputStream("lorem2.txt"); 
		OutputStream fos = System.out;
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);

		String line = br.readLine();
		bw.write(line);
		
		line = br.readLine();
//		
//		while (line != null && !line.isEmpty()) {
//			bw.write(line);
//			bw.newLine();
//			bw.flush();//this methods makes that the buffer read and write each line
//					   //instead of using doing in all at once
//			line = br.readLine();
//		}
		//always close the buffer
		br.close();
		bw.close();
	}
}
 