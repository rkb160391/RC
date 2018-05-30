package com.bt.ngoss.model.response;

import java.util.Map;

public class RestAPISelectResponce extends DruidBaseResponse {

	private Map<String, Object> result;

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

}
