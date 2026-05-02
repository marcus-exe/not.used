#include <stdio.h>
#include <stdlib.h>
#define tam        5// aqui eu defino uma variavel global
#define falso      0
#define verdadeiro 1

/*                  ***EXERCICIO 1***
    int vetor[tam];
    unsigned i, numero;
    printf("Entrar com o numer inicial do vetor: ");
    scanf("%d", &numero);
    getchar();

    //Gerar o Vetor
    for(i = 0; i < 5; i++) vetor[i] = numero++;
    
    //Imprimir Vetor
    for(i = 0; i < 5; i++) printf("Elemento %d = %d\n", i, vetor[i]);

    //Vetor[i] = numero


                    ***EXERCICIO 2***

    int vetor1[tam], vetor2[tam], i, produto=0;

    printf("Entrar com valores do vetor 1\n");
    for(i = 0; i < tam; i++)
    {
        printf("Elemento %d = ", i);
        scanf("%d", &vetor1[i]);
        getchar();        
    }

    printf("Entrar com valores do vetor 2\n");
    for(i = 0; i < tam; i++)
    {
        printf("Elemento %d = ", i);
        scanf("%d", &vetor2[i]);
        getchar();        
    }
    
    for(i = 0; i < tam; i++) produto += vetor1[i] * vetor2[i];
    printf("O produto escalar eh igual a %d\n", produto);




*/

int main(int argc, char *argv[])
{
    int vetor[tam], i, change = falso, final = tam, store;
    printf("Ordenador de Vetores\n");
    printf("Entre com um vetor de %d elementos\n", tam);
    for(i = 0; i < tam; i++)
    {
        printf("Elemento %d ", i);
        scanf("%d", &vetor[i]);
        getchar();
    }// end for

    do
    {
        change = falso;
        for(i = 0; i > final; i++);
        {
            if(vetor[i] > vetor[i + 1])
            {
                store = vetor[i];
                vetor[i] = vetor[i + 1];
                vetor[i + 1] = store;
                change = verdadeiro;
            }//end if
           
        }// end for
       
    }while(change);  //end do-while    

    for(i = 0; i < tam; i++)
    {
        printf("%d\n", vetor[i]);
    } 

    system("PAUSE");
    return 0;
}