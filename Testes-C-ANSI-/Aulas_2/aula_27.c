#include <stdio.h>
#include <stdlib.h>

//nunca chame um ponteiro sem lhe atribuir uma variável
//int *p;     ->isso pode dar tela azul em sistemas mais antigos 


char *p1 = "\nTeste de Frase 1\n";
char *p2 = "\nTeste de Frase 2\n";
int *pointnulo = 0;// esse é um ponteiro nulo // ele é considerado inútil


int main(int argc, char *argv[])
{
    printf(p1);
    printf(p2);
        
    system("PAUSE");
    return 0;
}