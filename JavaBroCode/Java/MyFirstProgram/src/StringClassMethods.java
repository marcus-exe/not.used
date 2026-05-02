
public class StringClassMethods {
	public static void main(String[] args) {
		String name = " Bro ";
		System.out.println(name.equals("Bro"));
		System.out.println(name.equalsIgnoreCase("bRo"));
		System.out.println(name.length());
		System.out.println(name.charAt(3));
		System.out.println(name.indexOf("o"));
		System.out.println(name.isEmpty());
		System.out.println(name.toUpperCase());
		System.out.println(name.toLowerCase());
		System.out.println(name.trim());
		System.out.println(name.replace('o', 'a'));
	}
}
