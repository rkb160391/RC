package bt.com.druid.service.dispatcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.mockrunner.mock.jdbc.MockResultSet;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

import bt.com.druid.service.query.BaseQuery;
import bt.com.druid.service.query.GroupByQuery;
import bt.com.druid.service.query.Query;
import bt.com.druid.service.query.QueryConstants;
import bt.com.druid.service.query.SQLQuery;
import bt.com.druid.service.query.SelectQuery;
import bt.com.druid.service.query.aggregations.Aggregator;

public class RequestDispatcher<T> {

	private String druidBrokerIP;
	private int port;
	private String resourceURL;
	private String requestURL;
	private ObjectMapper mapper = new ObjectMapper();
	private Properties prop = new Properties();
	private Logger logger = Logger.getLogger(RequestDispatcher.class);
	private Client client = Client.create();

	public RequestDispatcher() {
		try {
			prop.load(new FileInputStream("config/druid/druidQuery.properties"));
			setProperties();
		} catch (Exception e) {
			logger.error("No query.properties found.");
		}
	}

	public RequestDispatcher(String propertiesFileOrRequestURL, boolean isRequestURL) {
		if (!isRequestURL) {
			try {
				prop.load(new FileInputStream(propertiesFileOrRequestURL));
				setProperties();
			} catch (Exception e) {
				logger.error("No " + propertiesFileOrRequestURL + " found.");
			}
		} else {
			requestURL = propertiesFileOrRequestURL;
		}
	}

	private void setProperties() {
		druidBrokerIP = prop.getProperty("druid.broker.ip");
		port = Integer.parseInt(prop.getProperty("druid.broker.port"));
		resourceURL = prop.getProperty("druid.broker.jsonResourceURL");
		requestURL = "http://" + druidBrokerIP + ":" + port + "/" + resourceURL;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	/*public <T> serveRequest17(Query query,Class<T> claz) throws Exception {

		return ( client.resource(requestURL).type("application/json").post(claz.class, mapper.writeValueAsString(query)));

	}
	*/

	
	//Druid response returning in ClientResponse 
	public ClientResponse serveRequest(Query query) throws Exception {

		return client.resource(requestURL).type("application/json").post(ClientResponse.class,
				mapper.writeValueAsString(query));

	}
	
	public ResultSet sendRequest(Query query) throws SQLException, JsonMappingException, JsonGenerationException {
		try {
			// Creating REST URL client to send request
			Client client = Client.create();
			WebResource resource = client.resource(requestURL);
			System.out.println(requestURL);
			// Converting query object into the String Json
			ObjectMapper mapper = new ObjectMapper();
			String queryJson = mapper.writeValueAsString(query);
			System.out.println("queryJson : " + queryJson);
			// Sending query to Broker

			ClientResponse response = resource.type("application/json").post(ClientResponse.class, queryJson);

			if (response.getStatus() == Status.OK.getStatusCode()) {
				if (query instanceof BaseQuery) {
					if (query.getQueryType().equals(QueryConstants.QUERY_GROUPBY) && query instanceof GroupByQuery) {
						return handleGroupByJson(response, query);
					}
					if (query.getQueryType().equals(QueryConstants.QUERY_SELECT) && query instanceof SelectQuery) {
						return handleSelectJson(response, query);
					}
				} else if (query instanceof SQLQuery) {
					return handleSQLJSON(response);
				}
			} else {
				JsonNode root = mapper.readTree(response.getEntityInputStream());
				if (root.size() > 1) {
					throw new SQLException(root.path("errorMessage").toString());

				} else {
					throw new SQLException("Didn't get Any Message from Druid");
				}
			}
		} catch (JsonGenerationException exception) {
			// While creating query
			throw exception;
		} catch (JsonMappingException exception) {
			// While parsing the result
			throw exception;
		} catch (IOException exception) {
			// while Reading
			throw new JsonMappingException(exception.getMessage());
		} catch (SQLException exception) {
			// While moving to next row
			throw exception;
		} catch (Exception exception) {
			// While Adding row to result Set
			throw new SQLException();
		}
		return new MockResultSet("Blank Resultset");
	}

	private ResultSet handleGroupByJson(ClientResponse response, Query query) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getEntityInputStream());
		GroupByQuery groupByQuery = (GroupByQuery) query;
		MockResultSet resultSet = new MockResultSet(response.getHeaders().get("X-Druid-Query-Id").get(0));
		for (String dimension : groupByQuery.getDimensions()) {
			resultSet.addColumn(dimension);
		}
		for (Aggregator aggregator : groupByQuery.getAggregations()) {
			resultSet.addColumn(aggregator.getName());
		}
		resultSet.addColumn("timeStamp");
		for (JsonNode event : root) {
			HashMap<String, Object> result = mapper.readValue(event.path("event"),
					new TypeReference<Map<String, Object>>() {
					});
			if ((event.path("timestamp")) == null || "".toString().equals(event.path("timestamp"))) {
				result.put("timestamp", "");
			} else {
				result.put("timeStamp", event.path("timestamp").toString().replaceAll("\"", ""));
			}
			List values = new ArrayList();
			for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
				values.add(result.get(resultSet.getMetaData().getColumnName(i + 1)));
			}
			resultSet.addRow(values);
		}
		return resultSet;

	}

	private void handleSelectJson() {

	}

	private ResultSet handleSelectJson(ClientResponse response, Query query) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getEntityInputStream());
		SelectQuery selectQuery = (SelectQuery) query;
		MockResultSet resultSet = new MockResultSet(response.getHeaders().get("X-Druid-Query-Id").get(0));
		resultSet.addColumn("timestamp");
		for (String dimension : selectQuery.getDimensions()) {
			resultSet.addColumn(dimension);
		}
		if (selectQuery.getDimensions().size() == 0) {
			ArrayList<String> dimensions = mapper.readValue(root.get(0).path("result").path("dimensions"),
					ArrayList.class);
			for (String dimension : dimensions) {
				resultSet.addColumn(dimension);
			}
		}
		for (String metric : selectQuery.getMetrics()) {
			resultSet.addColumn(metric);
		}
		if (root.size() > 0) {
			for (JsonNode jsonNode : root) {
				System.out.println("==" + jsonNode.asText());
				JsonNode events = jsonNode.path("result").path("events");
				for (JsonNode event : events) {
					HashMap<String, Object> result = mapper.readValue(event.path("event"),
							new TypeReference<Map<String, Object>>() {
							});
					List values = new ArrayList();
					for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
						values.add(result.get(resultSet.getMetaData().getColumnName(i + 1)));
					}
					resultSet.addRow(values);
				}
			}
		}
		return resultSet;

	}

	private ResultSet handleSQLJSON(ClientResponse response) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getEntityInputStream());
		MockResultSet resultSet = new MockResultSet(Thread.currentThread().getName());
		if (root.size() > 0) {
			HashMap<String, Object> result = mapper.readValue(root.get(0), new TypeReference<Map<String, Object>>() {
			});
			for (String column : result.keySet()) {
				resultSet.addColumn(column);
			}
		}
		for (JsonNode jsonNode : root) {
			HashMap<String, Object> result = mapper.readValue(jsonNode, new TypeReference<Map<String, Object>>() {
			});
			List values = new ArrayList();
			for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
				values.add(result.get(resultSet.getMetaData().getColumnName(i + 1)));
			}
			resultSet.addRow(values);
		}
		return resultSet;
	}
}
