package bt.com.druid.service.query.aggregations;

public abstract class PostAggregator {
	private String type;
	private String name;
	
	public PostAggregator() {
		
	}
	
	public PostAggregator(String type)
	{
		this.type = type;
	}
	
	public PostAggregator(String type,String name)
	{
		this.type = type;
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	protected void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
