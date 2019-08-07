package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.ov.Data;
import com.atguigu.atcrowdfunding.util.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RoleService {
    int saveRole(Role role);
    Page<Role> queryPage(Map map);
    int addRole(Role role);
    int delete(int id);
    int batchDeleteRole(Data datas);

}
