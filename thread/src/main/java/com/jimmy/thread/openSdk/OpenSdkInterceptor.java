package com.jimmy.thread.openSdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author jimmy
 */
public class OpenSdkInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(OpenSdkInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        logger.info("OpenSDK request uri:{},method:{},headers:{},request body:{}", httpRequest.getURI(), httpRequest.getMethod(), httpRequest.getHeaders(), new String(bytes, StandardCharsets.UTF_8));
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
