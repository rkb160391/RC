package com.bt.dyns.model.druid.common;

import java.util.List;

public class Filter {

	private String type;
	private List<Fields> fields;
	private Fields field;

	public Fields getField() {
		return field;
	}

	public void setField(Fields field) {
		this.field = field;
	}

	public String getType() {
		return type;
	}

	public List<Fields> getFields() {
		return fields;
	}

	public void setFields(List<Fields> fields) {
		this.fields = fields;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Filter [type=" + type + ", fields=" + fields + ", field=" + field + "]";
	}

}