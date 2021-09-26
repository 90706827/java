package com.jimmy.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Description
 * @Author zhangguoq
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Content {


    private String src;

    private String price;

    private String name;

}