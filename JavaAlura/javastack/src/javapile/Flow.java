package javapile;

public class Flow {
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
		for (int i = 1; i <= 5; i++) {
			System.out.println(i);

//			try {
//				int a = i/0;
//			} catch(ArithmeticException ex) {
//				String message = ex.getMessage();
//				System.out.println("ArithmeticExeption: " + message);
//				//ex.printStackTrace();
//			}
			Account c = null;
			c.deposit();
			System.out.println("Ending Method 02");
		}
		
	}
}
