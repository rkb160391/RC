package com.bt.dyns.model.druid.request;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.bt.dyns.model.druid.common.SQLFilter;

public class SQLSelectRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6513819816691089783L;
	
	String datasource;
	
	String interval;
	
	String nodeName;
	
	Set<String> columns;
	
	List<SQLFilter> filter;
	
	int limit;
	
	public Set<String> getColumns() {
		return columns;
	}
	public void setColumns(Set<String> columns) {
		this.columns = columns;
	}
	public String getDatasource() {
		return datasource;
	}
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public List<SQLFilter> getFilter() {
		return filter;
	}
	public void setFilter(List<SQLFilter> filter) {
		this.filter = filter;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	@Override
	public String toString() {
		return "SQLSelectRequest [datasource=" + datasource + ", interval=" + interval + ", nodeName=" + nodeName
				+ ", columns=" + columns + ", filter=" + filter + ", limit=" + limit + "]";
	}
}
