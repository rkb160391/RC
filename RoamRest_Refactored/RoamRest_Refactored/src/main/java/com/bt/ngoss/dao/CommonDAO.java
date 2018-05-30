package com.bt.ngoss.dao;

import java.util.List;


public interface CommonDAO<T> {

	public List<T> getAllErrorRecords();
	public T getErrorByErrorCode(String errorCode);
}
