package com.bt.ngoss.model.report;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "USER_TEMPLATE")
public class UserTemplate implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_TEMPLATE_ID", unique = true, nullable = false)
	private String reportid;

	@Column(name = "REPORT_TYPE", nullable = true)
	private String reportType;

	@Lob
	@Column(name = "QUERY", nullable = false)
	private byte[] jsonString;

	@Column(name = "LEVEL", nullable = false)
	private String level;

	@Column(name = "TYPE", nullable = false)
	private String type;

	@Column(name = "QUERY_TYPE", nullable = false)
	private String query_type;

	@Lob
	@Column(name = "REPORT_RESPONSE_ID", nullable = false)
	private byte[] responseTemplate;
	// private ResponseTemplate responseTemplate;

	@Column(name = "DASHBOARD_ID", nullable = false)
	private String dashboardId;

	
	public String getReportid() {
		return reportid;
	}

	public void setReportid(String reportid) {
		this.reportid = reportid;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public byte[] getJsonString() {
		return jsonString;
	}

	public void setJsonString(byte[] jsonString) {
		this.jsonString = jsonString;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}

	public byte[] getResponseTemplate() {
		return responseTemplate;
	}

	public void setResponseTemplate(byte[] responseTemplate) {
		this.responseTemplate = responseTemplate;
	}

	public String getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}

	@Override
	public String toString() {
		return "UserTemplate [reportid=" + reportid + ", reportType=" + reportType + ", jsonString="
				+ Arrays.toString(jsonString) + ", level=" + level + ", type=" + type + ", query_type=" + query_type
				+ ", responseTemplate=" + Arrays.toString(responseTemplate) + ", dashboardId=" + dashboardId + "]";
	}

}
