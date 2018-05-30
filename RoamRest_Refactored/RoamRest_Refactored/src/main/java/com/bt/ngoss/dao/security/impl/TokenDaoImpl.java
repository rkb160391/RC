package com.bt.ngoss.dao.security.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bt.ngoss.dao.AbstractDAO;
import com.bt.ngoss.dao.security.TokenDao;

@Repository
public class TokenDaoImpl extends AbstractDAO implements TokenDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3919983685455505775L;

	@Override
	public List<Integer> loadTokenExpiry() {
		String sql="select expirytimeinseconds as TokenExpiry from TokenExpiry";
		@SuppressWarnings("unchecked")
		List<Integer> object =  execcuteSQLQuery(sql);
		return object;
	}

}
