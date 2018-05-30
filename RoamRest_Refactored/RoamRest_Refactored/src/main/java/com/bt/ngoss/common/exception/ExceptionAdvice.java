package com.bt.ngoss.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bt.ngoss.model.Message;
import com.bt.ngoss.service.MessageService;
import com.bt.ngoss.vo.MessageVO;

import javassist.NotFoundException;

@ControllerAdvice
public class ExceptionAdvice {

	@Autowired
	MessageService messageService;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<MessageVO> handleException(Exception exception) {
		MessageVO messageVO = new MessageVO();
		messageVO.setMessagecode(99999);
		messageVO.setMessage("Exception raised while proccessing request: " + exception.getMessage());
		return new ResponseEntity<MessageVO>(messageVO, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<MessageVO> handleException(NotFoundException ex) {
		MessageVO messageVO = new MessageVO();
		messageVO.setMessagecode(404);
		messageVO.setMessage(ex.getMessage() + ",,,  " + ex.getMessage() + ",,,  ");

		return new ResponseEntity<MessageVO>(messageVO, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { RuntimeException.class })
	public ResponseEntity<MessageVO> handleRuntimeException(RuntimeException e) {
		MessageVO messageVO = new MessageVO();
		messageVO.setMessagecode(99999);
		messageVO.setMessage("Unchecked Exception raised while proccessing request: " + e.getMessage()
				+ ",,,  " + e);

		return new ResponseEntity<MessageVO>(messageVO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { BusinessException.class })
	public ResponseEntity<MessageVO> handleBusinessException(BusinessException ex) {
		MessageVO messageVO = new MessageVO();

		Message message = messageService.getMessage(ex.getCode());
		messageVO.setMessagecode(message.getCode());
		messageVO.setMessage(message.getMessage());
		return new ResponseEntity<MessageVO>(messageVO, HttpStatus.BAD_REQUEST);
	}

}