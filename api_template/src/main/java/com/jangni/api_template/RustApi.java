package com.jangni.api_template;

import org.springframework.web.bind.annotation.*;

/**
 * ClassName RustApi
 * Description
 * Author Mr.Jangni
 * Date 2019/3/2 11:10
 * Version 1.0
 **/
@RestController
public class RustApi {


    @RequestMapping(value = "/success",method = RequestMethod.POST)
    public ResultBean<String> postSuccess(){
        ResultBean<String> resultBean = new ResultBean<String>();

        return resultBean;
    }

}