package com.bt.ngoss.service.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import com.bt.ngoss.constant.TokenAuthenticationConstant;
import com.bt.ngoss.helper.TokenHandler;
import com.bt.ngoss.vo.UserVO;

public class TokenAuthenticationService implements TokenAuthenticationConstant {

	private final TokenHandler tokenHandler;

	public TokenAuthenticationService(String secret, UserService userService) {
		tokenHandler = new TokenHandler(secret, userService);
	}

	/*
	 * public String addAuthentication(HttpServletResponse response,
	 * UserAuthentication authentication) throws ParseException { final UserVO
	 * user = authentication.getDetails(); String token =
	 * tokenHandler.createTokenForUser(user);
	 * response.addHeader(AUTH_HEADER_NAME, token); return token; }
	 */

	public Authentication getAuthentication(HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);
		if (token != null) {
			final UserVO user = tokenHandler.parseUserFromToken(token);
			if (user != null) {
				return new UserAuthentication(user);
			}
		}
		return null;
	}

	public TokenHandler getTokenHandler() {
		return tokenHandler;
	}

}
