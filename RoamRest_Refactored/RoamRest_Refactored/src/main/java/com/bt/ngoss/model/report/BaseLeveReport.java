package com.bt.ngoss.model.report;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class BaseLeveReport {

	private String timeStamp;
	private Map<String, Object> data;
	private String operatorName;
	private String product;
	private List<Map<String,Map<String,Object>>> dataList;

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	@Override
	public String toString() {
		return "BaseLeveReport [timeStamp=" + timeStamp + ", data=" + data + ", operatorName=" + operatorName
				+ ", product=" + product + "]";
	}
	
	
	
	
	public List<Map<String, Map<String, Object>>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, Map<String, Object>>> data2) {
		this.dataList = data2;
	}




	public static Comparator<BaseLeveReport> NameComparator = new Comparator<BaseLeveReport>() {

        @Override
        public int compare(BaseLeveReport e1, BaseLeveReport e2) {
        	Map<String, Object> map1=e1.getData();
        	Map<String, Object> map2=e2.getData();
        	Integer value1=(Integer)map1.get("TotalMSU");
        	Integer value2=(Integer)map2.get("TotalMSU");
            return value1.compareTo(value2);
        }
    };

}
