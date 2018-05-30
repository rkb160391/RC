package com.bt.ngoss.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bt.ngoss.model.report.UserTemplate;
import com.bt.ngoss.vo.UserTemplateVO;



public interface UserTemplateDao {

//	List<UserTemplate> searchUserTemplate(UserTemplateVO userTemplateVO) throws Exception;

	List<UserTemplate> getUserTemplate(UserTemplateVO userTemplateVO) throws Exception;
	List<UserTemplate> getUserTemplateWithQueryType(UserTemplateVO userTemplateVO) throws Exception ;

	/*void editUserTemplate(UserTemplateVO userTemplateVO) throws Exception;

	void saveUserTemplate(UserTemplateVO userTemplateVO)  throws Exception;

	void saveUserTemplate(UserTemplate userTemplate)  throws Exception;

	void deleteUserTemplate(UserTemplate userTemplate) throws Exception;

	void deleteUserTemplateBasedOnUserDetails(UserTemplate userTemplate) throws Exception;

	public List<UserTemplate> getUserTemplatesOfDashboard(String dashboardId);

	public List<UserTemplate> getAllUserTemplate();

	List<UserTemplateVO> fetchUserTemplate(UserTemplateVO userTemplateVO);

	void editUserTemplate(UserTemplate usertemplate);
	
	public List<UserTemplate> getUserTemplateforReportPack(UserTemplateVO userTemplateVo,Set<String> userTemplateIdList,Set<String> reportTypeSet) throws Exception;
	
	List<UserTemplate> searchUserTemplateforReportPack(UserTemplateVO userTemplateVO,Set<String> reportTypeSet) throws Exception;

	void deleteUserTemplate(UserDetails userDtl);

	void deleteUserTemplateByDashboardId(List<UserTemplate> usertemplates);

	void removeMarkDeletedUserTemplates(List<UserTemplate> userTemplates) throws Exception;
	
	void markUserTemplatesAsDeleted(Set<String> userTemplateIds);

	List<UserTemplate> getUserTemplateByDashboardIds(Map<String, Set> values) throws Exception;

	List<UserTemplate> getMarkDeleteUserTemplate(List<Long> userIds);

*/
}
