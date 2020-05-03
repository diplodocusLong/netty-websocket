package com.lianglong.nettywebsocket;

import com.lianglong.nettywebsocket.config.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NettyWebsocketApplication implements CommandLineRunner {

    private final WebSocketServer webSocketServer;
    @Autowired
    public NettyWebsocketApplication(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    public static void main(String[] args) {
        SpringApplication.run(NettyWebsocketApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        webSocketServer.run();
    }
}
