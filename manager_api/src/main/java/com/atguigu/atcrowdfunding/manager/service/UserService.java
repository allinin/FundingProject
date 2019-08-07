package com.atguigu.atcrowdfunding.manager.service;

import java.util.List;
import java.util.Map;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.ov.Data;
import com.atguigu.atcrowdfunding.util.Page;

public interface UserService {

	User queryUserlogin(Map<String, Object> paramMap);

	Page queryPage(Map<String, Object> paramMap);

	int saveUser(User user);

	User queryUserById(Integer id);

	int updateUser(User user);

	int deleteUser(Integer id);

	int BatchdeleteUser(Integer[] ids);

	List<Role> queryAllRole();

	List<Integer> queryRoleByUserId(Integer id);
	int saveUserRoleRelationship(Integer userid,Data data);
	int deleteRole(Integer userid, Data data);

}
