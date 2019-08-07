package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Role;
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

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

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
             result.setMessage("²éÑ¯½á¹û³ö´í");
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
          result.setMessage("Ìí¼ÓÊ§°Ü");
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
            result.setMessage("É¾³ýÊ§°Ü");
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
}
