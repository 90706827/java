package com.jimmy.shiro.config;

import com.jimmy.shiro.utils.snowflake.SnowflakeUidGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName UIDConfig
 * Description Uid
 * Author Mr.jimmy
 * Date 2018/12/24 23:55
 * Version 1.0
 **/
@Configuration
public class UIDConfig {
    @Bean
    public SnowflakeUidGenerator snowflakeUidGenerator() {
        long workerId = SnowflakeUidGenerator.getWorkerIdByIP(16);
        String baseDate = "2018-12-01";
        int timeBits = 32;
        int workerBits = 16;
        int seqBits = 15;
        return new SnowflakeUidGenerator(workerId, baseDate, timeBits, workerBits, seqBits);
    }
}