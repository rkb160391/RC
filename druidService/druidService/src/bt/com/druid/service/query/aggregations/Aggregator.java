package bt.com.druid.service.query.aggregations;

public class Aggregator {
	private String type;
	private String name;
	private String fieldName;
	
	public Aggregator() {
	}

	public Aggregator(String type,String name,String fieldName) {
		this.type = type;
		this.name = name;
		this.fieldName = fieldName;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	
}
