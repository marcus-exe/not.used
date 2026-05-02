#include <stdio.h>
#include <stdlib.h>

                // COMO PASSAR STRUCTS PARA FUNÇÃO
struct estrutura
{
    int a;
    int b;
    char c;
};

                //FUNÇÃO IMPRIMIR 
void imprimir(struct estrutura parametro)
{
    printf("%d\n", parametro.a);   
}
//dessa forma eu posso passsar uma estrutura inteira como parâmetro de uma função


                //MAIN
int main(int argc, char *argv[])
{
    struct estrutura argumento;

    argumento.a = 1200;

    imprimir (argumento);
       
    system("PAUSE");
    return 0;
}