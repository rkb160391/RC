package com.bt.ngoss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bt.ngoss.common.exception.BusinessException;
import com.bt.ngoss.common.helper.MessageHelper;
import com.bt.ngoss.constant.TokenAuthenticationConstant;
import com.bt.ngoss.model.report.UserReport;
import com.bt.ngoss.service.UserAdminService;
import com.bt.ngoss.service.report.ReportService;
import com.bt.ngoss.vo.MessageVO;
import com.bt.ngoss.vo.UserTemplateVO;
import com.bt.ngoss.vo.UserVO;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	ReportService reportService;

	@Autowired
	UserAdminService userAdminService;

	@Autowired
	MessageHelper messageHelper;

	/*
	 * @Autowired DynsHelper dynsHelper;
	 */

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserReport> login() throws Exception {
		UserReport userreport = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

			UserTemplateVO userTemplateVo = new UserTemplateVO();
			UserVO user = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String token = (String) SecurityContextHolder.getContext().getAuthentication().getDetails();
			responseHeaders.set(TokenAuthenticationConstant.AUTH_HEADER_NAME, token);

			// Need to correct this code
			userTemplateVo.setUserId(Long.valueOf(user.getId()));
			userTemplateVo.setUserName(user.getUsername());
			userreport = reportService.getDashboardReports(userTemplateVo);
			userreport.setUserDetails(userAdminService.getUser(user.getId()));
		}
		return new ResponseEntity<UserReport>(userreport, responseHeaders, HttpStatus.OK);

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageVO> logout(@RequestHeader HttpHeaders headers) throws Exception {
		MessageVO messageVO = new MessageVO();
		try {
			UserVO uservo = null;
			if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
				if (SecurityContextHolder.getContext().getAuthentication().getDetails() instanceof UserVO) {
					uservo = (UserVO) SecurityContextHolder.getContext().getAuthentication().getDetails();
					// UserDetails userDetails =
					// userAdminService.getUser(uservo.getUsername());
					// userDetails.setIsActive(Boolean.FALSE);
					// userAdminService.updateUserDetails(userDetails);
					messageVO = messageHelper.handleBusinessMessage(1008);
				}
			}
		} catch (Exception ex) {
			throw new BusinessException(1007);
		}
		return new ResponseEntity<MessageVO>(messageVO, HttpStatus.OK);
	}
}
