package com.atguigu.atcrowdfunding.manager.dao;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.ov.Data;
import com.atguigu.atcrowdfunding.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

	User queryUserlogin(Map<String, Object> paramMap);
	List<User> queryList(Map<String,Object> paramMap);
	Integer queryCount(Map<String,Object> paramMap);
	List<Role> queryAllRole();
	List<Integer> queryRoleByUserId(int id);
	int saveUserRoleRelationship(@Param("userid") Integer userid, @Param("data")Data data);
    int deleteRole(@Param("userid") Integer userid,@Param("data") Data data);
    List<Permission> queryPermissionsByUserId(Integer id);
}