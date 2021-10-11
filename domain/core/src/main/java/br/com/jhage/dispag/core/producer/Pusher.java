package br.com.jhage.dispag.core.producer;

/**
 * 
 * @author Alexsander Melo
 * @since 10/10/2021
 *
 */
public interface Pusher {
	
	public Boolean push(String topic, String payload);
}
