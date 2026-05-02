//---FUNÇÕES QUE RETORNAM VALORES NÃO INTEIROS--- 
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#define pi 3.14159265358979323846

//---Incializando Funções Auxiliares---
float area_do_circulo(float raio_do_circulo);

//---Função Principal---
int main(int argc, char *argv[])
{
    float raio_do_usuario;
    printf("Informe o valor do Raio: ");
    scanf("%f", &raio_do_usuario);
    getchar();
    printf("A area do circulo eh %f\n", area_do_circulo(raio_do_usuario));

    system("PAUSE");
    return 0;
}

//---Desenvolvendo Funções Auxiliares---
float area_do_circulo(float raio_do_circulo)
{   
    return (pow(raio_do_circulo, 2) * pi);
}