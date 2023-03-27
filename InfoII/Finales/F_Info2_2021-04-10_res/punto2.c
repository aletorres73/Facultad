// punto 2
#include "declaraciones.h"

// mdo: 0- dato nuevo / 1-(opcional) error
// dato: valor recibido

#define CANT_BYTES 6
volatile uint8_t DataBuffer [CANT_BYTES];
volatile uint8_t DataFlag=0;
extern int16_t NroTaTeTi;

void recibido (uint8_t dato)
{
static uint8_t idx=0;
static uint8_t flg=0;
static uint8_t buffer [CANT_BYTES];

	if (dato==0xFA)
	{
		buffer[0]=dato;
		flg=idx=1;
		return;
	}

	if (!flg) return;	// no se recibio byte de start

	// guardo el dato en el buffer de recepcion
	buffer[idx]=dato;
	idx++;

	if (idx >=CANT_BYTES) //trama completa
	{
		// se transfiere a Buffer global
		// se podria trabajar tambien con dos buffer (activo/inactivo)
		// o por las caracteristicas directamente sobre un solo buffer global

		for (idx=0;idx<CANT_BYTES;idx++)
			DataBuffer [idx]=buffer[idx];
		DataFlag=1;
		flg=idx=0;
	}
}

void UART1_IRQHandler (void)
{
	uint8_t iir;

	do
	{
		iir = U1IIR;
		if ( iir & 0x04 ) 		//Data ready
			recibido (U1RBR); 	//guardo en buffer
	}
	while( ! ( iir & 0x01 ) );
}


/*
Bytes
  0		- Byte de Inicio: 0xFA
  1		- Nro de TaTeTi-Display - nibbles superiores (00 ~ 99)
  2		- Nro de TaTeTi-Display - nibbles inferiores (00 ~ 99)
  3		- Comando:  (02,03,04)
  4		- Sub Comando	C:02 -> 0x14 / C:03 -> 0x11/0x12 / C:04 ->f/c/c
  5		- Control: autoXOR

*/
					// fila     columna      color
					// podrían ser global
int analisis_trama (uint8_t *f,uint8_t *c,uint8_t *color)
{
	uint8_t j,k,x;
	uint8_t aux;

	if (!DataFlag)
		return TR_NO;	// No hay trama

	// independiente del resultado del análisis el flag de trama se pone a cero
	DataFlag=0;

	// Byte de Inicio - Siempre 0xFA
	if (DataBuffer[0]!=0xFA)	return TR_NO;	// Trama invalida

	// Nro de TaTeTi - Se considera que está ordenado igual que en el RTC
	if (DataBuffer[1]!=(NroTaTeTi & 0xFF) )	return TR_NO;
	if (DataBuffer[2]!=((NroTaTeTi>>8) & 0xFF))	return TR_NO;

	//--- verifico en byte de comandos.
	if ((DataBuffer[3]!=0x02) && (DataBuffer[3]!=0x04)) 	return TR_NO;


	// por simplicidad verifico primero el byte de control (o pseudo checksum)
	for (aux=j=0 ; j<CANT_BYTES ; j++)
		aux^=DataBuffer[j];

	if (aux)	// debería ser cero
		return TR_NO;

	//--- Ahora simplemente verifico los sub comandos.

	// DataBuffer[3]==0x02 o 0x04
	aux=DataBuffer[4];
	if (aux==0x84)
		return TR_INICIO;	// inicio de partido

	if (aux==0x81)
		return TR_AZUL;		// Gano azul

	if (aux==0x82)
		return TR_ROJO;		// Gano rojo

	if (!(aux & 0x3F)) 	// fila/columna/color
	{
		j=aux     & 0x03;		// fila
		k=aux >>2 & 0x03;		// columna
		x=aux >>4 & 0x03;		// color
		if (j==0x03 ||k==0x03 ||x==0x03) //valores de fila, columna o color invalidos
			return TR_NO;	// Trama inválida

		*f=j;
		*c=k;
		*color=x;
		return TR_LED;
	}

return TR_NO;	// Trama invalida
}



