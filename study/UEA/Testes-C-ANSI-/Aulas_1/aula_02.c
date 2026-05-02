#include <stdio.h>
#include <stdlib.h>
//a linguagem C é case-sensetive, ou seja, ela diferencia letras maiusculas e minusculas 
int main(int argc, char *argv[])
{
    int numero;//the variable was created 
    printf("Digite um numero: ");//we learned this in the other lesson
    scanf("%d", &numero);//here we have the function scanf, that is basically a python insert
                        //this function needs 2 arguments, 1) the type of the variable 
                        //2) &name of the variable 
    getchar();//this is necessary to clean the buffer of the keyboard
    printf("O numero digitado eh: %d\n\n",numero);
                                        //for you to mention a variable in the middle of thi function
                                        //you need to put its kind in the middle of the function
                                        //and after that you need to "comma it" 
                                        //and then mention the variable

    system("PAUSE");
    return 0;
}       