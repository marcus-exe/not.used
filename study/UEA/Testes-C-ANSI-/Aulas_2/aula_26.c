#include <stdio.h>
#include <stdlib.h>

//cadeia de ponteiros ou indireção múltipla -> um ponteiro apontando para outro ponteiro 
int main(int argc, char *argv[])
{
    int var;
    int *point1;
    int **point2;
    int ***point3;//a norma padrão é parar no 2 ponteiro

    var = 178;

    point1 = &var;
    point2 = &point1;
    point3 = &point2;// a norma padrão é parar no 2 ponteiro 

    printf("\n%d\n", ***point3);
        
    system("PAUSE");
    return 0;
}