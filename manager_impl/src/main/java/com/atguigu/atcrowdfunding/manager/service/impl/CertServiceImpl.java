package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.manager.dao.CertMapper;
import com.atguigu.atcrowdfunding.manager.service.CertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CertServiceImpl implements CertService {

    @Autowired
    private CertMapper certMapper;

    @Override
    public List<Cert> queryCertByAcctType(String accttype) {
        return certMapper.queryCertByAcctType(accttype);
    }

    @Override
    public List<Cert> queryAllCert() {
        return certMapper.selectAll();
    }

    @Override
    public int insertAcctTypeCert(Map<String, Object> map) {
        return certMapper.insertAcctTypeCert(map);
    }

    @Override
    public int deleteAcctTypeCert(Map<String, Object> map) {
        return certMapper.deleteAcctTypeCert(map);
    }
}
