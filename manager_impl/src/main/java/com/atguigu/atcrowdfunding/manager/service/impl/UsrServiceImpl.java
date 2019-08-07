package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.dao.UserMapper;
import com.atguigu.atcrowdfunding.exception.LoginFailException;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.ov.Data;
import com.atguigu.atcrowdfunding.util.MD5Util;
import com.atguigu.atcrowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UsrServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserlogin(Map<String, Object> paramMap) {
        User user = userMapper.queryUserlogin(paramMap);
        if(user==null)
        {
            throw new LoginFailException("用户账号或密码不正确");
        }
        return user;
    }

    @Override
    public Page queryPage(Map<String,Object> paramMap) {
        Page page=new Page((Integer)paramMap.get("pageno"),(Integer)paramMap.get("pagesize"));
        Integer startIndex=page.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<User> datas = userMapper.queryList(paramMap);
        page.setDatas(datas);
        Integer totalsize=userMapper.queryCount(paramMap);
        page.setTotalsize(totalsize);
        return page;
    }

    @Override
    public int saveUser(User user) {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm;ss");
        Date date=new Date();
        String createtime=simpleDateFormat.format(date);
        user.setCreatetime(createtime);
        user.setUserpswd(MD5Util.digest("123"));
        return userMapper.insert(user);
    }

    @Override
    public User queryUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateUser(User user) {

        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int BatchdeleteUser(Integer[] ids) {
        int count=0;
        for(Integer id:ids) {
            count += userMapper.deleteByPrimaryKey(id);
        }
        if(count!=ids.length){
            throw new RuntimeException("批量删除失败");
        }
        return count;
    }

    @Override
    public List<Role> queryAllRole() {
       return userMapper.queryAllRole();
    }

    @Override
    public List<Integer> queryRoleByUserId(Integer id) {
        return userMapper.queryRoleByUserId(id);
    }

    @Override
    public int saveUserRoleRelationship(Integer userid,Data data) {
        return userMapper.saveUserRoleRelationship(userid,data);
    }

    @Override
    public int deleteRole(Integer userid, Data data) {
        return userMapper.deleteRole(userid,data);
    }
}
