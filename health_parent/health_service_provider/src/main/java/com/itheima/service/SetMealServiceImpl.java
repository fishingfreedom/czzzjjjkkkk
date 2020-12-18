package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.mapper.CheckGroupMapper;
import com.itheima.mapper.CheckItemMapper;
import com.itheima.mapper.SetMealMapper;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.SetMeal;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 套餐业务
 *
 * @author 闫泽辉
 */
@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealMapper setMealMapper;
    @Autowired
    private CheckGroupMapper checkGroupMapper;
    @Autowired
    private CheckItemMapper checkItemMapper;
    @Value("${outPutPath}")
    private String outPutPath;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    /*@Autowired
    private JedisPool jedisPool;*/

    /**
     * 添加套餐
     *
     * @param checkGroupIds
     * @param setMeal
     */
    @Override
    public void add(Integer[] checkGroupIds, SetMeal setMeal) {
        //1. 插入setmeal表
        setMealMapper.insertSelective(setMeal);
        Integer setMealId = setMeal.getId();
        //存入缓存中
        //jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setMeal.getImg());

        //2. 插入setmeal-checkGroup关系表
        for (Integer checkGroupId : checkGroupIds) {
            setMealMapper.insertSetMealAndCheckGroup(checkGroupId, setMealId);
        }

        //3. 页面静态化
        //List<SetMeal> setMealList = setMealMapper.selectAll();
        //generateSetMealListStaticHtml(setMealList);
        //generateAllSetMealDetailHtml(setMealList);
    }

    /**
     * 生成套餐列表静态页面
     */
    private void generateSetMealListStaticHtml(List<SetMeal> setMealList) {
        //封装数据
        Map map = new HashMap();
        map.put("setMealList", setMealList);
        generateStaticHtml("mobile_setmeal.ftl", "m_setMeal.html", map);
    }

    /**
     * 生成套餐详情静态页面
     * @param setMealList
     */
    public void generateAllSetMealDetailHtml(List<SetMeal> setMealList) {
        for (SetMeal setMeal : setMealList) {
            setMeal = findDetailById(setMeal.getId());
            Map map = new HashMap();
            map.put("setmeal", setMeal);
            generateStaticHtml("mobile_setmeal_detail.ftl", "setmeal_detail_" + setMeal.getId() + ".html", map);
        }
    }

    /**
     * 输出静态页面工具
     * @param templateName
     * @param htmlName
     * @param argsMap
     */
    public void generateStaticHtml(String templateName, String htmlName, Map argsMap) {
        //获取配置对象
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        BufferedWriter br = null;
        try {
            //获取模板对象
            Template template = configuration.getTemplate(templateName);
            br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outPutPath + htmlName)), StandardCharsets.UTF_8));
            //合并模板和数据输出为html
            template.process(argsMap, br);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 分页条件查询所有套餐
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        //设置分页
        PageHelper.startPage(currentPage, pageSize);
        List<SetMeal> setMeals = setMealMapper.pageQuery(queryPageBean);
        Page<SetMeal> setMealPage = (Page<SetMeal>) setMeals;
        return new PageResult(setMealPage.getTotal(), setMeals);
    }

    /**
     * 查询所有套餐
     *
     * @return
     */
    @Override
    public List<SetMeal> getAllSetMeal() {
        return setMealMapper.selectAll();
    }

    /**
     * 查询套餐详细信息
     * @param setMealId
     * @return
     */
    @Override
    public SetMeal findDetailById(int setMealId) {
        //1.查询"套餐"基本信息
        SetMeal setMeal = setMealMapper.selectByPrimaryKey(setMealId);
        //2.查询"套餐"关联的"检查组"信息
        List<CheckGroup> checkGroups = checkGroupMapper.selectGroupBySetMealId(setMealId);
        setMeal.setCheckGroups(checkGroups);
        //3.遍历"检查组"集合
        for (CheckGroup checkGroup : checkGroups) {
            //根据"检查组"查询其关联的"检查项"
            List<CheckItem> checkItems = checkItemMapper.selectCheckItemsByCheckGroupId(checkGroup.getId());
            checkGroup.setCheckItems(checkItems);
        }
        return setMeal;
    }

    /**
     * 查询套餐基本信息
     * @param setMealId
     * @return
     */
    @Override
    public SetMeal selectBasicSetMeal(int setMealId) {
        return setMealMapper.selectByPrimaryKey(setMealId);
    }

}
