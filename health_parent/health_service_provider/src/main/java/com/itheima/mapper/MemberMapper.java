package com.itheima.mapper;

import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Select;

public interface MemberMapper {


    Member selectByIdCard();

    void insert(Member member);

    Member selectByPrimaryKey(int id);

    Member selectByTel(String telephone);
}
