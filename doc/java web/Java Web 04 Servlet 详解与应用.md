## 一、Servlet详解【重点】

### 1.1 Servlet核心接口和类

- 在Servlet体系结构中，除了实现Servlet接口，还可以通过继承 GenericServlet或 HttpServlet类，完成编写。

#### 1.1.1 Servlet 接口

- 在ServletAPI中最重要的是Servlet接口，所有Servlet都会直接或间接的与该接口发生联系，或是直接实现该接口，或间接继承自实现了 该接口的类。该接口包括以下五个方法：
  - init(ServletConfig config)
  - ServletConfig getServletConfig()
  - service(ServletRequest req,ServletResponse res)
  - String getServletlnfo()
  - destroy()

#### 1.1.2 GenericServlet 抽象类

- GenericServlet使编写Servlet变得更容易。它提供生命周期方法init和destroy的简单实现，要编写一般的Servlet,只需重写抽象 service方法即可。

#### 1.1.3 HttpServlet类

- HttpServlet是继承GenericServlet的基础上进一步的扩展。
- 提供将要被子类化以创建适用于Web站点的HTTP servlet的抽象类。
- HttpServlet的子类至少必须重写一个方法，该方法通常是以下这些方法之一：
  - doGet，如果servlet支持HTTP GET请求
  - doPost,用于 HTTP POST 请求
  - doPut，用于 HTTP PUT 请求
  - doDelete，用于 HTTP DELETE 请求

### 1.2 Servlet两种创建方式

#### 1.2.1 实现接口 Servlet

```java
/** 
*  Servlet创建的第一种方式：实现接口Servlet
* */
public class HelloServlet2 implements Servlet{

    @0verride
    public void destroy() {}

    @0verride
    public ServletConfig getServletConfigO { return null;}

    @0verride
    public String getServletInfo() { return null;}

    @0verride
    public void init(ServletConfig arg0) throws ServletException {}

    @0verride
    public void service(ServletRequest request, ServletResponse response) throws 	ServletException, IOException {
        System.out.println("OK");
        response.getWriter().println("welcome use servlet");
    }

}
```

- 该方式比较麻烦，需要实现接口中所有方法。

代码：

```java
package com.zssea.servlet;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

public class MyServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {}

    @Override
    public ServletConfig getServletConfig() {return null;}

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("my first web project");
        System.out.println(new Date());
    }

    @Override
    public String getServletInfo() {return null;}

    @Override
    public void destroy() {}
}
```

#### 1.2.2 继承HttpServlet (推荐)

```java
/**
*  Servlet implementation class HelloServlet
*  Servlet的第二种创建方式，继承HttpServlet ,也是开发中推荐的
*/

public class HelloServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
	response.getWriter().print("elcome use servlet");
}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
	doGet(request, response);
}
}
```

代码：

```java
package com.zssea.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("这是get请求");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("这是post请求");
    }
}
```

#### 1.2.3常见锗误

- HTTP Status 404资源找不到。
  - 第一种情况：地址书写错误。
  - 第二种情况：地址没有问题，把IDEA项目中out目录删除，然后重新运行。
- Serlvet地址配置重复。both mapped to the url-pattern [/helloservlet] which is not permitted。
- Serlvet地址配置错误。比如没有写/ Invalid [helloservlet2] in servlet mapping。

### 1.3 Servlet两种配置方式

#### 1.3.1 使用web.xml (Servlet2.5之前使用)

```xml
<?xml version="1.0" encoding=MUTF-8"?>
<web-app xmlns:xsi="http:www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd" version="3.1">

    <display-name>Web_Day11</display-name>
    <!--Servlet的第二种配置 -->
    <!--Servlet 配置-->
    <servlet>
   		<!--名称-->
        <servlet-name>hello2</servlet-name>
        
        <!--Servlet的全称类名 -->
        <servlet-class>com.zssea.servlet.HelloServlet</servlet-class>
       
        <!--启动的优先级，数字越小越先起作用-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    
    <!--映射配置-->
    <servlet-mapping>
        <!--名称-->
        <servlet-name>hello2</servlet-name>
        
        <!--资源的匹配规则：精确匹配-->
		<url-pattern>/hello2</url-pattern>
     </servlet-mapping>
    
    <welcome-file-list>
        <welcome-file>login.html</welcome-file>
    </welcome-file-list>
</web_app>
```

#### 1.3.2配置属性

- url-pattern定义匹配规则，取值说明：
  - 精确匹配 /具体的名称 只有url路径是具体的名称的时候才会触发Servlet
  - 后缀匹配 *. xxx 只要是以xxx结尾的就匹配触发Servlet
  - 通配符匹配 /* 匹配所有请求，包含服务器的所有资源
  - 通配符匹配 / 匹配所有请求，包含服务器的所有资源，不包括.jsp
- load-on-startup
  - 元素标记容器是否应该在web应用程序启动的时候就加载这个servlet。
  - 它的值必须是一个整数，表示servlet被加载的先后顺序。
  - 如果该元素的值为负数或者没有设置，则容器会当Servlet被请求时再加载。
  - 如果值为正整数或者0时，表示容器在应用启动时就加载并初始化这个servlet，值越小，servlet的忧先级越高，就越先被加载。值相同时，容器就会自己选择顺序来加载。

#### 1.3.3使用注解（Sen/let3.0后支持，推荐)

```java
/**
*	Servlet implementation class HelloServlet 
*	演示Servlet注解式配置 
*/
©WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
	response .getWriter(). print( "0K");
}

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
{
	doGet(request, response);
}
} 
```

#### 1.3.4 @WebServlet注解常用属性

- name:Serlvet名字（可选）
- value:配置url路径，可以配置多个 （与web.xml中的url-pattern的作用是一样的）
- urIPatterns:配置url路径，和value作用一样，不能同时使用
- loadOnStartup:配置Servlet的创建的时机，如果是0或者正数启动程序时创建，如果是负数，则访问时创建。数子越小优先级越（与web.xml中的load-on-startup的作用是一样的）
- **注意：同时使用web.xml与使用注解方式来配置Servlet是不冲突的**

代码：

```java
package com.zssea.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//使用注解来配置Servlet
//@WebServlet(value = {"/bs","/bss"}) //这里面定义的是访问路径
@WebServlet(urlPatterns = {"/bs","/bss"},loadOnStartup = 0)
public class BasicServlet extends HttpsServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("这是BasicServlet----get请求");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("这是这是BasicServlet----post请求");
    }
}
```

## 二、Servlet应用【重点】

### 2.1 request 对象

- 在Servlet中用来处理客户端请求需要用doGet或doPost方法的request对象

| request                                                      |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808193819557.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |

#### 2.1.1get和post区别

- get请求
  - get提交的数据会放在URL之后，以?分割URL和传输数据，参数之间以&相连
  - get方式明文传递，数据量小，不安全
  - 效率高，浏览器默认请求方式为GET请求
  - 对应的Servlet的方法是doGet
- post请求
  - post方法是把提交的数据放在HTTP包的Body中
  - 密文传递数据，数据量大，安全
  - 效率相对没有GET高
  - 对应的Servlet的方法是doPost

#### 2.1.2 request主要方法

| 方法名                                    | 说明                         |
| ----------------------------------------- | ---------------------------- |
| String getParameter(String name)          | 根据表单组件名称获取提交数据 |
| void setCharacterEncoding(String charset) | 指定每个请求的编码           |

#### 2.1.3 request应用

- HTML页面

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册页面</title>
</head>
<body>
    <h1 >欢迎你</h1 >
    <div>
        <!--<form action="rs" method="get">-->
        <form action="rs" method="post">
            <label>用户：</label> <input type="text" name = "name"><br/>
            <label>密码：</label> <input type="password" name= "password"><br/>
            <input type="submit" value="提交"/>
        </form>
    </div>
</body>
</html>
```

- Servlet 代码

```java
package com.zssea.servlet.demo2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//使用注解方式配置访问路径
@WebServlet(value = "/rs")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取请求的参数
        String name = req.getParameter("name");
        //get请求解决乱码
        name =  new String(name.getBytes("ISO8859-1"),"UTF-8");
        String password = req.getParameter("password");

        System.out.println("提交的数据："+name+"\t"+password);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //post请求解决乱码
        //对request请求对象设置统一的编码
        req.setCharacterEncoding("UTF-8");
        //获取请求的参数
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        System.out.println("提交的数据："+name+"\t"+password);
    }
}
```

#### 2.1.4 请求收参乱码问题

- 产生乱码是因为服务器和客户端沟通的编码不一致造成的，
- 因此解决的办法是：在客户端和服务器之间设置一个统一的编码，之后就按照此编码进行数据的传输和接收

##### 2.1.4.1 get()方法 解决中文乱码

- 在Tomcat7及以下版本，客户端以UTF-8的编码传输数据到服务器端，而服务器端的request对象使用的是IS08859-1这个字符编码来接收数据，服务器和客户端沟通的编码不一致因此才会产生中文乱码的。
- 解决办法：在接收到数据后，先获取request对象以ISO8859-1字符编码接收到的原始数据的字节数组，然后通过字节数组以指定 的编码构建字符串，解决乱码问题。
- Tomcat8的版本中get方式不会出现乱码了（post请求还会），因为服务器对url的编码格式可以进行自动转换。

```java
//Servlet implementation class HttpServlet
/*演示Servlet的GET请求，中文乱码的问题 */

@WebServlet("/GETServlet")
public class GetServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //获取表单提交的内容
        String name=request.getParameter("name");
		name = new String(name.getBytes("ISO8859-1"),"UTF-8");//注意点
        String age=request.getParameter("age");

        System.out.println(request.getRemoteAddr() + "发来信息：姓名："+name+"------------------ >年龄："+age>;

}
```

##### 2.1.4.2 post()方法 解决中文乱码

- 由于客户端是以UTF-8字符编码将表单数据传输到服务器端的，因此服务器也需要设置以UTF-8字符编码逬行接收。
- 解决方案：使用从ServletRequest接口继承而来的setCharacterEncoding(charset)方法逬行统一的编码设置
- get() 与post() 两种请求处理乱码的方式不一样

```java
//Servlet implementation class HttpServlet 
//*演示Servlet的POST请求，中文乱码的问题

@WebServlet("/GETServlet")
public class GetServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        //设置请求参数的编码格式--对GET请求无效
        request.setCharacterEncoding("UTF-8"); //注意点

        //获取表单提交的信息
        String msg=request.getParameter("msg");
        System.out.println(request.getRemoteAddr()+ "发来信息："+msg);
    }
}
```

### 2.2 response

- response对象用于响应客户请求并向客户端输出信息。

| response                                                     |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808193853571.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |

#### 2.2.1 response 主要方法

| 方法名称                     | 作用                               |
| ---------------------------- | ---------------------------------- |
| setHeader(name,value)        | 设置响应信息头                     |
| setContentType(String)       | 设置响应文件类型、响应式的编码格式 |
| setCharacterEncoding(String) | 设置服务端响应内容编码格式         |
| getWriter()                  | 获取字符输出流                     |

#### 2.2.2 response 应用

```java
package com.zssea.servlet.demo2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//使用注解方式配置访问路径
@WebServlet(value = "/rs")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //request应用
        //获取请求的参数
        String name = req.getParameter("name");
        //get请求解决服务器（控制台）乱码
        name =  new String(name.getBytes("ISO8859-1"),"UTF-8");
        String password = req.getParameter("password");

        System.out.println("提交的数据："+name+"\t"+password);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //request应用
        //post请求解决服务器（控制台）乱码
        //对request请求对象设置统一的编码
        req.setCharacterEncoding("UTF-8");
        //获取请求的参数
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        //System.out.println("提交的数据："+name+"\t"+password);


        //response应用
        //响应数据给客户端，用中文时，客户端（浏览器）可能显示乱码,这是因为服务器默认采用IS08859-1编码响应内容
        //解决客户端乱码方式1.
        //resp.setCharacterEncoding("utf-8");//设置服务器端响应编码格式为 utf-8
        //设置客户端响应内容的头内容的文件类型及编码格式,告诉客户端我的响应内容的类型与字符编码
        //resp.setHeader("Content-type","text/html;charset=utf-8");

        //解决客户端乱码方式2.(推荐)，要在获取流之前使用
        resp.setContentType("text/html;charset=utf-8");
        
        PrintWriter writer = resp.getWriter();
        writer.println("注册成功");
    }
}
```

- 如果输出内容包含中文，则出现乱码，因为服务器默认采用IS08859-1编码响应内容

#### 2.2.3解决输出中文乱码

- 设置服务器端响应的编码格式
- 设置客户端响应内容的头内容的文件类型及编码格式

```java
response.setCharacterEncoding ("utf-8");//设置服务器端响应编码格式为 utf-8 
response.setHeader("Content-type","text/html;charset=UTF-8");//设置客户端响应内容的头内容的文件类型及编码格式
```

- 不推荐
- 同时设置服务端的编码格式和客户端响应的文件类型及响应时的编码格式

```java
response.setContentType("text/html;charset=UTF-8");
```

- 推荐

### 2.3 综合案例(Servlet + JDBC)

- 要求：实现登录功能、展示所有用户功能
- 以下仅展示关键代码

#### 2.3.1数据库

```sql
CREATE TABLE admin(
    username VARCHAR(20) PRIMARY KEY,
    PASSWORD VARCHAR(20) NOT NULL,
    phone varchar(11) NOT NULL,
	address varchar(20) NOT NULL 
)CHARSET=utf8;

INSERT INTO admin(username,PASSWORD,phone,address) 
VALUES('gavin','1123456', '12345678901','北京市昌平区');
INSERT INTO admin(username,PASSWORD,phone,address) 
VALUES('avin','1123456', '12345678901','北京市昌平区');
```

#### 2.3.2 DbUtils

```java
package com.zssea.servlet.demo3_servletProject.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DbUtils {
    private static DruidDataSource ds;
    private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        Properties properties = new Properties();
        InputStream is = DbUtils.class.getResourceAsStream("/database.properties");
        try {
            properties.load(is);
            ds = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection connection = threadLocal.get();
        try {
            if(connection == null){
                connection = ds.getConnection();
                threadLocal.set(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void begin(){
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void commint(){
        Connection connection = null;
        try {
            connection = getConnection();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ColseAll(connection,null,null);
        }
    }

    public static void rollback(){
        Connection connection = null;
        try {
            connection = getConnection();
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ColseAll(connection,null,null);
        }
    }

    public static void ColseAll(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(resultSet !=null){
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            if(connection != null){
                connection.close();
                threadLocal.remove();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

#### 2.3.3 AdminDaoImpl

```java
package com.zssea.servlet.demo3_servletProject.dao.impl;

import com.zssea.servlet.demo3_servletProject.dao.AdminDao;
import com.zssea.servlet.demo3_servletProject.entity.Admin;
import com.zssea.servlet.demo3_servletProject.utils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class AdminDapImpl implements AdminDao {
    QueryRunner queryRunner = new QueryRunner();
    @Override
    public int insert(Admin admin) {
        return 0;
    }

    @Override
    public int update(Admin admin) {
        return 0;
    }

    @Override
    public int delete(String username) {
        return 0;
    }

    @Override
    public Admin select(String username) {
        try {
            Admin admin = queryRunner.query(DbUtils.getConnection(), "select * from admin where username=?;"
                    , new BeanHandler<Admin>(Admin.class), username);
            return admin;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Admin> selectall() {
        try {
            List<Admin> admins = queryRunner.query(DbUtils.getConnection(), "select * from admin;"
                    , new BeanListHandler<Admin>(Admin.class));
            return admins;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

```

#### 2.3.4 AdminServicelmpl

```java
package com.zssea.servlet.demo3_servletProject.service.impl;

import com.zssea.servlet.demo3_servletProject.dao.AdminDao;
import com.zssea.servlet.demo3_servletProject.dao.impl.AdminDapImpl;
import com.zssea.servlet.demo3_servletProject.entity.Admin;
import com.zssea.servlet.demo3_servletProject.entity.Admin;
.servlet.demo3_servletProject.service.AdminService;
import com.import com.zssea.servlet.demo3_servletProject.entity.Admin;
.servlet.demo3_servletProject.utils.DbUtils;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    private AdminDao adminDao = new AdminDapImpl();

    @Override
    public Admin login(String username, String password) {
        Admin result = null;

        try {
            DbUtils.begin(); //开启事务
            Admin admin = adminDao.select(username);
            if(admin != null){
                if(admin.getPassword().equals(password)){
                    result = admin;
                }
            }
            DbUtils.commint(); // 成功则提交
        } catch (Exception e) {
            DbUtils.rollback(); //失败就回滚
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Admin> showAllAdmin() {
        List<Admin> result = null;
        try {
            DbUtils.begin();
            result= adminDao.selectall();
            DbUtils.commint();
        } catch (Exception e) {
            DbUtils.rollback();
            e.printStackTrace();
        }
        return result;
    }
}

```

#### 2.3.5 HTML页面代码

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录页面</title>
</head>
<body>
    <form action="login" method="post">
        <label>用户名：</label><input type="text" name="username"/><br/>
        <label>密码：</label><input type="password" name="password"/><br/>
        <input type="submit" value="提交"/>
    </form>
</body>
</html>
```

#### 2.3.6 LoginServlet

```java
package com.import com.zssea.servlet.demo3_servletProject.entity.Admin;
.servlet.demo3_servletProject.servlet;

import com.import com.zssea.servlet.demo3_servletProject.entity.Admin;
.servlet.demo3_servletProject.entity.Admin;
import com.import com.zssea.servlet.demo3_servletProject.entity.Admin;
.servlet.demo3_servletProject.service.AdminService;
import com.import com.zssea.servlet.demo3_servletProject.entity.Admin;
.servlet.demo3_servletProject.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//利用注解来配置servlet访问路径（未采用web.xml）
@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理乱码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //1.从客户端收参
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //2.调用业务逻辑
        AdminService adminService = new AdminServiceImpl();
        Admin admin = adminService.login(username, password);
        //3.处理结果
        PrintWriter writer = resp.getWriter();
        if(admin != null){
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<meta charset='utf-8'>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<h1>登录成功</h1>");
            writer.println("</body>");
            writer.println("</html>");
        }else {
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<meta charset='utf-8'>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<h1>登录失败</h1>");
            writer.println("</body>");
            writer.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}


```

#### 2.3.7 ShowAllAdminServlet

```java
package com.import com.zssea.servlet.demo3_servletProject.entity.Admin;
.servlet.demo3_servletProject.servlet;

import com.import com.zssea.servlet.demo3_servletProject.entity.Admin;
.servlet.demo3_servletProject.entity.Admin;
import com.import com.zssea.servlet.demo3_servletProject.entity.Admin;
.servlet.demo3_servletProject.service.AdminService;
import com.import com.zssea.servlet.demo3_servletProject.entity.Admin;
.servlet.demo3_servletProject.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(value = "/showall")
public class ShowAllServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理乱码
        resp.setContentType("text/html;charset=utf-8");

        //调用业务逻辑
        AdminService adminService = new AdminServiceImpl();
        List<Admin> admins = adminService.showAllAdmin();

        //处理结果
        PrintWriter writer = resp.getWriter();
        if(admins != null){
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<meta charset='utf-8'>");
            writer.println("<title>显示所有</title>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<table border='1'>");
            writer.println("    <tr>");
            writer.println("        <td>username</td>");
            writer.println("        <td>password</td>");
            writer.println("        <td>phone</td>");
            writer.println("        <td>address</td>");
            writer.println("    </tr>");
            for (Admin admin : admins){
                writer.println("    <tr>");
                writer.println("        <td>"+admin.getUsername()+"</td>");
                writer.println("        <td>"+admin.getPassword()+"</td>");
                writer.println("        <td>"+admin.getPhone()+"</td>");
                writer.println("        <td>"+admin.getAddress()+"</td>");
                writer.println("    </tr>");
            }
            writer.println("</table>");
            writer.println("</body>");
            writer.println("</html>");
        }else {
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<meta charset='utf-8'>");
            writer.println("<title>显示所有</title>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<h1>当前没有用户</h1>");
            writer.println("</body>");
            writer.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}

```

下面我们学习在 servlet 实现业务逻辑 与显示结果分离，先把代码写下：

```java
package com.import com.zssea.servlet.demo3_servletProject.entity.Admin;
.servlet.demo3_servletProject.servlet;

import com.import com.zssea.servlet.demo3_servletProject.entity.Admin;
.servlet.demo3_servletProject.entity.Admin;
import com.import com.zssea.servlet.demo3_servletProject.entity.Admin;
.servlet.demo3_servletProject.service.AdminService;
import com.import com.zssea.servlet.demo3_servletProject.entity.Admin;
.servlet.demo3_servletProject.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/showallcontroller") //访问路径
public class ShowAllAdminController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //只调用业务逻辑
        AdminService adminService = new AdminServiceImpl();
        List<Admin> admins = adminService.showAllAdmin();

        //request作用存储数据
        req.setAttribute("admins",admins);
        //通过转发 跳转到显示页面（同时存储的数据也可以通过作用域被获得）
        req.getRequestDispatcher("/showalljsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}


package com.import com.zssea.servlet.demo3_servletProject.entity.Admin;
.servlet.demo3_servletProject.servlet;

import com.import com.zssea.servlet.demo3_servletProject.entity.Admin;
.servlet.demo3_servletProject.entity.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(value = "/showalljsp")//访问路径
public class ShowAllAdminJSP extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //只负责显示页面
        //处理乱码
        resp.setContentType("text/html;charset=utf-8");
        //通过request作用域获得数据,(在此得到的是数据库里表admin的全部数据)
        List<Admin> admins = (List) req.getAttribute("admins");

        //处理结果
        PrintWriter writer = resp.getWriter();
        if(admins != null){
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<meta charset='utf-8'>");
            writer.println("<title>显示所有</title>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<table border='1'>");
            writer.println("    <tr>");
            writer.println("        <td>username</td>");
            writer.println("        <td>password</td>");
            writer.println("        <td>phone</td>");
            writer.println("        <td>address</td>");
            writer.println("    </tr>");
            for (Admin admin : admins){
                writer.println("    <tr>");
                writer.println("        <td>"+admin.getUsername()+"</td>");
                writer.println("        <td>"+admin.getPassword()+"</td>");
                writer.println("        <td>"+admin.getPhone()+"</td>");
                writer.println("        <td>"+admin.getAddress()+"</td>");
                writer.println("    </tr>");
            }
            writer.println("</table>");
            writer.println("</body>");
            writer.println("</html>");
        }else {
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<meta charset='utf-8'>");
            writer.println("<title>显示所有</title>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<h1>当前没有用户</h1>");
            writer.println("</body>");
            writer.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
```