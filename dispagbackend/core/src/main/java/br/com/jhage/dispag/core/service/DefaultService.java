package br.com.jhage.dispag.core.service;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jhage.dispag.core.exception.ConvertJsonToModelDataException;

/**
 * 
 * @author Alexsander Melo
 * @since 27/04/2021
 *
 */

public abstract class DefaultService<T> {
	
	private final CountDownLatch latch;
	private T modelo;
	private String received;
	private final Class<T> modeloClass;
	
	
	public DefaultService(Integer numberReceiverThreads, Class<T> modeloClass) {
	
		latch = new CountDownLatch(numberReceiverThreads);
		this.modeloClass = modeloClass;
	}
	
	public void transformToBusinessData(ConsumerRecord<?, ?> consumerRecord) throws ConvertJsonToModelDataException {
		
		this.received = new String((String) consumerRecord.value());
		convertJsonToModelData();
	}
	
	
	public void convertJsonToModelData() throws ConvertJsonToModelDataException{
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			this.modelo = (T) mapper.readValue(this.received, modeloClass);
		} catch (IOException e) {
			
			throw new ConvertJsonToModelDataException(e.getMessage());
		}  
	}
	
	public CountDownLatch getLatch() {
		return latch;
	}
	
	
	public T getModelo() {
		return modelo;
	}

	
	public void run(String... args) throws Exception {
		
		latch.await(600, TimeUnit.SECONDS);
	}
}
