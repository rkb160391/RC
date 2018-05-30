package bt.com.druid.service.query.filters;

import java.io.Serializable;

public abstract class Filter implements Serializable {
	private String type;
	
	protected Filter() {
	}
	
	protected Filter(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	protected void setType(String type) {
		this.type = type;
	}
	
	
}
