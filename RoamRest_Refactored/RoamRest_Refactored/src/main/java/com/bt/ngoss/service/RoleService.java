package com.bt.ngoss.service;

import java.util.List;

import com.bt.ngoss.model.UserDetails;
import com.bt.ngoss.model.admin.Role;
import com.bt.ngoss.vo.RoleVO;

public interface RoleService {
	List<Role> getAllRoles();

	void addRole(Role role);

	void editRole(Role role);

	void deleteRole(Role role);

	Role getRole(String role);
	
	Role getRole(Role role);
	
	String[] getAllowedRoles(UserDetails userDetails);
	
	Role getRoleByName(RoleVO roleVO) throws Exception;
	
	List<Role> getRoles(String allowedRoles);
}