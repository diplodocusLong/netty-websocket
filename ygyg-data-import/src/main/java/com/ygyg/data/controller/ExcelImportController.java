package com.ygyg.data.controller;

import com.ygyg.data.entity.Infomodel;
import com.ygyg.data.entity.InfomodelProperty;
import com.ygyg.data.service.InfoModelService;
import com.ygyg.data.service.InfomodelPropertyService;
import com.ygyg.data.utils.ReadInfoModelExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/report")
public class ExcelImportController {

    private final Logger logger = LoggerFactory.getLogger(ExcelImportController.class);
    @Autowired
    private InfoModelService infoModelService;
    @Autowired
    private InfomodelPropertyService infomodelPropertyService;


    @RequestMapping(value = "/importInfoModelFile", method = RequestMethod.GET)
    public void importInfoModelFile() {
        String filename = "供暖场景数据信息模型2020-4-23.xlsx";
        //  "C:\\Users\\sa\\Desktop";
        String path = "/home/nevermore/Desktop";
        long t1 = System.currentTimeMillis();
        Map hashMap = new HashMap();
        List<Infomodel> infomodels = ReadInfoModelExcel.readExcel(filename, path);
        if (!CollectionUtils.isEmpty(infomodels)) {
            infoModelService.saveBatch(infomodels);
            for (int i = 0; i < infomodels.size(); i++) {
                String name = infomodels.get(i).getName().trim();
                if (name.contains("信息模型")) {
                    name = name.substring(0, name.length() - 4);
                }
                hashMap.put(name, infomodels.get(i).getModelId());
            }
        }

        List<InfomodelProperty> infomodelProperties = ReadInfoModelExcel.readPropertyExcel(filename, path, hashMap);

        if (!CollectionUtils.isEmpty(infomodelProperties)) {
            infomodelPropertyService.saveBatch(infomodelProperties);
        }

        long t2 = System.currentTimeMillis();
        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));

    }


}

