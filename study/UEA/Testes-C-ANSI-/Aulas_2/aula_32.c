// --- Incluindo as Bibliotecas Auxiliares ---
#include <stdio.h>
#include <stdlib.h>
 //--- Estrutura de Dados ---
struct clock
{
    int segundos;
    int minutos;
    int horas;
};
//--- Protótipo das Funções Auxiliares ---
void delay();
void update(struct clock *t);
void display(struct clock *t);
//---Função Principal---
int main(int argc, char *argv[])
{
    //Variável da Estrutura
    struct clock time;

    //Inicializa os Dados da Estrutura
    time.horas = 0;
    time.minutos = 0;
    time.segundos = 0;

    //Loop Infinito
    for( ; ; ) 
    {
        update(&time);
        display(&time);
        delay();
        system("cls"); // esse negócio limpa a tela para não ter SPAM do tempo
    }
}
//--- Desenvolvimento das Funções Auxiliares ---
void delay()// tem função de criar uma temporização
{
    long t;// variável local para iterações essas muito grandes  
    for(t = 1; t<100000000; t++);//essa linha cria o atrado no nosso código 
}
void update(struct clock *t)// o parametro dessa estrutura é o ponteiro *t
{
    t -> segundos++; // o operador seta é usado quando temos um ponteiro para uma estrutura 

    if(t->segundos == 60)
    {
        t->segundos = 0;
        t->minutos++;
    }
    if(t->minutos == 60)
    {
        t->minutos = 0;
        t->horas++;
    }
    if (t->horas == 24) t->horas = 0;

    delay();// esse processo ocorrre em delay 
}
void display(struct clock *t)
{
    printf("%d:",t->horas);
    printf("%d:",t->minutos);
    printf("%d",t->horas); 
}
/*
ma dica para os usuários do Windows: em vez de utilizar a função delay,
 inclua a seguinte linha no começo do seu programa:
#include <windows.h>
O windows.h é um arquivo de cabeçalho específico do Windows para C/C++ 
que contém declarações para todas as funções na API do Windows.
Após incluir esse arquivo de cabeçalho, podemos utilizar a função Sleep.
Devemos passar um valor em milissegundos para a função Sleep como argumento.
Esse valor fará a thread atual esperar (dormir) x segundos antes de continuar com a execução.
Por exemplo, para aguardar 3 segundos antes do prograama prosseguir:
Sleep(3000);
3 segundos é equivalente a 3000 milissegundo
*/