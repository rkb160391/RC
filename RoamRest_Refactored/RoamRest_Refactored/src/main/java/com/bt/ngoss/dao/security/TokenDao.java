package com.bt.ngoss.dao.security;

import java.util.List;

public interface TokenDao {
	public List<Integer> loadTokenExpiry();
}
