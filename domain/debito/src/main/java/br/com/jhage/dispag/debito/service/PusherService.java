package br.com.jhage.dispag.debito.service;

/**
 * 
 * @author Alexsander Melo
 * @since 10/10/2021
 *
 */
public interface PusherService {
	
	public Boolean push(String topic, String payload);
}
