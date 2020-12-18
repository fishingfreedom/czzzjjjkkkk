package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {

    //条件查询页面信息
    PageResult pageQuery(QueryPageBean queryPageBean);
    //新增检查组
    void add(Integer[] checkItemIds, CheckGroup checkGroup);
    //编辑数据回显
    List infoRedisplay4Edit(Integer checkGroupId);
    //编辑检查组
    void edit(Integer[] checkItemIds, CheckGroup checkGroup);
    //查询所有检查组
    List selectAll();
}
