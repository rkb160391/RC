package com.bt.ngoss.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.ngoss.dao.RoleDao;
import com.bt.ngoss.model.UserDetails;
import com.bt.ngoss.model.admin.Role;
import com.bt.ngoss.service.RoleService;
import com.bt.ngoss.vo.RoleVO;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDao roleDao;

	@Override
	public List<Role> getAllRoles() {
		return roleDao.getAllRoles();
	}

	@Override
	public void addRole(Role role) {
		roleDao.addRole(role);
	}

	@Override
	public void editRole(Role role) {
		roleDao.editRole(role);
	}

	@Override
	public void deleteRole(Role role) {
		roleDao.deleteRole(role);
	}

	@Override
	public Role getRole(String role) {

		return roleDao.getRole(role);
	}

	@Override
	public Role getRoleByName(RoleVO roleVO) throws Exception {
		return roleDao.getRoleByName(roleVO);
	}

	@Override
	public Role getRole(Role role) {
		
		return roleDao.getRole(role);
	}

	@Override
	public String[] getAllowedRoles(UserDetails userDetails) {
		Role role=new Role();
		//role=getRole(userDetails.getRole());
		if(role.getAllowedRoles()!=null && !role.getAllowedRoles().isEmpty()){
			return role.getAllowedRoles().split(",");
		}
		else{
			return null;
		}
	}

	@Override
	public List<Role> getRoles(String allowedRoles) {
		return roleDao.getRoles(allowedRoles);
	}
}