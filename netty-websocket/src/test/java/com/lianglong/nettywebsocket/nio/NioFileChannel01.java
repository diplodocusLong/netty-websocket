package com.lianglong.nettywebsocket.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lianglong
 * @date 2020/5/3
 */
public class NioFileChannel01 {


    public static void main(String[] args) throws IOException {

        String str = "hello,梁龙";

        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("/home/nevermore/file01.txt");
        //获取对应的 channel
        FileChannel channel = fileOutputStream.getChannel();
        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将str放入byteBuffer
        byteBuffer.put(str.getBytes());
        //对byteBuffer进行flip
        byteBuffer.flip();
        //将byteBuffer数据写入到fileChannel
        channel.write(byteBuffer);
        fileOutputStream.close();


    }


}
