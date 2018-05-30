package bt.com.druid.service.query.filters;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PagingSpec implements Serializable{
	
	private Map<String,String> pagingIdentifiers;
	
	private Integer threshold;
	
	private boolean fromNext;
	
	public PagingSpec() {
		this.pagingIdentifiers = new HashMap<String,String>();
	}

	public Map<String, String> getPagingIdentifiers() {
		return pagingIdentifiers;
	}

	public void setPagingIdentifiers(Map<String, String> pagingIdentifiers) {
		this.pagingIdentifiers = pagingIdentifiers;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public boolean isFromNext() {
		return fromNext;
	}

	public void setFromNext(boolean fromNext) {
		this.fromNext = fromNext;
	}
	
	public void addPagingIdentifier(String key,String value)
	{
		pagingIdentifiers.put(key, value);
	}
	
	public void removePagingIdentifier(String key)
	{
		pagingIdentifiers.remove(key);
	}

}
