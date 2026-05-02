#include <stdio.h> //this way you can include libraries 
#include <stdlib.h>

//the variables declared inside the main function are denominated "locals"
//but, if they are declared outside the function, they are denominated "globals"
int main(int argc, char *argv[])
{
    //int numero01;   -> this variable was not initalized, thus it represents a random number 
    int numero01 = 15;

    float numero02 = 22.5;

    char caractere = 'w'; //in C, we always put it in simples Apostrophes.

    printf("%d\n", numero01);
    //printf("%f\n", numero02);-> this way written, this variable has all the numbers after the "."
    printf("%.1f\n",numero02);
    printf("%c\n", caractere);
    

    system("PAUSE");
    return 0;
}