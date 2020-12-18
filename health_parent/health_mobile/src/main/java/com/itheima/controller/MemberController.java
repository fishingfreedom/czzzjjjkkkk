package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    @RequestMapping("/login")
    public Result login(@RequestBody Map map) {
        // telephone   validateCode
        //1. 比对验证码
        String telephone = (String) map.get("telephone");
        String validateCode_input = (String) map.get("validateCode");
        String validateCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (validateCode == null || !validateCode.equals(validateCode_input)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //2. 查看用户是否已经注册
        Member member = memberService.selectByTel(telephone);
        if (member == null ) {
            //未注册则进行注册
            member = new Member();
            member.setPhoneNumber(telephone);
            memberService.add(member);
        }
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
