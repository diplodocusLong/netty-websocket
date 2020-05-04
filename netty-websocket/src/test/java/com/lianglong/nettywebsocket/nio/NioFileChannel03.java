package com.lianglong.nettywebsocket.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lianglong
 * @date 2020/5/3
 */
public class NioFileChannel03 {

    public static void main(String[] args) throws IOException {

        File file = new File("/home/nevermore/Downloads/robocode-1.9.3.9-setup/ReadMe.txt");

        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel channel01 = fileInputStream.getChannel();


        FileOutputStream fileOutputStream = new FileOutputStream("/home/nevermore/ReadMe.txt");

        FileChannel channel02 = fileOutputStream.getChannel();


        ByteBuffer byteBuffer = ByteBuffer.allocate(5);


       /* channel01.read(byteBuffer);

        byteBuffer.flip();

        channel02.write(byteBuffer);
*/



       while(true){

           int read = channel01.read(byteBuffer);

           System.out.println(read);

           byteBuffer.flip();

           channel02.write(byteBuffer);

           byteBuffer.clear();


           if(read == -1){
               break;
           }


       }

       fileInputStream.close();
       fileOutputStream.close();


    }


}
