package bt.com.druid.service.query.filters;

public class OrderByColumnSpec implements LimitSpec{
	
	private String dimension;
	private String direction;
	private String dimensionOrder;
	
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getDimensionOrder() {
		return dimensionOrder;
	}
	public void setDimensionOrder(String dimensionOrder) {
		this.dimensionOrder = dimensionOrder;
	}

	
}
