package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.CheckItemExample;

import java.util.List;

public interface CheckItemService {
    //添加检查项
    public abstract void add(CheckItem checkItem);

    PageResult pageQuery(QueryPageBean queryPageBean);

    void deleteCheckItem(long id);

    CheckItem findByCheckItemId(long checkItemId);

    void update(CheckItem checkItem);

    List<CheckItem> selectAll();
}
