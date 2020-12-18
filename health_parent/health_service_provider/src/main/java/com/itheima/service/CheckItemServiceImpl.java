package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.mapper.CheckItemMapper;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.CheckItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemMapper checkItemMapper;
    @Override
    public void add(CheckItem checkItem) {
        checkItemMapper.insert(checkItem);
    }

    /**
     * 条件分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //设置分页参数
        PageHelper.startPage(currentPage, pageSize);
        //查询,PageInfo
        List<CheckItem> rows = checkItemMapper.pageQuery(queryString);
        PageInfo<CheckItem> pageInfo = new PageInfo<CheckItem>(rows);
        long total = pageInfo.getTotal();
        int pageNum = pageInfo.getPageNum();
        return new PageResult(total, rows);
    }

    /**
     * 删除checkItem
     * @param id
     */
    @Override
    public void deleteCheckItem(long id) {
        //有外键约束时不删除
        checkItemMapper.deleteByPrimaryKey((int)id);
    }

    //编辑弹窗数据回显  id -> checkItem
    @Override
    public CheckItem findByCheckItemId(long checkItemId) {
        return checkItemMapper.selectByPrimaryKey((int)checkItemId);
    }

    //修改检查项信息
    @Override
    public void update(CheckItem checkItem) {
        checkItemMapper.updateByPrimaryKey(checkItem);
    }

    @Override
    public List<CheckItem> selectAll() {
        return checkItemMapper.selectByExample(new CheckItemExample());
    }

}
