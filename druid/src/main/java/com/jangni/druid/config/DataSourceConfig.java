package com.jangni.druid.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Description TODO
 * @Author Jangni
 * @Date 2018/11/26 12:02
 **/
@Configuration
public class DataSourceConfig {
    @Autowired
    private Environment env;

    @Bean(name = "secondaryDS")
    public DataSource dataSource() throws Exception {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        ds.setUniqueResourceName("secondaryRN");
        ds.setPoolSize(5);
        ds.setXaProperties(build("cheng.secondary.datasource."));
        return ds;
    }

    @Bean(name = "primaryDS")
    @Primary
    public DataSource primaryDataSource() throws Exception {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        ds.setUniqueResourceName("primaryRN");
        ds.setPoolSize(5);
        ds.setXaProperties(build("cheng.primary.datasource."));
        return ds;
    }

    private Properties build(String prefix) {
        Properties prop = new Properties();
        prop.put("url", env.getProperty(prefix + "url"));
        prop.put("username", env.getProperty(prefix + "username"));
        prop.put("password", env.getProperty(prefix + "password"));
        prop.put("driverClassName", env.getProperty(prefix + "driverClassName"));

        return prop;
    }
}
