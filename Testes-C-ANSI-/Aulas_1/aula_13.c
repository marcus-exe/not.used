#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>



        //EXEMPLO DE LOOP INFINITO
/*    
    comeco:
        comando1;
        comando2;
        comando3;

    goto comeco;
*/     
        //MUITOS PROGRMADORES CONDENAM O USO DO "GOTO"



int main(int argc, char *argv[])
{

    int i = 0, j = 1, resposta;
    int erros = 0, acertos = 0;
    char option;


    inicio:
        for(j = 1; j < 10; j++)
        {
            printf("Qual eh a resposta de %d + %d?\n", i,  j);
            scanf("%d", &resposta);
            getchar();

            if (resposta != (i + j))
                {
                erros++;
                printf("Resposta incorreta!\n");                    
                }
            else
            {
                acertos++;
                printf("Resposta correta!\n");
            }
        }// end ""for loop"
        printf("Erros: %d\n", erros);
        printf("Acertos: %d\n", acertos);


    meio:
        printf("Continuar respondendo? S - Sim, N - Nao: ");
        option = toupper(getche());        
        getchar();
        printf("\n");

        switch(option)
        {

            case 'S':
                i++;
                goto inicio;
                break;
            
            case 'N':
                goto fim;
                break;
            
            default:
                printf("Opcao invalida!\n");
                goto meio;
            
        }//end of switch       

    fim:


    system("PAUSE");
    return 0;
}