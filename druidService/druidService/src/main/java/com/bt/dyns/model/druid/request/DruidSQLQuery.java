package com.bt.dyns.model.druid.request;

public class DruidSQLQuery implements Query{
	
	private String query;
	
	public DruidSQLQuery(String query)
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
		// TODO Auto-generated method stub
		return null;
	}
}