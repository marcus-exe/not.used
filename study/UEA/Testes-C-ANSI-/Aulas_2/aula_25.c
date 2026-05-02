//Sistema de Busca de Componentes em linguagem C(ANSI)

//---Bibliotecas Auxiliares---
#include <stdio.h>
#include <stdlib.h>

//---Protótipo de Funções Auxiliares---
int check(char *s);
void take_component();

//---Variáveis Globais---
char databank[][50] = {// matriz cointendo componentes e suas respectivas características 
                        "Jeff Bezos",      "190 bi",   "Amazon",
                        "Elon R Musk",     "170 bi",   "Tesla, SpaceX ...",  
                        "Bill Gates",      "132 bi",    "Microsoft",
                        "Bernard Arnould", "114 bi",    "Grupo Louis Vuitton",
                        "Mark Zuckenberg", "104 bi",    "Facebook",
                        "Warren Buffett",  "87.6 bi",    "Berkshire Hathaway",
                        "Larry Page",      "82.4 bi",    "Google",
                        "Steve Ballmer",   "80.4 bi",    "Maior Acionista da Microsoft",
                        "Sergey Brin",     "79.8 bi",    "Google",
                        "Larry Elison",    "79.7 bi",    "Oracle"

                        };

char input[80];            //matriz para a entrada de dados(componente em pesquisa)
char component[80];        //matriz passada como pareâmetro para a função check)
char *point;               //Ponteiro   



//---Função Principal---
int main()
{
    int indice;     //aramzena o índice do componente
    int option;     //armazena a opção escolhida


    do{

        printf("Informe o nome do componente que deseja pesquisar: ");
        gets(input);
        point = input;
        printf("Caracteristicas do componente pequisado: ");
        take_component();

    
        do
        {
            indice = check(component);                                  //procura o índice do componente na databank

            
            if(indice != -1) printf("%s", databank[indice + 1]);       //impirme a palavra em português se foi traduzida
    
            else printf("Sem registros no banco de dados\n");

            take_component();                                           // Pega a próxima palavra 

        }while(*component);                                             // Repete até encontrar uma string nula




        printf("\n");                                           // Quebra de Linha
        printf("Pesquisar mais? s - sim | n - não");            // Pergunta se um usuário quer pesquisar mais
        scanf("%c", &option);                                   // Armazena a opção escolhida em option
        getchar();                                              // Limpa o buffer do teclado

    
    }while(option == 's' || option == 'S');

    system("PAUSE");
    return 0;
}




//---Desenvolvimento das Funções Auxiliares---

int check(char *s)                              //Retorna a Localização de uma correspondência entre a String
{
    int i;                                      //Variável de Iterações

    for (i = 0; *databank[i]; i++);             //Varre a matriz databank
    {
        if(!strcmp(databank[i], s)) break;      //Comparação das matrizes databank e s

    } //end for

    if(*databak[i]) return(i);                  //Retorna o índice correspondente, se uma correspondência existir
    else return(-1);
}//end check                                    

/*
    take_component lê o próximo componente da matriz input. Cada componente é considerado como sendo separado por 
    um espaço ou pelo terminador nulo.  Nenhuma outra pontução é permintida
*/


void take_component()
{
    char *q;                                // ponteiro auxiliar
    
    q = component;                          //Recarrega o endereço do componente toda vez que a função é chamada

    while (*point && *point != ' ');        //pega o próximo componente
    {
        *q = *point;
        point++;
        q++;
    }//end while

    if (*point == ' ') point++;
    *q = '\0'

}