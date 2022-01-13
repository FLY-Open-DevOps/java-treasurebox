## 过滤器【重点】

### 1.现有问题

- 在以往的Servlet中，有没有冗余的代码，多个Servlet都要进行编写。

### 2.概念

- 过滤器（Filter)是处于客户端与服务器目标资源之间的一道过滤技术。

| 过滤器                                                       |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808194539635.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |

### 3.过滤器作用

- 执行地位在Servlet之前，客户端发送请求时，会先经过Filter，再到达目标Servlet中；响应时，会根据执行流程再次反向执行Filter
- 可以解决多个Servlet共性代码的冗余问题（例如：乱码处理、登录验证）

### 4.编写过滤器

- Servlet API中提供了一个Filter接口，开发人员编写一个Java类实现了这个接口即可，这个Java类称之为过滤器（Filter)

#### 4.1实现过程

- 编写Java类实现Filter接口
- 在doFiIter方法中编写拦截逻辑
- 设置拦截路径

```java
package com.zssea.servlet.demo6_filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(value = "/t")
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    //执行过滤
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("-------MyFilter--------");
        //让请求继续
        filterChain.doFilter(servletRequest,servletResponse);

        System.out.println("-------MyFilter end--------");
    }

    @Override
    public void destroy() {

    }
}
```

### 5.过滤器配置

#### 5.1注解配置

- 在自定义的Filter类上使用注解 @WebFilter(value="/过滤目标资源")

#### 5.2 xml 配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
    <!--过滤器的xml配置-->
    <filter>
        <!--名称-->
        <filter-name>xmlFilter</filter-name>
        <!--过滤器类全称-->
        <filter-class>com.zssea.servlet.demo6_filter.XmlFilter</filter-class>
    </filter>
    <filter-mapping>
        <!--名称-->
        <filter-name>xmlFilter</filter-name>
        <!--这里写的是要访问的目标资源的访问路径，与servlet相似-->
        <url-pattern>/test</url-pattern>
    </filter-mapping>

</web-app>
```

#### 5.3过滤器路径

- 过滤器的过滤路径通常有三种形式：
  - 精确过滤匹配，比如/index.jsp /myservlet1
  - 后缀过滤匹配，比如 * .jsp、 * .html、 * .jpg
  - 通配符过滤匹配/ * ，表示拦截所有。注意过滤器不能使用 / 匹配。 /aaa/bbb/* 允许

### 6.过滤器链和优先级

#### 6.1过滤器链

- 客户端对服务器请求之后，服务器调用Servlet之前会执行一组过滤器（多个过滤器），那么这组过滤器就称为一条过滤器链。
- 每个过滤器实现某个特定的功能，当第一个Filter的doFilter方法被调用时，Web服务器会创建一个代表Filter链的FilterChain对象传递给该方法。在doFilter方法中，幵发人员如果调用了 FilterChain对象的doHIter方法，则Web服务器会检查FilterChain对象中是否还有filter， 如果有，则调用第2个filter,如果没有，则调用目标资源。

| 过滤器链                                                     |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808194603775.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |

#### 6.2过滤器优先级

- 在一个Web应用中，可以开发编写多个Filter，这些Filter组合起来称之为一个Filter链。
- 优先级:
  - 如果为注解的话，是按照类全名称的字符串顺序决定作用顺序
  - 如果web.xml，按照filter-mapping注册顺序，从上往下
  - web.xml配置高于注解方式
  - 如果注解和web.xm同时配置，会创建多个过滤器对象，造成过滤多次。

### 7.过滤器典型应用

#### 7.1过滤器解决编码

```java
package com.zssea.servlet.demo6_filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
@WebFilter(value = "/*")
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //统一处理请求和响应的乱码
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html;charset=utf-8");
        //放行
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
```

#### 7.2权限验证

- ShowAllAdminController

```java
package com.zssea.servlet.demo3_servletProject.servlet.controller;

import com.zssea.servlet.demo3_servletProject.entity.Admin;
import com.zssea.servlet.demo3_servletProject.entity.Manager;
import com.zssea.servlet.demo3_servletProject.service.AdminService;
import com.zssea.servlet.demo3_servletProject.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/showallcontroller")//访问路径
public class ShowAllAdminController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //利用过滤器做了权限控制，可以去掉了
        //通过HTTPsession完成权限控制
        //HttpSession session = req.getSession();
        //Manager manager = (Manager)session.getAttribute("manager");
        //if(manager != null){ //表明已登录
            //只调用业务逻辑
            AdminService adminService = new AdminServiceImpl();
            List<Admin> admins = adminService.showAllAdmin();

            //request作用存储数据
            req.setAttribute("admins",admins);
            //通过转发 跳转到显示页面（同时存储的数据也可以通过作用域被获得）
            req.getRequestDispatcher("/showalljsp").forward(req,resp);
        //}else {//表未登录，跳转登录页面
            //resp.sendRedirect("/javaweb_servlet_war_exploded/LoginMgr.html");
        //}
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
```

- CheckFilter

```java
package com.zssea.servlet.demo3_servletProject.filter;

import com.zssea.servlet.demo3_servletProject.entity.Manager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(value = "/showallcontroller")
public class CheckFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 权限验证，验证管理员是否登录
        //向下转型，拆箱
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession();
        Manager manager = (Manager)session.getAttribute("manager");
        if(manager != null){//已登录
            //放行
            filterChain.doFilter(request,response);
        }else {//未登录，跳转到登录页面
            response.sendRedirect(request.getContextPath()+"/LoginMgr.html");//获取上下文路径（应用程序名）
        }
    }

    @Override
    public void destroy() {

    }
}
```