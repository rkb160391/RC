package com.bt.dyns.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Metric extends Attribute {

	private String aggregationType;
	


	public String getAggregationType() {
		return aggregationType;
	}

	public void setAggregationType(String aggregationType) {
		this.aggregationType = aggregationType;
	}

	@Override
	public String toString() {
		return "Metric [aggregationType=" + aggregationType + "]";
	}




}
