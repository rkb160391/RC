package com.bt.dyns.model.druid.request;

import java.io.Serializable;

import com.bt.dyns.model.common.Configuration;
import com.bt.dyns.model.common.Pagination;
import com.bt.dyns.model.common.Rules;

public class ReportRequest implements Serializable {
	/**
	 */
	private static final long serialVersionUID = -4855407669924208756L;

	private Configuration configuration;
	private Rules filter;
	private String samplingPeriod;
	private String intervals;
	private Pagination pagination;
	private String granularity;
	private int level;
	private String datasource;
	private int totalRecords;
	private String reportType;
	
	

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getGranularity() {
		return granularity;
	}

	public void setGranularity(String granularity) {
		this.granularity = granularity;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public Rules getFilter() {
		return filter;
	}

	public void setFilter(Rules filter) {
		this.filter = filter;
	}

	public String getSamplingPeriod() {
		return samplingPeriod;
	}

	public void setSamplingPeriod(String samplingPeriod) {
		this.samplingPeriod = samplingPeriod;
	}

	public String getIntervals() {
		return intervals;
	}

	public void setIntervals(String intervals) {
		this.intervals = intervals;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	@Override
	public String toString() {
		return "ReportRequest [configuration=" + configuration + ", filter=" + filter + ", samplingPeriod="
				+ samplingPeriod + ", intervals=" + intervals + ", pagination=" + pagination + ", granularity="
				+ granularity + ", level=" + level + ", datasource=" + datasource + ", totalRecords=" + totalRecords
				+ ", reportType=" + reportType + "]";
	}
}
