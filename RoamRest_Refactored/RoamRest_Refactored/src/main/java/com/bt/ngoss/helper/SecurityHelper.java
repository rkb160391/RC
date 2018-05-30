package com.bt.ngoss.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bt.ngoss.common.exception.BusinessException;
import com.bt.ngoss.service.security.TokenAuthenticationService;
import com.bt.ngoss.vo.DashBoardVO;
import com.bt.ngoss.vo.ReportPackDetailsVO;
import com.bt.ngoss.vo.ReportpackTemplateVO;
import com.bt.ngoss.vo.UserTemplateVO;
import com.bt.ngoss.vo.UserVO;

@Component
public class SecurityHelper {

	@Autowired
	TokenAuthenticationService tokenAuthenticationService;

	public String getUserNameFromSecurityContext() throws Exception {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

			UserVO userVo = (UserVO) SecurityContextHolder.getContext().getAuthentication().getDetails();
			return userVo.getId();
		}
		return null;
	}

	public String setUserNameFromSecurityContext(UserTemplateVO usertemplatevo) throws Exception {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
				String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (!StringUtils.isEmpty(username)) {
					usertemplatevo.setUserName(username);
				} else {
					throw new BusinessException(HttpStatus.UNAUTHORIZED.toString());
				}
			}
		}
		return null;
	}

	public String setUserNameFromSecurityContext(DashBoardVO dashboardVO) throws Exception {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
				String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (!StringUtils.isEmpty(username)) {
					dashboardVO.setUserName(username);
				} else {
					throw new BusinessException(HttpStatus.UNAUTHORIZED.toString());
				}
			}
		}
		return null;
	}

	public String setUserNameFromSecurityContext(ReportPackDetailsVO reportPackDetailsVO) throws Exception {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
				String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (!StringUtils.isEmpty(username)) {
					reportPackDetailsVO.setUserName(username);
				} else {
					throw new BusinessException(HttpStatus.UNAUTHORIZED.toString());
				}
			}
		}
		return null;
	}

	public String setUserNameFromSecurityContext(ReportpackTemplateVO reportpackTemplateVO) throws Exception {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
				String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (!StringUtils.isEmpty(username)) {
					reportpackTemplateVO.setUserName(username);
				} else {
					throw new BusinessException(HttpStatus.UNAUTHORIZED.toString());
				}
			}
		}
		return null;
	}
}