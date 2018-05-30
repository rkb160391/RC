package com.bt.ngoss.dao.impl;

import org.springframework.stereotype.Repository;

import com.bt.ngoss.dao.AbstractDAO;
import com.bt.ngoss.dao.MessageDao;
import com.bt.ngoss.model.Message;

@Repository
public class MessageDaoImpl extends AbstractDAO<Message> implements MessageDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4364916123038960299L;

	@Override
	public Message getMessage(int code) {
		return getRecordById(Message.class, code);
	}
}