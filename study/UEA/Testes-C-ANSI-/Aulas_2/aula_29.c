#include <stdio.h>
#include <stdlib.h>
#include <conio.h>                      // biblioteca neceesaaria para fazermos a estrutura de dados 


                                        /*as estruturas são terminadas em ponto e vírgula, 
                                        pois não são funções, mas declarações.*/

struct componente                       // componente é o nome da estrutura
    {                                   // as variáveis são declaradas dentro dessas chaves
        char tipo[20];
        char referencia[4];
        unsigned char num_ref;
        int valor;
        char unidade[10];

    }comp;                              // variáveis de controle (devem ser separadas por vírgula)
    
                                        // struct componente comp; 
                                        // você também pode declarar variáveis de controle dessa maneira;
    
    
    
    //COMO DELARAR MATRIZES EM STRUCT 
    //struct componente matriz[100]

    //printf("%lu", matriz[0].valor) --> isso vai dentro de main 

int main(int argc, char *argv[])
{
    printf("Tipo de componente _______________: ");
    fflush(stdin); // utiliza entradas padrão para pegar dados do teclado
    fgets(comp.tipo, 20, stdin); // ele pega as entradas do teclado

    printf("Referencia do Componente _____________: ");
    fflush(stdin);
    fgets(comp.referencia, 4, stdin); // variável da estrutura, comprimento, stdin

    printf("Numero de Referência ______________: ");
    scanf("%c", &comp.num_ref); // eu uso scanf aqui pois essa variável é um número
    getchar();

    printf("Valor do Componente _______________ : ");
    scanf("%d", &comp.valor);
    getchar();

    printf("Unidade _________________:"); 
    fflush(stdin);
    fgets(comp.unidade, 10, stdin);
    
    printf("\n\nCOMPONENTE CRIADO:\n\n");
    printf("%s ", comp.tipo);
    printf("%s", comp.referencia);
    printf("%c", comp.num_ref);
    printf("\nValor: %d", comp.valor);
    printf("%s\n\n", comp.unidade);

    system("PAUSE");
    return 0;
}