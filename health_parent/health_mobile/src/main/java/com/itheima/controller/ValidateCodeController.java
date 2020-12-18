package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * 验证码
 *
 * @author NN
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order/{tel}")
    public Result send4Order(@PathVariable("tel") String telephone) {
        //防止用户恶意点击发送验证码
        //获取当前数据剩余的存活时间
        Long remainderSurvivalTime = jedisPool.getResource().ttl(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        if (remainderSurvivalTime > 300) {
            return new Result(false, "请勿频繁操作");
        }

        //1. 生成验证码
        String validateCode = String.valueOf(ValidateCodeUtils.generateValidateCode(4));
        System.out.println("*****************验证码:" + validateCode + "*********************");
        try {
            //2. 发送验证码
            SMSUtils.sendShortMessage(SMSUtils.ORDER_VALIDATE_CODE, telephone, SMSUtils.validateCode_param(validateCode));
            //3. 将验证码保存到redis中, 有效时长30分钟      global_session
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 60 * 30, validateCode);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }


    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        //防止用户恶意点击发送验证码
        //获取当前数据剩余的存活时间
        Long remainderSurvivalTime = jedisPool.getResource().ttl(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (remainderSurvivalTime > 300) {
            return new Result(false, "请勿频繁操作");
        }

        //1. 生成验证码
        String validateCode = String.valueOf(ValidateCodeUtils.generateValidateCode(4));
        System.out.println("*****************验证码:" + validateCode + "*********************");
        try {
            //2. 发送验证码
            //SMSUtils.sendShortMessage(SMSUtils.ORDER_VALIDATE_CODE, telephone, SMSUtils.validateCode_param(validateCode));
            //3. 将验证码保存到redis中, 有效时长30分钟      global_session
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN, 60 * 30, validateCode);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
