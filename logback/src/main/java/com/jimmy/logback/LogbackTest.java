package com.jimmy.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName LogbackTest
 * @Description test
 * @Author Mr.jimmy
 * @Date 2018/12/14 14:36
 * @Version 1.0
 **/
@Component
public class LogbackTest {

    Logger logger = LoggerFactory.getLogger("base");

    LogbackTest(){
        while (true){
            logger.info("你好");
            logger.error("我错了");
            logger.warn("我改正");
            try{
                int a = 2/0;
            }catch (Exception t){
                logger.error("异常",t);
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
