package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RequestMapping("/order")
@RestController
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    /**
     * 体检预约
     * @param map
     * @return
     */
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {
        //1. 从redis中获取验证码, 比对验证码
        String telephone = (String) map.get("telephone");
        String validateCode_input = (String) map.get("validateCode");
        String validateCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        if (validateCode == null || !validateCode.equals(validateCode_input)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        //2. 调用服务进行预约
        map.put("orderType", "微信预约");
        try {
            return orderService.submit(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
    }

    /**
     * 预约成功信息展示
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(int id) {
        try {
            Map map = orderService.findById(id);
            return new Result(true, MessageConstant.ORDER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.ORDER_FAIL);
        }
    }
}
