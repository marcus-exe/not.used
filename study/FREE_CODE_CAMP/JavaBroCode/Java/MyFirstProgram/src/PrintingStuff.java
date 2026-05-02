import java.util.Scanner;

public class PrintingStuff {
	
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("What is your name?");
		String name = scanner.nextLine();
		System.out.println("How old are you?");
		int age = scanner.nextInt();
		scanner.nextLine();
		System.out.println("What is you favorite Food?");
		String food = scanner.nextLine();
		
		
		System.out.println("Hello " + name);
		System.out.println("You are "+ age + " years old!");
		System.out.println("You like " + food);


	}
}
