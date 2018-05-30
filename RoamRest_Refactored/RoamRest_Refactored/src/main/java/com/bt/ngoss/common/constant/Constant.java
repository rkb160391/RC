package com.bt.ngoss.common.constant;

public interface Constant {
	public final String CURRENT = "current";
	public final String PREVIOUS = "previous";
	public final String LAST = "last";
	
	public final String REAL_TIME = "realtime";
	
	/**
	 * Druid Constants
	 */
	public final String TIMESTAMP = "timestamp";
	public final String TOPN = "topN";
	public final String GROUP_BY = "groupBy";
	public final String SELECT = "select";
	public final String TIMESERIES = "timeseries";
	public final String EVENT = "event";
	public final String EVENTS = "events";
	public final String PAGING_IDENTIFIERS = "pagingIdentifiers";
	public final String LEVEL ="level";
	
	/*
	 * Report Directory constant
	 */	
	public final String EXPORT_REPORT_DIR = "/ExportReport";
	public final String SCHEDULED_REPORT_DIR = "/ScheduledReport";
	
	public final String TOTAL_MSU = "TotalMSU";
	public final String REPORT_TYPE ="reportType";
	public final String HEATMAP_FORTOP10MSU ="HeatMapForTop10MSU";
	public final String TOP10_SUCCESS_RATE ="Top10SuccessRate";
	public final String ERROR_REPORT ="ErrorReport";
	public final String DESTINATION_REPORT = "DestinationReport";
	public final String ROAMERS_REPORT = "RoamersReport";
	
	public final String INBOUND="INBOUND";
	public final String IN_IMSI="InIMSI";
	public final String OUTBOUND="OUTBOUND";
	public final String OUT_IMSI="OutIMSI";
	public final String BILLING_REPORT = "BillingReport";
	public final String ROAMERS_REJECTION_REPORT = "RoamingRejectionAnalysis";
    public final String ERROR_LISTING_HEATMAP = "ErrorListingHeatMap";
	public final String SECURITY_VIOLATION_REPORT = "SecurityViolationReport";
	
	
	
}
