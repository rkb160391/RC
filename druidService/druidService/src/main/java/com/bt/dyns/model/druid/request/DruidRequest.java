package com.bt.dyns.model.druid.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bt.dyns.constant.AggregationEnum;
import com.bt.dyns.constant.Constants;
import com.bt.dyns.model.common.Metric;
import com.bt.dyns.model.druid.common.Aggregations;
import com.bt.dyns.model.druid.common.Context;
import com.bt.dyns.model.druid.common.Filter;
import com.bt.dyns.model.druid.common.PostAggregations;
import com.bt.dyns.util.DruidRequestHelper;
import com.bt.dyns.util.FilterHelper;


public class DruidRequest extends DruidBaseRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3777697603687103094L;

	private List<Aggregations> aggregations;
	private List<PostAggregations> postAggregations;
	private Filter filter;
	private Context context;

	protected DruidRequest(DruidBaseRequestBuilder druidBaseRequestBuilder, DruidRequestBuilder druidRequestBuilder) {
		super(druidBaseRequestBuilder);
		this.aggregations = druidRequestBuilder.aggregations;
		this.postAggregations = druidRequestBuilder.postAggregations;
		this.filter = druidRequestBuilder.filter;
		this.context = druidRequestBuilder.context;
	}

	public List<Aggregations> getAggregations() {
		return aggregations;
	}

	public Filter getFilter() {
		return filter;
	}

	public List<PostAggregations> getPostAggregations() {
		return postAggregations;
	}
	
	public Context getContext(){
		return context;
	}

	public static class DruidRequestBuilder {
		private List<Aggregations> aggregations = new ArrayList<>();
		private List<PostAggregations> postAggregations = new ArrayList<>();
		private Filter filter;
		private DruidBaseRequestBuilder baseRequestBuilder;
		private Context context;

		public DruidRequestBuilder(DruidBaseRequestBuilder druidBaseRequestBuilder, ReportRequest reportrequest) {
			this.baseRequestBuilder = druidBaseRequestBuilder;
			if (null != reportrequest && null != reportrequest.getConfiguration()
					&& null != reportrequest.getConfiguration().getMetrics()) {
				List<Metric> metrics = reportrequest.getConfiguration().getMetrics();
				metrics.forEach((metric) -> {
					if (AggregationEnum.AVERAGE.getValue().equalsIgnoreCase(metric.getAggregationType())) {
						PostAggregations postAggration = new PostAggregations.PostAggregationsBuilder(
								Constants.ARITHMETIC, metric.getName(), Constants.Divide).build();
						this.postAggregations.add(postAggration);
						Aggregations countAggregation = new Aggregations.AggregationsBuilder(Constants.COUNT,
								metric.getName() + Constants.UnderScore + Constants.ROWS, null).build();
						Aggregations sumAggregation = new Aggregations.AggregationsBuilder(Constants.DoubleSum,
								metric.getName() + Constants.UnderScore + Constants.Total, metric.getName()).build();
						this.aggregations.add(countAggregation);
						this.aggregations.add(sumAggregation);
					} else {
						if (null != metric.getAggregationType() && !"".equals(metric.getAggregationType())) {
							Aggregations aggregation = new Aggregations.AggregationsBuilder(metric.getAggregationType(),
									metric.getName(), metric.getName()).build();
							this.aggregations.add(aggregation);
						}
					}
				});

			}
			//------If needed just active else block and comment if block
           
			if(reportrequest.getReportType().equals("Roamers Report")){
				if( reportrequest.getLevel()==2 || reportrequest.getLevel()==1)
					this.filter = FilterHelper.getFilterForRoamers(reportrequest.getFilter());
			}else{
				this.filter = FilterHelper.getFilter(reportrequest.getFilter());
			}
	
	
			
			this.context  = DruidRequestHelper.getContext(reportrequest.getConfiguration().getContext());
		}

		public DruidRequest build() {
			return new DruidRequest(baseRequestBuilder, this);
		}

	}

}
