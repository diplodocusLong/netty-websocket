package com.lianglong.nettywebsocket.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author lianglong
 * @date 2020/5/3
 */
public class NioFileChannel {

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("/home/nevermore/Pictures/dy1.jpg");

        FileOutputStream fileOutputStream = new FileOutputStream("/home/nevermore/dy1.jpg");

        FileChannel channelSource = fileInputStream.getChannel();

        FileChannel channelTarget = fileOutputStream.getChannel();

        channelTarget.transferFrom(channelSource,0,channelSource.size());

         channelSource.close();

         channelTarget.close();

         fileInputStream.close();

         fileOutputStream.close();
    }
}
