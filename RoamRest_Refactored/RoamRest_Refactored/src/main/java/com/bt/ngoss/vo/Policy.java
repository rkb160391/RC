package com.bt.ngoss.vo;

public class Policy {
	
	private boolean islink;
	private String url;
	public boolean isIslink() {
		return islink;
	}
	public void setIslink(boolean islink) {
		this.islink = islink;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "Policy [islink=" + islink + ", url=" + url + "]";
	}
	
	

}
