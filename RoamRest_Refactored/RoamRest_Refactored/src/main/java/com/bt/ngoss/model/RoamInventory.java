package com.bt.ngoss.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "ROAM_INVENTORY")

public class RoamInventory implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "GLOBAL_TITLE", unique = true, nullable = false)
	private String globalTitle;

	@Column(name = "OPERATOR_CODE")
	private String operatorCode;

	@Column(name = "OPERATOR_NAME")
	private String opertorName;
	
	public RoamInventory() {
		// TODO Auto-generated constructor stub
	}

	public RoamInventory(String globalTitle, String operatorCode, String opertorName) {
		super();
		this.globalTitle = globalTitle;
		this.operatorCode = operatorCode;
		this.opertorName = opertorName;
	}

	public String getGlobalTitle() {
		return globalTitle;
	}

	public void setGlobalTitle(String globalTitle) {
		this.globalTitle = globalTitle;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getOpertorName() {
		return opertorName;
	}

	public void setOpertorName(String opertorName) {
		this.opertorName = opertorName;
	}
	
	
	

}



	


