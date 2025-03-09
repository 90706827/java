package com.qiangke;

/**
 * @Description
 * @Author zhangguoq
 * @Date 2024/1/20 18:55
 **/
public class test {
    public static void main(String[] args) {
        String text = "；sdf";

        System.out.println(text.matches(".*[\\u4E00-\\u9FA5]+.*"));

    }



    public static String filterImage(String image){
        System.out.println(image.indexOf("<img"));
        System.out.println(image.indexOf(">"));
        return image.substring(0);
    }
}
