package com.bt.dyns.model.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DruidProperty extends Attribute {

	@Override
	public String toString() {
		return "DruidProperty [level=" + level + ", subHeaders=" + subHeaders + ", getLevel()=" + getLevel()
				+ ", getRefrenceField()=" + getRefrenceField() + ", getName()=" + getName() + ", getDisplayName()="
				+ getDisplayName() + "]";
	}

	



//	private String type;
//	private boolean isLink;
//    private String refrenceField;
//	public String getType() {
//		return type;
//	}
//	public void setType(String type) {
//		this.type = type;
//	}
//	public boolean isLink() {
//		return isLink;
//	}
//	public void setLink(boolean isLink) {
//		this.isLink = isLink;
//	}
//	public String getRefrenceField() {
//		return refrenceField;
//	}
//	public void setRefrenceField(String refrenceField) {
//		this.refrenceField = refrenceField;
//	}
//	
//	

    
	
}
