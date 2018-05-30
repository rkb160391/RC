package bt.com.druid.service.query.filters;

public class DimensionHavingFilter extends HavingFilter{
	
	private String dimension;
	private String value;
	
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
