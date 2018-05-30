package com.bt.ngoss.service.inventory;

import java.util.List;
import java.util.Map;

import com.bt.dyns.model.common.Attribute;
import com.bt.ngoss.model.UserDetails;
import com.bt.ngoss.model.inventory.Property;
import com.bt.ngoss.vo.ReportPropertyVO;

public interface PropertyCacheService {

	List<Property> getProperty(String metricName) throws Exception;
	
/*	 Map<String, Object>  getPropertyValues( Map<String,Object> filterColumsValues,List<Attribute> properties, Attribute entity);

	 Map<String, String>  loadAttributesFromProperty();
	 
	 ReportPropertyVO loadDefaultPropertiesAccordingToUser(UserDetails userDetails,String entityType) throws Exception;
	 
	 FeatureDetailsVO getAllFeatureProperty();
	  
	 Map<String,Attribute> getFeatureSetup() throws Exception;
	 List<EntityType> getAllEntityTypeList();
	 List<Attribute> loadSpecificProperties(Attribute attribute,Attribute queryEntity,String fillterPatteren);*/
}
