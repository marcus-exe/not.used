//---FUNÇÕES QUE RETORNAM VALORES INTEIROS--- 
#include <stdio.h>
#include <stdlib.h>

//---Protótipo da Funções Auxiliares---
int soma(int num1, int num2);
int subt(int num1, int num2);
int mult(int num1, int num2);
int divi(int num1, int num2);


//---Função Principal---
int main(int argc, char *argv[])
{
    printf("\n%d\n%d\n", soma(2,4), subt(5,7));
    printf("\n%d\n%d\n", mult(5,5), divi(10, 2));
   //você pode chamar funções dentro do printf

    system("PAUSE");
    return 0;
}//end

//---Desenvolvimento das Funções Auxiliares---
int soma(int num1, int num2)
{
    return (num1 + num2);
}

int subt(int num1, int num2)
{
    return (num1 - num2);
}

int mult(int num1, int num2)
{
    return (num1 * num2);
}

int divi(int num1, int num2)
{
    return (num1 / num2);
}