package com.ygyg.data.utils;

import com.ygyg.data.entity.Infomodel;
import com.ygyg.data.entity.InfomodelProperty;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReadInfoModelExcel {
    private final static Logger logger = LoggerFactory.getLogger(ReadInfoModelExcel.class);

    public static List readExcel(String fileName, String path) {
        try {
            Workbook workBook = null;
            try {
                workBook = new XSSFWorkbook(path + "/" + fileName);
            } catch (Exception ex) {
                workBook = new HSSFWorkbook(new FileInputStream(path + "/"
                        + fileName));
            }

            Sheet sheet = workBook.getSheetAt(0);
            if (sheet == null) {
                return null;
            }
            ArrayList<Infomodel> infomodels = new ArrayList<Infomodel>();
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(rowNum);


                if (row == null) {
                    continue;
                }
                Infomodel infomodel = new Infomodel();
                infomodel.setModelId((long) 10000000 * (10 + rowNum));
                infomodel.setName(getValue(row.getCell(1)));
                infomodel.setCode(getValue(row.getCell(2)));
                if (getValue(row.getCell(3)).contains("企业级")) {
                    infomodel.setNodeType(10L);
                } else if (getValue(row.getCell(3)).contains("系统级")) {
                    infomodel.setNodeType(20L);
                } else if (getValue(row.getCell(3)).contains("设备群级")) {
                    infomodel.setNodeType(30L);
                } else if (getValue(row.getCell(3)).contains("设备级")) {
                    infomodel.setNodeType(40L);
                } else {
                    infomodel.setNodeType(null);
                }

                String shortName = getValue(row.getCell(1)).trim();
                if (shortName.contains("信息模型")) {
                    infomodel.setShortName(shortName.substring(0, shortName.length() - 4));
                } else {
                    infomodel.setShortName(shortName);
                }
                infomodel.setRelationModelId(0l);
                infomodel.setSubstanceSystem(getValue(row.getCell(4)));
                infomodel.setSubstanceDeviceGroup(getValue(row.getCell(5)));
                infomodel.setSubstanceDevice(getValue(row.getCell(6)));

                infomodels.add(infomodel);
            }

            return infomodels;
        } catch (IOException e) {
            System.out.println("e:" + e);
        }
        return null;
    }

    @SuppressWarnings("static-access")
    private static String getValue(Cell cell) {
        if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue()).trim();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue()).trim();
        } else {
            return String.valueOf(cell.getStringCellValue()).trim();
        }
    }

    public static List readPropertyExcel(String fileName, String path, Map<String, Long> map) {

        try {
            Workbook workBook = null;
            try {
                workBook = new XSSFWorkbook(path + "/" + fileName);
            } catch (Exception ex) {
                workBook = new HSSFWorkbook(new FileInputStream(path + "/"
                        + fileName));
            }
            ArrayList<InfomodelProperty> infomodelProperties = new ArrayList<InfomodelProperty>();
            for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
                Sheet sheet = workBook.getSheetAt(numSheet);
                if (sheet == null) {
                    continue;
                }

                if (numSheet != 0) {
                    String sheetName = sheet.getSheetName();

                    int i = sheet.getLastRowNum();
                    for (int rowNum = 2; rowNum <= i; rowNum++) {
                        logger.info("sheetName={}中第{}行数据", sheetName, rowNum);
                        org.apache.poi.ss.usermodel.Row row = sheet.getRow(rowNum);
                        if (null == row) {
                            continue;
                        }
                        if (getValue(row.getCell(1)).contains("监控标准状态")) {
                            continue;
                        }

                        InfomodelProperty infomodelProperty = new InfomodelProperty();
                        infomodelProperty.setModelPropertyId(map.get(sheetName) * 10000000000L + rowNum);
                        infomodelProperty.setSeq(getValue((row.getCell(0))));
                        infomodelProperty.setModelId(map.get(sheetName));
                        infomodelProperty.setName(getValue(row.getCell(1)));
                        infomodelProperty.setShortName(getValue(row.getCell(1)));


                        if (getValue(row.getCell(2)).contains("计算")) {
                            infomodelProperty.setType(2);
                        } else if (getValue(row.getCell(2)).contains("物联获取")) {
                            infomodelProperty.setType(1);
                        } else {
                            infomodelProperty.setType(0);
                        }
                        infomodelProperty.setComputeDescription(getValue(row.getCell(3)));
                        infomodelProperty.setCode(getValue(row.getCell(4)));
                        if (getValue(row.getCell(5)).contains("求和")) {
                            infomodelProperty.setComputeMethod("sum");
                        } else if (getValue(row.getCell(5)).contains("期差")) {
                            infomodelProperty.setComputeMethod("perioddiff");
                        } else if (getValue(row.getCell(5)).contains("平均")) {
                            infomodelProperty.setComputeMethod("average");
                        } else if (getValue(row.getCell(5)).contains("减法")) {
                            infomodelProperty.setComputeMethod("sub");
                        } //else if(getValue(row.getCell(5)).contains("条件")){
                        //  infomodelProperty.setComputeMethod("condition");
                        // }
                        else {
                            infomodelProperty.setComputeMethod("none");
                        }

                        if (getValue(row.getCell(6)).contains("自身")) {
                            infomodelProperty.setComputeObjectRange("1");
                        } else if (getValue(row.getCell(6)).contains("子级（全部）")) {
                            infomodelProperty.setComputeObjectRange("2");
                        } else if (getValue(row.getCell(6)).contains("无")) {
                            infomodelProperty.setComputeObjectRange("none");
                        } else {
                            //按条件分
                            infomodelProperty.setComputeObjectRange("3");
                        }

                        infomodelProperty.setComputeTimeRange(getValue(row.getCell(7)));

                        if (getValue(row.getCell(7)).contains("年内")) {
                            infomodelProperty.setComputeTimeRange("1_year");
                        } else if (getValue(row.getCell(7)).contains("月内")) {
                            infomodelProperty.setComputeTimeRange("1_month");
                        } else if (getValue(row.getCell(7)).contains("日内")) {
                            infomodelProperty.setComputeTimeRange("1_day");
                        } else if (getValue(row.getCell(7)).contains("10分钟内")) {
                            infomodelProperty.setComputeTimeRange("10_min");
                        } else {
                            infomodelProperty.setComputeTimeRange("none");
                        }
                        infomodelProperty.setComputeRelationCode(getValue(row.getCell(8)));

                        if (getValue(row.getCell(10)).contains("无")) {
                            infomodelProperty.setComputeFrequency("none");
                        } else if (getValue(row.getCell(10)).contains("实时")) {
                            infomodelProperty.setComputeFrequency("realtime");
                        } else if (getValue(row.getCell(10)).contains("日")) {
                            infomodelProperty.setComputeFrequency("1_day");
                        } else {
                            infomodelProperty.setComputeFrequency("10_min");
                        }
                        infomodelProperty.setStoreDescription(getValue(row.getCell(11)));

                        if (getValue(row.getCell(12)).contains("日")) {
                            infomodelProperty.setStoreRule("day");
                        } else if (getValue(row.getCell(12)).contains("月")) {
                            infomodelProperty.setStoreRule("month");
                        } else if (getValue(row.getCell(12)).contains("年")) {
                            infomodelProperty.setStoreRule("year");
                        } else if (getValue(row.getCell(12)).contains("分")) {
                            infomodelProperty.setStoreRule("ten_minutes");
                        } else if (getValue(row.getCell(12)).contains("点位") || getValue(row.getCell(12)).contains("变化")) {
                            infomodelProperty.setStoreRule("point_value");
                        } else if (getValue(row.getCell(12)).contains("全部存储")) {
                            infomodelProperty.setStoreRule("all");
                        } else {
                            infomodelProperty.setStoreRule("none");
                        }
                        if (getValue(row.getCell(14)).contains("数字量")) {
                            infomodelProperty.setDigitalAnalogType(1);
                        } else if (getValue(row.getCell(14)).contains("模拟量")) {
                            infomodelProperty.setDigitalAnalogType(2);
                        } else if (getValue(row.getCell(14)).contains("开关量")) {
                            infomodelProperty.setDigitalAnalogType(0);
                        } else {
                            infomodelProperty.setType(0);
                        }

                        if (getValue(row.getCell(15)).contains("REAL")) {
                            infomodelProperty.setDataType(1);
                        } else {
                            infomodelProperty.setDataType(0);
                        }
                        if (getValue(row.getCell(16)).contains("读写")) {
                            infomodelProperty.setReadWriteType(2);
                        } else if (getValue(row.getCell(16)).contains("写")) {
                            infomodelProperty.setReadWriteType(3);
                        } else if (getValue(row.getCell(16)).contains("只读")) {
                            infomodelProperty.setReadWriteType(1);
                        } else {
                            infomodelProperty.setType(0);
                        }
                        infomodelProperty.setUnitName(getValue(getCell(row, 17)));
                        infomodelProperty.setUnitMark(getValue(getCell(row, 18)));
                        infomodelProperty.setDescription(getValue(getCell(row, 19)));
                        infomodelProperties.add(infomodelProperty);
                    }
                }
            }

            return infomodelProperties;
        } catch (IOException e) {
            System.out.println("e:" + e);
        }
        return null;
    }

    public static Cell getCell(Row row, int i) {
        Cell cell = row.getCell(i);
        if (cell == null) {
            cell = row.createCell(i);
            cell.setCellValue("");
        }
        return cell;
    }

}
