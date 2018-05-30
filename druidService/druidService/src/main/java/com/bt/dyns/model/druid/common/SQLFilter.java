package com.bt.dyns.model.druid.common;

import java.io.Serializable;

public class SQLFilter implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -286585778246458508L;
	
	private String type;
	private String dimension;
	private String value;
	private String function;
	
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "SQLFilter [type=" + type + ", dimension=" + dimension + ", value=" + value + "]";
	}
}
