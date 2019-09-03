package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.ov.Data;

import java.util.List;

public interface PermissionService{
    Permission getRootPermission();
    List<Permission> getChildrenPermissionByPid(int id);
    List<Permission> queryAllPermission();

    int savePermission(Permission permission);

    Permission getPermissionById(Integer id);

    int updatePermission(Permission permission);

    int deletePermission(Integer id);

    List<Integer> getPermissionIdsByRoleId(Integer roleid);

}
