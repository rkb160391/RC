package com.bt.ngoss.client.druid;

import org.apache.log4j.Logger;

import com.bt.dyns.model.druid.request.DruidGroupByRequest;
import com.bt.dyns.model.druid.request.DruidSelectRequest;
import com.bt.dyns.model.druid.request.DruidTimeseriesRequest;
import com.bt.dyns.model.druid.request.DruidTopNRequest;
import com.bt.dyns.model.druid.request.ReportRequestQuery;
import com.bt.ngoss.model.response.DruidBaseResponse;
import com.bt.ngoss.model.response.DruidGroupByResponse;
import com.bt.ngoss.model.response.DruidSelectResponse;
import com.bt.ngoss.model.response.DruidTimeSeriesResponse;
import com.bt.ngoss.model.response.DruidTopNResponse;
import com.bt.ngoss.util.ObjectFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author 611022163
 *
 * @param <T>
 */
public class DruidRestClient<T extends DruidBaseResponse> extends AbstractRestClient {

	private static final Logger logger = Logger.getLogger(DruidRestClient.class);
	private ObjectMapper mapper = ObjectFactory.getObjectMapper();

	public T[] druidResponse(String uri, Object request) throws Exception {

		// This is purpose of logging response it will disable after successful
		// test
		Object json = mapper.readValue(mapper.writeValueAsString(request), Object.class);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));

		if (request instanceof ReportRequestQuery) {
			ReportRequestQuery requestQuery = (ReportRequestQuery) request;

			switch (requestQuery.getQueryType()) {
			case "select":
				return (T[]) post(uri, request, DruidSelectResponse[].class);
			case "topN":
				return (T[]) post(uri, request, DruidTopNResponse[].class);
			case "groupBy":
				return (T[]) post(uri, request, DruidGroupByResponse[].class);
			case "timeseries":
				return (T[]) post(uri, request, DruidTimeSeriesResponse[].class);

			}

		}
		return null;
	}

}