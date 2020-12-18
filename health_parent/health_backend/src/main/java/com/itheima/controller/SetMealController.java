package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.SetMeal;
import com.itheima.service.SetMealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * 套餐请求处理
 *
 * @author NN
 */
@RestController
@RequestMapping("/setMeal")
public class SetMealController {
    /*@Autowired
    private JedisPool jedisPool;*/
    @Reference
    private SetMealService setMealService;

    /**
     * 套餐图片上传
     *
     * @param imgFile
     * @return Result
     */
    @RequestMapping("/upload")
    public Result uploadPicture(MultipartFile imgFile) {
        //给图片指定唯一文件名
        String originalFilename = imgFile.getOriginalFilename();
        String filename = null;
        if (originalFilename != null && originalFilename.length() > 0){
            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            filename = UUID.randomUUID() + substring;
        }
        try {
            byte[] imgFileBytes = imgFile.getBytes();
            QiniuUtils.upload2Qiniu(imgFileBytes, filename);
            //  添加到redis
            //jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, filename);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true, MessageConstant.UPLOAD_SUCCESS, filename);
    }

    /**
     * 新增套餐
     *
     * @param checkGroupIds
     * @param setMeal
     * @return
     */
    @RequestMapping("/add")
    public Result add(Integer[] checkGroupIds, @RequestBody SetMeal setMeal) {
        try {
            setMealService.add(checkGroupIds, setMeal);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = null;
        try {
            pageResult = setMealService.pageQuery(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageResult;
    }
}
