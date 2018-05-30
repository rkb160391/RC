package com.bt.eenpm.indexing.rest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ServiceProvider{
	final static Logger logger = Logger.getLogger(ServiceProvider.class);
	private  String druidBrokerIP;
	private  int port;
	private  String resourceURL;
	private  String requestURL;
	public static Properties prop = new Properties();
	
	private  Client client = Client.create();

	public ServiceProvider(String druidBrokerIP,int port, String resourceURL) throws FileNotFoundException, IOException{
		/*if(prop.isEmpty()){
		prop.load(new FileInputStream("config/druid/druidQuery.properties") );
		setProperties();
		}*/
		this.druidBrokerIP=druidBrokerIP;
		this.port=port;
		this.resourceURL=resourceURL;
		setProperties();
	}
	
	private void setProperties() {
		/*druidBrokerIP = prop.getProperty("druid.broker.ip");
		port = Integer.parseInt(prop.getProperty("druid.broker.port"));
		resourceURL = prop.getProperty("druid.broker.jsonResourceURL");*/
		
		requestURL = "http://" + druidBrokerIP + ":" + port + "/" + resourceURL;
	}
	
	public ClientResponse serveRequest(String queryJson) throws Exception {
		WebResource resource = client.resource(requestURL);
		return resource.type("application/json").post(ClientResponse.class, queryJson);
		//return null;
	}

	
}
