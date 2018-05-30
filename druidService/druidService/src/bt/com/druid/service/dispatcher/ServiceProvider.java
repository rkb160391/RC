package bt.com.druid.service.dispatcher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import bt.com.druid.service.query.SelectQuery;

public class ServiceProvider {
	final static Logger logger = Logger.getLogger(ServiceProvider.class);
	private static String druidBrokerIP;
	private static int port;
	private static  String resourceURL;
	private static String requestURL;
	public static Properties prop = new Properties();
	
	private  Client client = Client.create();
	private ObjectMapper mapper = new ObjectMapper();
	
	
	public ServiceProvider() throws FileNotFoundException, IOException{
		if(prop.isEmpty()){
		prop.load(new FileInputStream("config/druid/druidQuery.properties") );
		setProperties();
		}
	}
	
	private void setProperties() {
		druidBrokerIP = prop.getProperty("druid.broker.ip");
		port = Integer.parseInt(prop.getProperty("druid.broker.port"));
		resourceURL = prop.getProperty("druid.broker.jsonResourceURL");
		requestURL = "http://" + druidBrokerIP + ":" + port + "/" + resourceURL;
	}
	
	
	
	public ClientResponse serveRequest(SelectQuery query) throws Exception {
		WebResource resource = client.resource(requestURL);
		String queryJson = mapper.writeValueAsString(query);
		logger.info(" serveRequest Query "+queryJson);
		return resource.type("application/json").post(ClientResponse.class, queryJson);
	}
	
	public JsonNode serveRequestAsJsonNode(SelectQuery query) throws Exception {
		ClientResponse clienresponse=serveRequest(query);
		JsonNode root = mapper.readTree(clienresponse.getEntityInputStream());
		return root;
	}
}
