package com.qiangke;

/**
 * @author zhangguoq
 * @description
 * @date 2024/5/29 14:41
 **/
public class StaticTest {


    public static String getStaticName(String s) {
        return s + "- StaticTest";
    }


    public String getName(String s) {
        return s + "- StaticTest";
    }
}
