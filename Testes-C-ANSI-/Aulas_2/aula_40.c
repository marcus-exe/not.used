//Regra de Escopo e Variáveis Locais 
#include <stdio.h>
#include <stdlib.h>

//---Protótipo da Função Auxiliar---
void function();

//---Variáveis Globais---
int x, y, z;

//---Main Function---
int main(int argc, char *argv[])
{
    int a, b, c, d;

    a = 100;
    b = 200;
    c = 300;
    d = 400;

    printf("Variaveis Globais: a = %d, b = %d, c = %d, d = %d\n", a, b, c, d);
    function();

   
    system("PAUSE");
    return 0;
}


//---Desenvolvimento da Função Auxiliar---
void function()
{
    int a, b, c, d;//variáveis locais da function

    a = 33;
    b = 44;
    c = 55;
    d = 66;

    printf("Variaveis Locais de function: a = %d, b = %d, c = %d, d = %d\n", a, b, c, d);

    if (a < b)
    {
        int z = 200; // essa variável deixza de existir assim q termina o if statement
        printf("Variavel Local do IF dentro da function: z = %d", z);
    }  
}