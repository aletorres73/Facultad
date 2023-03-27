//--- Segunda Parte  -----------

//================ ítem 1 ======================

char get_key(void);

void set_time(int ms);
void set_led(char color);

#define UN_SEG 1000 // un segundo


//--------------------------------------------------------
void MdE_Teclado(void)
{
static int estado=INICIAL;
static int count=0;
char key,aux;
static uint_8 arr[4];   // datos ingresados

    switch(estado)
    {
    case INICIAL:    // deja todo apagado
        count=0;
        set_led (0); //  0 – indicador luminico apagado
        set_rele (OFF); //  OFF – relé en reposo
        set_time (0); // timer desactivado
        estado=INGRESO;
        break;

    case INGRESO:   // queda a la espera del ingreso de las teclas
        key=get_key();

        // se analiza la tecla ingresa respecto a la cantidad de teclas ingresadas

        switch (key)
        {
        case NO_KEY: // Sin tecla ingresada
            break;   // sin modificaciones

        case KEY_A: // Cancelacion
            estado=INICIAL;
            break;

        case KEY_B: // Enter
            if (count>=4)    // si ya hay 4 digitos paso al envio de trama
                estado=ENVIO;// caso contrario omito el ingreso
            break;

        default: // tecla presionada
            if (key<KEY_0 || key>KEY_9) // verifica que no haya error en la tecla
                break;                  // presionada. No seria necesario

            if (count<4)
            {
                arr[count]=key; // se guarda la tecla presionada
                count++;
            }

            if (count>=4)   // ni bien se alcanza los 4 dígitos se enciende el led
            {
                set_led (1); //  1 – indicador luminico verde
            }
        break;
        }

    case ENVIO:   // Se ingresaron los 4 digitos y se presiono enter
        envio_trama(arr); // se arma y envía la trama
        set_time(2*UN_SEG);  // se dispara el temporizador desde el inicio del envío de trama
        estado= ESPERO_RESPUESTA;
        break;

    case ESPERO_RESPUESTA: // espera la respuesta del PS
        aux=recibo_trama(arr);

        switch (aux)
        {
        case TRAMA_OK:  // Trama Ok - se abre la puerta por 6 segundos
            set_led (2); //  indicador luminico amarillo
            set_rele (ON); //  se activa el relé
            set_time (6*UN_SEG); // timer en 6 segundos
            estado= ESPERA_TIEMPO;
            break;

        case TRAMA_NG:   // respuesta Not Good - se niega el acceso
        case TRAMA_ERR:  // Erros de Trama - se niega el acceso
            set_led (3); //  indicador luminico en rojo
            set_rele (OFF); //  se bloquea la puerta (opcional)
            set_time (2*UN_SEG); // timer en 2 segundos
            estado= ESPERA_TIEMPO;
            break;

        default:    // SIN_TRAMA
            if (!XYZ)   // vencio el tiempo de espera
            {           // idem error de trama
                set_led (3); //  indicador luminico en rojo
                set_rele (OFF); //  se bloquea la puerta (opcional)
                set_time (2*UN_SEG); // timer en 2 segundos
                estado= ESPERA_TIEMPO;
            }
            break;
        }

    case ESPERA_TIEMPO: //
        if (!XYZ)   // vencio el tiempo de espera
        {
            estado=INICIAL;
            get_key();      // se borra la tecla que haya podido quedar en el buffer
        }

        break;
    }
}

//================ ítem 2 ======================

/* nota:
 * dada la aplicacion, no se espera recibir y transmitir al mismo tiempo.
*/
#define TX_SIZE    7
#define RX_SIZE    7

uint8_t tx_buff [TX_SIZE];	//buffer de recepción
uint8_t tx_out;     	//índice de transmisión

uint8_t rx_buff [RX_SIZE];	//buffer de recepción
uint8_t rx_in;			//índice de recepción

void envio_trama(uint_8 *pArr)
{
    // -- armo la trama
    tx_buff[0]=0xAA; // byte inicio de trama;
    tx_buff[1]=pArr[0]; // datos del teclado;
    tx_buff[2]=pArr[1];
    tx_buff[3]=pArr[2];
    tx_buff[4]=pArr[3];

    tx_buff[5]=pArr[0]^pArr[1]^pArr[2]^pArr[3];

    tx_buff[6]=0xFF; // byte fin de trama;

    U1THR = tx_buff[0];    // transmito el primer byte
    tx_out=1;       // reinicia el indice de transmision
}


/*
 * la función inicia la recepción luego de finalizar
 * el envío - por ese motivo no requiere ser circular
 * - no se espera recibir más datos que los especificados
 */


int recibo_trama(uint_8 *pArr)
{
    if (rx_in<RX_SIZE)   // sin datos suficientes
       return SIN_TRAMA;

    //---- analizo la trama ----

    // verifica inicio y fin de trama
    if (rx_buff[0]!=0xAA || rx_buff[6]!=0xFF)
        return TRAMA_ERR;

    // verifica los dígitos recibidos vs enviados
    if (rx_buff[1]!=pArr[0] || rx_buff[2]!=pArr[1] || rx_buff[3]!=pArr[2] || rx_buff[4]!=pArr[3])
        return TRAMA_ERR;

    // - Se analiza el byte ANS - de respuesta

    if (rx_buff[5]!=0xA5) // Acceso permitido
        return TRAMA_OK;

    if (rx_buff[5]!=0xEE) // Acceso Denegado
        return TRAMA_NG;

return TRAMA_ERR;   // byte de ANS invalido
}

void UART1_IRQHandler (void)
{
    uint8_t iir, aux;

    do
    {
        iir = U1IIR;

        if ( iir & 0x04 ) //Data ready
        {
            if (rx_in<RX_TOPE)  // garantiza que no haya desborde
                rx_buff[rx_in++] = (uint8_t) U1RBR;  //guardo en buffer e incremento índice
        }

        if ( iir & 0x02 ) //THRE- transmisión
        {
            if (tx_out<TX_TOPE)
                U1THR = buffer[tx_out++];
            else
            {
                tx_out = 0;  //Finalizo el envio.
                rx_in=0;    // inicializa el buffer de recepción
            }
        }
    }
    while( ! ( iir & 0x01 ) );
}
