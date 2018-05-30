package bt.com.druid.service.query.filters;

import java.util.ArrayList;
import java.util.List;

public class LogicalHavingFilter extends HavingFilter{

	private List<HavingFilter> havingSpecs;

	public List<HavingFilter> getHavingSpecs() {
		return havingSpecs;
	}

	public void setHavingSpecs(List<HavingFilter> havingSpecs) {
		this.havingSpecs = havingSpecs;
	}
	
	
	public boolean addHavingSpecs(HavingFilter filter)
	{
		if(havingSpecs == null)
		{
			havingSpecs = new ArrayList<HavingFilter>();
		}
		return havingSpecs.add(filter);
	}
	
	public boolean removeHavingSpecs(HavingFilter filter)
	{
		if(havingSpecs == null)
		{
			havingSpecs = new ArrayList<HavingFilter>();
		}
		return havingSpecs.remove(filter);
	}
	
	
}
