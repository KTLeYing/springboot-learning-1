package com.example.mybatisplusdemo1.generate;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @ClassName :   CodeGenerator5
 * @Description: mybatis代码生成器（好用,官网演示）
 * @Author: mzl
 * @CreateDate: 2020/11/6 2:01
 * @Version: 1.0
 */
public class CodeGenerator3 {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/test3?useSSL=true&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("105293");
        mpg.setDataSource(dsc);                                 //设置数据源配置

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");    //获取当前项目的根目录
        gc.setOutputDir(projectPath + "/src/main/java");        //设置输出文件夹
        gc.setAuthor("mzl");                                    //设置作者信息
        gc.setOpen(false);                                      //生成之后是否打开文件夹
        gc.setServiceName("%sService");                         //设置service接口不包含I开头
        mpg.setGlobalConfig(gc);                                //设置全局配置


        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.example");                //设置java目录下的输出包位置
        pc.setModuleName("mybatisplusdemo1");            //设置包名
        pc.setEntity("entity");                 //设置实体类
        pc.setMapper("mapper");                 //设置mapper接口
        pc.setService("service");               //service
        pc.setServiceImpl("service.impl");      //设置service实现类
        pc.setController("controller");         //controller层
        mpg.setPackageInfo(pc);                 //设置包配置

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);                      //开启_转驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);                //开启_转驼峰命名
        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);                                        //lombok
        strategy.setRestControllerStyle(true);                                      //RestController
        strategy.setSuperEntityColumns("id");                                       //实体公共字段
        String[] tables={"user","teacher","student","blog"};                               //设置输入表
        strategy.setInclude(tables);                                                //多个表名
        strategy.setControllerMappingHyphenStyle(true);                             //是否生成实体时，生成字段注解
        strategy.setTablePrefix(pc.getModuleName() + "_");                          //字段前缀


        mpg.setStrategy(strategy);  //执行
        mpg.execute();
    }

}
