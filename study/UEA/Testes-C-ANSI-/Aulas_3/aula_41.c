//---VARIÁVEIS GLOBAIS VS VARIÁVEIS LOCAIS---
//---Bibliotecas Auxiliares---
#include <stdio.h>
#include <stdlib.h>

//---Protótipo das Funções Auxiliares
void funcao1();
void funcao2();

//---Variáveis Globais 
int counter;

//---Função Main---
int main(int argc, char *argv[])
{
    counter =200;
    funcao1();

    system("PAUSE");
    return 0;
}//end main

//---Desenvolvimento das Funções Auxiliares---
void funcao1()
{
    funcao2();
    printf("O valor de counter eh %d\n\n", counter);//imprimir o valor 200
}  
void funcao2()
{
    int counter;
    for(counter = 1; counter < 20; counter++) printf("-"); 
}

/*
    Os 3 motivos para se evitar o uso de variáveis globais
    1) Ocupam a memória em todo tempo em que o progrma está sendo executado
    2) Usar uma variável local torna menos geral uma função, pois eloa dependerá de uma função externa,
    pois a variável global pode ser alterada em qualquer parte do código
    3) Um grande número de varfiantes globais pode ocasionar erros no projeto devido efeitos desconhecido 

*/