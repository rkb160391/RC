package com.bt.ngoss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bt.ngoss.helper.SecurityHelper;
import com.bt.ngoss.model.UserDetails;
import com.bt.ngoss.model.report.UserReport;
import com.bt.ngoss.service.UserAdminService;
import com.bt.ngoss.service.report.ReportService;
import com.bt.ngoss.service.report.UserTemplateService;
import com.bt.ngoss.vo.UserTemplateVO;;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequestMapping(value = "/api/reports")
@RestController
public class ReportController {

	@Autowired
	ReportService reportService;

	@Autowired
	SecurityHelper securityHelper;

	@Autowired
	UserTemplateService userTemplateService;
	
	@Autowired
	UserAdminService userAdminService;

	/** 
	 * 
	 * @param userTemplateVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getspecificuserreport", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReport> getUserReportsSpecificToTemplate(@RequestBody UserTemplateVO userTemplateVO)
			throws Exception {

		String userName = securityHelper.getUserNameFromSecurityContext();
	//	userTemplateService.getUserTemplate(userTemplateVO);
		
		UserDetails userDetail = userAdminService.getUser(userName);
		userTemplateVO.setUserId(userDetail.getId());
		
		UserReport userReport = reportService.getSpecificUserReports(userTemplateVO);
		userReport.setUserDetails(userDetail);
		return new ResponseEntity<UserReport>(userReport, HttpStatus.OK);
	}

}