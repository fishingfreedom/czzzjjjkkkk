package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.SetMeal;

import java.util.List;

/**
 * 套餐服务接口
 */
public interface SetMealService {

    /**
     * 添加套餐
     * @param checkGroupIds
     * @param setMeal
     */
    void add(Integer[] checkGroupIds, SetMeal setMeal);

    /**
     * 查询所有套餐
     * @param queryPageBean
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    /**
     * 查询所有套餐
     * @return
     */
    List<SetMeal> getAllSetMeal();

    /**
     * 查询套餐详细信息
     * @param setMealId
     * @return
     */
    SetMeal findDetailById(int setMealId);

    /**
     * 查询套餐基本信息
     * @param setMealId
     * @return
     */
    SetMeal selectBasicSetMeal(int setMealId);
}
