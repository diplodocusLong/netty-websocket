package com.ygyg.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.ygyg.data.dao")
@SpringBootApplication
public class DataImportApplication {


    public static void main(String[] args) {
        SpringApplication.run(DataImportApplication.class, args);
    }

}
