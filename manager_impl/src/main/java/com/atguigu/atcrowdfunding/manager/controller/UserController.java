package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.ov.Data;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    //同步请求
//    @RequestMapping("/index")
//    public String index(@RequestParam(value = "pageno" ,required = false,defaultValue = "1") Integer pageno,
//                        @RequestParam(value = "pagesize",required = false,defaultValue = "10") Integer pagesize,Map map)
//    {
//        Map<String,Object> paramMap=new HashMap<>();
//        paramMap.put("pageno",pageno);
//        paramMap.put("pagesize",pagesize);
//        Page page= userService.queryPage(paramMap);
//        map.put("page",page);
//
//        return "user/index";
//
//    }
    @RequestMapping("/toIndex")
    public String toIndex()
    {
        return "/user/index";
    }

    @RequestMapping("/toAdd")
    public String toAdd()
    {
        return "/user/add";
    }
    @RequestMapping("/toUpdate")
    public String toUpdate(@RequestParam(value = "id") Integer id,Map map)
    {
         User user=userService.queryUserById(id);
         map.put("user",user);
         return "/user/update";
    }
    @RequestMapping("/doAdd")
    @ResponseBody
    public Object add(User user)
    {
     AjaxResult result=new AjaxResult();
     try{
         int count =userService.saveUser(user);
         result.setSuccess(count==1);
     }catch (Exception e)
     {
         result.setSuccess(false);
         e.printStackTrace();
         result.setMessage("保存数据失败");
     }
     return result;
    }

    @RequestMapping("/doUpdate")
    @ResponseBody
    public Object update(User user)
    {
        AjaxResult result=new AjaxResult();
        try{
            int count =userService.updateUser(user);
            result.setSuccess(count==1);
        }catch (Exception e)
        {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("保存数据失败");
        }
        return result;
    }


    @RequestMapping("/index")
    @ResponseBody
    public Object index(@RequestParam(value = "pageno",required = false,defaultValue ="1") Integer pageno,
                        @RequestParam(value = "pagesize",required = false,defaultValue = "10") Integer pagesize,String queryContent)
    {
        AjaxResult result =new AjaxResult();
        try {
            Map paramMap = new HashMap();
            paramMap.put("pageno",pageno);
            paramMap.put("pagesize",pagesize);
            if(StringUtil.isNotEmpty(queryContent))
            {
                if(queryContent.contains("%"))
                {
                    queryContent=queryContent.replaceAll("%", "\\\\%");

                }
                paramMap.put("queryContent", queryContent);
            }
            Page page = userService.queryPage(paramMap);
            result.setSuccess(true);
            result.setPage(page);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("查询数据失败");
        }
        return result;
    }
    @RequestMapping("/doDelete")
    @ResponseBody
    public Object doDelete(Integer id)
    {
        AjaxResult result =new AjaxResult();
        try {
             int count=userService.deleteUser(id);
             result.setSuccess(count==1);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("查询数据失败");
        }
        return result;
    }
    @RequestMapping("/doDeleteBatch")
    @ResponseBody
    public Object doDeleteBatch(Integer[] id)
    {
        AjaxResult result =new AjaxResult();
        try {
            int count=userService.BatchdeleteUser(id);
            result.setSuccess(count==id.length);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("查询数据失败");
        }
        return result;
    }
    @RequestMapping("/assignRole")
    public String assignRole(Integer id,Map map)
    {
        List<Role> allRole=userService.queryAllRole();
        List<Integer> roleId=userService.queryRoleByUserId(id);

        List<Role>leftRole=new ArrayList<>();
        List<Role>rightRole=new ArrayList<>();

        for(Role role:allRole)
        {
            if(roleId.contains(role.getId()))
            {
                rightRole.add(role);
            }else{
               leftRole.add(role);
            }
        }

        map.put("leftRole",leftRole);
        map.put("rightRole",rightRole);



      return "user/assignRole";
    }
    //分配角色
    @ResponseBody
    @RequestMapping("/doAssignRole")
    public Object doAssignRole(Integer userid,Data data){
        AjaxResult result = new AjaxResult();
        try {

            userService.saveUserRoleRelationship(userid,data);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            e.printStackTrace();
            result.setMessage("分配角色数据失败!");
        }

        return result;
    }

    @RequestMapping("/doDeleteRole")
    @ResponseBody
    public Object doDeleteRole(Integer userid,Data data)
    {
        AjaxResult result=new AjaxResult();
        try{
            int count=userService.deleteRole(userid,data);
            result.setSuccess(true);

        }catch (Exception e)
        {
            e.printStackTrace();
            result.setMessage("撤销失败");
            result.setSuccess(false);
        }
        return  result;
    }

}
