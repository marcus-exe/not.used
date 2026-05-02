#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

// utililizacao da funcao continue 

/*  *Imprimir os numero pares

    int x;

    for (x = 0; x <= 100; x++)
    {
        if ((x % 2) != 0) continue;
        printf("%d", x);

    }
*/

void codifica(); //prototipo de funcao 

int main(int argc, char *argv[])
{
    codifica();
    system("PAUSE");
    return 0;
}

void codifica()
{
    char ok = 0, ch;

    while(!ok) //enquanto ok = 0
    {
        ch = getche(); 
        if(ch == '$')
        {
            ok = 1;
             continue;
        }
        printf("%c", ch + 2);
    }

}