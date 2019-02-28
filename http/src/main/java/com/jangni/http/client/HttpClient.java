package com.jangni.http.client;

import com.google.common.io.CharStreams;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName HttpClient
 * @Description http客户端
 * @Author Mr.Jangni
 * @Date 2019/2/28 17:10
 * @Version 1.0
 **/
public class HttpClient {
    private String host;
    private int port;
    private int timeout;
    private CloseableHttpAsyncClient client;

    HttpClient(String host, int port, int timeout) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.client = build();
        this.client.start();
    }

    private CloseableHttpAsyncClient build() {
        return HttpAsyncClients
                .custom()
                .setMaxConnTotal(500)
                .setMaxConnPerRoute(500)
                .setDefaultRequestConfig(requestConfig())
                .build();
    }

    public CompletableFuture<String> send(String msg) {
        HttpPost post = new HttpPost("http://" + host + ":" + port);
        post.setConfig(requestConfig());
        post.setEntity(new StringEntity(msg, ContentType.TEXT_PLAIN.withCharset("UTF-8")));

        return CompletableFuture.supplyAsync(() -> {
            client.execute(post, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse resp) {
                    if (resp.getStatusLine().getStatusCode() == 200) {
                        String respStr = "";
                        try {
                            InputStream input = resp.getEntity().getContent();
                            respStr = CharStreams.toString(new InputStreamReader(input, StandardCharsets.UTF_8));
                        } catch (IOException e) {
                            throw new RuntimeException("请求异常");
                        }
                        if (respStr.isEmpty()) {
                            throw new RuntimeException("返回为空");
                        }
                    } else {
                        throw new RuntimeException("server from code != 200, return code :" + resp.getStatusLine().getStatusCode());
                    }
                }

                @Override
                public void failed(Exception e) {
                    e.getCause();
                }

                @Override
                public void cancelled() {
                    new RuntimeException();
                }
            });
            return "";
        });
    }

    private RequestConfig requestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectTimeout(5000)
                .setSocketTimeout(timeout)
                .build();
    }

    public void close(){
        try {
            client.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
