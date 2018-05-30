package com.bt.ngoss.service.druid.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bt.ngoss.dao.DruidTemplateDAO;
import com.bt.ngoss.model.DruidTemplate;
import com.bt.ngoss.service.druid.DruidTemplateService;

@Service
public class DruidTemplateServiceImpl implements DruidTemplateService {

	@Autowired
	DruidTemplateDAO druidTemplateDAO;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DruidTemplate> getAllTemplates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DruidTemplate getTemplate(String type) {
		if (!StringUtils.isEmpty(type)) {
			return druidTemplateDAO.getTemplate(type.toLowerCase());
		}
		return null;
	}

}
