package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.manager.dao.AccountTypeCertMapper;
import com.atguigu.atcrowdfunding.manager.service.CerttypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CerttypeServiceImpl implements CerttypeService {
    @Autowired
    private AccountTypeCertMapper accountTypeCertMapper;


    @Override
    public List<Map<String, Object>> queryCertAcctType() {
        return accountTypeCertMapper.queryCertAcctType();
    }
}
