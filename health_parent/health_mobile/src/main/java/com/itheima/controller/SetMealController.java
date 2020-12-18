package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.SetMeal;
import com.itheima.service.SetMealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setMeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;

    /**
     * 查询所有套餐
     *
     * @return
     */
    @RequestMapping("/getAllSetMeal")
    public Result getAllSetMeal() {
        try {
            List<SetMeal> setMealList = setMealService.getAllSetMeal();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setMealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * 查询套餐详情
     * @param setMealId
     * @return
     */
    @RequestMapping("/findDetailById")
    public Result findDetailById(int setMealId) {
        try {
            SetMeal setMeal = setMealService.findDetailById(setMealId);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setMeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/selectBasicSetMeal")
    public Result selectBasicSetMeal(int setMealId) {
        try {
            SetMeal setMeal = setMealService.selectBasicSetMeal(setMealId);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setMeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
