package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.MemberMapper;
import com.itheima.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 根据电话查找用户
     * @param telephone
     * @return
     */
    @Override
    public Member selectByTel(String telephone) {
        return memberMapper.selectByTel(telephone);
    }

    /**
     * 增加会员
     * @param member
     */
    @Override
    public void add(Member member) {
        memberMapper.insert(member);
    }
}
