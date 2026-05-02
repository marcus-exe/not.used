#include <stdio.h>
#include <stdlib.h>
  

//Non Conventional Aplication for the FOR loop
//Including Multi variable ones



/*

    1) Imprimir os Numeros Pares de Zero a Cem  

    int i, j;
    for(i=0, j=0; i + j <= 100; i++, j++ )
    {
        printf("%d\n", i+j);
    }


    2) Jogo acerte o Valor

    int i;
    for(i=0; i!=12; )
    {
        printf("Digite o numero correto: ");
        scanf("%d", &i);
        getchar();
    }
.
*/

void display();
int leitura();
void square();


int main(int argc, char *argv[])
{
    int valor;
    for(display(); valor = leitura(); display())
    {
        square(valor);
    }
    system("PAUSE");
    return 0;
}// end main

void display()
{   
    printf("\nCalculadora de Quadrado de numeros Interios\n");
    printf("Digite 0 para sair\n");
    printf("Ou digite um numero inteiro: \n");
}// end display

int leitura()
{
    int t;//armazenamos o numero dado pelo usuario para calcularmpos seu quadrado   
    scanf("%d", &t); 
    return t;

}//end leitura

void square(int numero)
{
    printf("%d", numero * numero);

}
