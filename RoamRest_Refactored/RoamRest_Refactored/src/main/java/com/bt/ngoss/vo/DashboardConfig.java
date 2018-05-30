package com.bt.ngoss.vo;

public class DashboardConfig {

	private String reportname;
	private String reportType;
	private String reportid;
	private Policy reportPolicy;
	
	public String getReportname() {
		return reportname;
	}
	public void setReportname(String reportname) {
		this.reportname = reportname;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getReportid() {
		return reportid;
	}
	public void setReportid(String reportid) {
		this.reportid = reportid;
	}
	public Policy getReportPolicy() {
		return reportPolicy;
	}
	public void setReportPolicy(Policy reportPolicy) {
		this.reportPolicy = reportPolicy;
	}
	@Override
	public String toString() {
		return "DashboardConfig [reportname=" + reportname + ", reportType=" + reportType + ", reportid=" + reportid
				+ ", reportPolicy=" + reportPolicy + "]";
	}
	
	
	


}
