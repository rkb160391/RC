package com.bt.ngoss.dao;

import java.util.List;

import com.bt.ngoss.model.DruidQueryTemplate;

public interface DruidQueryTemplateDAO {

	List<DruidQueryTemplate> getAllTemplates();

	DruidQueryTemplate getTemplate(Long druidQueryId);
}
