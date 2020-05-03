package com.lianglong.nettywebsocket.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lianglong
 * @date 2020/5/1
 */

@Slf4j
@Service
public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {

    //用于记录和管理所有客户端的channel
    //客户端组
    public static Map<String,ChannelGroup> channelGroups;
    public static ObjectMapper mapper;


    static {

        channelGroups = new HashMap<>();
        channelGroups.put("1000",new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
        channelGroups.put("2000",new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));

        // json处理工具
        mapper = new ObjectMapper();


    }


    /**
     * 接收客户端传来的消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {


        //文本消息
        if (msg instanceof TextWebSocketFrame) {
            //第一次连接成功后，给客户端发送消息
            //Channel channel = ctx.channel();
            //channel.writeAndFlush(new TextWebSocketFrame("连接客户端成功"));

            String message = ((TextWebSocketFrame) msg).text();

            Map map = mapper.readValue(message, Map.class);

            String app_id = (String) map.get("app_id");

            channelGroups.get(app_id).add(ctx.channel());


        }
        //二进制消息
        if (msg instanceof BinaryWebSocketFrame) {
            log.info("收到二进制消息：" + ((BinaryWebSocketFrame) msg).content().readableBytes());
            BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame(Unpooled.buffer().writeBytes("hello".getBytes()));
            //给客户端发送的消息
            ctx.channel().writeAndFlush(binaryWebSocketFrame);
        }
        //ping消息
        if (msg instanceof PongWebSocketFrame) {
            log.info("客户端ping成功");
        }
        //关闭消息
        if (msg instanceof CloseWebSocketFrame) {
            log.info("客户端关闭，通道关闭");
            Channel channel = ctx.channel();
            channel.close();
        }
    }

    /**
     * 当客户端连接服务端之后(打开连接)
     * 获取客户端的channel,并且放到ChannelGroup中去进行管理
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        /*  //获取当前channel绑定的IP地址
        InetSocketAddress ipSocket = (InetSocketAddress)ctx.channel().remoteAddress();
        String address = ipSocket.getAddress().getHostAddress();
        log.info("address为:"+address);
        //将IP和channel的关系保存
        if (!channelMap.containsKey(address)){
            channelMap.put(address,ctx.channel());
        }*/

     //   channelGroup.add(ctx.channel());


    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当触发handlerRemoved,ChannelGroup会自动移除对应的客户端的channel
        //所以下面这条语句可不写
//        channelGroup.remove(ctx.channel());
        log.info("客户端断开，channel对应的长id为:" + ctx.channel().id().asLongText());
        log.info("客户端断开，channel对应的短id为:" + ctx.channel().id().asShortText());
    }

    /**
     * 异常处理
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("连接异常：" + cause.getMessage());
        cause.printStackTrace();
        ctx.channel().close();

        for (Map.Entry<String,ChannelGroup> s : channelGroups.entrySet()) {
            ChannelGroup value = s.getValue();
            if (value.contains(ctx.channel())) {
                value.remove(ctx.channel());
                break;
            }
        }
    }

    public void sendAllMessage(String message) {
        //收到信息后，群发给所有channel
        for (ChannelGroup value : channelGroups.values()) {

            value.writeAndFlush(new TextWebSocketFrame(message));
        }
    }

    public void sendMessage(String message, String app_id) {

        ChannelGroup channels = channelGroups.get(app_id);


        channels.writeAndFlush(new TextWebSocketFrame(message));

    }
}
