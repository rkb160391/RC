package com.bt.ngoss.model.admin;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bt.ngoss.model.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "ROLE")
@Entity
public class Role {

	@Id
	@Column(name = "ROLE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ALLOWED_ROLES")
	private String allowedRoles;

/*	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private List<Profile> profiles;*/

//	@JsonIgnore
//	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "role")
//	private List<UserDetails> userdetails;

	public String getAllowedRoles() {
		return allowedRoles;
	}

	public void setAllowedRoles(String allowedRoles) {
		this.allowedRoles = allowedRoles;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*@JsonIgnore
	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}*/

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", name=" + name + ", allowedRoles=" + allowedRoles
				+ "]";
	}

	

//	public List<UserDetails> getUserdetails() {
//		return userdetails;
//	}
//
//	public void setUserdetails(List<UserDetails> userdetails) {
//		this.userdetails = userdetails;
//	}

//	@Override
//	public String toString() {
//		return "Role [roleId=" + roleId + ", name=" + name + ", allowedRoles=" + allowedRoles + ", profiles=" + profiles
//				+ ", userdetails=" + userdetails + "]";
//	}
	
}