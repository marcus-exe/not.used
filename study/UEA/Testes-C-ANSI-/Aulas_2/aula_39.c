//Funções que Retornam Ponteiros 
#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
// para você usar strings como parâmetros de função, você terá que apelar para os ponteiros

//---Incialização da Função Auxiliar---
char *findCharacter(char character, char *str); 


//---Função Principal---
int main(int argc, char *argv[])
{
    char frase[80];// string de 80 posições
    char chr, *pointer;

    printf("\nDigite um frase: ");
    gets(frase);
    printf("Digite um Caractere: ");
    chr = getche();
    pointer = findCharacter(chr, frase);

    if (pointer) printf("\n\n%s\n\n", pointer);// Se encontrar correspondência, imprime uma frase a partir dela
    else printf("\nNenhuma paridade\n");     
   
    system("PAUSE");
    return 0;
}


//---Desenvolvimento das Funções Auxiliares---
char *findCharacter(char character, char *str)
{
    int counter = 0;

    while (character != str[counter] && str[counter != '\0']) counter++;
    
    if (str[counter]) return (&str[counter]);   //Se houver correspondência ou paridade retorna o ponteiro para a localização

    else return (str[counter]= '\0');           //Senão retorna um ponteiro nulo 
}