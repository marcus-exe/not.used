#include <stdio.h>
#include <stdlib.h>

/*  Enumerção: é basicamente uma lista onde as variáveis são do tipo int 
    Essas variáveis são ordenadas em ordem crescente e podem receber inteiros referentes a elas
*/

enum componentes{transistor, capacitor, resistor, diodo, mcu};

char matriz[][20] = // uma matriz com n linhas e coluna com no máximo 20 caracteres
{
    "trasistor",
    "capacitor",
    "reistor",
    "diodo",
    "mcu"
};

int main(int argc, char *argv[])
{
    enum componentes referencia;// aqui eu delcaro a veriável de componentes 

    for (referencia = transistor; referencia <= mcu; referencia++)
    {
        printf("%s\n", matriz[referencia]); // o número de referência tem seu correspondente na matriz
    } 

    system("PAUSE");
    return 0;
}







/*
enum exemplo{item1, item2 = 2, item3, item4 = 150, item5};


ESSA AQUI VAI DENTRO DA MAIN
    printf("\n%d", item1);// a enumeração começa por padrão em 0    
    printf("\n%d", item2);// mas você pode mudar qualquer valor    
    printf("\n%d", item3);// e valor seguinte será acrescido em 1   
    printf("\n%d", item4);
    printf("\n%d\n", item5);
*/
