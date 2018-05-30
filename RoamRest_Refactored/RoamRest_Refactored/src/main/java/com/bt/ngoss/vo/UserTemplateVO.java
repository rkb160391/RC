package com.bt.ngoss.vo;

import java.io.Serializable;
import java.util.Map;

public class UserTemplateVO extends BaseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6920509619299421086L;
	private String operatorId;

	private String userType;
	private Long userId;

	private String dashboardId;

	private String reportType;

	private int level;
	
	private Map<String, Object> requestParam;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}

	public String getReportId() {
		return reportType;
	}

	public void setReportId(String reportId) {
		this.reportType = reportId;
	}

	public Map<String, Object> getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(Map<String, Object> requestParam) {
		this.requestParam = requestParam;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	
	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}



	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "UserTemplateVO [operatorId=" + operatorId + ", userType=" + userType + ", userId=" + userId
				+ ", dashboardId=" + dashboardId + ", reportType=" + reportType + ", level=" + level + ", requestParam="
				+ requestParam + "]";
	}


	
}
