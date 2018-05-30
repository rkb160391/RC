package com.bt.ngoss.model.report;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name = "ERROR_DETAILS")
@Entity
public class ErrorDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8790653937745286480L;
	
    @Id
	@Column(name = "ERROR_ID")
	private int errorId;
	

	@Column(name = "ERROR_CODE")
	private String errorCode;


	@Column(name = "ERROR_TYPE")
	private String errorType;
	
	@Column(name = "ERROR_DESCRIPTION")
	private String errorDescription;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getErrorId() {
		return errorId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorType() {
		return errorType;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	@Override
	public String toString() {
		return "ErrorDetail [errorId=" + errorId + ", errorCode=" + errorCode + ", errorType=" + errorType
				+ ", errorDescription=" + errorDescription + "]";
	}
	
}
