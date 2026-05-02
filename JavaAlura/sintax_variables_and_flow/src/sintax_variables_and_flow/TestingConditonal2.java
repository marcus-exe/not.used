package sintax_variables_and_flow;

public class TestingConditonal2 {
	
	public static void main(String[] args) {
		System.out.println("testando condicionais");
		int idade = 16;
		int quantidadePessoas = 2;
		boolean acompanhado = quantidadePessoas >= 2;
		//outra sintaxe que ainda nao havia sido falada, era sobre boolenos
		//aqui tambem falamos sobre algumas condicoes de linguagem
		System.out.println("valor de acompanhado = " + acompanhado);
		
		if (idade >= 18 || acompanhado) {
			System.out.println("Seja bem vindo");
		} 
		else {
				System.out.println("Infelizmente, você não poderá entrar");
			}
		}

	} 

