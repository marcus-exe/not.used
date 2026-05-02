package sintax_variables_and_flow;

public class Testing_Converting {
	public static void main(String[] args) {
		double salario = 1270.5;
		int valor = (int) salario; 
		// a alternativa acima é feita para avisar o java 
		// que temos ciência que estamos perdendo dados
		// isso recebe o nome de Casting
		
		System.out.println(valor);
		
		// também temos outros tipos de variáveis: long, short, byte, float
	}
}
