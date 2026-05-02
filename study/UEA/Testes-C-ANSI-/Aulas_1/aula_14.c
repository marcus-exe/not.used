#include <stdio.h>
#include <stdlib.h>

/*  AQUI TEREMOS ALGUNS EXEMPLOS DE COMANDOS DE REPETICAO

     *EXEMPLO 1*

    while(1)// ou while(True)// para loops infinitos
    {
        comandos;
    }

    *EXEMPLO 2*
    int z = 0;
    while (z < 10)
    {
        comandos;
        z++;
    }
    
    *EXEMPLO 3*
    do
    {
        comandos;     
    }while(condicao)


    *EXEMPLO 4*
    for(;;)  // loop infinito
    {
        comandos; 
    }


*/


int main(int argc, char *argv[])
{
    int option;
    do
    {
        printf("Digite a opcao: 1 - sim, 2 - nao:");
        scanf("%d", &option);
        getchar();


    }while(option < 1 || option > 2);

    system("PAUSE");
    return 0;
}