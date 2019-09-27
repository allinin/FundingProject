package com.atguigu.atcrowdfunding.potal.service;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.ov.Data;

import java.util.List;
import java.util.Map;

public interface MemberService {
    Member queryMemberLogin(Map<String, Object> paramMap);

    int updateAcctType(Member member);

    int updateMember(Member loginMember);

    void insertMemberCert(List<MemberCert>certimgs);
}
