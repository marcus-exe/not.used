#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
//Ponteiros e Matrizes 

/*
MATRIZES COM STRING
    char matriz[20] = "Teste de String";

    char *point1, *point2;

    point1 = matriz; // aqui ele aponta para a posição inicial da matriz 
    
    point2 = &matriz[2]; // aqui ele aponta para a terceira posição da matriz  

    point1 = point1 + 2; // aqui ele aponta para 2 casas a mais da matriz

    // devemos obter o mesmo resultado nesse print 
    printf("\n%c\n", *point1);
    printf("\n%c\n", *point2);



 TOLOWER - TECNICA USANDO MATRIZES 

    char mat[20];
    int i;

    printf("Entre com uma frase utilizando letras maiusculas\n");
    scanf("%s", &mat);
    getchar();

    printf("Frase minuscula:\n ");

    for (i = 0; mat[i]; i++) printf("%c", tolower(mat[i]));
    printf("\n");

    system("PAUSE");

*/

int main(int argc, char *argv[])
{   
    char mat[30], *point;

    printf("Entre com uma frase maiuscula: \n");
    scanf("%s", &mat);
    getchar();

    printf("Frase em letra minuscula: ");

    point = mat;

    while(*point) printf("%c", tolower(*point++));
    printf("\n");


    return 0;
}