# 实现邮件发送

## 1.邮件发送原理图

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200708003842143.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NTE0ODk5Nw==,size_16,color_FFFFFF,t_70#pic_center)

## 2.jar包的支持

**activation-1.1.1.jar**

**mail-1.4.7.jar**

## 3.授权码的获取

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200708003902427.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NTE0ODk5Nw==,size_16,color_FFFFFF,t_70#pic_center)

## 4.简易文本邮件发送的实现

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200708003918960.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NTE0ODk5Nw==,size_16,color_FFFFFF,t_70#pic_center)

**由上图我们可以确定几个必须步骤**

**1.创建session对象**

**2.创建Transport对象**

**3.使用邮箱的用户名和授权码连上邮件服务器**

**4.创建一个Message对象（需要传递session）**

- **message需要指明发件人、收件人以及文件内容**

**5.发送邮件**

**6.关闭连接**

```java
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailDemo01 {
    public static void main(String[] args) throws Exception {
        Properties prop=new Properties();
        prop.setProperty("mail.host","smtp.qq.com");///设置QQ邮件服务器
        prop.setProperty("mail.transport.protocol","smtp");///邮件发送协议
        prop.setProperty("mail.smtp.auth","true");//需要验证用户密码
        //QQ邮箱需要设置SSL加密
        MailSSLSocketFactory sf=new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable","true");
        prop.put("mail.smtp.ssl.socketFactory",sf);

        //使用javaMail发送邮件的5个步骤
        //1.创建定义整个应用程序所需要的环境信息的session对象
        Session session=Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("XXXX@qq.com","授权码");
            }
        });
        //开启session的debug模式，这样可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2.通过session得到transport对象
        Transport ts=session.getTransport();
        //3.使用邮箱的用户名和授权码连上邮件服务器
        ts.connect("smtp.qq.com","XXXX@qq.com","授权码");
        //4.创建邮件：写文件
        //注意需要传递session
        MimeMessage message=new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress("XXXX@qq.com"));
        //指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress("XXXX@qq.com"));
        //邮件标题
        message.setSubject("发送的标题");
        //邮件的文本内容
        message.setContent("内容","text/html;charset=UTF-8");
        //5.发送邮件
        ts.sendMessage(message,message.getAllRecipients());

        //6.关闭连接
        ts.close();

    }
}
```

## 5.复杂文件内容的发送

### 5.1文件构成解析

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200708004214134.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NTE0ODk5Nw==,size_16,color_FFFFFF,t_70#pic_center)

**除了邮件内容部分，其他的部分代码往往是相同的，除了需要根据不同的邮箱运营商编写不同的配置代码外。邮件内容也被分为很多个部分，由文件、图片、附件等构成，编写邮件内容的过程，类似于积木的拼接，另外值得注意的是文本内容一般为HTML的格式发送。**

**每一个文本、图片、附件可以分为一个MimeBodyPart，由MimeMultipart完成组装**

### 5.2包含图片的发送

```java
import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class MailDemo02 {
    public static void main(String[] args) throws Exception {
        Properties prop=new Properties();
        prop.setProperty("mail.host","smtp.qq.com");///设置QQ邮件服务器
        prop.setProperty("mail.transport.protocol","smtp");///邮件发送协议
        prop.setProperty("mail.smtp.auth","true");//需要验证用户密码
        //QQ邮箱需要设置SSL加密
        MailSSLSocketFactory sf=new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable","true");
        prop.put("mail.smtp.ssl.socketFactory",sf);

        //使用javaMail发送邮件的5个步骤
        //1.创建定义整个应用程序所需要的环境信息的session对象
        Session session=Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("11927XXX@qq.com","授权码");
            }
        });
        //开启session的debug模式，这样可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2.通过session得到transport对象
        Transport ts=session.getTransport();
        //3.使用邮箱的用户名和授权码连上邮件服务器
        ts.connect("smtp.qq.com","11927XXX@qq.com","授权码");
        //4.创建邮件：写文件
        //注意需要传递session
        MimeMessage message=new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress("11927XXX@qq.com"));
        //指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress("11927XXX@qq.com"));
        //邮件标题
        message.setSubject("java发出");

        //邮件的文本内容
        //=================================准备图片数据=======================================
        MimeBodyPart image=new MimeBodyPart();
        //图片需要经过数据化的处理
        DataHandler dh=new DataHandler(new FileDataSource("D:\\Bert\\1594126632(1).jpg"));
        //在part中放入这个处理过图片的数据
        image.setDataHandler(dh);
        //给这个part设置一个ID名字
        image.setContentID("bz.jpg");

        //准备正文的数据
        MimeBodyPart text=new MimeBodyPart();
        text.setContent("这是一张正文<img src='cid:bz.jpg'>","text/html;charset=UTF-8");

        //描述数据关系
        MimeMultipart mm=new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(image);
        mm.setSubType("related");

        //设置到消息中，保存修改
        message.setContent(mm);
        message.saveChanges();
        //5.发送邮件
        ts.sendMessage(message,message.getAllRecipients());

        //6.关闭连接
        ts.close();

    }
}
```

### 5.3包含附件的发送

```java
import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailDemo03 {
    public static void main(String[] args) throws Exception {
        Properties prop=new Properties();
        prop.setProperty("mail.host","smtp.qq.com");///设置QQ邮件服务器
        prop.setProperty("mail.transport.protocol","smtp");///邮件发送协议
        prop.setProperty("mail.smtp.auth","true");//需要验证用户密码
        //QQ邮箱需要设置SSL加密
        MailSSLSocketFactory sf=new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable","true");
        prop.put("mail.smtp.ssl.socketFactory",sf);

        //使用javaMail发送邮件的5个步骤
        //1.创建定义整个应用程序所需要的环境信息的session对象
        Session session=Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1192XXXX@qq.com","授权码");
            }
        });
        //开启session的debug模式，这样可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2.通过session得到transport对象
        Transport ts=session.getTransport();
        //3.使用邮箱的用户名和授权码连上邮件服务器
        ts.connect("smtp.qq.com","1192XXXX@qq.com","授权码");
        //4.创建邮件：写文件
        //注意需要传递session
        MimeMessage message=new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress("1192XXXX@qq.com"));
        //指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress("1192XXXX@qq.com"));
        //邮件标题
        message.setSubject("java发出");

        //邮件的文本内容
        //=================================准备图片数据
        MimeBodyPart image=new MimeBodyPart();
        //图片需要经过数据化的处理
        DataHandler dh=new DataHandler(new FileDataSource("D:\\Bert\\1594126632(1).jpg"));
        //在part中放入这个处理过图片的数据
        image.setDataHandler(dh);
        //给这个part设置一个ID名字
        image.setContentID("bz.jpg");

        //=================================准备正文数据
        MimeBodyPart text=new MimeBodyPart();
        text.setContent("这是一张正文<img src='cid:bz.jpg'>","text/html;charset=UTF-8");

        //=================================准备附件数据
        MimeBodyPart body1= new MimeBodyPart();
        body1.setDataHandler(new DataHandler(new FileDataSource("D:\\Bert\\cmd.txt")));
        body1.setFileName("1.txt");

        //描述数据关系
        MimeMultipart mm=new MimeMultipart();
        mm.addBodyPart(body1);
        mm.addBodyPart(text);
        mm.addBodyPart(image);
        mm.setSubType("mixed");

        //设置到消息中，保存修改
        message.setContent(mm);
        message.saveChanges();
        //5.发送邮件
        ts.sendMessage(message,message.getAllRecipients());

        //6.关闭连接
        ts.close();

    }
}
```

## 6.实战：注册通知邮件

### 6.1 index.JSP

```java
<html>
<body>
<h2>Hello World!</h2>
<form action="${pageContext.request.contextPath}/RegisterServlet.do" method="post">
    用户名：<input type="text" name="username">
    密码：<input type="text" name="pwd">
    邮箱：<input type="text" name="email">
    <input type="submit" value="注册">

</form>
</body>
</html>
```

### 6.2 实体类

```java
public class User {
    private String username;
    private String password;
    private String email;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
```

### 6.3 绑定路径

```xml
  <servlet>
    <servlet-name>RegisterServlet</servlet-name>
    <servlet-class>cn.csn.MailDemo01.servlet.RegisterServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>RegisterServlet</servlet-name>
    <url-pattern>/RegisterServlet.do</url-pattern>
  </servlet-mapping>
```

### 6.4 servlet

```java
import cn.csn.MailDemo01.pojo.User;
import cn.csn.MailDemo01.utils.Sendmail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username=req.getParameter("username");
        String password=req.getParameter("pwd");
        String email=req.getParameter("email");

        User user=new User(username,password,email);
        Sendmail send=new Sendmail(user);
        send.start();
        System.out.println("success");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
```

### 6.5发送邮件核心类

```java
import cn.csn.MailDemo01.pojo.User;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Sendmail extends Thread {

    private String from="XXX@qq.com";

    private String host="smtp.qq.com";

    private User user;

    public Sendmail(User user){
        this.user=user;
    }
    @Override
    public void run() {
        try {
            Properties prop=new Properties();
            prop.setProperty("mail.host",host);///设置QQ邮件服务器
            prop.setProperty("mail.transport.protocol","smtp");///邮件发送协议
            prop.setProperty("mail.smtp.auth","true");//需要验证用户密码
            //QQ邮箱需要设置SSL加密
            MailSSLSocketFactory sf=new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable","true");
            prop.put("mail.smtp.ssl.socketFactory",sf);

            //使用javaMail发送邮件的5个步骤
            //1.创建定义整个应用程序所需要的环境信息的session对象
            Session session= Session.getDefaultInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from,"授权码");
                }
            });
            //开启session的debug模式，这样可以查看到程序发送Email的运行状态
            session.setDebug(true);
            //2.通过session得到transport对象
            Transport ts=session.getTransport();
            //3.使用邮箱的用户名和授权码连上邮件服务器
            ts.connect(host,from,"授权码");
            //4.创建邮件：写文件
            //注意需要传递session
            MimeMessage message=new MimeMessage(session);
            //指明邮件的发件人
            message.setFrom(new InternetAddress(from));
            //指明邮件的收件人
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(user.getEmail()));
            //邮件标题
            message.setSubject("注册通知");
            //邮件的文本内容
            message.setContent("恭喜你("+user.getUsername()+")成功注册！"+"密码："+user.getPassword()
                    ,"text/html;charset=UTF-8");
            //5.发送邮件
            ts.sendMessage(message,message.getAllRecipients());

            //6.关闭连接
            ts.close();
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
```

这里引入多线程的目的是为了提高用户的体验，防止因发送文件时间过长，导致前端响应过久，因此这里采用异步响应。