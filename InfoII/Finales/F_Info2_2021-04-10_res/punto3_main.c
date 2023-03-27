// punto 3
#include "soporte.h"
#include "declaraciones.h"
int16_t NroTaTeTi;
volatile int16_t TO_40seg;
volatile int32_t TO_10min;	// 16 bits no alcanza

enum eEstados {CLEAR, REPOSO, JUEGO, GANADOR};

int main(void)
{
enum eEstados state= CLEAR;
uint8_t fila, colum,color;
int aux;

	InitPanta();
	InitSystick();

	//--- verifico si la placa esta configurada
	if (!(ClkCtrReg &0x01))
	{
		SetTaTeTiDisplay ();
		return 0;
	}

	// Por enunciado, se entiende que no hay error, por lo que no se verifica su valor.
	NroTaTeTi=(GPREG0>>2)&0xFFFF;

	switch (GPREG0& 0x03)
	{
		case 0:UART1_Inicializacion ( 9600); break;
		case 1:UART1_Inicializacion ( 2400); break;
		case 2:UART1_Inicializacion ( 1200); break;
		case 3:UART1_Inicializacion (  300); break;
	}

	while (1)
	{
		aux=analisis_trama (&fila,&colum,&color);

		if (aux!=TR_NO)	//trama válida - no importa el estado
			TO_10min=TT_10MIN;

		if (!TO_10min)	// Sin datos por 10 minutos
			state= CLEAR;

		switch (state)
		{
		case CLEAR:
			ClearPanta();
			state=REPOSO; // @suppress("No break at end of case")

		case REPOSO:	// solo espera una trama de start
			if (aux==TR_INICIO)
				state= JUEGO;
					break;


		case JUEGO:
			if (aux==TR_ROJO ||aux==TR_AZUL )	// Fin de partida
			{
				TO_40seg=TT_40SEG;
				state=GANADOR;
			}
			if (aux==TR_LED )	// Fin de partida
			{
				SetPanta (fila, colum, 0);	//apago el led
				SetPanta (fila, colum, color);
			}
			break;

		case GANADOR:	// cuidado con el orden de los if
			if (aux==TR_INICIO)
			{
				ClearPanta();
				state= JUEGO;
				break;
			}

			if (!TO_40seg)	// pasaron 40 segundos
				state= CLEAR;
			break;
		}
	}
    return 0 ;
}


void SysTick_Handler(void)	// se invoca cada 5ms
{
static uint8_t tics=0;

	tics++;
	if (tics==5) // cada 25ms / tambien podría ser !(tics%5) con el cuidado del overflow
	{
		RefreshPanta ();
		tics=0;
	}

	if (TO_40seg)
		TO_40seg--;

	if (TO_10min)
		TO_10min--;
}


