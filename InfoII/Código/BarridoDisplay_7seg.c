#include <stdio.h>

#define DIGITO 5

extern __uint8_t Msg_Display[];

void Barrido_Diplay(valor)
{
    __uint8_t aux[DIGITO];
    __uint8_t i=0;

    for (i=DIGITO-1 ; i > -1; i--)
    {
        aux[i]= tabla_7seg_bcd[valor%10];
        valor/=10;
    }
    
    TICKINT = 0;
    for(i=DIGITO -1; i>-1; i--)
        Msg_Display[i]= aux[i];
    TICKINT = 1;
}