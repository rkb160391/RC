package bt.com.druid.service.query.aggregations;

import java.util.ArrayList;
import java.util.List;

import bt.com.druid.service.query.QueryConstants;

public class ArithmeticPostAggregator extends PostAggregator {

	private String fn;
	private List<Aggregator> fields;
	private String ordering;

	public ArithmeticPostAggregator() {
		super(QueryConstants.POST_AGGREGATOR_ARITHMETIC);
		fields =  new ArrayList<Aggregator>();
	}

	public String getFn() {
		return fn;
	}

	public void setFn(String fn) {
		this.fn = fn;
	}

	public List<Aggregator> getFields() {
		return fields;
	}

	public void setFields(List<Aggregator> fields) {
		this.fields = fields;
	}

	public boolean addFields(Aggregator aggregation) {
		return fields.add(aggregation);
	}

	public boolean removeFields(Aggregator aggregation) {
		return fields.remove(aggregation);
	}
	
	public String getOrdering() {
		return ordering;
	}

	public void setOrdering(String ordering) {
		this.ordering = ordering;
	}
	
	
	
}
