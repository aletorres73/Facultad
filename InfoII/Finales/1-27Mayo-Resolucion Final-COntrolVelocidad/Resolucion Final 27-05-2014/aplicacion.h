#ifndef APLICACION_H_
#define APLICACION_H_

	#define DISTANCIA (1/1000)    //La distancia de un pulso en km
	#define TIEMPO_DETENIDO  800   //La interrupcion del Systick cada 2.5ms
	#define FRECUENCIA_TIMERHR  (0.025/3600)  //La frecuencia del timer en hr para calcular la vel en km/h


	void CalculaVelocidad();

#endif /* KIT_INFO2_H_ */
