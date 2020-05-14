package com.ygyg.data.controller;

import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MysqlTest {

    /**
     * @param args
     * @throws ClassNotFoundException
     * @throws SQLException
     */


    public static void main(String[] args) {



            List<DemoData> list = new ArrayList<DemoData>();

            for (int i = 0; i < 10; i++) {

                DemoData data = new DemoData();

                data.setString("字符串" + i);

                data.setDate(new Date());

                data.setDoubleData(0.56);

                list.add(data);

            }

        // 写法1

       // String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";

        File file = new File("/home/nevermore/simpleWrite.xlsx");

        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭

        // 如果这里想使用03 则 传入excelType参数即可

        EasyExcel.write(file, DemoData.class).sheet("模板").doWrite(list);



        }
    }


