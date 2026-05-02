#include <stdio.h> 
#include <stdlib.h>
/*
if (condition) response;  -> you can also write single arguments this way
normally is something more like this 
if(condition)
{
    response;
}

*/




int main(int argc, char *argv[])
{
    int a, b;

    printf("Digite um valor para a: ");
    scanf("%d", &a);
    getchar();
    printf("Digite um valor para b: ");
    scanf("%d", &b);
    getchar();

    if (a < b)
    {
        printf("b eh maior do que a\n\n");
    }
    else if (b < a)
    {
        printf("a eh maior que b\n\n");
    }
    else
    {
        printf("b eh igual a a\n\n");
    }



    system("PAUSE");
    return 0;

}