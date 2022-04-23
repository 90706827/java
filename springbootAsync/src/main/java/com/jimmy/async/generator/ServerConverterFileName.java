package com.jimmy.async.generator;

import com.baomidou.mybatisplus.generator.function.ConverterFileName;

/**
 * @Description
 * @Author zhangguoq
 **/
public class ServerConverterFileName implements ConverterFileName {
    @Override
    public String convert(String entityName) {
        return  entityName + "Service";
    }
}