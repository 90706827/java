package com.jimmy.druid.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.jimmy.druid.DruidApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: java
 * @description: Druid配置
 * @author: Mr.jimmy
 * @create: 2018-08-02 21:44
 **/
@Configuration
public class DruidConfig {
    private static final Logger logger = LoggerFactory.getLogger(DruidApplication.class);

    @Bean
    public ServletRegistrationBean<StatViewServlet> servletRegistrationBean() {
        logger.info("init Druid Monitor Servlet ...");
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(),
                "/druid/*");
        // IP白名单
        servletRegistrationBean.addInitParameter("allow", "192.168.1.101,127.0.0.1");
        // IP黑名单(共同存在时，deny优先于allow)
        servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
        // 控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "admin");
        // 是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>();
        //注入过滤器
        filterRegistrationBean.setFilter(new WebStatFilter());
        //拦截规则
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        //过滤器名称
//        filterRegistrationBean.setName("testFilter2");
        //过滤器顺序
//        filterRegistrationBean.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE - 1);
        return filterRegistrationBean;
    }
}
