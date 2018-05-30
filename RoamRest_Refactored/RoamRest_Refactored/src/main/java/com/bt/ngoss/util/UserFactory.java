package com.bt.ngoss.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.bt.ngoss.model.UserDetails;
import com.bt.ngoss.vo.UserVO;

public final class UserFactory {

	private UserFactory() {
	}

	public static UserVO create(UserDetails user) {
		return new UserVO(user.getUserName(), user.getCarrierName(), user.getId(), mapToGrantedAuthorities("ADMIN")

		);
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(String role) {
		List<GrantedAuthority> grantedauthlist = new ArrayList<>();
		GrantedAuthority grandauth = new SimpleGrantedAuthority(role);
		grantedauthlist.add(grandauth);
		return grantedauthlist;
	}
}
