package com.lianglong.nettywebsocket.zeroCopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author lianglong
 * @date 2020/5/5
 */
public class NewIoClient {
    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();


        socketChannel.connect(new InetSocketAddress("localhost",7001));


        String fileName = "/home/nevermore/software/NVIDIA-Linux-x86_64-430.50.run";


        FileChannel fileChannel = new FileInputStream(fileName).getChannel();


        long start = System.currentTimeMillis();


        //在linux下一个transferTo方法就可以完成传输

        //在windows下一次调用transferTo只能发送8M  就需要分段传输文件，而且要注意传输时的位置

        long l = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        System.out.println("发送的总的字节数"+l);

        long end = System.currentTimeMillis();

        System.out.println("耗时"+(end-start));

        fileChannel.close();


    }
}
