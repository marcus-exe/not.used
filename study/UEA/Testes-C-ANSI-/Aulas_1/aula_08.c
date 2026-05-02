#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[])
{
    int modo;
    int valor;

    printf("Conversor de Bases Numericas\n");
    printf("1: Decimal para Hexadecimal\n");
    printf("2: Hexadecimal para decimal\n");
    printf("Escolha um modo: ");
    scanf("%d", &modo);
    getchar();

    if (modo == 1)
    {
        printf("\nInformar o valor em decimal: ");
        scanf("%d", &valor);
        getchar();
        printf("%d em hexadecimal eh %x\n", valor, valor);
    }

    else if (modo == 2)
    {
        printf("\nInformar valor em hexadecimal: ");
        scanf("%x", &valor);
        getchar();
        printf("%x em decimal eh %d\n",valor, valor);
    }

    else
    {
        printf("\nSeu modo eh invalido\n");
    }

    system("PAUSE");
    return 0;

}