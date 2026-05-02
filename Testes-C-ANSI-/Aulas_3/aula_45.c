//---INTRODUÃO AOS ARQUIVOS NA LINGUAGEM C---

//---Bibliotecas Axiliares---
#include <stdio.h>
#include <stdlib.h>

//---Main Function---
int main(int argc, char *argv[])
{
    FILE *arquivo;                          //Cria um ponteiro para um arquivo 
    arquivo = fopen("aula_45.txt", "w");    //Abre um arquivo para escrita do tipo txt
    fprintf(arquivo, "\nHello World!!!\n");          //Escreve uma mensagem no arquivo
    fclose(arquivo);                        //Fecha o arquivo

    system("PAUSE");
    return 0;
}//end Main