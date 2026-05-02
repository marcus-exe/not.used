#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

/*

        Data Base System
        Selects Dealers by Region 
        

*/


int main(int argc, char *argv[])
{
    char region, dealer;
    printf("Regioes: Norte, Sul, Leste, Oeste\n");
    printf("Informe a primeira letra da regiao: ");
    region = getche(); //similar to the function scanf - both scan a keyboard command
    region = toupper(region); // capitalize all the letters in the function 
    printf("\n");

    switch(region)
    {
         case 'L':
         printf("Vendedores: Lucindo, Pereira, Alcimar, Joana.\n");
         printf("Informe a primeira letra do vendedor: ");
         dealer = toupper (getche());
         printf("\n");

         switch(dealer)
         {
             case 'L':
             printf("Vendas: R$%d\n", 5000);
             break;

             case 'P':
             printf("Vendas: R$%d\n", 7000);
             break;

             case 'A':
             printf("Vendas: R$%d\n", 3500);
             break;

             case 'J':
             printf("Vendas: R$%d\n", 4000);
             break;
             
         }

         case 'N':
         printf("Vendedores: Laureano, Pussilda , Alencar, Julia.\n");
         printf("Informe a primeira letra do vendedor: ");
         dealer = toupper (getche());
         printf("\n");

         switch(dealer)
         {
             case 'L':
             printf("Vendas: R$%d\n", 15000);
             break;

             case 'P':
             printf("Vendas: R$%d\n", 700);
             break;

             case 'A':
             printf("Vendas: R$%d\n", 12500);
             break;

             case 'J':
             printf("Vendas: R$%d\n", 4900);
             break;
             
         }
        case 'O':
         printf("Vendedores: Falco, Gabi, Zeke, Eren.\n");
         printf("Informe a primeira letra do vendedor: ");
         dealer = toupper (getche());
         printf("\n");

         switch(dealer)
         {
             case 'F':
             printf("Vendas: R$%d\n", 1200);
             break;

             case 'G':
             printf("Vendas: R$%d\n", 7800);
             break;

             case 'Z':
             printf("Vendas: R$%d\n", 16000);
             break;

             case 'E':
             printf("Vendas: R$%d\n", 40000);
             break;
             
         }

         case 'S':
         printf("Vendedores: Petra, Subaru, Emilia, Julius.\n");
         printf("Informe a primeira letra do vendedor: ");
         dealer = toupper (getche());
         printf("\n");

         switch(dealer)
         {
             case 'P':
             printf("Vendas: R$%d\n", 15000);
             break;

             case 'S':
             printf("Vendas: R$%d\n", 7300);
             break;

             case 'E':
             printf("Vendas: R$%d\n", 2800);
             break;

             case 'J':
             printf("Vendas: R$%d\n", 5000);
             break;
             
         }
    }
    system("PAUSE");
    return 0;
}