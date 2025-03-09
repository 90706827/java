package com.qiangke;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author zhangguoq
 * @description
 * @date 2025/1/7 20:13
 **/
@Data
public class JianDa {
    @Excel(name = "问题", height = 30  ,width = 70,needMerge = true)
    private String title;
    @Excel(name = "答案",width = 80, needMerge = true)
    private String text;
    @Excel(name = "解析",width = 80, needMerge = true)
    private String desc;
}
