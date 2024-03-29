package com.jimmy.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jimmy.shiro")
@MapperScan("com.jimmy.shiro.mapper")
public class ShiroApplication {
	private static final Logger logger = LoggerFactory.getLogger(ShiroApplication.class);

//	@Bean
//	public ServletRegistrationBean<StatViewServlet> servletRegistrationBean(){
//        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(),
//                "/druid/*");
//		// IP白名单
//		servletRegistrationBean.addInitParameter("allow", "192.168.1.101,127.0.0.1");
//		// IP黑名单(共同存在时，deny优先于allow)
//		servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
//		// 控制台管理用户
//		servletRegistrationBean.addInitParameter("loginUsername", "admin");
//		servletRegistrationBean.addInitParameter("loginPassword", "admin");
//		// 是否能够重置数据 禁用HTML页面上的“Reset All”功能
//		servletRegistrationBean.addInitParameter("resetEnable", "false");
//		return servletRegistrationBean;
//	}
//
//	@Bean
//	public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
//		FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());
//		filterRegistrationBean.addUrlPatterns("/*");
//		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//		return filterRegistrationBean;
//	}

	public static void main(String[] args) {
		SpringApplication.run(ShiroApplication.class, args);
	}
}
