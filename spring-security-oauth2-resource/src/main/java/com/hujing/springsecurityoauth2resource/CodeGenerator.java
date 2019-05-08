package com.hujing.springsecurityoauth2resource;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = "D:\\workspace\\oauth2-demo\\spring-security-oauth2-resource";
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("hujing");
        gc.setOpen(false);
        gc.setEnableCache(true);
        gc.setFileOverride(true);
        gc.setBaseResultMap(true); //生成基础resultMap
        gc.setBaseColumnList(true); //生成基础sql片段
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/resource?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pcConfig = new PackageConfig();
        pcConfig.setParent("com.hujing.springsecurityoauth2resource")
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("domain");
        mpg.setPackageInfo(pcConfig);


        /*============================================自定义配置=================================================*/
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);


        /*============================================配置模板=================================================*/
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

//        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);


        /*============================================策略配置=================================================*/
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(true); //全局大写命名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix(""); //设置表前缀
        strategy.setInclude("tb_content"); //生成表的名字
        strategy.setEntityLombokModel(true); //实体类添加lombok注解
//        strategy.setRestControllerStyle(true); //rest风格Controller
//        strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
//        strategy.setSuperEntityColumns("id");
//        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}