


extern uint32_t tiempo_luz;
extern uint32_t tiempo_pulso;

void SysTick_Handler(void)
{
	DriverTeclado();

	if(tiempo_luz)
	{
		tiempo_luz--;
		if(!tiempo_luz)
		{
			tiempo_luz = TIEMPO_TIT;
			SetPIN(LUZ, GetPIN(LUZ) ^ 1); //Invierto el estado de la señal luminosa

		}

	}


	if(tiempo_pulso)
		tiempo_pulso--;



}


void DriverTeclado(void)
{
	uint8_t codigoActual;
	codigoActual = DriverTecladoHW();

	DriverTecladoSW(codigoActual);
}

