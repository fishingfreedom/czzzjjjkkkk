package com.itheima.mapper;

import com.itheima.pojo.CheckItem;
import com.itheima.pojo.CheckItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckItemMapper {
    int countByExample(CheckItemExample example);

    int deleteByExample(CheckItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CheckItem record);

    int insertSelective(CheckItem record);

    List<CheckItem> selectByExample(CheckItemExample example);

    CheckItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CheckItem record, @Param("example") CheckItemExample example);

    int updateByExample(@Param("record") CheckItem record, @Param("example") CheckItemExample example);

    int updateByPrimaryKeySelective(CheckItem record);

    int updateByPrimaryKey(CheckItem record);

    //条件查询体检项
    List<com.itheima.pojo.CheckItem> pageQuery(@Param("queryString") String queryString);

    /**
     * 根据"检查组"查询其关联的"检查项"
     * @param checkGroupId
     * @return
     */
    List<CheckItem> selectCheckItemsByCheckGroupId(Integer checkGroupId);
}