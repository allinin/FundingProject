package com.atguigu.atcrowdfunding.potal.dao;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.ov.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MemberMapper{
    int updataAcctType(Member member);


    Member selectMemberLogin(Map<String,Object> paramMap);

    int updateMember(Member loginMember);

    void insertMemberCert( MemberCert memberCert);
    void updateEmail(Member member);

    void updateAuthstatus(Member loginMember);
}
