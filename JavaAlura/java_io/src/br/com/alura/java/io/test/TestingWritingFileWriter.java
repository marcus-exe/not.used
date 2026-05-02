package br.com.alura.java.io.test;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


//this is the same as reading, but with different methods
public class TestingWritingFileWriter {
	public static void main(String[] args) throws IOException {
		//File Input Stream
//		OutputStream fos = new FileOutputStream("lorem2.txt");//it has bits as input
//		OutputStreamWriter osw = new OutputStreamWriter(fos);//it has chars as input
//		BufferedWriter bw = new BufferedWriter(osw);//it convert in lines
		
		FileWriter fw = new FileWriter("lorem2.txt");
		BufferedWriter bw = new BufferedWriter(fw);//it convert in lines
		bw.write("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod ");
		//bw.write(System.lineSeparator());
		//fw.write("\n");
		bw.newLine();//it is a method from the buffer
		bw.write("just testing");
		
		bw.close();//I always need to close the buffer
		
		
	}
}
