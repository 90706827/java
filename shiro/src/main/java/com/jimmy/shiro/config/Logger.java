package com.jimmy.shiro.config;


import org.slf4j.LoggerFactory;

/**
 * ClassName Logger
 * Description 系统日志
 * Author Mr.jimmy
 * Date 2018/12/24 22:58
 * Version 1.0
 **/
public interface Logger {
    org.slf4j.Logger logger = LoggerFactory.getLogger("base");
}