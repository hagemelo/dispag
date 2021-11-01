package br.com.jhage.dispag.core.helper;

public abstract class TimerHelperImp implements TimerHelper {
	
	private long startTime;
	
	public TimerHelperImp() {
	}

	// Inicializa o tempo
	@Override
	public void startTime() {

		this.startTime = System.nanoTime();
	}

	// Finaliza o tempo e devolve o resultado
	@Override
	public String endTime() {

		long endTime = System.nanoTime();
		long duration = (endTime - this.startTime);
		return String.valueOf(duration);
	}

}
