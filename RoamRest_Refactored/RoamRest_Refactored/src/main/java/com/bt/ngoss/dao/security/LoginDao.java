package com.bt.ngoss.dao.security;

import java.util.List;

import com.bt.ngoss.model.UserDetails;

public interface LoginDao {
	
	public List<UserDetails> loadUsers();
}
