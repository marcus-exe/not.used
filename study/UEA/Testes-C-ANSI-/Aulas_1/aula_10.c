#include <stdio.h>
#include <stdlib.h>

//esssa declaração é interessante quando há muitos testes a serem feitos
//nesse aqui não podemos fazer testes de comparação apenas testes de igualdade 

/*
    switch(variable)
    {
        case constante1:
            your comands1;
            break;
        case constante2:
            your comands2;
            break;
            .
            .
            .
        default:    -> if the user put any other thing than what is already available
        comandos;
    }
*/


//Conversor de Decimal Para Hexadecimal e Vice-Versa

int main(int argc, char *argv[])
{
    int option;
    int number;

    printf("\nConversor de Decimal para Hexadecimal");
    printf("\nEscolha uma variavel"); 
    printf("\nDigite 1 - Decimal para Hexadecimal");
    printf("\nDigite 2 - Hexadecimal para Decimal");
    printf("\nEscolha a sua opcao: ");
    scanf("%d", &option);
    getchar();

    switch (option)
    {
        case 1:
            printf("\nInformar o valor em decimal: ");
            scanf("%d", &number);
            printf("\nO numero %d em Hexadecimal eh %x\n", number, number);
            break;

        case 2:
            printf("\nInformar o valor em hexadecimal: ");
            scanf("%x", &number);
            printf("\nO numero %x em Decimal eh %d\n", number, number);
            break;
        
        default:
            printf("\nA variavel que voce escolheu eh invalida\n");


    }        
    system("PAUSE");
    return 0;
}