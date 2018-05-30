package bt.com.druid.service.query;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class BaseQuery implements Query , Serializable{
	
	private String queryType;
	
	private String dataSource;
		
	private List<String> intervals;
	
	@JsonIgnore
	private Date lowerInterval;
	
	@JsonIgnore
	private Date upperInterval;
	
	private Context context;
	
	public String getQueryType() {
		return queryType;
	}
	
	protected void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	
	public String getDataSource() {
		return dataSource;
	}
	
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	
	public Date getLowerInterval() {
		return lowerInterval;
	}
	
	public void setLowerInterval(Date lowerInterval) {
		this.lowerInterval = lowerInterval;
	}
	
	public Date getUpperInterval() {
		return upperInterval;
	}
	
	public void setUpperInterval(Date upperInterval) {
		this.upperInterval = upperInterval;
	}
		
	public List<String> getIntervals() {
		DateFormat ISO8601Formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		String lowerIntervalString = ISO8601Formatter.format(lowerInterval);
		String upperIntervalString = ISO8601Formatter.format(upperInterval);
		if(intervals==null || !intervals.isEmpty())
		{
			intervals = new ArrayList<String>();
		}
		intervals.add(lowerIntervalString+"/"+upperIntervalString);
		return intervals;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	

}
