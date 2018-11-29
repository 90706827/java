package com.jangni.doubledatasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Description TODO
 * @Author Jangni
 * @Date 2018/11/24 1:42
 **/
//@Configuration
public class DatasourceConfig {

    @Bean(destroyMethod =  "close", name = DataSources.MASTER_DB)
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean(destroyMethod =  "close", name = DataSources.CLUSTER_DB)
    @ConfigurationProperties(prefix = "spring.datasourceLog")
    public DataSource dataSourceLog() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }
}