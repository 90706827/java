package com.jimmy.doubledatasource;

import com.jimmy.doubledatasource.atomikos.DataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@ComponentScan("com.jimmy.doubledatasource.atomikos")
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@Import({ DataSourceConfig.class })
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
