package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.ov.Data;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.util.StringUtil;
import com.sun.xml.internal.ws.handler.MessageUpdatableContext;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

//    @RequestMapping("/index")
//    public String index(@RequestParam(value = "pageno" ,required = false,defaultValue = "1") Integer pageno,
//                        @RequestParam(value = "pagesize",required = false,defaultValue = "10") Integer pagesize,Map map)
//    {
//        Map<String,Object> paramMap=new HashMap<>();
//        paramMap.put("pageno",pageno);
//        paramMap.put("pagesize",pagesize);
//        Page page= roleService.queryPage(paramMap);
//        map.put("page",page);
//
//        return "role/index";
//
//    }
    @RequestMapping("/index")
    public String index()
    {
        return "role/index";
    }

    @RequestMapping("/doIndex")
    @ResponseBody
    public Object doIndex(@RequestParam(value = "pageno" ,required = false,defaultValue = "1") Integer pageno,
                          @RequestParam(value ="pagesize",required = false ,defaultValue = "10") Integer pagesize,String queryContent )
    {
        AjaxResult result=new AjaxResult();
         try {
             Map<String, Object> map = new HashMap<>();
             map.put("pageno", pageno);
             map.put("pagesize", pagesize);
             if (!StringUtil.isEmpty(queryContent)) {
                 if(queryContent.contains("%"))
             queryContent.replaceAll("%", "\\\\%");
             map.put("queryContent",queryContent);
         }
        Page<Role> page=roleService.queryPage(map);
        result.setSuccess(true);
             result.setPage(page);
         }catch (Exception e)
         {
             result.setSuccess(false);
             result.setMessage("查询结果出错");
         }

         return result;

    }
    @RequestMapping("/add")
    public String doAdd()
    {
        return "role/add";
    }

    @RequestMapping("/doAdd")
    @ResponseBody()
    public Object doAdd(String name)
    {  AjaxResult result=new AjaxResult();
      try {
          Role role = new Role();
          role.setName(name);
          int count = roleService.addRole(role);
          result.setSuccess(count==1);
      }catch (Exception e)
      {
          result.setSuccess(false);
          result.setMessage("添加失败");
          e.printStackTrace();
      }
      return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(int id)
    {
        AjaxResult result=new AjaxResult();
        try{

            int count=roleService.delete(id);
            result.setSuccess(count==1);
        }catch (Exception e)
        {
            result.setSuccess(false);
            result.setMessage("删除失败");
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/doDeleteBatch")
    @ResponseBody
    public Object doDeleteBatch(Data datas)
    {
        AjaxResult result=new AjaxResult();
        try{
            int count=roleService.batchDeleteRole(datas);
             if(count==datas.getUserList().size())
             {
                 result.setSuccess(true);
             }else {
                 result.setSuccess(false);

             }

        }catch (Exception e)
        {
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/update")
    public String update(@RequestParam(value="id") Integer id,Map map)
    {
        Role role=roleService.queryRoleById(id);
        map.put("role",role);
        return "role/update";
    }
    @RequestMapping("/doUpdate")
    @ResponseBody
    public Object doUpdate(Role role)
    {
        AjaxResult result=new AjaxResult();
        try{
            int count=roleService.updateRoleById(role);
            result.setSuccess(count==1);

        }catch (Exception e)
        {
            result.setSuccess(false);
            result.setMessage("修改失败");
        }
        return result;
    }

    @RequestMapping("/assignPermission")
    public String assignPermission()
    {
        return "role/assignPermission";
    }
    @RequestMapping("/loadDataAsync")
    @ResponseBody
    public Object loadDataAsync(Integer roleid)
    {
        List<Permission> root=new ArrayList<>();
        List<Permission>childrenPermissons=permissionService.queryAllPermission();

        //显示该角色分配的权限
        List<Integer> permissionIdsForRoleid=permissionService.getPermissionIdsByRoleId(roleid);

        Map<Integer,Permission> map=new HashMap<>();
        for(Permission permission:childrenPermissons)
        {
            map.put(permission.getId(),permission);
            if(permissionIdsForRoleid.contains(permission.getId()))
            {
                permission.setChecked(true);
            }
        }

        for(Permission innerPermission:childrenPermissons)
        {
            //通过子查找父
            //子节点
            Permission child=innerPermission;
            if(child.getPid()==null){
                root.add(child);
            }else{
                Permission parent=map.get(child.getPid());
                parent.getChildren().add(child);
            }
        }
        return root;
    }

    @RequestMapping("/doAssignPermissions")
    @ResponseBody
    public Object doAssignPermissions(Integer roleid,Data datas)
    {
        AjaxResult result=new AjaxResult();
        try{
            int count=roleService.saveRolePermissionRelationship(roleid,datas);
            result.setSuccess(count==datas.getIds().size());
        }catch (Exception e)
        {
            result.setSuccess(false);
        }
        return result;
    }
}
