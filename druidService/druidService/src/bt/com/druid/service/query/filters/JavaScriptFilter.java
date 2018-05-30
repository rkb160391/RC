package bt.com.druid.service.query.filters;

import bt.com.druid.service.query.QueryConstants;

public class JavaScriptFilter extends Filter{

	private String dimension;
	
	private String function;
	
	public JavaScriptFilter() {
		super(QueryConstants.FILTER_JAVASCRIPT);
	}

	public JavaScriptFilter(String dimension,String function) {
		super(QueryConstants.FILTER_JAVASCRIPT);
		this.dimension = dimension;
		this.function = function;
	}
	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}	
}
