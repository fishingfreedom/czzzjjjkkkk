package com.itheima.service;

import com.itheima.entity.Result;
import com.itheima.pojo.Order;

import java.util.Map;

public interface OrderService {
    /**
     * 预约服务
     * @param map
     */
    Result submit(Map map);

    /**
     * 查询预约人信息
     * @param id
     * @return
     */
    Map findById(int id);
}
