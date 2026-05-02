package sintax_variables_and_flow;

public class Count {
	public static void main(String[] args) {
		int contador = 0;
		int total = 0;
		while (contador <= 10) {
			total += contador;
			contador++;
		}
		System.out.println(total);
	}
}
