package bt.com.druid.service.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import bt.com.druid.service.query.filters.Filter;
import bt.com.druid.service.query.filters.PagingSpec;

public class SelectQuery extends BaseQuery implements Serializable{
	
	@JsonSerialize(include = Inclusion.NON_NULL)
	private boolean descending;
	
	@JsonSerialize(include = Inclusion.NON_NULL)
	private Filter filter;
	
	@JsonSerialize(include = Inclusion.NON_NULL)
	private List<String> dimensions;
	
	@JsonSerialize(include = Inclusion.NON_NULL)
	private List<String> metrics;
	
	private PagingSpec pagingSpec;
	
	private String granularity;
	
	public SelectQuery() {
		setQueryType(QueryConstants.QUERY_SELECT);
		pagingSpec = new PagingSpec();
		pagingSpec.setThreshold(10000);
		dimensions = new ArrayList<String>();
		metrics = new ArrayList<String>();
		granularity = "all";
	}
	
	public SelectQuery(PagingSpec pagingSpec) {
		setQueryType(QueryConstants.QUERY_SELECT);
		this.pagingSpec=pagingSpec;
		dimensions = new ArrayList<String>();
		metrics = new ArrayList<String>();
		this.granularity ="all";
	}

	public boolean isDescending() {
		return descending;
	}

	public void setDescending(boolean descending) {
		this.descending = descending;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public List<String> getDimensions() {
		return dimensions;
	}

	public void setDimensions(List<String> dimensions) {
		this.dimensions = dimensions;
	}

	public List<String> getMetrics() {
		return metrics;
	}

	public void setMetrics(List<String> metrics) {
		this.metrics = metrics;
	}

	public PagingSpec getPagingSpec() {
		return pagingSpec;
	}

	public void setPagingSpec(PagingSpec pagingSpec) {
		this.pagingSpec = pagingSpec;
	}
	
	public boolean addDimensions(String dimension) {
		return dimensions.add(dimension);
	}

	public boolean removeDimensions(String dimension) {
		return dimensions.remove(dimension);
	}
	
	public boolean addMetrics(String metric) {
		return metrics.add(metric);
	}

	public boolean removeMetrics(String metric) {
		return metrics.remove(metric);
	}

	public String getGranularity() {
		return granularity;
	}

	public void setGranularity(String granularity) {
		this.granularity = granularity;
	}

	@Override
	public String toString() {
		return "SelectQuery [descending=" + descending + ", filter=" + filter + ", dimensions=" + dimensions
				+ ", metrics=" + metrics + ", pagingSpec=" + pagingSpec + ", granularity=" + granularity + "]";
	}
	
	
}
