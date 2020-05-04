package com.lianglong.nettywebsocket.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lianglong
 * @date 2020/5/4
 * 1 mappedByteBuffer 可以让文件直接在内存（堆外内存）修改,操作系统不需要拷贝一次
 */
public class MappedByteBufferTest {


    public static void main(String[] args) throws IOException {


        RandomAccessFile rw = new RandomAccessFile("/home/nevermore/ReadMe.txt", "rw");

        //获取对应的通道
        FileChannel channel = rw.getChannel();

        /**
         * 1.使用读写模式
         * 2.可以直接修改的起始位置
         * 3.是映射到内存的大小，即将文件的多少个字节映射到内存
         * 可以直接修改的位置就是0到5  这个5不是下标  相当于limit只能操作5个
         * 实际类型 DirectByteBuffer
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);


        map.put(0,(byte)'H');
        map.put(3,(byte)'9');

        rw.close();

    }
}
