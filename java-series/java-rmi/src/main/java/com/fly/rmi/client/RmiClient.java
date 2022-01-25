package com.fly.rmi.client;

import java.rmi.Naming;

import com.fly.rmi.server.User;
import com.fly.rmi.server.UserService;

/**
 * @author https://www.fly.com
 * @date 2021/05/08
 */
public class RmiClient {
    public static void main(String args[]) {
        User answer;
        String userId = "00001";
        try {
            // lookup method to find reference of remote object
            UserService access = (UserService)Naming.lookup("rmi://localhost:1900/user");
            answer = access.findUser(userId);
            System.out.println("query:" + userId);
            System.out.println("result:" + answer);
        } catch (Exception ae) {
            System.out.println(ae);
        }
    }
}
