package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.mapper.CheckGroupMapper;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckGroupExample;
import com.itheima.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    //条件查询页面信息
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {

        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //设置分页
        PageHelper.startPage(currentPage, pageSize);
        List<CheckGroup> rows = checkGroupMapper.pageQuery(queryString);
        Page<CheckGroup> page = (Page<CheckGroup>) rows;

        return new PageResult(page.getTotal(), rows);
    }

    @Override
    //新增检查组
    public void add(Integer[] checkItemIds, CheckGroup checkGroup) {
        //1.插入到 CheckGroup表
        checkGroupMapper.insert(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        //2.插入到 checkItem-checkGroup中间表
        for (Integer checkItemId : checkItemIds) {
            checkGroupMapper.insertCheckItemAndCheckGroupRel(checkItemId, checkGroupId);
        }
    }

    //编辑界面信息回显
    @Override
    public List infoRedisplay4Edit(Integer checkGroupId) {
        //1.checkGroup信息查询
        CheckGroup checkGroup = checkGroupMapper.selectByPrimaryKey(checkGroupId);
        //2.checkItem-checkGroup关联信息查询
        List checkItemIds = checkGroupMapper.selectCheckItemIdsByCheckGroupId(checkGroupId);
        List resultList = new ArrayList();
        resultList.add(checkGroup);
        resultList.add(checkItemIds);
        return resultList;
    }

    //编辑checkGroup
    @Override
    public void edit(Integer[] checkItemIds, CheckGroup checkGroup) {
        //1.修改checkGroup
        checkGroupMapper.updateByPrimaryKeySelective(checkGroup);
        //2.删除,重新插入checkItem-checkGroup关联表
        Integer checkGroupId = checkGroup.getId();
        checkGroupMapper.delItemAndGroupRelByGroupId(checkGroupId);
        //3.插入到 checkItem-checkGroup中间表
        for (Integer checkItemId : checkItemIds) {
            checkGroupMapper.insertCheckItemAndCheckGroupRel(checkItemId, checkGroupId);
        }
    }
    //查询所有检查组
    @Override
    public List selectAll() {
         return checkGroupMapper.selectByExample(new CheckGroupExample());
    }
}
