package com.bt.ngoss.common.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bt.ngoss.model.Message;
import com.bt.ngoss.service.MessageService;
import com.bt.ngoss.vo.MessageVO;

@Component
public class MessageHelper {

	@Autowired
	MessageService messageService;

	public MessageVO handleBusinessMessage(int messageCode) {
		MessageVO messageVO = new MessageVO();
		Message message = messageService.getMessage(messageCode);
		messageVO.setMessagecode(message.getCode());
		messageVO.setMessage(message.getMessage());
		return messageVO;
	}
}
