package bt.com.druid.service.query;

import java.io.Serializable;

public class Context implements Serializable {

	private long timeout;
	private String queryId;
	private long queryPriority;

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public long getQueryPriority() {
		return queryPriority;
	}

	public void setQueryPriority(long queryPriority) {
		this.queryPriority = queryPriority;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
}
