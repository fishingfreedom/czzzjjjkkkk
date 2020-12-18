package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import com.itheima.service.CheckItemService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    //分页查询检查组
    @RequestMapping("/pageQuery")
    public Result pageQuery(@RequestBody(required = false) QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = checkGroupService.pageQuery(queryPageBean);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 查询所有检查组
     * @return
     */
    @RequestMapping("/selectAll")
    public Result selectAll() {
        try {
            List checkGroups = checkGroupService.selectAll();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroups);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }

    //新建检查项
    @RequestMapping("/add")
    public Result addCheckGroup(Integer[] checkItemIds, @RequestBody CheckGroup checkGroup) {
        try {
            checkGroupService.add(checkItemIds, checkGroup);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    //编辑界面信息回显
    @RequestMapping("/infoRedisplay4Edit/{checkGroupId}")
    public Result infoRedisplay4Edit(@PathVariable("checkGroupId") Integer checkGroupId) {
        try {
            List resultList = checkGroupService.infoRedisplay4Edit(checkGroupId);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, resultList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //检查组编辑
    @RequestMapping("/edit")
    public Result edit(Integer[] checkItemIds, @RequestBody CheckGroup checkGroup){
        try {
            checkGroupService.edit(checkItemIds, checkGroup);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }
}
