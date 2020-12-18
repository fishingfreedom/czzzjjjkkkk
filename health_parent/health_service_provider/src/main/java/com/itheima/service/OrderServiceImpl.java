package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.mapper.MemberMapper;
import com.itheima.mapper.OrderMapper;
import com.itheima.mapper.OrderSettingMapper;
import com.itheima.mapper.SetMealMapper;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.pojo.SetMeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderSettingMapper orderSettingMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SetMealMapper setMealMapper;

    /**
     * 用户预约
     * @param map
     */
    @Override
    public Result submit(Map map) {
        //1. 进行会员检查, 未注册则直接注册会员
        String idCard = (String) map.get("idCard");
        Member member = memberMapper.selectByIdCard();
        if (member == null) {
            member = new Member();
            String name = (String) map.get("name");
            String sex = (String) map.get("sex");
            String telephone = (String) map.get("telephone");
            member.setIdCard(idCard);
            member.setName(name);
            member.setSex(sex);
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            //插入t_member表
            memberMapper.insert(member);
        }
        //2. 查询用户所选日期是否允许预约
        String orderDate = (String) map.get("orderDate");
        OrderSetting orderSetting = orderSettingMapper.selectByOrderDate(orderDate);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //2.1 判断是否预约满
        if (orderSetting.getNumber() <= orderSetting.getReservations()) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //3. 进行预约操作
        String setMealId = (String) map.get("setMealId");
        String orderType = (String) map.get("orderType");
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setSetmealId(Integer.parseInt(setMealId));
        order.setOrderType(orderType);
        order.setOrderStatus("未到诊");
        try {
            order.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").parse(orderDate));
            //3.1 order表插入数据
            orderMapper.insert(order);
            //3.2 orderSetting表预约人数 +1
            orderSettingMapper.updateReservationByOrderDate(orderDate);
            return new Result(true, MessageConstant.ORDER_SUCCESS, order.getId());
        } catch (ParseException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
    }

    /**
     * 查询预约人信息
     * @param id
     * @return
     */
    @Override
    public Map findById(int id) {
        Order order = orderMapper.selectById(id);
        Member member = memberMapper.selectByPrimaryKey(order.getMemberId());
        SetMeal setMeal = setMealMapper.selectByPrimaryKey(order.getSetmealId());
        Map map = new HashMap();
        map.put("member", member.getName());
        map.put("setmeal", setMeal.getName());
        String orderDate = JSON.toJSONString(order.getOrderDate());
        map.put("orderDate", orderDate);
        map.put("orderType", order.getOrderType());
        return map;
    }
}
