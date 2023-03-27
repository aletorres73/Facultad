/*
===============================================================================
FINAL 27-05-2014
===============================================================================
*/
#include "aplicacion.h"


uint8_t Velocidad;
uint32_t tiempo_luz;
uint32_t tiempo_pulso;
uint8_t VelocidadMaxima;
uint8_t codigo[4] = {'1','2','3','4'};

void main(void)
{
	Inicializar ( );
	
	while(1)
	{
		MdeControlador();
	}

}



/********************************************************************************
	\fn  void MdeControlador( void )
	\brief Función de con la máquina de estados general.
	\author & date: Informática II
 	\param void
	\return:	void
*/
void MdeControlador( void )
{
	static uint8_t estado = DETENIDO;


	CalculaVelocidad();

	if( GetPIN( PULSADOR ) && estado != INACTIVO )
		estado = CODIGO;



	switch(estado)
	{
		case DETENIDO:
				if( Velocidad > 0 )
					estado = MOVIMIENTO;

				AnalizaSerie();
				break;

		case MOVIMIENTO:
				if( Velocidad > VelocidadMaxima )
				{
					SetPIN( BUZZER, ON );
					SetPIN( BOMBA, OFF );
					tiempoluz = TIEMPO_TIT;
					SetPIN( LUZ, ON );
					estado = EXCESO;
				}

				if( Velocidad == 0  )
					estado = DETENIDO;

				break;


		case EXCESO:
				if( Velocidad < VelocidadMaxima - 10 )
				{
					SetPIN( BUZZER, OFF );
					SetPIN( BOMBA, ON );
					tiempoluz = 0;
					SetPIN( LUZ, OFF );
					estado = MOVIMIENTO;
				}

				break;

		case CODIGO:
				tecla[i] = GetKey();
				i++;
				if( i == TAM_CODIGO )
				{
					error = 0;
					for(i = 0; i < TAM_CODIGO && !error; i++)
					{
						if( codigo[i] != tecla[i] )
							error = 1;

					}
					if( error )
					{
						estado = MOVIMIENTO; //En una vuelta de la máquina de estados se acomoda el estado acuerdo a la Velocidad
					}
					else
					{
						estado = INACTIVO;
					}

				}

				break;

		case INACTIVO:
				if( GetPIN(PULSADOR) )
				{
					estado = MOVIMIENTO; //En una vuelta de la máquina de estados se acomoda el estado acuerdo a la Velocidad
				}
				break;
	}



}


void CalculaVelocidad()
{
	if( GetPIN( SENSOR_VEL ) )
	{
		Velocidad = DISTANCIA / ((TIEMPO_DETENIDO - tiempo_pulso)*FRECUENCIA_TIMERHR);
		tiempo_pulso = TIEMPO_DETENIDO;
	}

	if( !tiempo_pulso )
	{	Velocidad = 0;
		tiempo_pulso = TIEMPO_DETENIDO;
	}

}


