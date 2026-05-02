#include <stdio.h>
#include <stdlib.h>


int main(int argc, char *argv[])
{
    
    struct
    {
        int inteiro;
        double flutuante;

    }exemplo1, exemplo2;


    //exemplo1.inteiro = 100;
    //exemplo2 = exemplo1;
    //printf("%d\n", exemplo2.inteiro);


    exemplo2.flutuante = 1001.678;
    exemplo1 = exemplo2;
    printf("%lf \n", exemplo1.flutuante);// assim atribuímos uma estrutura a outra
        
    
    system("PAUSE");
    return 0;
}