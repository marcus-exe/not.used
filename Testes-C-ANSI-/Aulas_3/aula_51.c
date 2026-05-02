#include <stdio.h>
#include <stdlib.h>
//Protótipo das Funções 
void function1();
void function2();

//Declarando variáveis globais 
float N1, N2, N3, N4, M, N5, M2;

int main(int argc, char *argv[])
{
	function1();
	if(M>7.0)
{
    printf("\nAluno Aprovado");
	printf("Media: %0.1f",M);
}	
	else
{
	if(M<5.0)
	{
		printf("\nAluno Reprovado");
		printf("Media: %0.1f",M);
	}
	else
	{
		function2();
		if(M2>5)
		{
			printf("\nAluno Aprovado");
			printf("\nMédia Final:%0.1f",M2);
		}
		else
		{
			printf("\nAluno Reprovado");
			printf("\nMédia Final:%0.1f", M2);
		}
	}

}
    
    system("PAUSE");
    return 0;
}

//---Desenvolvimento das Funções Auxiliares---
void function1()
{
	scanf("%f","%f","%f","%f",&N1,&N2,&N3,&N4);
	getchar();
	M = (N1*2+N2*3+N3*4+N4*1)/10;

}
void function2()
{
	scanf("%f",&N5);
	getchar();
	M2 = (M + N5)/2;
}
