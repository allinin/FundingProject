package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.dao.PermissionMapper;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public Permission getRootPermission() {
        return permissionMapper.getRootPermission();
    }

    @Override
    public List<Permission> getChildrenPermissionByPid(int id) {
        return permissionMapper.getChildrenPermissionByPid(id);
    }

    @Override
    public List<Permission> queryAllPermission() {
        return  permissionMapper.selectAll();
    }
}
