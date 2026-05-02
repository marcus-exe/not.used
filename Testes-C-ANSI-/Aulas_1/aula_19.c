#include <stdio.h>
#include <stdlib.h>
#define dim 5


//int vetor[] = {10, 5,6 ,12, 31, 24, 11, 87}; we do not need to delcare its lenght

/* we can declare vectors this way 
int vetor[3] = {5,
                4,
                1};
*/

int main(int argc, char *argv[])
{

    int vetor1[dim] = {4, 5, 6, 9, 7};
    int vetor2[] = {15, 13, 7, 5, 6, 8, 9, -1};
    int vetor3[] = {12, 13, 17, 20, 24, 25, 26, 30};

    unsigned i, tamanho; // unsigned without specifiing the variable kind, its INT

    // vector 1
    printf("Vetor com tamanho definido\n");
    
    for(i = 0; i < dim; i ++)
    {
        printf("Elemento %d = %d\n", i, vetor1[i]);
    }
    
    
    // vector 2
    printf("Vetor terminado em -1\n");
    
    for(i = 0; vetor2[i] > 0; i ++)
    {
        printf("Elemento %d = %d\n", i, vetor2[i]);
    }

   //vector 3   
    printf("Vetor sem tamanho definido.\n");
    
    tamanho = sizeof(vetor3)/sizeof(int);
    //we use the size of function, but it measures the vector lenght using bits
    //that's why we need to divide it by "sizeof(int)"
    
    for(i =0; i < tamanho; i ++)
    {
        printf("Elemento %d = %d\n", i, vetor3[i]);
    }



    system("PAUSE");
    return 0;
}


