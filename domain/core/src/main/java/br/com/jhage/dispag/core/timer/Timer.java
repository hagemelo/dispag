package br.com.jhage.dispag.core.timer;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Alexsander Melo
 * @since 10/10/2021
 * Objeto com função de calcular o tempo de execução dos metodos
 *
 */
@Component
public class Timer {
	
	private long startTime;
	
	public static Timer newInstance() {
		
		return new Timer();
	}
	
	public Timer() {}
	
	//Inicializa o tempo
	public void startTime() {
		
		this.startTime = System.nanoTime();
	}
	
	//Finaliza o tempo e devolve o resultado
	public String endTime() {
		
		long endTime = System.nanoTime();
		long duration = (endTime - this.startTime);
		return String.valueOf(duration);
	}

}
