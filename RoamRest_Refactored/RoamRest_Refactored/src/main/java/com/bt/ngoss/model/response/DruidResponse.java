package com.bt.ngoss.model.response;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.bt.ngoss.model.report.BaseLeveReport;

/**
 * 
 * @author 611022088
 *
 */
public class DruidResponse extends BaseResponse<DruidResponse> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Response> response;

	private List<BaseLeveReport> reports;
	private Set<String> headers;

	public Set<String> getHeaders() {
		return headers;
	}

	public void setHeaders(Set<String> headers) {
		this.headers = headers;
	}

	public List<Response> getResponse() {
		return response;
	}

	public void setResponse(List<Response> response) {
		this.response = response;
	}

	public List<BaseLeveReport> getReports() {
		return reports;
	}

	public void setReports(List<BaseLeveReport> reports) {
		this.reports = reports;
	}

	
	@Override
	public String toString() {
		return "DruidResponse [response=" + response + ", reports=" + reports + "]";
	}
	
	

}
