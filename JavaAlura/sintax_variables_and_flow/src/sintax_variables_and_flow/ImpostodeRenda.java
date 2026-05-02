package sintax_variables_and_flow;

public class ImpostodeRenda {
	public static void main(String[] args) {
		
		double salario = 2900.00;
		
		if (salario < 1900.0) {
			System.out.println(" Não haverá imposto");
		}
		
		else if (salario >= 1900.0 && salario <= 2800.0) {
			System.out.println("Imposto de 7,5%");
		}
		
		else if (salario > 2800.00 && salario <=3800.0) {
			System.out.println("Imposto de 15%");
		}
		
		else if (salario > 3800.0 && salario <= 4700) {
			System.out.println("Imposto de 15%");
		}
				
		
	}
}
