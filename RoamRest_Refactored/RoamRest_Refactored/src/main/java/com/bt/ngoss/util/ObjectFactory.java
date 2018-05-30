package com.bt.ngoss.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.bt.ngoss.client.druid.DruidRestClient;
import com.bt.ngoss.common.constant.DateConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectFactory {

	public static DruidRestClient<?> getDruidRestClient() {
		return new DruidRestClient();
	}
	
	public static ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}
	public static DateFormat getDateFormatForDisplay(){
		return new SimpleDateFormat(DateConstant.dateFormatForDisplay);
	}
	
	public static SimpleDateFormat getSimpleDateFormat(){
		return new SimpleDateFormat(DateConstant.dateFormat);
	} 
}