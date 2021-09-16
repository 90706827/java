package com.jimmy.elasticsearch.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

/**
 * @Description
 * 对应ES index dalex
 * ID 不可为空
 * 通过Lombok创建getter setter 以及无参数构造函数和全参数构造函数
 * 通过Field注解申明数据存入ES的类型以及解析器
 * icon字段因为是路径地址故申明不建立索引
 * ik_max_word来自于ik分词器，这个需要我们自己添加扩展。
 * @Author zhangguoq
 **/
@Document(indexName = "dalex")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @NonNull
    private String id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String msg;
    @Field(type = FieldType.Keyword)
    private String sender;
    @Field(type = FieldType.Integer)
    private Integer type;
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Instant sendDate;
    @Field(index = false)
    private String icon;
}