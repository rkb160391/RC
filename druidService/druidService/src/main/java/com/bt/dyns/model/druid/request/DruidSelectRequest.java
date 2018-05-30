package com.bt.dyns.model.druid.request;

import java.util.List;

import com.bt.dyns.constant.Constants;
import com.bt.dyns.model.common.DruidProperty;
import com.bt.dyns.model.common.Rule;
import com.bt.dyns.model.druid.common.PagingSpec;

public class DruidSelectRequest extends DruidRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2972429363998388520L;

	private String[] dimensions;
	private String[] metrics;
	private PagingSpec pagingSpec;

	protected DruidSelectRequest(DruidBaseRequestBuilder baseRequestBuilder, DruidRequestBuilder druidRequestBuilder,
			SelectRequestBuilder selectRequestBuilder) {
		super(baseRequestBuilder, druidRequestBuilder);
		this.dimensions = selectRequestBuilder.dimensions;
		this.metrics = selectRequestBuilder.metrics;
		this.pagingSpec = selectRequestBuilder.pagingSpec;
	}

	public String[] getDimensions() {
		return dimensions;
	}

	public String[] getMetrics() {
		return metrics;
	}

	public PagingSpec getPagingSpec() {
		return pagingSpec;
	}

	public static class SelectRequestBuilder {
		private String[] dimensions;
		private String[] metrics;
		private PagingSpec pagingSpec;
		private DruidRequestBuilder druidRequestBuilder;
		private DruidBaseRequestBuilder baseRequestBuilder;

		public SelectRequestBuilder(DruidBaseRequestBuilder baseRequestBuilder, ReportRequest reportRequest
				) {
			// Build dimensions

			String[] dimensions = null;
			if (Constants.TABLE.equalsIgnoreCase(reportRequest.getConfiguration().getType())) {
				if (reportRequest.getFilter() != null) {
					List<DruidProperty>props=reportRequest.getConfiguration().getProperties();
					for (Rule rule : reportRequest.getFilter().getRules()) {
						rule.setField(props.get(0).getId());
					}

				}
			}

			this.baseRequestBuilder = baseRequestBuilder;
			this.druidRequestBuilder = new DruidRequestBuilder(baseRequestBuilder, reportRequest);
			this.dimensions = dimensions;

			/*
			 * this.pagingSpec = new
			 * PagingSpecBuilder(reportrequest.getPagination().
			 * getPagingIdentifiers(),
			 * reportrequest.getPagination().getPagesize(),
			 * reportrequest.getPagination().getPagenumber()).build();
			 */
			PagingSpec pagingSpec = reportRequest.getPagination().getPagingSpec();
			if (null == pagingSpec) {
				pagingSpec = new PagingSpec();
				reportRequest.getPagination().setPagingSpec(pagingSpec);
			}
			pagingSpec.setThreshold(reportRequest.getTotalRecords());
			this.pagingSpec = pagingSpec;
		}

		public DruidSelectRequest build() {
			return new DruidSelectRequest(baseRequestBuilder, druidRequestBuilder, this);
		}
	}

}
