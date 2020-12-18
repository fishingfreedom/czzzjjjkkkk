package com.itheima.service;

import com.itheima.pojo.Member;

public interface MemberService {
    Member selectByTel(String telephone);

    /**
     * 增加会员
     * @param member
     */
    void add(Member member);
}
