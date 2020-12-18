package com.itheima.mapper;

import com.itheima.pojo.Order;

public interface OrderMapper {

    void insert(Order order);

    Order selectById(int primaryKey);
}
