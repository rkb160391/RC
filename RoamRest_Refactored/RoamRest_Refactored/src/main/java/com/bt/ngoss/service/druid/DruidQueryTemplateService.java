package com.bt.ngoss.service.druid;

import java.util.List;

import com.bt.ngoss.model.DruidQueryTemplate;

public interface DruidQueryTemplateService {

	List<DruidQueryTemplate> getAllTemplates();

	DruidQueryTemplate getTemplate(Long druidQueryId);
	
}