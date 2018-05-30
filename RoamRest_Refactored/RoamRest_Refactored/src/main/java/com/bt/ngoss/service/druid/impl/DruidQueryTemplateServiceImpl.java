package com.bt.ngoss.service.druid.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bt.ngoss.dao.DruidQueryTemplateDAO;
import com.bt.ngoss.model.DruidQueryTemplate;
import com.bt.ngoss.service.druid.DruidQueryTemplateService;

@Service
public class DruidQueryTemplateServiceImpl implements DruidQueryTemplateService {

	@Autowired
	DruidQueryTemplateDAO druidQueryTemplateDAO;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DruidQueryTemplate> getAllTemplates() {
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DruidQueryTemplate getTemplate(Long druidQueryId) {
		return druidQueryTemplateDAO.getTemplate(druidQueryId);
	}

}
