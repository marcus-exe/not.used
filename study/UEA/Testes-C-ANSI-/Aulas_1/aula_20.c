#include <stdio.h>
#include <stdlib.h>

//&  *
//tipo *nome;



int main(int argc, char *argv[])
{
    int variavel = 250;  //variavel inteira com o valor 250
    int *ponteiro; //ponteiro para a variavel 
    int exibe;

    ponteiro = &variavel;
    exibe = *ponteiro;

    printf("\n%d\n", exibe);
    
    system("PAUSE");
    return 0;
}