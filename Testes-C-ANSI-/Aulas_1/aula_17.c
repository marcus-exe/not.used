#include <stdio.h>
#include <stdlib.h>
#include <string.h> // uma biblioteca cheia de funcionalidades de string

//Strings -> Cadeia de caracteres
int main(int argc, char *argv[])
{
    char nome[51], sobrenome[51];// the amount of characteres we want to keep and we add 1 (/0)     
    int comp1, comp2;

    printf("Digite seu nome: ");
    scanf("%s", &nome);
    comp1 = strlen(nome); // len function using C(ANSI)
    getchar();
    
    printf("Digite seu sobrenome: ");
    scanf("%s", &sobrenome);
    comp2 = strlen(sobrenome); // len function using C(ANSI)
    getchar();

    //concatenetion function
    strcat(nome, " ");
    strcat(nome, sobrenome);

    printf("Ola %s!\n", nome);
    printf("Seu nome tem %d caracteres e seu sobrenome tem %d caracteres.\n", comp1, comp2);
    
    printf("\n");
    printf(strlwr(nome)); // lower case function
    printf("\n");

    strcpy(nome, "que nome estranho");//it copies the oring string to the end string
    printf(nome);
    printf("\n");

    system("PAUSE");
    return 0;
}