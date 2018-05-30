package com.bt.ngoss.common.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.bt.dyns.validator.Preconditions;
import com.bt.ngoss.constant.TokenAuthenticationConstant;
import com.bt.ngoss.service.security.TokenAuthenticationService;
import com.bt.ngoss.service.security.UserService;
import com.bt.ngoss.vo.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestAuthenticationFilter extends GenericFilterBean {

	private final TokenAuthenticationService tokenAuthenticationService;
	private UserService userService;
	public Map<String, String> loggedInUsers = new HashMap<String, String>();

	public RestAuthenticationFilter(UserService userService, TokenAuthenticationService tokenAuthenticationService) {
		this.tokenAuthenticationService = Preconditions.checkNotNull(tokenAuthenticationService);
		this.userService = userService;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		try {
			if ("OPTIONS".equals(httpRequest.getMethod())) {
				filterChain.doFilter(request, response);
			} else {
				final String token = httpRequest.getHeader(TokenAuthenticationConstant.AUTH_HEADER_NAME);

				if (!StringUtils.isEmpty(token)) {
					UserVO userVo = tokenAuthenticationService.getTokenHandler().parseUserFromToken(token);
					if (tokenAuthenticationService.getTokenHandler().validateToken(token, userVo)) {
						Authentication authentication = tokenAuthenticationService
								.getAuthentication((HttpServletRequest) request);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				} else if (httpRequest.getRequestURI().endsWith("login")) {
					UserVO userDetails = userService.loadUserByUsername((String) httpRequest.getHeader("operatorId"));
					final String authtoken = tokenAuthenticationService.getTokenHandler()
							.createTokenForUser(userDetails);
					if (!StringUtils.isEmpty(authtoken)) {
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								userDetails, "", userDetails.getAuthorities());
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
						authentication.setDetails(authtoken);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
				if (SecurityContextHolder.getContext().getAuthentication() != null
						&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
					filterChain.doFilter(request, response);
				}
			}

		} catch (Exception ex) {
			SetResponse(httpResponse, HttpStatus.UNAUTHORIZED, 403, ex);
		}

	}

	@SuppressWarnings("deprecation")
	public void SetResponse(HttpServletResponse httpResponse, HttpStatus message, Integer responsecode, Exception ex)
			throws IOException {
		ObjectMapper jsonMapper = new ObjectMapper();
		httpResponse.setContentType("application/json;charset=UTF-8");
		((HttpServletResponse) httpResponse).setStatus(responsecode, message.toString());
		PrintWriter out = httpResponse.getWriter();
		if (null != ex) {
			out.print(jsonMapper.writeValueAsString(ex.getMessage()));
		}
	}

}
