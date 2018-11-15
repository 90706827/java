package com.jangni.tomcatjdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @Description TODO
 * @Author Jangni
 * @Date 2018/11/15 23:53
 **/
public abstract class JdbcProsLoad {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public Properties init(String fileName) {
        Properties properties = new Properties();
        try {
            properties.load(JdbcProsLoad.class.getClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            logger.error("读取文件[" + fileName + "]错误：", e);
        }
        return properties;
    }
}
