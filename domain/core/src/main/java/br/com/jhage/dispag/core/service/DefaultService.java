package br.com.jhage.dispag.core.service;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Alexsander Melo
 * @since 27/04/2021
 *
 */

public abstract class DefaultService<D,E> {
	
	private final CountDownLatch latch;
	private D dtoObject;
	private E modelObject;
	private String received;
	private final Class<D> modeloClass;
	
	private static final Logger logger = LogManager.getLogger(DefaultService.class);
	
	public DefaultService(Integer numberReceiverThreads, Class<D> modeloClass) {
	
		latch = new CountDownLatch(numberReceiverThreads);
		this.modeloClass = modeloClass;
	}
	
	public Boolean transformToDTO(ConsumerRecord<?, ?> consumerRecord) {
		
		this.received = new String((String) consumerRecord.value());
		return convertJsonToDTO();
	}

	public Boolean convertJsonToDTO(){
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			this.dtoObject = (D) mapper.readValue(this.received, modeloClass);
			return true;
		} catch (IOException e) {
			
			logger.error(e.getMessage());
			return false;
		}  
	}
	
	public abstract void createModelFromDtoObject();
	
	
	public String getRecordReceived() {
		
		return this.received;
	}
	
	public CountDownLatch getLatch() {
		return latch;
	}
	
	
	public D getDtoObject() {
		return dtoObject;
	}

	public E getModel() {
		
		return this.modelObject;
	}
	
	public void setModel(E e) {
		
		this.modelObject = e;
	}
	
	public void run(String... args) throws Exception {
		
		latch.await(6000, TimeUnit.DAYS);
	}
}
