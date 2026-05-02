import java.io.File;

public class Main {
	public static void main(String[] args) {
		File file = new File("secretmessage.txt");
		if(file.exists()) {
			System.out.println("That file exists! :O!");
		}
		else {
			System.out.println("That file doesn't exist :(");
		}
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getPath());
		System.out.println(file.isFile());
		file.delete();  
		
	}
}
