package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Cert;

import java.util.List;
import java.util.Map;

public interface CertService {
      List<Cert> queryCertByAcctType(String accttype);

      List<Cert> queryAllCert();

      int insertAcctTypeCert(Map<String, Object> map);

      int deleteAcctTypeCert(Map<String, Object> map);
}
