package com.bt.ngoss.model.report;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
public class DashboardKey implements Serializable{

	
	@Column(name = "ID", nullable=false)
	private String dashboardId;
	
	@Column(name="TYPE")
	private String type;
	
	@Column(name="USER_ID")
	private long userId;

	public String getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "DashboardKey [dashboardId=" + dashboardId + ", type=" + type + ", userId=" + userId + "]";
	}

	
	
	
}
