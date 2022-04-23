package com.jimmy.async.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

/**
 * @Description mybatis 代码生成器
 * @Author zhangguoq
 **/
public class MysqlGenerator {


    public static void main(String[] args) {
        // 数据源配置
        String url = "jdbc:mysql://localhost:3306/homemaking?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8";
        String username = "root";
        String password = "Root-1234";
        DataSourceConfig dsc = new DataSourceConfig.Builder(url, username, password)
                // 类型转换,数据库=》JAVA类型
                .typeConvert(new MySqlTypeConvert())
                // 关键字处理 ,这里选取了mysql5.7文档中的关键字和保留字（含移除）
                .keyWordsHandler(new MySqlKeyWordsHandler())
                // 数据库信息查询类,默认由 dbType 类型决定选择对应数据库内置实现
                .dbQuery(new MySqlQuery())
                // 数据库名称
//                .schema("homemaking")
                .build();

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator(dsc);

        String projectPath = System.getProperty("user.dir");

        // 全局配置
        GlobalConfig gc = new GlobalConfig.Builder()
                .author("jimmy")
                //重新生成文件是否覆盖
                .fileOverride()
                //是否打开生成目录
                .openDir(false)
                .outputDir(projectPath + "/springbootAsync/src/main/java/").build();
        mpg.global(gc);

        // 包配置
        PackageConfig pc = new PackageConfig.Builder()
                // 父包名称
                .parent("com.jimmy.async")
                // 备份时开启
                .moduleName("copy")
                .build();
        mpg.packageInfo(pc);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                // 指定表名（可以同时操作多个表，使用 , 隔开）
                .addInclude("test_a", "test_b")
//                .addTablePrefix("t_l_")
                .entityBuilder()
                .idType(IdType.ASSIGN_ID)
                // 驼峰命名策略
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                // 开启lombok模型
                .enableLombok()
                // 实体配置构建者
                .enableChainModel()
                .enableTableFieldAnnotation()
                .enableColumnConstant()
                .enableSerialVersionUID()
                .enableActiveRecord()
//                .versionColumnName("version")
//                .logicDeleteColumnName("deleted")
                // 基于数据库字段填充
//                .addTableFills(new Column("create_time", FieldFill.INSERT))
                // 基于模型属性填充
//                .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE))


                .mapperBuilder()
                .enableBaseColumnList()
                .enableBaseResultMap()

                .serviceBuilder()
                .convertServiceFileName(new ServerConverterFileName())

                .controllerBuilder()
                .enableHyphenStyle()
                .build();
        mpg.strategy(strategyConfig);

        // 激活所有默认模板
        TemplateConfig templateConfig = new TemplateConfig.Builder().build();
        mpg.template(templateConfig);

        InjectionConfig injectionConfig = new InjectionConfig.Builder().build();
        mpg.injection(injectionConfig);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.execute(new FreemarkerTemplateEngine());
    }
}