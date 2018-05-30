package bt.com.druid.service.query.filters;

import bt.com.druid.service.query.QueryConstants;

public class RegexFilter extends Filter {
	
	private String dimension;
	private String pattern;
	
	public RegexFilter() {
		super(QueryConstants.FILTER_REGEX);
	}
	
	public RegexFilter(String dimension,String pattern)
	{
		super(QueryConstants.FILTER_REGEX);
		this.dimension = dimension;
		this.pattern = pattern;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	
	

}
