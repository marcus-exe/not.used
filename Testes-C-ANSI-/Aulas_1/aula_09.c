#include <stdio.h>
#include <stdlib.h>

/*
this is the way of working with for statement in C

    for(inicialization;condition;increment)
    {
        comands
    }

*/
int main(int argc, char *argv[])
{
    int i;
    int j;
    //dessa mesma forma é possível fazer decrescente e for dentro de outro for
    for(i=10; i > 0; i--)
    {
        printf("\n\nLINHA %d\n\n",i);

        for(j=10; j<15; j++)
        {   
            printf("letra %x |",j);
        }

    }

    system("PAUSE");
    return 0;

}