package bt.com.druid.service.query;

public class SQLQuery implements Query{
	
	private String query;
	
	public SQLQuery(String query)
	{
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String getQueryType() {
		return null;
	}
	
}
