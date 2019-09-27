package com.atguigu.atcrowdfunding.manager.controller;


import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.manager.service.CertService;
import com.atguigu.atcrowdfunding.manager.service.CerttypeService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/certtype")
public class CerttypeController{

    @Autowired
    private CerttypeService certtypeService;

    @Autowired
    private CertService certService;

    @RequestMapping("/index")
    public String toindex(Map<String,Object> map)
    {
        List<Cert> queryAllCerts= certService.queryAllCert();
        map.put("queryAllCerts",queryAllCerts);

        List<Map<String,Object>> certAcctType=certtypeService.queryCertAcctType();
        map.put("certAcctType",certAcctType);

        return "certtype/index";
    }

    @RequestMapping("/insertAcctTypeCert")
    @ResponseBody
    public Object insertAcctTypeCert(Integer certid,String accttype)
    {
        AjaxResult result=new AjaxResult();
        try{
            Map<String,Object>map=new HashMap<>();
            map.put("certid",certid);
            map.put("accttype",accttype);
            int count=certService.insertAcctTypeCert(map);
            result.setSuccess(count==1);
        }catch (Exception e)
        {
            result.setSuccess(false);
        }
        return result;
    }
    @RequestMapping("/deleteAcctTypeCert")
    @ResponseBody
    public Object deleteAcctTypeCert(Integer certid,String accttype)
    {
        AjaxResult result=new AjaxResult();
        try{
            Map<String,Object>map=new HashMap<>();
            map.put("certid",certid);
            map.put("accttype",accttype);
            int count=certService.deleteAcctTypeCert(map);
            result.setSuccess(count==1);
        }catch (Exception e)
        {
            result.setSuccess(false);
        }
        return result;
    }


}
