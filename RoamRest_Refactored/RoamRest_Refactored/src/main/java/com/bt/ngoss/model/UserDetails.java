package com.bt.ngoss.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author 611164825
 *	modified by 611269568
 */
@Entity
@Table(name = "USER_DETAILS")
public class UserDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3830649584518621238L;

	@Id
	@Column(name = "USER_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CARRIER_ID", unique = true, nullable = false)
	private String userName;

	@Column(name = "Carrier_NAME", nullable = false)
	private String carrierName;

	@Column(name = "COMPANY_NAME", nullable = false)
	private String companyName;

	@Column(name = "DISPLAY_NAME", nullable = false)
	private String displayName;

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return "UserDetails [userName=" + userName + ", carrierName=" + carrierName + ", companyName=" + companyName
				+ ", displayName=" + displayName + "]";
	}

}
