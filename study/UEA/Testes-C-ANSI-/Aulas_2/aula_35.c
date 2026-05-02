#include <stdio.h>
#include <stdlib.h>

//você pode usar o typedef para mudar o nome das estruturas em C
// serve para tornar o seu código mais intuitivo 


//há diversos usos para esse tipo de estrutura, por exemplo:


/*
Você pode mudar a formas como você chama as variáveis da STRUCT para ficar menos pesado
você pode colocar isso dentro de aula_29.c

ANTES:
struct componente variavel1;
struct componente variavel2;
struct componente variavel3;
DEPOIS:
typedef struct componente novo;
novo variavel1;
novo variavel2;
novo variavel3;

*/

typedef int inteiro;
typedef float flutuante;
typedef char character;

int main(int argc, char *argv[])
{
    inteiro x;
    flutuante a;
    character s;

    x = 14;
    a = 3.14;
    s = 'd';

    printf("\n%d", x);
    printf("\n%f", a);
    printf("\n%c", s);
   
    system("PAUSE");
    return 0;
}