package com.bt.ngoss.dao.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.bt.ngoss.dao.AbstractDAO;
import com.bt.ngoss.dao.KeyIdentifierDao;
import com.bt.ngoss.model.KeyIdentifier;

@Repository
public class KeyIdentifierDaoImpl extends AbstractDAO<KeyIdentifier> implements KeyIdentifierDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2029943487684545642L;

	@Override
	@Cacheable(value="sqlcache")
	public List<KeyIdentifier> getKeyIdentifiers() {
		return (List<KeyIdentifier>) loadClass(KeyIdentifier.class);
	}
}