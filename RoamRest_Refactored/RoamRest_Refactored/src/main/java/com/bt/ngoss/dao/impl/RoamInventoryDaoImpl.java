package com.bt.ngoss.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bt.ngoss.dao.RoamInventoryDao;
import com.bt.ngoss.model.RoamInventory;

@Transactional
@Repository
public class RoamInventoryDaoImpl   implements RoamInventoryDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(RoamInventoryDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	
	@Override
	public boolean saveOrUpdateInventory(RoamInventory roamInventory) {
		
		boolean success = false;   
		  try {
		    // obtaining session is omitted
			  this.sessionFactory.getCurrentSession().saveOrUpdate(roamInventory);
		    success = true;

		  } catch (Exception e) {
		   logger.error("error in updating/inserting the given inventory row:"+e);
		  } 
		  return success ;
		
	}

}
