#include <stdio.h>
#include <stdlib.h>

//ponteiros e strings

/*  UTILIZAÇÃO DE PONTEIROS
    char *point;
    point = "Teste de String Qualquer"; 
    printf("\n");
    printf(point);
    printf("\n");

    UTILIZAÇÃO DE PONTOIS ESPECÍFICOS EM MATRIZES: &

*/

/*  ALGUMAS EXPLICAÇÕES SOBRE O FUNCIONAMNETO DA LINGUAGEM C E O CÓDIGO ABAIXO


Ao invés de \0, poderíamos colocar o 0, ou false 
(caso o arquivo de cabeçalho stdbool.h esteja incluso no código) 
ou o NULL (definido no arquivo de cabeçalho stdio.h).
O NULL e false são definidos como 0, por isso o resultado é o mesmo.
O final de toda string em C é denotado pelo caractere terminador nulo \0.
Em outras palavras, colocar apenas str[i] ou str[i] != '\0' é a mesma coisa.
Outra maneira de escrever o for:
for(i = 0; str[i] && str[i] != 32; i++); //Produz o mesmo resultado
O 32 é a representação em decimal do espaço na tabela ASCII.


*/
int main(int argc, char *argv[])
{
    char str[50];

    char *point;

    int i; //iterações

    printf("Entre com uma string: ");
    gets(str); // usar isso é bem inseguro 

    //encontramos o primeiro espaço ou o fim da string
    for(i=0; str[i] && str[i] != ' '; i++);

    //essa linha de código é equivalente a:
    //for(i = 0; (str[i] != '\0') && (str[i] != ' '); i++);

    point = &str[i];
    printf("\n");
    printf(point);
    printf("\n");

        
    system("PAUSE");
    return 0;
}