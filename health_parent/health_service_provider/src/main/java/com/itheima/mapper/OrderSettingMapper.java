package com.itheima.mapper;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author NN
 */
public interface OrderSettingMapper {
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(OrderSetting record);

    /**
     * 选择性插入
     * @param record
     * @return
     */
    int insertSelective(OrderSetting record);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    OrderSetting selectByPrimaryKey(Integer id);

    /**
     * 选择性修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(OrderSetting record);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKey(OrderSetting record);

    /**
     * 查询当前日期是否已经添加预约信息
     * @param orderDate
     * @return
     */
    int findCountByOrderDate(Date orderDate);

    int updateNumberByOrderDate(OrderSetting orderSetting);

    /**
     * 查询预约设置信息
     * @param beginDate
     * @param endDate
     * @return
     */
    List<OrderSetting> selectNumberAndResv(@Param("beginDate") String beginDate,@Param("endDate") String endDate);


    OrderSetting selectByOrderDate(String orderDate);

    void updateReservationByOrderDate(String orderDate);
}