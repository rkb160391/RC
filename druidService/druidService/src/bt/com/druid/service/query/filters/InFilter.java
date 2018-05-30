package bt.com.druid.service.query.filters;

import java.util.ArrayList;
import java.util.List;

import bt.com.druid.service.query.QueryConstants;

public class InFilter extends Filter{

	private String dimension;
	
	private List<String> values;
	
	public InFilter() {
		super(QueryConstants.FILTER_IN);
		values = new ArrayList<String>();
	}
	
	public InFilter(String dimension,List<String> values) {
		super(QueryConstants.FILTER_IN);
		this.dimension = dimension;
		this.values = values;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
	
	public boolean addValue(String value)
	{
		return this.values.add(value);
	}
	
	public boolean removeValue(String value)
	{
		return this.values.remove(value);
	}
}
