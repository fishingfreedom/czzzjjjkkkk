package com.itheima;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerDemo {
    public static void main(String[] args) throws Exception{
        //1.创建配置类
        Configuration configuration=new Configuration(Configuration.getVersion());
        //2.设置模板所在的目录
        configuration.setDirectoryForTemplateLoading(new File("D:\\ftl"));
        //3.设置字符集
        configuration.setDefaultEncoding("utf-8");
        //4.加载模板
        Template template = configuration.getTemplate("test.txt");
        //5.创建数据模型
        Map map=new HashMap();
        map.put("name", "张三");
        map.put("message", "欢迎来到传智播客！");

        Map map1 = new HashMap();
        map1.put("mobile", null);
        map1.put("address", "北京市昌平区");
        //map.put("map1", map1);
        //6.创建Writer对象
        Writer out =new FileWriter(new File("d:\\ftl\\test.html"));
        //7.输出
        template.process(map, out);
        //8.关闭Writer对象
        out.close();
    }
}
