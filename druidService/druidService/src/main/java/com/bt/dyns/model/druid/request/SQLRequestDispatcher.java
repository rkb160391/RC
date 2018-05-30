package com.bt.dyns.model.druid.request;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.bt.dyns.model.druid.common.SQLFilter;
import com.mockrunner.mock.jdbc.MockResultSet;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

public class SQLRequestDispatcher {

	private String requestURL;

	public SQLRequestDispatcher(String requestURL) {
		this.requestURL = requestURL;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	public ResultSet sendRequest(Query query) throws SQLException, JsonMappingException, JsonGenerationException {
		try {
			// Creating REST URL client to send request
			Client client = Client.create();
			
			//WebResource resource = client.resource("http://172.25.182.147:8082/druid/v2/sql");
			WebResource resource = client.resource(requestURL);
			
			// Converting query object into the String Json
			ObjectMapper mapper = new ObjectMapper();
			String queryJson = mapper.writeValueAsString(query);
			System.out.println(queryJson);
			// Sending query to Broker
			ClientResponse response = resource.type("application/json").post(ClientResponse.class, queryJson);

			if (response.getStatus() == Status.OK.getStatusCode()) {
					return handleSQLJSON(response);
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
			for (int i=0;i<resultSet.getMetaData().getColumnCount();i++) {
				values.add(result.get(resultSet.getMetaData().getColumnName(i+1)));
			}
			resultSet.addRow(values);
		}
		return resultSet;
	}
	
	
	public String prepareSQLSelectQuery(SQLSelectRequest sqlSelectRequest) throws ParseException{
		String sqlQuery="SELECT __time,NodeName,SubElementID,";
		if(null!=sqlSelectRequest.getColumns()){
			for (String column : sqlSelectRequest.getColumns()) {
				sqlQuery+=" \""+column+"\",";
			}
		}
		//remove extra comma.
		sqlQuery=sqlQuery.substring(0, sqlQuery.length() - 1);
		
		sqlQuery+=" FROM "+sqlSelectRequest.getDatasource()+" where NodeName='"+sqlSelectRequest.getNodeName()+"' and (";
		
		//added filter
		if(null!=sqlSelectRequest.getFilter()){
			for (SQLFilter filter : sqlSelectRequest.getFilter()) {
				sqlQuery+=" \""+filter.getDimension()+"\""+filter.getFunction()+filter.getValue()+" "+filter.getType();
			}
		}
		//remove extra 'or' 
		sqlQuery=sqlQuery.replaceAll(" \\S*$", "");
		
		String interval=convertSQLDateFormat(sqlSelectRequest.getInterval());
		sqlQuery+=") AND __time > TIMESTAMP '"+interval.split("/")[0]+"' AND __time < TIMESTAMP '"+interval.split("/")[1]+"' LIMIT "+sqlSelectRequest.getLimit();
		return sqlQuery;
	}
	
	private String convertSQLDateFormat(String interval) throws ParseException{
		SimpleDateFormat dateFomatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); 
		Date startDate = dateFomatter.parse(interval.split("/")[0]); 
		Date endDate = dateFomatter.parse(interval.split("/")[1]); 
		SimpleDateFormat applyDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return applyDateFormatter.format(startDate)+"/"+applyDateFormatter.format(endDate);
	}
}
