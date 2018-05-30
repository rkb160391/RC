package com.bt.ngoss.model.response;

import java.io.Serializable;

import com.bt.dyns.model.druid.request.ReportResponseUI;

public class BaseResponse<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5793271177401250834L;

	private String userTemplateId;

	private ReportResponseUI reportConfiguration;

	public String getUserTemplateId() {
		return userTemplateId;
	}

	public void setUserTemplateId(String userTemplateId) {
		this.userTemplateId = userTemplateId;
	}

	public ReportResponseUI getReportConfiguration() {
		return reportConfiguration;
	}

	public void setReportConfiguration(ReportResponseUI reportConfiguration) {
		this.reportConfiguration = reportConfiguration;
	}

	@Override
	public String toString() {
		return "BaseResponse [userTemplateId=" + userTemplateId + ", reportConfiguration=" + reportConfiguration + "]";
	}

}
