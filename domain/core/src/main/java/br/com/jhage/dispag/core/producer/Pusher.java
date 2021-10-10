package br.com.jhage.dispag.core.producer;


public interface Pusher {
	
	public void push(String topic, String payload);
}
