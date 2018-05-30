package bt.com.druid.service.query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public interface QueryConstants {
	
	public static final String QUERY_GROUPBY = "groupBy";
	public static final String QUERY_SELECT = "select";
	
	public static final String FILTER_SELECTOR = "selector";
	public static final String FILTER_REGEX = "regex";
	public static final String FILTER_AND = "and";
	public static final String FILTER_OR = "or";
	public static final String FILTER_NOT = "not";
	public static final String FILTER_JAVASCRIPT = "javascript";
	public static final String FILTER_IN = "in";
	
	public static final String POST_AGGREGATOR_ARITHMETIC = "arithmetic";
	public static final String POST_AGGREGATOR_FIELDACCESS ="fieldAccess";
	public static final String POST_AGGREGATOR_CONSTANT = "constant";
	
}
