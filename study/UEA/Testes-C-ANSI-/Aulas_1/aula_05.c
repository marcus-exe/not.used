#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[])
{
    char a = 1;
    char b = 2;
    char c = 3; 
    char d = 1;

    if (a > b && a < c)
    {
        printf("The first proposition is True");
    }
    if (c==d || d==a)
    {
        printf("The second proposition is True");
    }
    else 
    {
        printf("There is no True alternative");
    }

    system("PAUSE");
    return 0;
}