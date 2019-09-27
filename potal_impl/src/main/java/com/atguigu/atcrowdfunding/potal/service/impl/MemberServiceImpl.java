package com.atguigu.atcrowdfunding.potal.service.impl;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.ov.Data;
import com.atguigu.atcrowdfunding.potal.dao.MemberMapper;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member queryMemberLogin(Map<String, Object> paramMap) {
        return memberMapper.selectMemberLogin(paramMap);
    }

    @Override
    public int updateAcctType(Member member) {
        return memberMapper.updataAcctType(member);
    }

    @Override
    public int updateMember(Member loginMember) {
        return memberMapper.updateMember(loginMember);
    }

    @Override
    public void insertMemberCert(List<MemberCert> certimgs) {
        for(MemberCert memberCert:certimgs)
        {
            memberMapper.insertMemberCert(memberCert);
        }

    }

}
