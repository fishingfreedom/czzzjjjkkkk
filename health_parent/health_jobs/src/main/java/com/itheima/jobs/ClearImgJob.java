package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.Set;

/**
 * 定时清理图片
 *
 * @author 闫泽辉
 */
public class ClearImgJob {
    private JedisPool jedisPool;

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 清理垃圾图片
     */
    public void clearImg(){
        Jedis resource = jedisPool.getResource();
        Set<String> rubbishImgNames = resource.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (rubbishImgNames.isEmpty()) {
            return;
        }
        for (String rubbishImgName : rubbishImgNames) {
            QiniuUtils.deleteFileFromQiniu(rubbishImgName);
            resource.srem(RedisConstant.SETMEAL_PIC_RESOURCES, rubbishImgName);
        }
    }
}
