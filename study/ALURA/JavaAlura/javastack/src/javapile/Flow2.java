package javapile;

public class Flow2 {
	public static void main(String[] args) {
		System.out.println("Starting Main");
		try {
			method02();
		} catch (ArithmeticException | NullPointerException | MyException ex) {
			System.out.println("Exeption: " + ex.getMessage());
		}
		System.out.println("Ending Main");

	}

	private static void method01() throws MyException {
		System.out.println("Starting Method 01");
		method02();
		System.out.println("Ending Method 01");
	}

	private static void method02() throws MyException {
		System.out.println("Starting Method 02");
		
		throw new MyException("something went wrong");

	}
}
