package com.jimmy.doubledatasource.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Description TODO
 * @Author jimmy
 * @Date 2018/11/24 1:50
 **/
//@Configuration
//@MapperScan(basePackages = {"com.jimmy.doubledatasource.daoLog"}, sqlSessionFactoryRef = "logSqlSessionFactory")
public class MybatisLogConfig {

    @Autowired
    @Qualifier("logDB")
    private DataSource logDB;

    @Bean(name = "logSqlSessionFactory")
    @ConfigurationProperties(prefix = "mybatisLog")
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(logDB);
        return sqlSessionFactoryBean;
    }
}