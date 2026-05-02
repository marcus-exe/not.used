//---Ponteiros como Argumentos para Funções---
#include <stdio.h>
#include <stdlib.h>
//---Protótipo das Funções Auxiliares---
void change(int *x, int *y);


int main(int argc, char *argv[])
{
    int num1, num2;

    printf("\nDigitar um numero: ");
    scanf("%d", &num1);
    getchar();
    printf("\nDigitar outro numero: ");
    scanf("%d", &num2);
    getchar();

    printf("\nOs valores iniciais sao: num1 = %d e num2 = %d", num1, num2);

    change(&num1, &num2);

    printf("\nOs valores alterados sao: num1 = %d e num2 = %d", num1, num2);
    
    system("PAUSE");
    return 0;
}
//---Desenvolver Funções Auxiliares---
void change(int *x, int *y)
{
    int temp;       //uma variável temporária
    temp = *x;      //salvando o conteúdo aramzenado no ponteiro apontado por x
    *x = *y;        // armazena o contúdo de y em x
    *y = temp;      //armazena o conteúdo original de x em y
}