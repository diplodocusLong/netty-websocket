package com.lianglong.nettywebsocket.service.impl;

import com.lianglong.nettywebsocket.handle.WebSocketHandler;
import com.lianglong.nettywebsocket.service.PushMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author lianglong
 * @date 2020/5/1
 */
@Service
public class PushMessageImpl implements PushMessage {

    final
    WebSocketHandler webSocketHandler;
    @Autowired
    public PushMessageImpl(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    @Scheduled(cron = "0/5 * * * * ?")
    public void pushMessage() {

        webSocketHandler.sendAllMessage("hello,world");

    }


    @Scheduled(cron = "0/5 * * * * ?")
    @Override
    public void pushMessageToOne() {

        webSocketHandler.sendMessage("A组","1000");

        webSocketHandler.sendMessage("B组","2000");

    }


}
