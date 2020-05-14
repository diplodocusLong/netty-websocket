package com.ygyg.data.controller;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;


/**
 * @author lianglong
 * @date 2020/5/14
 */

@Data
public class DemoData {
    @ColumnWidth(8)
    @ExcelProperty("字符串标题")
    private String string;
    @ColumnWidth(18)
    @ExcelProperty("日期标题")
    private Date date;
    @ColumnWidth(6)
    @ExcelProperty("数字标题")
    private Double doubleData;

    /**

     * 忽略这个字段

     */

    @ExcelIgnore

    private String ignore;

}