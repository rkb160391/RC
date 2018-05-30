package bt.com.druid.service.query;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import bt.com.druid.service.query.aggregations.Aggregator;
import bt.com.druid.service.query.aggregations.PostAggregator;
import bt.com.druid.service.query.filters.Filter;
import bt.com.druid.service.query.filters.HavingFilter;
import bt.com.druid.service.query.filters.LimitSpec;

public class GroupByQuery extends BaseQuery {
	
	@JsonSerialize(include = Inclusion.NON_NULL)
	private Filter filter;
	
	@JsonSerialize(include = Inclusion.NON_NULL)
	private LimitSpec limitSpec;
	
	@JsonSerialize(include = Inclusion.NON_NULL)
	private HavingFilter having;
	
	@JsonSerialize(include = Inclusion.NON_NULL)
	private List<Aggregator> aggregations;
	
	@JsonSerialize(include = Inclusion.NON_NULL)
	private List<PostAggregator> postAggregations;
	
	private List<String> dimensions;
	
	private String granularity;

	
	public GroupByQuery() {
		setQueryType(QueryConstants.QUERY_GROUPBY);
		dimensions = new ArrayList<String>();
		granularity = "all";
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public LimitSpec getLimitSpec() {
		return limitSpec;
	}

	public void setLimitSpec(LimitSpec limitSpec) {
		this.limitSpec = limitSpec;
	}

	public HavingFilter getHaving() {
		return having;
	}

	public void setHaving(HavingFilter having) {
		this.having = having;
	}

	public List<Aggregator> getAggregations() {
		return aggregations;
	}

	public void setAggregations(List<Aggregator> aggregations) {
		this.aggregations = aggregations;
	}

	public List<PostAggregator> getPostAggregations() {
		return postAggregations;
	}

	public void setPostAggregations(List<PostAggregator> postAggregations) {
		this.postAggregations = postAggregations;
	}

	public List<String> getDimensions() {
		return dimensions;
	}

	public void setDimensions(List<String> dimensions) {
		this.dimensions = dimensions;
	}

	public String getGranularity() {
		return granularity;
	}

	public void setGranularity(String granularity) {
		this.granularity = granularity;
	}
	
	public boolean addAggregations(Aggregator aggregator) {
		if(aggregations == null)
		{
			aggregations = new ArrayList<Aggregator>();
		}
		return aggregations.add(aggregator);
	}

	public boolean removeAggregations(Aggregator aggregator) {
		if(aggregations == null)
		{
			return false;
		}
		return aggregations.remove(aggregator);
	}

	public boolean addPostAggregations(PostAggregator postAggregator) {
		if(postAggregations == null)
		{
			postAggregations = new ArrayList<PostAggregator>();
		}
		return postAggregations.add(postAggregator);
	}

	public boolean removePostAggregations(PostAggregator postAggregator) {
		if(postAggregations == null)
		{
			return false;
		}
		return postAggregations.remove(postAggregator);
	}

	public boolean addDimensions(String dimension) {
		return dimensions.add(dimension);
	}

	public boolean removeDimensions(String dimension) {
		return dimensions.remove(dimension);
	}
	
	
	
}
