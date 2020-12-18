package com.itheima.mapper;

import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.SetMeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetMealMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SetMeal record);

    int insertSelective(SetMeal record);

    SetMeal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SetMeal record);

    int updateByPrimaryKey(SetMeal record);


    void insertSetMealAndCheckGroup(@Param("checkGroupId") Integer checkGroupId, @Param("setMealId") Integer setMealId);

    /**
     * 条件分页查询
     * @param queryPageBean
     * @return
     */
    List<SetMeal> pageQuery(QueryPageBean queryPageBean);

    /**
     * 条件查询套餐总数
     * @param queryString
     * @return
     */
    Long selectAllByCondition(String queryString);

    /**
     * 查询所有套餐
     * @return
     */
    List<SetMeal> selectAll();
}