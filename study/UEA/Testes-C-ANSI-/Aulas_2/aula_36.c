#include <stdio.h>
#include <stdlib.h>

/* FUNCÕES - SINTAXE
    tipo_da_funcao nome_da_funcao (parametros_da_funcao)
    
    void nome_funcao();

    int nome_funcao();

    nome_funcao(); quandos não falamos o nome da função é porque ela é inteira 
*/  


//---Protótipo das Funções Auxiliares---
//(há na realidade 2 formas de você chamar uma função:
//na primeira você inicializa a função direto o que pode ser meio desorganizado
//na segunda, a qual estamos fazendo agora mesmo, você inicializa a função
// e depois você faz a função  



//---Protótipo das Funções Auxiliares---
void imprimir1();                   // void significa que essa função não vai retornar nenhum dado,
                                    // somente executar as ordens 
void imprimir2();                   // função com parenteses vazios significa que não há parâmentros 

void soma(int num1, int num2);      //aqui eu tenho que colocar os 2 parâmetros



//---Função Principal---
int main(int argc, char *argv[])
{
    imprimir1();
    imprimir2();
    soma(1, 3); // aqui eu tenho que colocar os 2 parâmetros necessários
                // parametros não, necessariamente, são inteiros 
   
    system("PAUSE");
    return 0;
}

//---Funções Auxiliares---
void imprimir1()
{
    printf("Mensagem 1\n");
    printf("Mensagem 2\n");
    printf("Mensagem 3\n");
    printf("Mensagem 4\n");
}
void imprimir2()
{
    printf("Mensagem 5\n");
    printf("Mensagem 6\n");
    printf("Mensagem 7\n");
    printf("Mensagem 8\n");
}
void soma(int num1, int num2)
{
    int resultado;

    resultado = num1 + num2;

    printf("A soma de num1 + num2 = %d", resultado);
}
/*
 também podemos declarar essa função diretamente na parte de cima, sem termos que inicializarmos ela
 mas esse processo de inicialização é bem amias elegante para os códigos que forem desenvolvidos 
*/