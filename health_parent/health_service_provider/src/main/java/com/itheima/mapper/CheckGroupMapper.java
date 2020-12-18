package com.itheima.mapper;

import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckGroupExample;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupMapper {
    @SuppressWarnings("AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
    int countByExample(CheckGroupExample example);

    /**
     *
     * @param example
     * @return
     */
    int deleteByExample(CheckGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CheckGroup record);

    int insertSelective(CheckGroup record);

    List<CheckGroup> selectByExample(CheckGroupExample example);

    CheckGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CheckGroup record, @Param("example") CheckGroupExample example);

    int updateByExample(@Param("record") CheckGroup record, @Param("example") CheckGroupExample example);

    int updateByPrimaryKeySelective(CheckGroup record);

    int updateByPrimaryKey(CheckGroup record);

    /**
     * 条件查询检查组
     * @param queryString
     * @return
     */
    List<CheckGroup> pageQuery(@Param("queryString") String queryString);

    /**
     *          checkItem-checkGroup    关联表
     *
     *  */
    //插入数据
    void insertCheckItemAndCheckGroupRel(@Param("checkItemId") Integer checkItemId,@Param("checkGroupId") Integer checkGroupId);
    //    //    //查询关联表中checkItemIds
    List<Integer> selectCheckItemIdsByCheckGroupId(Integer checkGroupId);
    //删除关联关系
    void delItemAndGroupRelByGroupId(Integer checkGroupId);

    /**
     * 查询"套餐"关联的"检查组"信息
     * @param setMealId
     * @return
     */
    List<CheckGroup> selectGroupBySetMealId(int setMealId);

}