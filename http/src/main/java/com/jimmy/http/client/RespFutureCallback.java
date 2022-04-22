package com.jimmy.http.client;

import com.google.common.io.CharStreams;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * ClassName FutureResp
 * Description 请求返回异步处理
 * Author Mr.jimmy
 * Date 2019/2/28 21:12
 * Version 1.0
 **/
public class RespFutureCallback implements FutureCallback<HttpResponse> {

    private BasicHttpContext context;

    RespFutureCallback(BasicHttpContext context){
        this.context = context;
    }

    @Override
    public void completed(HttpResponse resp) {
        if (resp.getStatusLine().getStatusCode() == 200) {
            try {
                InputStream input = resp.getEntity().getContent();
                String respMsg = CharStreams.toString(new InputStreamReader(input, StandardCharsets.UTF_8));
//               logger.info(respMsg);
                context.setAttribute("respMsg",respMsg);
            } catch (IOException e) {
                throw new RuntimeException("请求异常");
            }
        } else {
            throw new RuntimeException("server from code != 200, return code :" + resp.getStatusLine().getStatusCode());
        }
    }

    @Override
    public void failed(Exception e) {
        throw new RuntimeException(e);
    }

    @Override
    public void cancelled() {
       throw new RuntimeException();
    }
}