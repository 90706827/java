package com.example.demo.config;

import okhttp3.internal.http2.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName CustomHandlerExceptionResolver
 * @Description 统一异常处理
 * @Author Mr.jimmy
 * @Date 2019/3/13 8:58
 * @Version 1.0
 **/
@Component
public class CustomHandlerExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(CustomHandlerExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception ex) {
        Method method = null;
        if (handler != null && handler instanceof HandlerMethod) {
            method = ((HandlerMethod) handler).getMethod();
        }
        logger.error("[{}] system error", method, ex);

        byte[] bytes = "全局异常处理".getBytes(StandardCharsets.UTF_8);
        try {
            FileCopyUtils.copy(bytes, httpServletResponse.getOutputStream());
        } catch (IOException e) {
            logger.error("error", e);
            throw new RuntimeException(e);
        }
        return new ModelAndView();
    }
}
