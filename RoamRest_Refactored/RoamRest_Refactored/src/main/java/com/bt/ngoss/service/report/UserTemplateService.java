package com.bt.ngoss.service.report;

import java.util.List;

import com.bt.ngoss.model.report.UserTemplate;
import com.bt.ngoss.vo.UserTemplateVO;

public interface UserTemplateService {

	List<UserTemplate> getUserTemplate(UserTemplateVO userTemplateVO) throws Exception;
	
	List<UserTemplate> getUserTemplateWithQueryType(UserTemplateVO userTemplateVO) throws Exception ;

}
