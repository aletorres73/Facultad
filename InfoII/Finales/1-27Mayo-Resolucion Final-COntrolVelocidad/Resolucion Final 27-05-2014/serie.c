
/*****************************
 * DRIVER SERIE *
 ****************************/
void UART0_IRQHandler (void)
{
	uint8_t iir, dato;

	do
	{
		//IIR es reset por HW, una vez que lo lei se resetea.
		iir = U0IIR;

		if ( iir & 0x04 ) //Data ready
		{
			PushRx((uint8_t )U0RBR );
		}

	}
	while( ! ( iir & 0x01 ) ); /* me fijo si cuando entre a la ISR habia otra
						     	int. pendiente de atencion: b0=1 (ocurre unicamente si dentro del mismo
								espacio temporal lleguan dos interrupciones a la vez) */
}



/*****************************
 * PRIMITIVAS SERIE *
 ****************************/


// Buffer de Recepcion
uint8_t bufferRx[RXBUFFER_SIZE];


// Indices de Recepcion
uint8_t rx_in = 0,rx_out = 0;
// Flags de estado de los buffers
uint8_t rx_buffer_full = 0, rx_buffer_empty = 1;



uint8_t PushRx( uint8_t dato )
{
	if(rx_buffer_full)
		return 1;	//buffer lleno

	bufferRx[rx_in] = dato;
	rx_in ++;
	rx_in %= RXBUFFER_SIZE;
	rx_buffer_empty = 0;	//si agrego un dato el buffer ya no esta vacio

	if(rx_in == rx_out)
		rx_buffer_full = 1;	//se lleno el buffer

	return 0;	//dato agregado al buffer
}

uint8_t PopRx( uint8_t *dato )
{
	if(rx_buffer_empty)
		return 1;	//buffer vacio

	*dato = (uint8_t) bufferRx[rx_out];
	rx_out++;
	rx_out %= RXBUFFER_SIZE;
	rx_buffer_full = 0;	//si saco un dato el buffer ya no esta lleno

	if(rx_out == rx_in)
		rx_buffer_empty = 1;	//se vacio el buffer

	return 0;	//dato sacado del buffer
}





/*****************************
 * APLICACION SERIE *
 ****************************/


extern uint8_t VelocidadMaxima;
extern uint8_t codigo[4];

void AnalizaSerie(void)
{  
	uint8_t dato;
   	static uint8_t i;
   	static uint8_t estado = HEADER;
	
	if(  PopRX( &dato ) )
	{
		switch( estado )
		{
			case HEADER:
				if( dato == '#' )
					estado = VELOCIDAD;
				break;

			case VELOCIDAD:
				VelRecibida = dato;
				estado = COD;
				i = 0;
				break;

			case COD:
				CodRecibido[i] = dato;
				i++;
				if( i == 4 )
				{
					estado = FIN_TRAMA;
				}
				break;

			case FIN_TRAMA:
				if( dato == '$' )
				{
					VelicidadMaxima = VelRecibida;
					for (i = 0; i < 4 ; i++)
						codigo[i] = CodRecibido[i];
				}
				estado = HEADER;
				break;
		}


	}

}
