package com.bt.ngoss.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bt.ngoss.dao.AbstractDAO;
import com.bt.ngoss.dao.DruidTemplateDAO;
import com.bt.ngoss.model.DruidTemplate;

@Repository
public class DruidTemplateDAOImpl extends AbstractDAO<DruidTemplate> implements DruidTemplateDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3437677455334386891L;

	@Override
	public List<DruidTemplate> getAllTemplates() {
		return (List<DruidTemplate>) loadClass(DruidTemplate.class);
	}

	@Override
	public DruidTemplate getTemplate(String type) {
		StringBuilder queryString = new StringBuilder(" from DruidTemplate tp where tp.type='"+type+"' ");
		
		return executeQueryForUniqueRecord(queryString.toString());
	}

	

}