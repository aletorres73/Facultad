//--- Parte Inicial -----------

//================ ítem 1 ======================

/* - nota respecto a la implementación -
 * En la primer invocación a la función, solo se barre la fila 1,
 * en la siguiente invocación (1ms despues), se analiza la entrada.
 * Luego de analizar la entrada, se barre la siguiente fila, la cual se
 * leera en la siguiente invocación y así sucesivamente.
 * --------------------------------------------------------------------- */
char BoardHW( void )
{
    static uint8_t st = 0;	//variable de estado
    uint8_t aux= NO_KEY;

    switch (st)
    {
    case 0:    // Primer invocación - inicio barrido de fila 1
        setPIN (KEY_ROW_1, OFF);   // fila a analizar
        setPIN (KEY_ROW_2, ON);
        setPIN (KEY_ROW_3, ON);
	    st = 1;
        break;

    case 1:
        // analizo entrada (columnas) de fila 1
        if (getPIN (KEY_COL_1, OFF))  aux= KEY_1;
        if (getPIN (KEY_COL_2, OFF))  aux= KEY_4;
        if (getPIN (KEY_COL_3, OFF))  aux= KEY_7;
	    if (getPIN (KEY_COL_4, OFF))  aux= KEY_A;
        
        // inicio barrido de fila 2
        setPIN (KEY_ROW_1, ON);   
        setPIN (KEY_ROW_2, OFF);	// fila a analizar
        setPIN (KEY_ROW_3, ON);
        st = 2; break;

    case 2:
        // analizo entrada (columnas) de fila 2
        if (getPIN (KEY_COL_1, OFF))  aux= KEY_2;
        if (getPIN (KEY_COL_2, OFF))  aux= KEY_5;
        if (getPIN (KEY_COL_3, OFF))  aux= KEY_8;
	    if (getPIN (KEY_COL_4, OFF))  aux= KEY_0;

        // inicio barrido de fila 3
        setPIN (KEY_ROW_1, ON);   
        setPIN (KEY_ROW_2, ON);	
        setPIN (KEY_ROW_3, OFF);		// fila a analizar
        st = 3; break;

    case 3:
        // analizo entrada (columnas) de fila 3
        if (getPIN (KEY_COL_1, OFF))  aux= KEY_3;
        if (getPIN (KEY_COL_2, OFF))  aux= KEY_6;
        if (getPIN (KEY_COL_3, OFF))  aux= KEY_9;
	    if (getPIN (KEY_COL_4, OFF))  aux= KEY_B;

        setPIN (KEY_ROW_1, OFF);   
        setPIN (KEY_ROW_2, ON);	
        setPIN (KEY_ROW_3, ON);
        st = 1; break;

    } // cierre del switch

    return aux;
}


//================ ítem 2 ======================
/* - Respuesta -
Siendo que la función BoardHW se invoca originalmente cada 6ms y que a partir de la modificación
para realizar el barrido de todas las filas se requieren 3 invocaciones, se
requerira invocar a la función BoardHW cada 2ms.

* --------------------------------------------------------------------- */

//================ ítem 3 ======================

/* - nota respecto a la implementación -
  KEY: Variable global, la cual será utilizada por get_key cada vez
  que sea invocada.

  Barrido_Teclado: Dejará en KEY la última tecla presionada.
  Esta versión contempla el antirrebote, sin embargo esto no era solicitado
  en el examen.

 * --------------------------------------------------------------------- */


// Buffer de teclado
volatile char KEY=NO_KEY;

char get_key(void)
{
u8 aux;
    aux=KEY;        // se copia el valor a devolver
    KEY=NO_KEY;     // se limpia KEY
return aux;
}

//-----------------------------------------
/* nota- si se presiona más de una tecla:
 * en la misma fila - se devolverá la que primero aparezca en la fila del if.
 * en filas distintas - se devolverá NO_KEY
 */

void Barrido_Teclado (void)
{
static char anterior=NO_KEY;
char actual;
static char cont=0;
static char row=-1; // barrido inicial
char prev_row=row;
static char x_row=-1; // barrido con datos

    actual=BoardHW(row);

    ++row%=3;

    if (actual==NO_KEY)
    {
        if (prev_row==x_row) // verifica que sea la fila bajo analisis  
        {
            anterior=NO_KEY;
            cont=0;
            x_row=-1;
        }
        return;
    }

    /* nota- si se presiona más de una tecla
     * en filas distintas - se devolverá NO_KEY  */

    if (actual!=anterior)
    {
        anterior=actual;
        cont=0;
        x_row=prev_row;
        return;
    }

    cont++;

    if (cont==6)
        KEY=actual;

    if (cont>6) // por si se retiene la tecla presionada
        cont=6;

}

