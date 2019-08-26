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

    //demo1:��Ҫ��β�ѯ���ݿ⣬������Ĵ�
//    @RequestMapping("/loadData")
//    @ResponseBody
//    public Object loadData()
//    {
//        AjaxResult result =new AjaxResult();
//        try{
//            List<Permission> root=new ArrayList<>();
//            //�����
//            Permission permission=permissionService.getRootPermission();
//            permission.setOpen(true);
//            root.add(permission);
//
//            //�ӽڵ�
//            List<Permission> children=permissionService.getChildrenPermissionByPid(permission.getId());
//
//            //���ø��ӹ�ϵ
//            permission.setChildren(children);
//
//            for(Permission child:children)
//            {
//                child.setOpen(true);
//                //���ݸ���㣬��ѯ�ӽڵ�
//                List<Permission>innerChildren=permissionService.getChildrenPermissionByPid(child.getId());
//                child.setChildren(innerChildren);
//            }
//            result.setData(root);
//            result.setSuccess(true);
//        }catch (Exception e)
//        {
//            result.setSuccess(false);
//            result.setMessage("�������");
//            e.printStackTrace();
//        }
//        return result;
//    }

    //���õݹ�ķ�ʽ�����������Ķ���������
    private void queryChildPermission(Permission permission){
        List<Permission> children=permissionService.getChildrenPermissionByPid(permission.getId());
        //��ϸ��ӹ�ϵ
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
//            //�����
//            Permission permission=permissionService.getRootPermission();
//            root.add(permission);
//            queryChildPermission(permission);
//            result.setSuccess(true);
//            result.setData(root);
//        }catch (Exception e)
//        {
//            result.setSuccess(false);
//            result.setMessage("����ʧ��");
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
                //�����Ӳ˵�
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
            result.setMessage("����ʧ��");
        }

        return result;
    }
//Demo5 - ��Map���������Ҹ�,����ϸ��ӹ�ϵ.����ѭ���Ĵ��� ,�������.
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
//            //ͨ���Ӳ��Ҹ�
//            //�Ӳ˵�
//            Permission child = permission ; //����Ϊ�Ӳ˵�
//            if(child.getPid() == null ){
//                root.add(permission);
//            }else{
//                //���ڵ�
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
//        result.setMessage("�������������ʧ��!");
//    }
//
//
//    return result ;
//}
}
