package com.bt.ngoss.dao;

import java.util.List;

import com.bt.ngoss.model.admin.Role;
import com.bt.ngoss.vo.RoleVO;

public interface RoleDao {
	List<Role> getAllRoles();

	void addRole(Role role);

	void editRole(Role role);

	void deleteRole(Role role);
	
	Role getRole(String role);
	
	Role getRole(Role role);
	
	Role getRoleByName(RoleVO roleVO) throws Exception;
	
	Role getRoleId(String roleName);
	
	List<Role> getRoles(String allowedRoles);

}