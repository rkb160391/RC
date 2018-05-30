package bt.com.druid.service.query.filters;

import java.util.ArrayList;
import java.util.List;

public class DefaultLimitSpec implements LimitSpec {
	private String type;
	private String limit;
	private List<String> columns;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	
	public boolean addColumn(String column)
	{
		if(columns==null)
		{
			columns= new ArrayList<String>();
		}
		return columns.add(column);
	}
	
	
	public boolean removeColumn(String column)
	{
		if(columns==null)
		{
			columns= new ArrayList<String>();
		}
		return columns.remove(column);
	}
	
	
	

}
