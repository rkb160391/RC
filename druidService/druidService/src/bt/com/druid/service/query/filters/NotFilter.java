package bt.com.druid.service.query.filters;

import bt.com.druid.service.query.QueryConstants;

public class NotFilter extends Filter{
	
	private Filter field;
	
	public NotFilter() {
	}
	
	public NotFilter(Filter field) {
		super(QueryConstants.FILTER_NOT);
		this.field = field;
	}

	public Filter getField() {
		return field;
	}

	public void setField(Filter field) {
		this.field = field;
	}
	
}
