package com.lianglong.nettywebsocket.group;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author lianglong
 * @date 2020/5/4
 */
public class GroupChatClient {

    private final String HOST = "127.0.0.1";

    private final int PORT = 6667;

    private Selector selector;

    private SocketChannel socketChannel;

    private String userName;

    public GroupChatClient() throws IOException {

        this.selector = Selector.open();

        this.socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));

        socketChannel.configureBlocking(false);

        socketChannel.register(selector, SelectionKey.OP_READ);

        userName = socketChannel.getLocalAddress().toString();

        System.out.println(userName+"is ok");

    }


    public void sendInfo(String info){


        info = userName+"说："+ info;


        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readInfo(){


        int select = 0;
        try {
            select = selector.select();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(select>0){


            selector.selectedKeys()
                    .stream()
                    .filter(selectionKey -> selectionKey.isReadable())
                    .map(selectionKey -> (SocketChannel)selectionKey.channel())
                    .forEach(socketChannel -> {

                        ByteBuffer allocate = ByteBuffer.allocate(1024);

                        try {
                            socketChannel.read(allocate);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("没有可用的通道");
                        }

                        String msg = new String(allocate.array());

                        System.out.println(msg.trim());

                        selector.selectedKeys().remove(socketChannel.keyFor(selector));

                    });


        }

    }

    public static void main(String[] args) throws IOException {

        GroupChatClient groupChatClient = new GroupChatClient();

         new Thread(()->{

                 while(true){

                     groupChatClient.readInfo();

                     try {
                         Thread.currentThread().sleep(1_000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }



         }).start();


        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNextLine()){

            String next = scanner.next();

            groupChatClient.sendInfo(next);
        }


    }
}
