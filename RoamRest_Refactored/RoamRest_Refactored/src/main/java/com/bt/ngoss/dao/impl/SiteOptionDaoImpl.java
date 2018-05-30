package com.bt.ngoss.dao.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.bt.ngoss.dao.AbstractDAO;
import com.bt.ngoss.dao.SiteOptionDao;
import com.bt.ngoss.model.common.SiteOption;

@Repository
public class SiteOptionDaoImpl extends AbstractDAO<SiteOption> implements SiteOptionDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3710925223590239936L;

	public List<SiteOption> loadSiteOption() {
		return (List<SiteOption>) loadClass(SiteOption.class);
	}

}
