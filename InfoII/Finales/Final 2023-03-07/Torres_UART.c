#include    <stdio.h>

#define MY_BUFF    64
#define MY_OK       1
#define MY_ERR      0
#define BITS_TRAMA 13


typedef unsigned char uint8_t;
enum state {TR_START,TR_ENVIADO};   

typedef struct reg
{
    uint8_t byte[4];
    
}uint32_t;

static uint8_t buffer_tx[MY_BUFF];
static uint8_t tx_in,tx_out,tr_cont=0;                  //indices de transmición de entrada y salida.
static uint8_t paridad;

uint8_t tick;                                             // ticks para transmitir


uint8_t MyTx(uint8_t data)
{
       
    if(tx_in >= MY_BUFF) return MY_ERR;         // si está lleno

 
    buffer_tx[tx_in]= data;                     // guardo el 1 byte en el buffer de transmisión
    
    tx_in++;
    tx_in%=MY_BUFF;    

    tr_cont++;

    return MY_OK;       
    
}

// uint8_t get_par(uint8_t data)
// {
//      if((data & 0x01)^((data>>1) & 0x01)^((data>>2) &0x01)^((data>>3) &0x01)^((data>>4) &0x01)
//                                                  ^((data>>5) &0x01)^((data>>6) &0x01)^((data>>7) &0x01)) //xor de todos los bits
//          return paridad = 1;

// }

uint32_t get_trama(void)
{
    uint32_t trama,aux;
    uint8_t i;
    int cp=0;
    
    if (tr_cont == 4)
        tr_cont=0;

    trama.byte[tr_cont]=buffer_tx[tx_out++];
    tx_out%=MY_BUFF;

    /*Calcula e inserta el bit de paridad en la trama*/
    do
    {
        if(aux.byte[tr_cont] & 0x01) cp++;

        aux.byte[tr_cont]>>=1;   

    }while(aux.byte[tr_cont]);

    


}

void MyTick(void)
{

}


