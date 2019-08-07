package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.manager.dao.RoleMapper;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.ov.Data;
import com.atguigu.atcrowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public int saveRole(Role role) {
      return roleMapper.insert(role);
    }

    @Override
    public Page<Role> queryPage(Map map) {
        Page page=new Page((Integer)map.get("pageno"),(Integer)map.get("pagesize"));
        int startIndex=page.getStartIndex();
        map.put("startIndex",startIndex);
        List<Role> roles= roleMapper.queryRoler(map);
        page.setDatas(roles);
        int totalSize=roleMapper.queryNum(map);
        page.setTotalsize(totalSize);
        return page;


    }

    @Override
    public int addRole(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public int delete(int id){
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int batchDeleteRole(Data datas) {
        return roleMapper.batchDeleteObj(datas);
    }
}
