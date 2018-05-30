package com.bt.ngoss.dao;

import java.util.List;

import com.bt.ngoss.model.DruidTemplate;

public interface DruidTemplateDAO {

	List<DruidTemplate> getAllTemplates();

	DruidTemplate getTemplate(String type);
}
