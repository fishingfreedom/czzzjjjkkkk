package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * 预约服务
 * @author NN
 */
public interface OrderSettingService {

    /**
     * 预约设置上传
     * @param orderSettingList
     */
    void upload(List<OrderSetting> orderSettingList);

    List<Map> showOrderSettingMsg(String str_date);

    /**
     * 设置预约人数
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting);
}
