package com.bt.ngoss.model.response;

import java.util.Map;

public class DruidGroupByResponse extends DruidBaseResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2961538125986369283L;
	private String version;
	private String timestamp;
	private Map<String, Object> event;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map<String, Object> getEvent() {
		return event;
	}

	public void setEvent(Map<String, Object> event) {
		this.event = event;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "DruidGroupByResponse [version=" + version + ", timestamp=" + timestamp + ", event=" + event + "]";
	}

	

}
