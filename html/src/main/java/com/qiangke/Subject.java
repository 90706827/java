package com.qiangke;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Description
 * @Author zhangguoq
 * @Date 2024/1/20 16:23
 **/
@Data
public class Subject {
    @Excel(name = "题目", height = 30  ,width = 70,needMerge = true)
    private String title;
    @Excel(name = "选项",  width = 50,needMerge = true)
    private String selected;
    @Excel(name = "解析",width = 80, needMerge = true)
    private String text;
    @Excel(name = "正确", needMerge = true)
    private String answerRight;
    @Excel(name = "我的", needMerge = true)
    private String answerMe;
}
