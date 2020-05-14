package com.lianglong.nettywebsocket.zeroCopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author lianglong
 * @date 2020/5/5
 */
public class NewIoServer {

    public static void main(String[] args) throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7001);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        ServerSocket socket = serverSocketChannel.socket();

        socket.bind(inetSocketAddress);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);


        while(true){

            SocketChannel socketChannel = serverSocketChannel.accept();

            int readCount = 0;

            readCount = socketChannel.read(byteBuffer);

            while (readCount!=-1){
                socketChannel.read(byteBuffer);

                byteBuffer.rewind(); //倒带  position = 0  mark作废
            }




        }


    }
}
