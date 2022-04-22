package com.jimmy.thread.openSdk;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClientResponseException;

import java.nio.charset.Charset;

/**
 * @author jimmy
 */
public class OpenSdkException extends RestClientResponseException {

    public OpenSdkException(String message, int statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(message, statusCode, statusText, responseHeaders, responseBody, responseCharset);
    }
}
