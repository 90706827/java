package com.jimmy.thread.openSdk;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author jimmy
 */
public class OpenSdkErrorHandler extends DefaultResponseErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(OpenSdkErrorHandler.class);

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (HttpStatus.OK.equals(response.getStatusCode())) {
            InputStream inputStream = response.getBody();
            String errorInfo = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            logger.error("OpenSdk error:{}", errorInfo);
            throw new OpenSdkException(errorInfo, response.getStatusCode().value(), response.getStatusText(), response.getHeaders(), this.getResponseBody(response), this.getCharset(response));
        }
    }
}
