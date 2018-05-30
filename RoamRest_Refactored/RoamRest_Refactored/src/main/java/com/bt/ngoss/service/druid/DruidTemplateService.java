package com.bt.ngoss.service.druid;

import java.util.List;

import com.bt.ngoss.model.DruidTemplate;

public interface DruidTemplateService {

	List<DruidTemplate> getAllTemplates();

	DruidTemplate getTemplate(String type);
	
}