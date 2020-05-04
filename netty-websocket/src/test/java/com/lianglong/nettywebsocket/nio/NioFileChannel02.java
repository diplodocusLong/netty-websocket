package com.lianglong.nettywebsocket.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lianglong
 * @date 2020/5/3
 */
public class NioFileChannel02 {

    public static void main(String[] args) throws IOException {

        File file = new File("/home/nevermore/file01.txt");

        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());

        channel.read(byteBuffer);

        String s = new String(byteBuffer.array());

        System.out.println(s);

        fileInputStream.close();




    }

}
