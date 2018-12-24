package com.jangni.shiro.config;


import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * program: java
 * description: shiro配置
 * author: Mr.Jangni
 * create: 2018-08-01 23:40
 **/
@Configuration
public class ShiroConfig {

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 注入 securityManager
     * 权限管理，配置主要是Realm的管理认证
     */
    @Bean
    public SecurityManager securityManager(ShiroRealm shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }

    /**
     * anon	无参，开放权限，可以理解为匿名用户或游客
     * authc	无参，需要认证
     * logout	无参，注销，执行后会直接跳转到shiroFilterFactoryBean.setLoginUrl(); 设置的 url
     * authcBasic	无参，表示 httpBasic 认证
     * user	无参，表示必须存在用户，当登入操作时不做检查
     * ssl	无参，表示安全的URL请求，协议为 https
     * perms[user]	参数可写多个，表示需要某个或某些权限才能通过，多个参数时写 perms[“user, admin”]，当有多个参数时必须每个参数都通过才算通过
     * roles[admin]	参数可写多个，表示是某个或某些角色才能通过，多个参数时写 roles[“admin，user”]，当有多个参数时必须每个参数都通过才算通过
     * rest[user]	根据请求的方法，相当于 perms[user:method]，其中 method 为 post，get，delete 等
     * port[8081]	当请求的URL端口不是8081时，跳转到schemal://serverName:8081?queryString 其中 schmal 是协议 http 或 https 等等，
     * serverName 是你访问的 Host，8081 是 Port 端口，queryString 是你访问的 URL 里的 ? 后面的参数
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置拦截器
        Map<String, String> filterMap = new LinkedHashMap<>();
        //游客，开发权限
        filterMap.put("/guest/**", "anon");
        //用户，需要角色权限 “user”
        filterMap.put("/user/**", "roles[user]");
        //管理员，需要角色权限 “admin”
        filterMap.put("/admin/**", "roles[admin]");
        //开放登陆接口
        filterMap.put("/login", "anon");
        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterMap.put("/**", "authc");
        filterMap.put("/druid/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");
        //登录成功后跳转页面
        shiroFilterFactoryBean.setSuccessUrl("/index");

        System.out.println("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }
}
