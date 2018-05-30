package bt.com.druid.service.query.aggregations;

import bt.com.druid.service.query.QueryConstants;

public class ConstantPostAggregator extends PostAggregator {
	
	public String value;
	
	public ConstantPostAggregator() {
		super(QueryConstants.POST_AGGREGATOR_CONSTANT);
	}

	public ConstantPostAggregator(String name,String value) {
		super(QueryConstants.POST_AGGREGATOR_CONSTANT,name);
		this.value = value;
	}

	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	

}
