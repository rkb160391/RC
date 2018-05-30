package com.bt.ngoss.model.common;

import java.io.Serializable;

import com.bt.dyns.model.common.Configuration;
import com.bt.dyns.model.druid.request.BaseRequest;

public class Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5787942295082168667L;
	
	private Configuration configuration;
	private BaseRequest requeststring;
	
	public Configuration getConfiguration() {
		return configuration;
	}
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	public BaseRequest getRequeststring() {
		return requeststring;
	}
	public void setRequeststring(BaseRequest requeststring) {
		this.requeststring = requeststring;
	}
	
}
