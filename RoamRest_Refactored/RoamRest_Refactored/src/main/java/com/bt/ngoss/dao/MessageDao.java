package com.bt.ngoss.dao;

import com.bt.ngoss.model.Message;

public interface MessageDao {
	Message getMessage(int code);
}