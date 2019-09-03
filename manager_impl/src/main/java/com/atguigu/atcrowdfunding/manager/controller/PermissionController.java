package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private  PermissionService permissionService;

    @RequestMapping("/index")
    public String index() {
        return "permission/index";
    }

    @RequestMapping("/toAdd")
    public String toAdd()
    {
        return "permission/add";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id,Map map)
    {   Permission permission=permissionService.getPermissionById(id);
        map.put("permission",permission);
        return "permission/update";
    }
    @RequestMapping("/doAdd")
    @ResponseBody
    public Object doAdd(Permission permission)
    {
       AjaxResult result =new AjaxResult();
       try{
           String icon=permissionService.getPermissionById(permission.getPid()).getIcon();
           permission.setIcon(icon);
           int count=permissionService.savePermission(permission);
           result.setSuccess(count==1);


       }catch (Exception e)
       {
           result.setSuccess(false);
           result.setMessage("保存许可树数据失败");
       }

       return  result;
    }

    @RequestMapping("/doUpdate")
    @ResponseBody
    public Object doUpdate(Permission permission)
    {
        AjaxResult result=new AjaxResult();
        try{
            int count=permissionService.updatePermission(permission);
            result.setSuccess(count==1);

        }catch (Exception e)
        {
           result.setSuccess(false);
            result.setMessage("修改许可树数据失败!");
        }
        return result;
    }

    @RequestMapping("/doDelete")
    @ResponseBody
    public Object doDelete(Integer id)
    {
        AjaxResult result=new AjaxResult();
        List<Permission> childrenPermissions = permissionService.getChildrenPermissionByPid(id);

        try {
            if (!childrenPermissions.isEmpty()) {
                int count=0;
                for (Permission permisson : childrenPermissions) {
                    int childId=permisson.getId();
                    permissionService.deletePermission(childId);
                    count++;
                }
                int i = permissionService.deletePermission(id);
                result.setSuccess((count+i)==(childrenPermissions.size()+1));
            }else {
                int m=permissionService.deletePermission(id);
                result.setSuccess(m==1);
           }


        }catch (Exception e)
        {
            result.setSuccess(false);
        }
        return result;
    }

    //demo1:需要多次查询数据库，性能损耗大
//    @RequestMapping("/loadData")
//    @ResponseBody
//    public Object loadData()
//    {
//        AjaxResult result =new AjaxResult();
//        try{
//            List<Permission> root=new ArrayList<>();
//            //父结点
//            Permission permission=permissionService.getRootPermission();
//            permission.setOpen(true);
//            root.add(permission);
//
//            //子节点
//            List<Permission> children=permissionService.getChildrenPermissionByPid(permission.getId());
//
//            //设置父子关系
//            permission.setChildren(children);
//
//            for(Permission child:children)
//            {
//                child.setOpen(true);
//                //根据父结点，查询子节点
//                List<Permission>innerChildren=permissionService.getChildrenPermissionByPid(child.getId());
//                child.setChildren(innerChildren);
//            }
//            result.setData(root);
//            result.setSuccess(true);
//        }catch (Exception e)
//        {
//            result.setSuccess(false);
//            result.setMessage("请求出错");
//            e.printStackTrace();
//        }
//        return result;
//    }

    //采用递归的方式来解决许可树的多个层次问题
    private void queryChildPermission(Permission permission){
        List<Permission> children=permissionService.getChildrenPermissionByPid(permission.getId());
        //组合父子关系
        permission.setChildren(children);

        for(Permission innerChildren:children)
        {
            queryChildPermission(innerChildren);
        }
    }

//    @RequestMapping("/loadData")
//    @ResponseBody
//    public Object loadData()
//    {
//        AjaxResult result=new AjaxResult();
//        try{
//            List<Permission>root=new ArrayList<>();
//
//            //父结点
//            Permission permission=permissionService.getRootPermission();
//            root.add(permission);
//            queryChildPermission(permission);
//            result.setSuccess(true);
//            result.setData(root);
//        }catch (Exception e)
//        {
//            result.setSuccess(false);
//            result.setMessage("加载失败");
//        }


//
//        return result;
//    }

            //一次全部加载数据，减少与数据库的通信。提高访问效率
            @RequestMapping("/loadData")
            @ResponseBody
            public Object loadData()
            {
                AjaxResult result=new AjaxResult();

                try{

                    List<Permission> root=new ArrayList<>();
                    List<Permission> childrenPermission=permissionService.queryAllPermission();
                    for(Permission permission:childrenPermission)
            {
                //设置子菜单
                Permission child=permission;
                if(permission.getPid()==null)
                {
                    permission.setOpen(true);
                    root.add(permission);
                }else{

                    for(Permission innerPermission:childrenPermission)
                    {
                        if(child.getPid()==innerPermission.getId())
                        {
                            Permission parent=innerPermission;
                            parent.getChildren().add(child);
                            break;
                        }
                    }
                }
            }
            result.setSuccess(true);
            result.setData(root);
        }catch (Exception e)
        {
            result.setSuccess(false);
            result.setMessage("加载失败");
        }

        return result;
    }
//Demo5 - 用Map集合来查找父,来组合父子关系.减少循环的次数 ,提高性能.
//@ResponseBody
//@RequestMapping("/loadData")
//public Object loadData(){
//    AjaxResult result = new AjaxResult();
//
//    try {
//
//        List<Permission> root = new ArrayList<Permission>();
//
//
//        List<Permission> childredPermissons =  permissionService.queryAllPermission();
//
//
//        Map<Integer,Permission> map = new HashMap<Integer,Permission>();//100
//
//        for (Permission innerpermission : childredPermissons) {
//            map.put(innerpermission.getId(), innerpermission);
//        }
//
//
//        for (Permission permission : childredPermissons) { //100
//            //通过子查找父
//            //子菜单
//            Permission child = permission ; //假设为子菜单
//            if(child.getPid() == null ){
//                root.add(permission);
//            }else{
//                //父节点
//                Permission parent = map.get(child.getPid());
//                parent.getChildren().add(child);
//            }
//        }
//
//
//
//        result.setSuccess(true);
//        result.setData(root);
//
//    } catch (Exception e) {
//        result.setSuccess(false);
//        result.setMessage("加载许可树数据失败!");
//    }
//
//
//    return result ;
//}


}
