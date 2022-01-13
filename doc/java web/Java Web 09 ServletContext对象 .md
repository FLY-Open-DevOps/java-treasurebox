### 1.ServletContext 概述

- 全局对象，也拥有作用域，对应一个Tomcat中的Web应用
- 当Web服务器启动时，会为每一个Web应用程序创建一块共享的存储区域（ServletContext)。
- ServletContext在Web服务器启动时创建，服务器关闭时销毁。

### 2.获取ServletContext对象

- GenericServlet提供了getServletContext()方法。（推荐）this.getServletContext();
- HttpServletRequest提供了getServletContext()方法.（推荐) request.getServletContext();
- HttpSession 提供了getServletContext()方法。

### 3.ServletContext作用

#### 3.1获取项目真实路径

- 获取当前项目在服务器发布的真实路径

```java
String realpath=servletContext.getRealPath("/");
```

#### 3.2获取项目上下文路径

- 获取当前项目上下文路径（应用程序名称）

```java
System.out.println(servletContext.getContextPath()); //上下文路径（应用程序名称) 
System.out.println(request.getContextPath());
```

#### 3.3全局容器

- ServletContext拥有作用域，可以存储数据到全局容器中 (在整个web应用下都可以获取ServletContext存储的数据)
  - 存储数据：servletContext.setAttribute(“name”,value);
  - 获取数据：servletContext.getAttribute(“name”);
  - 移除数据：servletContext.removeAttribute(’’name");

代码：

```java
package com.zssea.servlet.demo5_sessioncontext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SessionContextController" ,value = "/sessioncontext")
public class SessionContextController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.（推荐)this.getServletContext();
        ServletContext servletContext = this.getServletContext();

        //2.（推荐) request.getServletContext();
        //ServletContext servletContext1 = request.getServletContext();
        //3.HttpSession 提供了getServletContext()方法。
        //HttpSession session = request.getSession();
        //ServletContext servletContext2 = session.getServletContext();

        System.out.println(servletContext);
        System.out.println(servletContext.getRealPath("/"));//作用1：获取项目真实路径(绝对路径)

        //上下文路径可以用于设置项目的访问路径（例如，html中表单的action），这样可以确保正确
        System.out.println(servletContext.getContextPath());//作用2：获取当前项目上下文路径（应用程序名称）
        System.out.println(request.getContextPath());//获取当前项目上下文路径（应用程序名称）

        //作用3：存储数据
        servletContext.setAttribute("context","info");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```

#### 3.4共享数据

我在这个Servlet中保存的数据，可以在另外一个servlet中拿到；

```java
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        //this.getInitParameter()   初始化参数
        //this.getServletConfig()   Servlet配置
        //this.getServletContext()  Servlet上下文
        ServletContext context = this.getServletContext();

        String username = "++"; //数据
        context.setAttribute("username",username); //将一个数据保存在了ServletContext中，名字为：username 。值 username

    }

}


public class GetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = this.getServletContext();
        String username = (String) context.getAttribute("username");

        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().print("名字"+username);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
```

#### 3.5获取初始化参数

```xml
    <!--配置一些web应用初始化参数-->
    <context-param>
        <param-name>url</param-name>
     <paramvalue>jdbc:mysql://localhost:3306/mybatis</param-      value>
    </context-param>


protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ServletContext context = this.getServletContext();
    String url = context.getInitParameter("url");
    resp.getWriter().print(url);
}
```

#### 3.6请求转发

```java
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ServletContext context = this.getServletContext();
    System.out.println("进入了ServletDemo04");
    //RequestDispatcher requestDispatcher = context.getRequestDispatcher("/gp"); //转发的请求路径
    //requestDispatcher.forward(req,resp); //调用forward实现请求转发；
    context.getRequestDispatcher("/gp").forward(req,resp);
}
```

#### 3.7读取资源文件
Properties

- 在java目录下新建properties
- 在resources目录下新建properties

发现：都被打包到了同一个路径下：classes，我们俗称这个路径为classpath:
思路：需要一个文件流

```
username=root12312
password=zxczxczxc
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200505153908944.png)

```java
public class ServletDemo05 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        InputStream is = this.getServletContext().getResourceAsStream("/WEB-INF/classes/com/kuang/servlet/aa.properties");

        Properties prop = new Properties();
        prop.load(is);
        String user = prop.getProperty("username");
        String pwd = prop.getProperty("password");

        resp.getWriter().print(user+":"+pwd);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
```

### 4.ServletContext 特点

- 唯一性：一个应用对应一个ServletContext。
- 生命周期：只要容器不关闭或者应用不卸载，ServletContext就一直存在。

### 5. ServletContext应用场景

- ServletContext统计当前项目访问l次数

```java
package com.zssea.servlet.demo5_sessioncontext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CounterController",value="/count")
public class CounterController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取ServletContext
        ServletContext servletContext = request.getServletContext();

        //2.获得计时器
        Integer count = (Integer) servletContext.getAttribute("count");
        if(count == null){
            count = 1;
            servletContext.setAttribute("count",count);
        }else {
            count++;
            servletContext.setAttribute("count",count);
        }

        System.out.println("count:"+count);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```

### 6.作用域总结

- HttpServletRequest: —次请求，请求响应之前有效
- HttpSession:一次会话开始，浏览器不关闭或不超时之前有效
- ServletContext:服务器启动开始，服务器停止之前有效