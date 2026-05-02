#include <stdio.h>
#include <stdlib.h>
#define DIML 3
#define DIMC 5


// be careful when using matrix in Microcontrollers  
int main(int argc, char *argv[])
{
    int i, j;//iteration variables

    int matrix[DIML][DIMC];// matrix declaration
    
    printf("Sua matriz terá %d linhas e %d colunas.\n", DIML, DIMC);
    
    for (i = 0; i < DIML; i++)
    {
        printf("Digite a sua linha %d.\n", (i + 1));
        for (j = 0; j < DIMC; j++)
        {
            scanf("%d", &matrix[i][j]);
            getchar();
        } //end for aninhado
    } // end for 

    for (i = 0; i < DIML; i++)
    {
        for (j = 0; j < DIMC; j++)
        {
            printf("%4d", matrix[i][j]);
        }// end for aninhado
        printf("\n");        
    }// end for 
    
    system("PAUSE");
    return 0;
}