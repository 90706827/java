package com.jangni.shiro.service;

import com.jangni.shiro.module.UserInfo;
import org.springframework.stereotype.Component;

/**
 * Author ZhangGuoQiang
 * Date: 2018/7/30/030
 * Time: 13:56
 * Description:
 */
@Component
public class UserInfoService {

   public UserInfo findByUsername(String username){
       UserInfo userInfo =  new UserInfo();

       return userInfo;
   }

}
