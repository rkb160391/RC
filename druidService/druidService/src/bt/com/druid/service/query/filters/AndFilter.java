package bt.com.druid.service.query.filters;

import java.util.ArrayList;
import java.util.List;

import bt.com.druid.service.query.QueryConstants;

public class AndFilter extends Filter {
	
	private List<Filter> fields;
	
	public AndFilter() {
		super(QueryConstants.FILTER_AND);
		fields = new ArrayList<Filter>();
	}
	
	public AndFilter(List<Filter> fields)
	{
		super(QueryConstants.FILTER_AND);
		this.fields = fields;
	}

	public List<Filter> getFields() {
		return fields;
	}

	public void setFields(List<Filter> fields) {
		this.fields = fields;
	}
	
	public boolean addField(Filter filter)
	{
		return fields.add(filter);
	}
	
	public boolean removeField(Filter filter)
	{
		return fields.remove(filter);
	}
}
