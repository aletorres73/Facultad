//  punto 1

#include "soporte.h"
#include "declaraciones.h"

#define 	PCONP	 (* ( ( uint32_t * ) 0x400FC0C4UL ))
#define		PCLKSEL0 (* ( ( uint32_t * ) 0x400FC1A8UL ))
#define		ISER0	 (* ( ( uint32_t * ) 0xE000E100UL ))

#define		DDLL (25000000/16)

void UART1_Inicializacion ( int speed )
{
	PCONP |= 0x01<<4;			// se enciende UART1 (bit 4 en 1)
	PCLKSEL0 &= ~(0x03<<8);		// PCLOCK=25Mhz (bits 8 y 9 en cero)

	U1LCR = 0x80;	//DLAB ON

	// set velocidad
	U1DLM = ((DDLL/speed) >> 8) & 0xFF;
	U1DLL =  (DDLL/speed)       & 0xFF;

	// 8 bits de datos, paridad impar y 2 bits de stop.
	//DLAB OFF
	U1LCR = 0x0F;

	SetPINSEL( 0, 16, 1 );	// Port 0, Pin 16, func 1

	//DLAB OFF - 8 bits de datos, paridad impar y 2 bits de stop.
	U1LCR = 0x0F;
	U1IER = 0x01;	// Interrupcion Recepcion


	ISER0 |= ( 1 << 6 );	//UART1 Interrupt Enable.
}



