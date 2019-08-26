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
