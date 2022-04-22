package com.jimmy.zookeeper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jimmy
 */
@Data
@Component
@ConfigurationProperties(prefix = "curator")
public class ZkConfig {
    /**
     * 重试次数
     */
    private int retryCount;

    /**
     * 重试间隔 毫秒
     */
    private int baseSleepTimeMs;

    /**
     * 连接地址
     */
    private String connectString;

    /**
     * session 超时 毫秒
     */
    private int sessionTimeoutMs;

    /**
     * 连接超时 毫秒
     */
    private int connectionTimeoutMs;
}