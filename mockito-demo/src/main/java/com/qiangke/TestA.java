package com.qiangke;

/**
 * @author zhangguoq
 * @description
 * @date 2024/5/29 14:15
 **/
public class TestA {

    public static String getStaticName(String s) {
        return s + "-A";
    }

    public static String doStaticName(String s) {
        return StaticTest.getStaticName(s);
    }

    private String getPrivateName(String s) {
        return s + "-你好";
    }

    public String getNmaeString(String s) {
        return s + "public ";
    }

}
