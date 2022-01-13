## 一、转发与重定向

### 1.1现有问题

- 在之前案例中，调用业务逻辑和显示结果页面都在同一个Servlet里，就会产生设计问题
  - 不符合单一职能原则、各司其职的思想
  - 不利于后续的维护
- 应该将业务逻辑和显示结果分离开

| 现阶段问题                                                   |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808194026527.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |

#### 1.1.1业务、显示分离

| 业务与显示分离                                               |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808194044879.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |

- 问题：业务逻辑和显示结果分离后，如何跳转到显示结果的Servlet?
- 业务逻辑得到的数据结果如何传递给显示结果的Servlet?

### 1.2转发

- **转发作用在服务器端**，将请求发送给服务器上的其他资源，以**共同完成一次请求的处理。**

#### 1.2.1页面跳转

- 在调用业务逻辑的Servlet中，编写以下代码
  - request.getRequestDispatcher(“目标URL-pattern”).forward(request，response);
  - 注意参数（“目标URL-pattern”），前面不能加 request.getContextpath()

| forward                                                      |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808194106257.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |

- **使用forward跳转时， 是在服务器内部跳转，地址栏不发生变化，属于同一次请求**

#### 1.2.2数据传递

- forward表示一次请求，是在服务器内部跳转，可以

  共享同一次request作用域中的数据

  - request作用域：拥有存储数据的空间，作用范围是一次请求有效(一次请求可以经过多次转发)
    - 可以将数据存入request后，在一次请求过程中的任何位置进行获取
    - 可传递任何数据(基本数据类型、对象、数组、集合等）
  - 存数据：request.setAttribute(key，value);
    - 以键值对形式存储在request作用域中。key为String类型，v/alue为Object类型
  - 取数据：request.getAttribute(key);
    - 通过String类型的key访问Object类型的value

#### 1.2.3转发特点

- 转发是**服务器行为**
- 转发是**浏览器只做了一次访问请求**
- 转发**浏览器地址不变**
- 转发**两次跳转之间传输的信息不会丢失，所以可以通过request进行数据的传递**
- 转发只能将请求转发给同一个Web应用中的组件

代码：（上一节最后写的案例的servlet 的改善）

```java
package com.zssea.servlet.demo3_servletProject.servlet;

import com.zssea.servlet.demo3_servletProject.entity.Admin;
import com.zssea.servlet.demo3_servletProject.service.AdminService;
import com.zssea.servlet.demo3_servletProject.service.impl.AdminServiceImpl;

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



package com.zssea.servlet.demo3_servletProject.servlet;

import com.zssea.servlet.demo3_servletProject.entity.Admin;

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

### 1.3重定向

- **重定向作用在客户端**，客户端将请求发送给服务器后，服务器响应给客户端一个新的请求地址，客户端重新发送新请求。

#### 1.3.1页面跳转

- 在调用业务逻辑的Servlet中，编写以下代码
  - response.sendRedirect(“目标 URI”);
- URI:统一资源标识符(Uniform Resource Identifier),用来表示服务器中定位一个资源，资源在web项目中的路径(/project/source)

| redirect                                                     |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808194129348.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |

- 使用redirect跳转时，是在客户端跳转，地址栏发生变化，属于多次请求

#### 1.3.2数据传递

- sendRedirect跳转时，地址栏改变，代表客户端重新发送的请求。属于两次请求
  - response没有作用域，两次request请求中的数据无法共享
  - 传递数据：通过URI的拼接进行数据传递("/WebProject/b?username=tom");
  - 获取数据：request.getParameter(“username”)；

#### 1.3.3重定向特点

- 重定向是客户端行为。
- 重定向是浏览器做了至少两次的访问请求。
- 重定向浏览器地址改变。
- 重定向两次跳转之间传输的信息会丢失（request范围）。
- 重定向**可以指向任何的资源，包括当前应用程序中的其他资源、同一个站点上的其他应用程序中的资源、其他站点的资源。**

代码：(转发与重定向)

```java
package com.zssea.servlet.demo3_servletProject.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/a")
public class AServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //转发
        //request作用存储数据
        //req.setAttribute("username","小明");
        //通过转发 跳转到显示页面（同时存储的数据也可以通过作用域被获得）,注意是通过访问路径来跳转的
        //req.getRequestDispatcher("/b").forward(req,resp);

        //重定向
        //注意是通过目标的URL(资源在web项目中的路径(/project/source)) 来跳转的
        //而数据传递 也就要在这个路径后加上要传递的数据(并且只能传递String类型的数据)
        resp.sendRedirect("/javaweb_servlet_war_exploded/b?username=tom");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}


package com.zssea.servlet.demo3_servletProject.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/b")
public class BServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //转发
        // 获得数据
        //String username = (String)req.getAttribute("username");
        //System.out.println(username);

        //重定向
        //获得数据
        String username = req.getParameter("username");
        System.out.println("B Executed........"+username);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
```

### 1.4转发、重定向总结

- 当两个Servlet需要传递数据时，选择forward转发。不建议使用sendRedirect进行传递![在这里插入图片描述](https://img-blog.csdnimg.cn/20200505153728272.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2JlbGxfbG92ZQ==,size_16,color_FFFFFF,t_70)