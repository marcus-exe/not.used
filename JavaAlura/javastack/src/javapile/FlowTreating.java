package javapile;

public class FlowTreating {
	public static void main(String[] args) {
		System.out.println("Starting Main");
		try {
			method01();
		} catch (ArithmeticException | NullPointerException | MyRuntimeException exception) {
			System.out.println("Exeption: " + exception.getMessage());
			exception.printStackTrace();
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
		
		//ArithmeticException exception = new ArithmeticException();
		
		
		throw new MyRuntimeException("Something gone really wrong!");
		//this is a complete new exception
		
		
		//System.out.println("Ending Method 02");
		//since the exception is going to break everything bellow itself
		//the compiler complains about having code bellow
		
	}
}
