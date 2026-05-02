#include <stdio.h>
#include <stdlib.h>

//PONTEIROS INDEXADOS

int main(int argc, char *argv[])
{
    int mat[20] = {1, 17, 95, 32, 19, 57, 99, 15, 9001, 2044};

    int *point, i;

    point = mat;

    for(i = 0; i < 20; i++) printf("%d\n", *(point + i));//uma forma de colocar todos os ponteiros
    
    system("PAUSE");
    return 0;
}