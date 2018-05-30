package bt.com.druid.service.query.aggregations;

import bt.com.druid.service.query.QueryConstants;

public class FieldPostAggregator extends PostAggregator {
	
	private String fieldName;
	
	public FieldPostAggregator() {
		super(QueryConstants.POST_AGGREGATOR_FIELDACCESS);
	}
	
	public FieldPostAggregator(String name,String fieldName) {
		super(QueryConstants.POST_AGGREGATOR_FIELDACCESS,name);
		this.fieldName=fieldName;
	}



	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	
}
