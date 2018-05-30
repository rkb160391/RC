package com.bt.ngoss.service.inventory;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class CreateKafkaProducer {
	
	private static CreateKafkaProducer instance =  new CreateKafkaProducer();
	private static final String KAFKAQ_TOPIC_NAME = "Roam_inventory";
	private static final String MESSAGING_CONFIG_PATH = "/kafkaConfig.properties";
	private static Logger logger = Logger.getLogger(CreateKafkaProducer.class);
	
	
	
	public static CreateKafkaProducer getInstance(){
		return instance;
	}
	
	private KafkaProducer<String, String> createKafkaProducer(){
		KafkaProducer<String, String> kafkaProducer = null;
		Resource resource = new ClassPathResource(MESSAGING_CONFIG_PATH);
		Properties props = null;
		try {
			 props = PropertiesLoaderUtils.loadProperties(resource);

				kafkaProducer = new KafkaProducer<String, String>(props);
				logger.debug("initiated KAFKA Producer for ::  " +KAFKAQ_TOPIC_NAME );
				
				if (StringUtils.isEmpty(props.getProperty(KAFKAQ_TOPIC_NAME, null))) {
					logger.debug("KAFKAQ_TOPIC_NAME is not defined in property file :" + KAFKAQ_TOPIC_NAME);
				}
				return kafkaProducer;

		} catch (Exception e) {
			if(props == null || props.size() == 0){
				logger.error("Failed to load KAFKA property for ::  " + MESSAGING_CONFIG_PATH +".properties");
			}else{
			  logger.error("Exception in createKafkaProducer while creating KAFKA message queue:  reason: " + e.getMessage());
			}
		}
		return kafkaProducer;
	}
	

	public static void sendKafkaMessage(List<String>inventedRows){
		KafkaProducer<String, String> kafkaProducer = CreateKafkaProducer.getInstance()
				.createKafkaProducer();
		for (String inventedRow : inventedRows) {
				kafkaProducer.send(new ProducerRecord<String, String>(KAFKAQ_TOPIC_NAME.trim(), inventedRow));
			}
				
	}	

}
