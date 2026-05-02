#include <stdio.h>
#include <stdlib.h>

//Expressoes Aritimeticas com Ponteiros 

/*  COMO FUNCIONA A LOCALIZAÇÃO E VALOR DOS PONTEIROS 
    int teste;

    int *point1, *point2;

    teste = 500;

    point1 = &teste;

    point2 = point1;

    printf("No local %p ,", point2); // endereco de teste 

    printf(" encontra-se valor %d", *point2); // valor do teste 
*/

int main(int argc, char *argv[])
{

    int *itPoint;   // adicione 1 e avance 4 bytes 
    char *chPoint;  // adicione 1 e avance 1 byte
    float *flPoint; // adicione 1 e avance 4 bytes

    printf("\n %d \n", chPoint); //endereço do ponteiro 

    chPoint = chPoint + 9; //operação realizada com o ponteiro 
    // nesse caso do ch -> 1 bytes x 9

    printf("\n %d \n", chPoint); //endereço deslocado em x bytes

    system("PAUSE");
    return 0;
}