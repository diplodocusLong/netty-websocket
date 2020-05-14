package com.lianglong.nettywebsocket.group;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lianglong
 * @date 2020/5/4
 */
public class GroupChatServer {

    private Selector selector;

    private ServerSocketChannel listenChannel;

    private static final int PORT = 6667;

    public GroupChatServer() {

        try {
            selector = Selector.open();

            listenChannel = ServerSocketChannel.open();

            listenChannel.socket().bind(new InetSocketAddress(PORT));

            listenChannel.configureBlocking(false);

            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {

        try {

            while (true) {
                int count = selector.select(1000);

                if (count > 0) {

                    Set<SelectionKey> selectionKeys = selector.selectedKeys();

                    Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();

                    while (selectionKeyIterator.hasNext()) {
                        SelectionKey next = selectionKeyIterator.next();

                        if(!next.isValid()){
                            continue;
                        }

                        if (next.isAcceptable()) {

                            SocketChannel accept = listenChannel.accept();

                            accept.configureBlocking(false);

                            accept.register(selector, SelectionKey.OP_READ);

                            accept.configureBlocking(false);

                            System.out.println(accept.getRemoteAddress() + "上线");
                        }

                        if (next.isReadable()) {

                            //处理读事件

                            readDate(next);
                        }

                        if (next.isWritable()) {
                            //处理写事件
                        }


                        selectionKeyIterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readDate(SelectionKey selectionKey) {

        SocketChannel socketChannel = null;


        socketChannel = (SocketChannel) selectionKey.channel();


        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        int count = 0;
        try {
            count = socketChannel.read(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                System.out.println(socketChannel.getRemoteAddress() + "is lost");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (count > 0) {

            String msg = new String(byteBuffer.array());

            System.out.println("客户端发送的消息是：" + msg);


            //向其他客户端推送消息 排除发送消息的客户端
            sendToOtherClients(msg, socketChannel);
        }

    }

    private void sendToOtherClients(String msg, SocketChannel self) {
        System.out.println("服务器转发消息中**********");

        System.out.println("服务器转发数据给客户端线程" + Thread.currentThread().getName());

        //遍历所有注册到selector上的 socketChannel 并排除self
        Iterator<SelectionKey> iterator = selector.keys().iterator();

        while (iterator.hasNext()) {
            SelectionKey next = iterator.next();

            if(next.channel() instanceof SocketChannel && (!next.channel().equals(self))){

                SocketChannel channel = (SocketChannel) next.channel();
                ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes());

                try {
                    channel.write(wrap);
                } catch (IOException e) {

                    try {
                        System.out.println(channel.getRemoteAddress() + " 离线了..");
                        //取消注册
                        next.cancel();
                        //关闭通道
                        channel.close();
                    }catch (IOException e2) {
                        e2.printStackTrace();;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();

        groupChatServer.listen();
    }
}
