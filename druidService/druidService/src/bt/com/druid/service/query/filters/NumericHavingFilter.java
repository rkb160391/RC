package bt.com.druid.service.query.filters;

public class NumericHavingFilter extends HavingFilter{
	
	private String aggregation;
	private String value;
	
	public String getAggregation() {
		return aggregation;
	}
	public void setAggregation(String aggregation) {
		this.aggregation = aggregation;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
