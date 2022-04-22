package com.jimmy.thread.openSdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author jimmy
 */
public class OpenSDK {
    private RestTemplate restTemplate;
    private HttpMethod method;
    private Object body;
    private Map<String, String> params;
    private String url;
    private HttpHeaders requestHeaders = new HttpHeaders();
    private Map<String, String> httpHeaders = new HashMap<>();
    ExecutorService service;

    public OpenSDK(Integer connectTimeout, Integer readTimeout, ThreadPoolExecutor threadPoolExecutor) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        BufferingClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(factory);
        factory.setReadTimeout(readTimeout);
        factory.setConnectTimeout(connectTimeout);
        factory.setOutputStreaming(false);
        this.restTemplate = new RestTemplate(requestFactory);
        this.restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        this.restTemplate.setErrorHandler(new OpenSdkErrorHandler());
        this.restTemplate.setInterceptors(Collections.singletonList(new OpenSdkInterceptor()));
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        this.service = threadPoolExecutor;
    }

    public String call() throws ExecutionException, InterruptedException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        URI uri = builder.build().toUri();
        this.requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        Iterator headers = httpHeaders.entrySet().iterator();
        while (headers.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) headers.next();
            this.requestHeaders.add(entry.getKey(), entry.getValue());
        }
        HttpEntity httpEntity;
        if (this.body != null) {
            String bodyDataJson;
            if (this.body instanceof String) {
                bodyDataJson = String.valueOf(this.body);
            } else {
                bodyDataJson = JSON.toJSONString(this.body, SerializerFeature.WriteMapNullValue);
            }
            httpEntity = new HttpEntity(bodyDataJson, this.requestHeaders);
        } else {
            httpEntity = new HttpEntity(this.requestHeaders);
        }

        FutureTask<ResponseEntity<String>> responseTask = new FutureTask(() ->
                this.restTemplate.exchange(uri, this.method, httpEntity, String.class)
        );
        service.submit(responseTask);
        ResponseEntity<String> exchange = responseTask.get();
        return exchange.getBody();

    }

    public OpenSDK buildMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public OpenSDK buildBody(Object body) {
        this.body = body;
        return this;
    }

    public OpenSDK buildParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public OpenSDK buildParam(String key, String value) {
        if (this.params == null) {
            this.params = new HashMap(16);
        }

        this.params.put(key, value);
        return this;
    }

    public OpenSDK buildHeaders(Map<String, String> httpHeaders) {
        this.httpHeaders = httpHeaders;
        return this;
    }

    public OpenSDK buildUrl(String url) {
        this.url = url;
        return this;
    }


}
