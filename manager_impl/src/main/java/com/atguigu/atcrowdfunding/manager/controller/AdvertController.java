package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.AdvertService;
import com.atguigu.atcrowdfunding.ov.Data;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.util.StringUtil;
import jdk.nashorn.api.scripting.AbstractJSObject;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/advert")
public class AdvertController {

    @Autowired
    private AdvertService advertService;

    @RequestMapping("/index")
    public String index()
    {
        return "advert/index";
    }

    @RequestMapping("/toAdd")
    public String add()
    {
        return "advert/add";
    }
    @RequestMapping("/doDelete")
    @ResponseBody
    public Object doDelete(int id)
    {
        AjaxResult result = new AjaxResult();
        try {
            int count = advertService.deleteAdvert(id);
            result.setSuccess(count == 1);
        }catch (Exception e)
        {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;

    }

    @RequestMapping("/batchDelete")
    @ResponseBody
    public Object batchDelete(Data datas)
    {
        AjaxResult result=new AjaxResult();
        try{
            int count=advertService.batcheDelete(datas);
            if(count==datas.getAds().size())
            {
                result.setSuccess(true);
            }else {
                result.setSuccess(false);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    //    @RequestMapping("/doAdd")
//    public String doAdd(HttpServletRequest request, Advertisement advertisement, HttpSession session)
//    {
//       try{
//           MultipartHttpServletRequest mreq=(MultipartHttpServletRequest) request;
//           MultipartFile advpic = mreq.getFile("advpic");//上传后生成的临时文件
//           String name=advpic.getOriginalFilename();
//           String extname=name.substring(name.lastIndexOf("."));
//           String iconpath= UUID.randomUUID().toString()+extname;
//           ServletContext servletContext=session.getServletContext();
//           String realPath=servletContext.getRealPath("/pics");
//           String path=realPath+"\\adv\\"+iconpath;
//           advpic.transferTo(new File(path));//文件上传，保存到指定的目录
//           User user=(User)session.getAttribute(Const.LOGIN_USER);
//           advertisement.setUserid(user.getId());
//           advertisement.setStatus("1");
//           advertisement.setIconpath(iconpath);
//
//           int count=advertService.insertAdvert(advertisement);
//
//
//
//       }catch (Exception e)
//       {
//           e.printStackTrace();
//       }
//       return "redirect:/advert/index.htm";
//    }


    @RequestMapping("/doAdd")
    @ResponseBody
    public Object doAdd1(HttpServletRequest request, Advertisement advertisement, HttpSession session)
    {
        AjaxResult result=new AjaxResult();
        try{
            MultipartHttpServletRequest mreq=(MultipartHttpServletRequest) request;
            MultipartFile advpic = mreq.getFile("advpic");//上传后生成的临时文件
            String name=advpic.getOriginalFilename();
            String extname=name.substring(name.lastIndexOf("."));
            String iconpath= UUID.randomUUID().toString()+extname;
            ServletContext servletContext=session.getServletContext();
            String realPath=servletContext.getRealPath("/pics");
            String path=realPath+"\\adv\\"+iconpath;
            advpic.transferTo(new File(path));//文件上传，保存到指定的目录
            User user=(User)session.getAttribute(Const.LOGIN_USER);
            advertisement.setUserid(user.getId());
            advertisement.setStatus("1");
            advertisement.setIconpath(iconpath);

            int count=advertService.insertAdvert(advertisement);
            result.setSuccess(count==1);

        }catch (Exception e)
        {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping("/pageQuery")
    @ResponseBody
    public Object pageQuery(String pagetext,Integer pageno,Integer pagesize)
    {
        AjaxResult result=new AjaxResult();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("pageno", pageno);
            map.put("pagesize", pagesize);
            if (StringUtil.isNotEmpty(pagetext)) {
                pagetext = pagetext.replaceAll("%", "\\\\%");
            }

            map.put("pagetext", pagetext);
            Page<Advertisement> page = advertService.pageQuery(map);
            result.setPage(page);
            result.setSuccess(true);
        }catch (Exception e)
        {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }
}