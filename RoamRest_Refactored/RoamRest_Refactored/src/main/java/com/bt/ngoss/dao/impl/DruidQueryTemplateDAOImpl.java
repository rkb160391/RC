package com.bt.ngoss.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bt.ngoss.dao.AbstractDAO;
import com.bt.ngoss.dao.DruidQueryTemplateDAO;
import com.bt.ngoss.model.DruidQueryTemplate;

@Repository
public class DruidQueryTemplateDAOImpl extends AbstractDAO<DruidQueryTemplate> implements DruidQueryTemplateDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1882027680351030020L;

	@Override
	public List<DruidQueryTemplate> getAllTemplates() {
		return (List<DruidQueryTemplate>) loadClass(DruidQueryTemplate.class);
	}

	@Override
	public DruidQueryTemplate getTemplate(Long druidQueryId) {
		StringBuilder queryString=new StringBuilder();
		queryString.append(" from DruidQueryTemplate tp where tp.druidQueryId=" +druidQueryId);
		return executeQueryForUniqueRecord(queryString.toString());
	}

}