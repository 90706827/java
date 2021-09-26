package com.jimmy.elasticsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @Description
 * @Author zhangguoq
 **/
@Configuration
public class ElasticsearchConfig {

    // 集群地址，多个用,隔开
    private static String hosts = "127.0.0.1";
    // 使用的端口号
    private static int port = 9200;
    // 使用的协议
    private static String schema = "https";
    private static ArrayList<HttpHost> hostList = null;
    // 连接超时时间
    private static int connectTimeOut = 1000;
    // 连接超时时间
    private static int socketTimeOut = 30000;
    // 获取连接的超时时间
    private static int connectionRequestTimeOut = 500;
    // 最大连接数
    private static int maxConnectNum = 100;
    // 最大路由连接数
    private static int maxConnectPerRoute = 100;

    static {
        hostList = new ArrayList<>();
        String[] hostStrs = hosts.split(",");
        for (String host : hostStrs) {
            hostList.add(new HttpHost(host, port, schema));
        }
    }

    @Bean
    public RestHighLevelClient client() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        RestClientBuilder builder = RestClient.builder(hostList.toArray(new HttpHost[0]));

        // SSL
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
        SSLIOSessionStrategy sessionStrategy = new SSLIOSessionStrategy(sslContext, NoopHostnameVerifier.INSTANCE);
        //  用户密码
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "ca888888"));

        // 异步httpclient连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(connectTimeOut);
            requestConfigBuilder.setSocketTimeout(socketTimeOut);
            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
            return requestConfigBuilder;
        });
        // 异步httpclient连接数配置
        builder.setHttpClientConfigCallback(httpAsyncClientBuilder -> {
            httpAsyncClientBuilder.setMaxConnTotal(maxConnectNum);
            httpAsyncClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
            httpAsyncClientBuilder.setSSLContext(sslContext);
            httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            return httpAsyncClientBuilder;
        });
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }
}