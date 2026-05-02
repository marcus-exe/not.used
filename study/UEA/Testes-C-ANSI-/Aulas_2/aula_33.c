#include <stdio.h>
#include <stdlib.h>

/* 
o uso da UNION é muito paraceido com a da struct
mas a UNION ocupa menos espaço na memória
e isso se deve em decorrência da UNION compartilhar um espaço de momória
entre diferentes tipos de variável, provaremos isso no código abaixo
*/

union estrutura_01
{
    int inteiro01;
    char character01;
    float flutuante01;
};

struct estrutura_02
{
    int inteiro02;
    char character02;
    float flutuante02;
};


int main(int argc, char *argv[])
{
    printf("\n%d\n", sizeof(union estrutura_01));
    printf("\n%d\n", sizeof(struct estrutura_02));
    
    system("PAUSE");
    return 0;
}