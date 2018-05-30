package com.bt.ngoss.model.report;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bt.ngoss.model.UserDetails;

@Entity
@Table(name = "DASHBOARD_DETAILS")
public class Dashboard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6272470889206863748L;


	@Column(name = "NAME", nullable = false)
	private String name;


	@EmbeddedId
	private DashboardKey key;


	@Lob
	@Column(name = "ARRANGEMENT")
	private byte[] arrangement;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DashboardKey getKey() {
		return key;
	}

	public void setKey(DashboardKey key) {
		this.key = key;
	}

	

	public byte[] getArrangement() {
		return arrangement;
	}

	public void setArrangement(byte[] arrangement) {
		this.arrangement = arrangement;
	}

	@Override
	public String toString() {
		return "Dashboard [name=" + name + ", key=" + key + ", arrangement="
				+ Arrays.toString(arrangement) + "]";
	}

	


}
