package com.ygyg.data.controller;

import com.alibaba.excel.EasyExcel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lianglong
 * @date 2020/5/14
 */
@Controller
public class ExcelDownController {

    @GetMapping("/load")
    public void download(HttpServletResponse response){


        //设置响应头和客户端保存文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + "simpleWrite.xlsx");
        //用于记录以完成的下载的数据量，单位是byte
        long downloadedLength = 0l;
        try {

            //激活下载操作
            OutputStream os = response.getOutputStream();

            List<DemoData> list = new ArrayList<>();

            for (int i = 0; i < 10; i++) {

                DemoData data = new DemoData();

                data.setString("字符串" + i);

                data.setDate(new Date());

                data.setDoubleData(0.56);

                list.add(data);

            }

            EasyExcel.write(os, DemoData.class).sheet("模板").doWrite(list);

            // 这里主要关闭。
            os.close();

        } catch (Exception e){
         e.printStackTrace();

        }

    }


}
