package bt.com.druid.service.query.filters;

import bt.com.druid.service.query.QueryConstants;

public class SelectorFilter extends Filter{
	
	private String dimension;
	private String value;
	
	public SelectorFilter() {
		super(QueryConstants.FILTER_SELECTOR);
	}
	
	public SelectorFilter(String dimension,String value) {
		super(QueryConstants.FILTER_SELECTOR);
		this.dimension=dimension;
		this.value=value;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
