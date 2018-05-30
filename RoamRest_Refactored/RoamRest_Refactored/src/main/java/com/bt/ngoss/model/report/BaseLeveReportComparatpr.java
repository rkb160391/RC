package com.bt.ngoss.model.report;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class BaseLeveReportComparatpr implements Comparator<BaseLeveReport>{

	@Override
	public int compare(BaseLeveReport o1, BaseLeveReport o2) {

			Map<String, Object> map1=o1.getData();
	    	Map<String, Object> map2=o2.getData();
	    	Integer value1=(Integer)map1.get("TotalMSU");
	    	Integer value2=(Integer)map2.get("TotalMSU");
	        return value2.compareTo(value1);

	
	}
}
