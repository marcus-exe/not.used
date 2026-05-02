package sintax_variables_and_flow;

public class TestingEscope {

	public static void main(String[] args) {
		System.out.println("testando condicionais");
		int idade = 16;
		int quantidadePessoas = 2;
		boolean acompanhado;
		//boolean acompanhado = quantidadePessoas >= 2;
		
		if (quantidadePessoas >= 2) {
			acompanhado = true;
		}
		else {
			acompanhado = false;
		}
		
		System.out.println("valor de acompanhado = " + acompanhado);
		
		if (idade >= 18 || acompanhado) {
			System.out.println("Seja bem vindo");
		} 
		else {
				System.out.println("Infelizmente, você não poderá entrar");
			}
		}
}
