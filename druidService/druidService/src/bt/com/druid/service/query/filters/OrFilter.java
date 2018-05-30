package bt.com.druid.service.query.filters;

import java.util.List;

import bt.com.druid.service.query.QueryConstants;

public class OrFilter extends AndFilter {
	public OrFilter() {
		setType(QueryConstants.FILTER_OR);
	}
	
	public OrFilter(List<Filter> fields)
	{
		super(fields);
		setType(QueryConstants.FILTER_OR);
	}
}
