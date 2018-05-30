package com.bt.ngoss.service.report.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bt.ngoss.dao.UserTemplateDao;
import com.bt.ngoss.model.report.UserTemplate;
import com.bt.ngoss.service.report.UserTemplateService;
import com.bt.ngoss.vo.UserTemplateVO;

@Service
@Transactional
public class UserTemplateServiceImpl implements UserTemplateService {

	@Autowired
	UserTemplateDao userTemplateDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<UserTemplate> getUserTemplate(UserTemplateVO userTemplateVO) throws Exception {
		return userTemplateDao.getUserTemplate(userTemplateVO);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<UserTemplate> getUserTemplateWithQueryType(UserTemplateVO userTemplateVO) throws Exception {
		return userTemplateDao.getUserTemplateWithQueryType(userTemplateVO);
	
	}
}
