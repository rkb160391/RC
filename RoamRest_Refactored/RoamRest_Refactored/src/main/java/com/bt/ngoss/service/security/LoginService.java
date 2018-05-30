package com.bt.ngoss.service.security;

import java.util.List;

import com.bt.ngoss.model.UserDetails;


public interface LoginService {
	
	public List<UserDetails> loadUsers();  
}
