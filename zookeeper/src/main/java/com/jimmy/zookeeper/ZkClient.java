package com.jimmy.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ZkClient {
    private static final Logger logger = LoggerFactory.getLogger(ZkClient.class);

    @Resource
    private ZkConfig zkConfig;

    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework() {
        logger.info(zkConfig.toString());
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(zkConfig.getBaseSleepTimeMs(), zkConfig.getRetryCount());
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(zkConfig.getConnectString())
                .sessionTimeoutMs(zkConfig.getSessionTimeoutMs())
                .connectionTimeoutMs(zkConfig.getConnectionTimeoutMs())
                .retryPolicy(retryPolicy)
                .build();
        logger.info("zk curator初始化完成...");
        return client;
    }

    @Bean(initMethod = "start")
    public CuratorFramework curatorFrameworkTwo() {
        logger.info(zkConfig.toString());
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(zkConfig.getBaseSleepTimeMs(), zkConfig.getRetryCount());
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(zkConfig.getConnectString())
                .sessionTimeoutMs(zkConfig.getSessionTimeoutMs())
                .connectionTimeoutMs(zkConfig.getConnectionTimeoutMs())
                .retryPolicy(retryPolicy)
                .build();
        logger.info("zk curator初始化完成...");
        return client;
    }

    public static void main(String[] args) {
        String gql = "b{1}a";
        Map<String, String> map = new LinkedHashMap<>();
        map.put("tenantIdcode", "desc");
        map.put("storeIdcode", "desc");
        map.put("code", "desc");
//                    , sortBy: [{fieldName: "tenantIdcode", order: "desc"}, {fieldName: "storeIdcode", order: "desc"}, {fieldName: "code", order: "desc"}]
        StringBuilder builder = new StringBuilder(", sortBy: [");
        for (String key : map.keySet()) {
            builder.append("{fieldName：'").append(key).append("', order: '").append(map.get(key)).append("'}, ");
        }
        builder.delete(builder.lastIndexOf(","), builder.length());
        builder.append("]");
        gql = gql.replace("{1}", builder);
        System.out.println(gql);
    }
}
