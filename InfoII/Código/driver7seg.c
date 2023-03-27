// driver display 7 segmentos

#include <stdio.h>

//definen el modo del puerto,y la dirección en el micro
#define PINSEL_GPIO     0
#define SALIDA          0

#define SET_SEGMENTO(x)     SET_PINSEL(EXPANSION(x),PINSEL_GPIO)
#define SET_SEGMENTO_DIR(x) SET_DIR(EXPANSION(x),SALIDA)

//Constantes para el barrido display
#define DIGITOS             5                
#define ON                  1
#define OFF                 0
#define SET_ON_DIGITO(x)    SET_PIN(Digito[(x)],ON)
#define SET_OFF_DIGITO(x)   SET_PIN(Digito[(x)],OFF)
// Estos valores de dígito debertían estar en un archivo.h que mapee las direccione de memoria de los puertos.
#define DIGITO0             0
#define DIGITO1             1
#define DIGITO2             2
#define DIGITO3             3
#define DIGITO4             4

//Declaración de funciones
void init_7seg_display(void);
void Barrido_Display(void);
void SET_PIN(__uint8_t, __uint8_t);

__uint8_t Msg_Display[];

__uint8_t Digito[]= {DIGITO0, DIGITO1, DIGITO2, DIGITO3, DIGITO4};


void init_7seg_display(void)
{
    SET_SEGMENTO(0);
    SET_SEGMENTO(1);
    SET_SEGMENTO(2);
    SET_SEGMENTO(3);
    SET_SEGMENTO(4);
    SET_SEGMENTO(5);
    SET_SEGMENTO(6);
    SET_SEGMENTO(7);
    SET_SEGMENTO(9);
    SET_SEGMENTO(10);
    SET_SEGMENTO(11);
    SET_SEGMENTO(12);
    SET_SEGMENTO(13);
    SET_SEGMENTO(14);
    SET_SEGMENTO(15);

    SET_DIR(0);
    SET_DIR(1);
    SET_DIR(2);
    SET_DIR(3);
    SET_DIR(4);
    SET_DIR(5);
    SET_DIR(6);
    SET_DIR(7);
    SET_DIR(9);
    SET_DIR(10);
    SET_DIR(11);
    SET_DIR(12);
    SET_DIR(13);
    SET_DIR(14);
    SET_DIR(15);

    
}

void Barrido_Display(void)
{   
    __uint8_t i=DIGITOS -1;
    __uint8_t auxiliar;
    __uint8_t digito =0;

    auxiliar = MsgDisplay[digito]; // MsgDisplay buffer global para la primitiva el el barrido display

    do
    {
        SET_OFF_DIGITO(i);
        i--;
    } while (i>-1);

    SET_PIN(segmento_a, (auxiliar & 0x01)); //falta asignar el mapeo de segmento al puerto del micro
    SET_PIN(segmento_b, (auxiliar & 0x01));
    SET_PIN(segmento_c, (auxiliar & 0x01));
    SET_PIN(segmento_d, (auxiliar & 0x01));
    SET_PIN(segmento_e, (auxiliar & 0x01));
    SET_PIN(segmento_f, (auxiliar & 0x01));
    SET_PIN(segmento_g, (auxiliar & 0x01));

    i=DIGITOS -1;
    
    do
    {
        SET_ON_DIGITO(i);
        i--;
    } while (i>-1);

    
}