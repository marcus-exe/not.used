package javapile;

public class Error {
	public static void main(String[] args) {
		System.out.println("Starting Main");
		try {
			method01();
		} catch (ArithmeticException | NullPointerException ex) {
			System.out.println("Exeption: " + ex.getMessage());
		}
		System.out.println("Ending Main");

	}

	private static void method01() {
		System.out.println("Starting Method 01");
		method02();
		System.out.println("Ending Method 01");
	}

	private static void method02() {
		System.out.println("Starting Method 02");
		
		method02();
		//this will cause a error called stack overflow, this causes the stack
		//to start a new process every time
		System.out.println("Ending Method 02");
	}
}
