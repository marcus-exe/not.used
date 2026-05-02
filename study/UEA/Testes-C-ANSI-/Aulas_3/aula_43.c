//MATRIZES COMO PARÂMETROS PARA FUNÇÕES 
//uma matriz nunca pode ser passada inteira como parâmetro opu argumento de uma funcao
//mas sim o endereço dela

//---Bibliotecas Auxiliares---
#include <stdio.h>
#include <stdlib.h>
//---Declaração das Funções Auxiliares
void exemplo1(int mat1[10]);
void exemplo2(int mat2[]);
void exemplo3(int *point);
//essas são as 3 formas existentes, porem somente a terceira é usual e elegante  


//---Funçãon Principal
int main(int argc, char *argv[])
{
    //criação da matriz t
    int t[10], i;
    for (i = 0; i < 10; i++) t[i] = i;
    //execução da função 1 - > usando a matriz t como base
    exemplo1(t);
    printf("\n");
    exemplo2(t);
    printf("\n");
    exemplo3(t);
    printf("\n");
    
    system("PAUSE");
    return 0;
}
//---Desenvolvimento das Funções Auxiliares
void exemplo1(int mat1[10])
{
    int i; //essa variável é usada no for loop 
    for (i = 0; i < 10; i++) printf("%d ", mat1[i]);
}
void exemplo2(int mat2[])
{
    int i; //essa variável é usada no for loop 
    for (i = 0; i < 10; i++) printf("%d ", mat2[i]);
}
void exemplo3(int *point)
{
    int i;
    for (i = 0; i < 10; i++) printf("%d ", point[i]);
}