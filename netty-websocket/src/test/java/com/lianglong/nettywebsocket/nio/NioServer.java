package com.lianglong.nettywebsocket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lianglong
 * @date 2020/5/4
 */
public class NioServer {


    public static void main(String[] args) throws IOException {

        //创建serverSocketChannel

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //得到一个selector对象
        Selector selector = Selector.open();

        //绑定一个端口6666，在服务端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannel注册到selector 关心事件为 连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(true){
            //这里我们等待一秒，如果没有事件发生  就继续
            if(selector.select(1000)==0){

                System.out.println("服务器等待了1s无连接");

                continue;
            }

            //如果返回大于0 获取到相关的 selectionKey集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                //根据key 对应通道发生的事件进行处理

                if(selectionKey.isAcceptable()){
                    //给该客护端生成一个socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //将socketChannel设置成非阻塞
                    socketChannel.configureBlocking(false);

                    System.out.println("连接成功"+socketChannel.hashCode());

                    //将当前socketChannel注册到selector并关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                if(selectionKey.isReadable()){

                    //通过key反向获取到对应的channel
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    //获取到channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();

                    channel.read(buffer);

                    System.out.println(new String(buffer.array()));
                }

                //手动从集合中移动当前的selectionKey 防止重复操作
                iterator.remove();
            }

        }

    }


}
