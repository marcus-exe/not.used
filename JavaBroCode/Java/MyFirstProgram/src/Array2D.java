
public class Array2D {
	public static void main(String[] args) {
		String[][] cars = new String[3][3];
		cars[0][0] = "Camaro";
		cars[0][1] = "Corvette";
		cars[0][2] = "Siverado";
		cars[1][0] = "Mustang";
		cars[1][1] = "Tesla";
		cars[1][2] = "Celta";
		cars[2][0] = "Sandero";
		cars[2][1] = "Onix";
		cars[2][2] = "Palio";

		for (int i = 0; i < cars.length; i++) {
			System.out.println();
			for (int j = 0; j < cars[i].length; j++) {
				System.out.print(cars[i][j] + " ");

			}
		}
		
		System.out.println();
		
		String[][] flowers = {{"Iris-Setosa", "Iris-Virginica"},
							{"Iris-Versicolor", "urmom"}};
		
		
		for (int i = 0; i < flowers.length; i++) {
			System.out.println();
			for (int j = 0; j < flowers[i].length; j++) {
				System.out.print(flowers[i][j] + " ");

			}
		}
	}
}
