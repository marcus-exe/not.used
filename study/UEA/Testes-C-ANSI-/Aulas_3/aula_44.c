                                     //---FUNÇÕES RECURSIVAS---
                                     //é aquela funão em que chamamos ela mesma dentro do bloco de comandos da função 
//---Bibliotecas Auxiliares---
#include <stdio.h>
#include <stdlib.h>
//---Protótipo da Função Auxiliar---
unsigned long fatorial(int num);    //função para cálculo do fatorial de um número inteiro

//---Função Principal---
int main()
{
    int entrada_do_usuario;
    unsigned long fat;
    printf("Calculadora de Fatoriais\n");
    printf("Digite um numero: ");
    scanf("%d", &entrada_do_usuario);
    getchar();
    fat = fatorial(entrada_do_usuario);
    printf("O fatorial eh: %d ", fat);
    printf("\n");

    system("PAUSE");//para segurar o sistema no escopo
    return 0;
}//end main

//---Desenvolvimento da Função Auxiliar---
unsigned long fatorial(int num)
{
    unsigned long result;

    if(num == 1 || num == 0) return result = 1;

    else 
    {
        result = num * fatorial(num - 1);
    }
}