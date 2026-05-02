package sintax_variables_and_flow;

public class TestingConditional {

	public static void main(String[] args) {
		System.out.println("testando condicionais");
		int idade = 18;
		int quantidadePessoas = 2;
		
		if (idade >= 18) {
			System.out.println("você tem mais de 18 anos");
			System.out.println("Seja bem vindo");
		} else {
			if (quantidadePessoas >= 2) {
				System.out.println("Você pode entrar, "
						+ "mas deve estar acompanhado");
				// essa é uma maneira de quebrar linha e continuar 
			}
			else {
				System.out.println("Infelizmente, você não poderá entrar");
			}
		}

	}

}