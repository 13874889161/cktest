package com.lemon;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

public class CodeGenerator {
	
public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("qjf"); //作者
        gc.setOpen(false);
        gc.setFileOverride(true);

        gc.setServiceName("%sService");//去掉IXXService的I
        gc.setIdType(IdType.ID_WORKER);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true); //实体属性 Swagger2 加上注解
        mpg.setGlobalConfig(gc);

        // 数据源(数据库)配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/testplatform?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");  //数据库用户
        dsc.setPassword("443321"); //密码
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 包配置--需要生成的几个包
        PackageConfig pc = new PackageConfig();     
        pc.setParent("com.lemon"); //包的父包
      // pc.setModuleName("模块名");
        pc.setController("controller");  //控制层包
        pc.setService("service");  //业务层包
        pc.setMapper("mapper");  //dao层()包
     
        pc.setEntity("pojo"); //每个表都会封装成一个普通的java类  放在pojo类中
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        String templatePath = "/templates/mapper.xml.ftl";
     // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
               //mapper文件生成的全是xml文件,现在已经全部由 注解替代了
            	return projectPath + "/src/main/resources/mapper/" 
                       + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        // 关闭默认 xml 生成
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
      //需要生成的表名，多个表用英文逗号间隔
        strategy.setInclude("user","test_rule","test_report",
        		"suite","project","cases","case_param_value",
        		"api_request_param","api_classification","api");
     
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(false); //url中驼峰转连字符
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.setStrategy(strategy);
      
       
        // 6、执行
        mpg.execute();
    }

}