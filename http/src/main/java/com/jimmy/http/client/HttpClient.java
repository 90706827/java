package com.jimmy.http.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @ClassName HttpClient
 * @Description http客户端
 * @Author Mr.jimmy
 * @Date 2019/2/28 17:10
 * @Version 1.0
 **/
public class HttpClient {
    private CloseableHttpAsyncClient client;
    private String host;
    private int port;
    private int timeout;

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

    private RequestConfig requestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectTimeout(5000)
                .setSocketTimeout(timeout)
                .build();
    }

    public void close() {
        try {
            client.close();
        } catch (IOException e) {
//           logger.info(e.getMessage());
        }
    }

    public Future<HttpResponse> send(BasicHttpContext context) {
        HttpPost post = new HttpPost("http://" + host + ":" + port +"/post");
        post.setConfig(requestConfig());
        post.setEntity(new StringEntity(context.getAttribute("reqMsg").toString(), ContentType.TEXT_PLAIN.withCharset("UTF-8")));
        return client.execute(post,context, new RespFutureCallback(context));
    }

    public static void main(String[] args) {
        HttpClient httpClient = new HttpClient("127.0.0.1",8080,60000);
        BasicHttpContext context = new BasicHttpContext();
        context.setAttribute("reqMsg","你好");
        Future<HttpResponse> future = httpClient.send(context);
        try {
            HttpResponse response = future.get();
//           logger.info(response.getStatusLine().getStatusCode());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
