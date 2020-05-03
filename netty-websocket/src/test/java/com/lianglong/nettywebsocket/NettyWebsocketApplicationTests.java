package com.lianglong.nettywebsocket;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

//@SpringBootTest
class NettyWebsocketApplicationTests {

    @Test
    void contextLoads() {

        LocalDate start = LocalDate.of(2020, 3, 23);

        LocalDate localDate = start.plusDays(280);

        System.out.println(localDate.toString());
    }

}
