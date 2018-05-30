package com.bt.ngoss.service.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import com.bt.ngoss.controllers.LoadApplicationData;
import com.bt.ngoss.vo.UserVO;

public class UserService implements UserDetailsService {

	public final UserVO loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO user = LoadApplicationData.userMap.get(username);
		if (user == null) {
			throw new NullPointerException("user not found.");
		}
		return user;
	}

	public final void removeUser(String username) {

		if (username != null && !StringUtils.isEmpty(username)) {
			LoadApplicationData.userMap.remove(username);

		}
	}

}
