package com.bt.ngoss.model.report;

import java.io.Serializable;
import java.util.List;

import com.bt.ngoss.vo.DashboardConfig;

public class DashboardReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3325134775188520397L;

	private String id;
	private String name;
	private String type;
	private String viewType;
	private List<DashboardConfig> config;

	private List<Report> reports;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public List<DashboardConfig> getConfig() {
		return config;
	}

	public void setConfig(List<DashboardConfig> config) {
		this.config = config;
	}

	@Override
	public String toString() {
		return "DashboardReport [id=" + id + ", name=" + name + ", type=" + type + ", viewType=" + viewType
				+ ", config=" + config + ", reports=" + reports + "]";
	}
	
	

}