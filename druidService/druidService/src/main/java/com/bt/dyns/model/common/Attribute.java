package com.bt.dyns.model.common;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribute implements Serializable {

	/**
	 * 
	 */
	
	
	
	
	private static final long serialVersionUID = 2738635113698431608L;

	
	private String name;
	private String displayName;
	private String id;
	private String type;
	private String refrenceField;
	private boolean isLink;
	int level;
	List<Attribute> subHeaders=null;
	

	public List<Attribute> getSubHeaders() {
		return subHeaders;
	}
	public void setSubHeaders(List<Attribute> subHeaders) {
		this.subHeaders = subHeaders;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isLink() {
		return isLink;
	}
	public void setLink(boolean isLink) {
		this.isLink = isLink;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRefrenceField() {
		return refrenceField;
	}
	public void setRefrenceField(String refrenceField) {
		this.refrenceField = refrenceField;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	@Override
	public String toString() {
		return "Attribute [name=" + name + ", displayName=" + displayName + ", id=" + id + "]";
	}	
}
