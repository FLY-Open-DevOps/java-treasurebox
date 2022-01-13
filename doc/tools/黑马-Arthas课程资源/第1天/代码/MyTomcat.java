package com.itheima;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;

/**
 * 实现的功能：将服务器上网页共享给浏览器端
 * 使用到技术点：
 * 1. 多线程：每个用户使用一个线程处理。
 * 2. Socket网络编程
 * 3. IO流，读取文件
 */
public class MyTomcat extends Thread {

    private Socket socket;

    //socket代表一个客户端，通过构造方法传入
    public MyTomcat(Socket socket) {
        this.socket = socket;
    }

    /**
     * 处理每个线程的任务，将服务器上网页共享给浏览器端
     */
    @Override
    public void run() {
        try (
                //1.读取本地(服务器)上文件，得到输入流
                FileInputStream in = new FileInputStream("e:/MyWeb/index.html");
                //2.通过Socket得到输出流
                OutputStream out = socket.getOutputStream();) {
            //3.将输入流的数据写到输出流中
            int len = 0;
            byte[] buf = new byte[512];
            //读取输入的数据到数组中
            while ((len = in.read(buf)) != -1) {
                //把数组中数据写到输出流中
                out.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        //创建服务器端
        ServerSocket serverSocket = new ServerSocket(8080);
        //输出现在的时间
        System.out.println(new Timestamp(System.currentTimeMillis()) + " 服务器启动");
        //接收客户端的请求
        while(true) {
            //得到一个客户端
            Socket socket = serverSocket.accept();
            System.out.println(new Timestamp(System.currentTimeMillis()) + " "
                    + socket.getInetAddress().getHostAddress() + " 连接了服务器");
            //启动线程执行上面任务
            new MyTomcat(socket).start();
        }
    }
}
