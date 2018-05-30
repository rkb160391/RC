package com.bt.ngoss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bt.ngoss.dao.KeyIdentifierDao;
import com.bt.ngoss.model.KeyIdentifier;
import com.bt.ngoss.service.KeyIdentifierService;

@Service
public class KeyIdentifierServiceImpl implements KeyIdentifierService {

	@Autowired
	KeyIdentifierDao keyIdentifierDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KeyIdentifier> getKeyIdentifiers() {
		return keyIdentifierDao.getKeyIdentifiers();
	}

}
