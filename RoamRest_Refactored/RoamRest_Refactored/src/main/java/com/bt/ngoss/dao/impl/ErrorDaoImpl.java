package com.bt.ngoss.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.bt.ngoss.dao.AbstractDAO;
import com.bt.ngoss.dao.CommonDAO;
import com.bt.ngoss.model.report.ErrorDetail;

@Repository
public class ErrorDaoImpl extends AbstractDAO<ErrorDetail> implements CommonDAO<ErrorDetail> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4965191169889424280L;

	@Override
	public List<ErrorDetail> getAllErrorRecords(){
		return (List<ErrorDetail>) loadClass(ErrorDetail.class);
	}


	@Override
	public ErrorDetail getErrorByErrorCode(String errorCode) {

		StringBuilder selectQuery = new StringBuilder();
		StringBuilder whereQuery = new StringBuilder();
		selectQuery.append("from ErrorDetail err ");
		whereQuery.append("where ");
		if (!StringUtils.isEmpty(errorCode)) {
			whereQuery.append("err.errorCode='").append(errorCode + "'");
	    }
		selectQuery.append(whereQuery);
		return executeQueryForUniqueRecord(selectQuery.toString());
	}

}
