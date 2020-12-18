package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.OrderSettingMapper;
import com.itheima.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 预约设置服务
 * @author NN
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional(rollbackFor = {})
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;


    /**
     * 预约设置上传数据库
     * @param list
     */
    @Override
    public void upload(List<OrderSetting> list) {
        for (OrderSetting orderSetting : list) {
            //判断当前日期是否已经添加预约信息
            int count = orderSettingMapper.findCountByOrderDate( orderSetting.getOrderDate());
            if (count > 0) {
                //执行更新操作
                orderSettingMapper.updateNumberByOrderDate(orderSetting);
            } else {
                //执行插入操作
                orderSettingMapper.insert(orderSetting);
            }
        }
    }

    /**
     * 展示日历中的预约信息
     * @param str_date
     * @return
     */
    @Override
    public List<Map> showOrderSettingMsg(String str_date) {
        String beginDate = str_date + "-1";
        String endDate = str_date + "-31";
        List<OrderSetting> orderSettings = orderSettingMapper.selectNumberAndResv(beginDate, endDate);
        List<Map> results = new ArrayList<>();
        for (OrderSetting orderSetting : orderSettings) {
            //封装Map对象
            Map map = new HashMap();
            map.put("date", orderSetting.getOrderDate().getDate());
            map.put("number", orderSetting.getNumber());
            map.put("reservations", orderSetting.getReservations());
            results.add(map);
        }
        return results;
    }

    /**
     * 设置预约人数
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //查询当前日期是否已经设置了预约人数
        int count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
        if (count > 0) {
            //已经设置了,此时进行修改操作
            orderSettingMapper.updateNumberByOrderDate(orderSetting);
        } else {
            orderSettingMapper.insert(orderSetting);
        }
    }
}
