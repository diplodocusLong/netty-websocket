package com.lianglong.nettywebsocket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * @author lianglong
 * @date 2020/5/2
 */
public class TestObjectMapper {

    public static ObjectMapper mapper = new ObjectMapper();
    //涉及到一下问题需要预处理，否则直接在引用时new new ObjectMapper();即可
    static {
        // 转换为格式化的json
        mapper .enable( SerializationFeature.INDENT_OUTPUT);

        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        mapper .configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //反序列化的时候如果多了其他属性,不抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //如果是空对象的时候,不抛异常
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        //序列化的时候序列对象的所有属性
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        //取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式

        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    }

    public static void main(String[] args) throws IOException {




        JsonNode jsonNode = mapper.readTree(new File("/home/nevermore/test.json"));

        Iterator<JsonNode> risks = jsonNode.findValue("risks").elements();

        while (risks.hasNext()) {
            JsonNode next = risks.next();
            String riskName = next.findValue("riskName").asText();
            System.out.println(riskName);


        }

        System.out.println();

    }

    @Data
    public  static class User implements Serializable {

        private static final long serialVersionUID = 1L;

        private String userName;
        private String age;
        private String sex;
        private String email;
        private String phone;

        public User(String userName, String age, String sex, String email, String phone) {
            super();
            this.userName = userName;
            this.age = age;
            this.sex = sex;
            this.email = email;
            this.phone = phone;
        }


    }

}
