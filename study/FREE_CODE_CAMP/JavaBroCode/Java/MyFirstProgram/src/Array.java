
public class Array {
	public static void main(String[] args) {
		String[] flowers = new String[3];
		flowers[0] = "iris-setosa";
		flowers[1] = "iris-virginica";
		flowers[2] = "iris-setosa";
		//String[] cars = {"Camaro", "Corvette", "Tesla"};
		
		for (int i = 0; i<flowers.length;i++) {
			System.out.println(flowers[i]);
		}
		
	}
}
