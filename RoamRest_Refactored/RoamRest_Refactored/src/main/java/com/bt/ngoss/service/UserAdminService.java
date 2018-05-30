package com.bt.ngoss.service;

import java.util.List;

import com.bt.ngoss.model.UserDetails;
import com.bt.ngoss.model.admin.Role;
import com.bt.ngoss.vo.RoleUserVo;
import com.bt.ngoss.vo.UserDetailVO;
import com.bt.ngoss.vo.UserVO;

public interface UserAdminService {

	UserDetails getUser(String userName);
	

/*	UserDetails addUser(UserDetails user) throws Exception;

	List<UserDetails> getAllUser(String userRole);

	List<UserDetails> getUsers(long profileId);

	List<UserDetails> getAllUsers();



	List<UserVO> getActiveUsers(String role);

	List<ProfileUserVO> getProfilesAccordingToRole(Role role);

	List<RoleUserVo> getUsersAccordingToAllowedRole(String allowedRole);

	void enableDisableUser(UserDetails userVo);

	UserVO getUserInformation(String userName);

	UserVO updateUser(UserVO userVo, String userName) throws Exception;

	UserDetailVO updateUser(UserDetailVO user) throws Exception;

	void updateUserDetails(UserDetails user) throws Exception;
//	
	void markAsUserDelete(String username) throws Exception;
	void handleMarkDelete();

	void markUsersAsModified(List<UserDetails> userList);*/

	
}
