## 一、状态管理

### 1.1现有问题

- HTTP协议是无状态的，不能保存每次提交的信息
- 如果用户发来一个新的请求，服务器无法知道它是否与上次的请求有联系。
- 对于那些需要多次提交数据才能完成的Web操作，比如登录来说，就成问题了。

### 1.2概念

- **将浏览器与web服务器之间多次交互当作一个整体来处理，并且将多次交互所涉及的数据（即状态）保存下来。**

### 1.3状态管理分类

- 客户端状态管理技术：将状态保存在客户端。代表性的是Cookie技术。
- 服务器状态管理技术：将状态保存在服务器端。代表性的是session技术（服务器传递sessionID时需要使用Cookie的方式) 和 application

## 二、Cookie的使用

### 2.1 什么是 Cookie

- Cookie是在浏览器访问Web服务器的某个资源时，由Web服务器在HTTP响应消息头中附带传送给浏览器的一小段数据。

- 一旦Web浏览器保存了某个Cookie，那么它在以后每次访问该Web服务器时，都应在HTTP请求头中将这个Cookie回传给Web服务器。

- —个Cookie主要由标识该信息的名称（name)和值（value)组成。（键值对）

  

| Cookie原理                                                   |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/2020080819424287.jpg) |

### 2.2 创建 Cookie

```java
//创建Cookie
Cookie ck=new Cookie("code", code);
ck.setPath("/webs" );//设置Cookie的路径（可以设置哪个路径下的资源可以访问）
ck.setMaxAge(-1);//内存存储，取值有三种: >日有效期，单位秒; =θ浏览器关闭; <θ内存存储，默认-1
response.addCookie(ck); //添加到response对象中，响应时发送给客户端
//注意:有效路径:当前访问资源的上一级目录，不带主机名
```

- chrome浏览器查看cookie信息：chrome://settings/content/cookies

代码：

```java
package com.zssea.servlet.demo5_cookies;

import com.zssea.servlet.demo1.HttpsServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/cs")
public class CookieServlet extends HttpsServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.创建Cookies
        Cookie cookie = new Cookie("username","xiaoming");
        //1.1 设置Cookie的访问路径
        cookie.setPath("/javaweb_servlet_war_exploded/get"); //表明只有这个项目下的get及get下的资源可以访问该Cookie

        //1.2设置Cookie的有效期
        cookie.setMaxAge(60*60);//一个小时

        //2.将Cookie响应给客户端
        resp.addCookie(cookie);
    }
}
```

### 2.3 获取 Cookie

- 服务器响应给浏览器（浏览器保存），之后浏览器每次访问该Web服务器时，都会将这个Cookie回传给Web服务器，而服务器要去获取它

```java
//获取所有的Cookie
Cook1e[] cks=request.getCookies();
    //遍历Cookie
    for(Cookie ck:cks){
        //检索出自己的Cookie (比较键值对中的键)
        if(ck.getName().equals("code")){
            //记录Cook1e的值
            code =ck getValue();
            break;
        } 
    }
```

代码：

```java
package com.zssea.servlet.demo5_cookies;

import com.zssea.servlet.demo1.HttpsServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/get")
public class GetServlet extends HttpsServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取Cookies
        Cookie[] cookies = req.getCookies();
        //2.遍历Cookies数组
        if(cookies != null){
            for(Cookie cookie : cookies){
                System.out.println(cookie.getName()+":"+cookie.getValue());
            }
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
```

### 2.4 修改 Cookie

- 只需要保证Cookie的名和路径一致即可修改

```java
//修改Cookie
Cookie ck=new Cookie("code", code);
ck. setPath("/webs" );//设置Cookie的路径
ck. setHaxAge(-1)://内存存储，取值有三种: >0有效期，单位秒: =0失效: <日内存存储
response。addCookie(ck);//让浏览器添加Cookie
```

- 注意：如果改变cookie的name和有效路径会新建cookie，而改变cookie值、有效期会覆盖原有cookie
- 只有cookie的name 和访问路径一样时，才会修改

### 2.5 Cookie编码与解码

- Cookie默认不支持中文，只能包含ASCII字符，所以Cookie需要对Unicode字符进行编码，否则会出现乱码
  - 编码可以使用 java.net.URLEncoder 类的 encode(String str，String encoding)方法
  - 解码使用java.net.URLDecocder类的decode(String str,String encoding)方法

#### 2.5.1创建带中文Cookie

```java
//使用中文的Cookie.name与value都使用UTF-8编码。
Cook1e cook1e = new Cookie(
	URLEncoder.encode("姓名"，"UTF-8"), 
	URLEncoder.encode("老邢","UTF-8")):
//发送到客户端
response.addCookie( cookie);
```

#### 2.5.2 读取带中文Cookie

```java
if(request.getCookies() != null){
    for(Cookie cc : request.getCookies())(
        String cookieName = URLDecoder.decode(cc.getName()，"UTF-8");
        String cookievalue = URLDecoder.decode(cc.getValue()，"UTF-8"):
        out.print1n(cookieName + "=");
        out.println( cookieValue +"; <br/>");
    }            
}else{
	out.println( "Cookie已经写入客户端,请剧新页面，");
}
```

代码：

```java
package com.zssea.servlet.demo5_cookies;

import com.zssea.servlet.demo1.HttpsServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet(value = "/cs3")
public class CookieServlet3 extends HttpsServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.创建Cookies
        //编码
        Cookie cookie = new Cookie(URLEncoder.encode("姓名","UTF-8"),URLEncoder.encode("小明","UTF-8"));
        cookie.setPath("/javaweb_servlet_war_exploded/get");
        //1.2设置Cookie的有效期
        cookie.setMaxAge(60*60);//一个小时
        //2.将Cookie响应给客户端
        resp.addCookie(cookie);
    }
}

package com.zssea.servlet.demo5_cookies;

import com.zssea.servlet.demo1.HttpsServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

@WebServlet(value = "/get")
public class GetServlet extends HttpsServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取Cookies
        Cookie[] cookies = req.getCookies();
        //2.遍历Cookies数组
        if(cookies != null){
            for(Cookie cookie : cookies){
                //解码
                System.out.println(URLDecoder.decode(cookie.getName(),"UTF-8")+":"+URLDecoder.decode(cookie.getValue(),"UTF-8"));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
```

### 2.6 Cookie优点和缺点

#### 2.6.1优点

- 可配置到期规则。
- 简单性：Cookie是一种基于文本的轻量结构，包含简单的键值对。
- 数据持久性：Cookie默认在过期之前是可以一直存在客户端浏览器上的。

#### 12.6.2缺点

- 大小受到限制：大多数浏览器对Cookie的大小有4K、8K字节的限制。
- 用户配置为禁用：有些用户禁用了浏览器或客户端设备接收Cookie的能力，因此限制了这一功能。
- 潜在的安全风险：Cookie可能会被篡改。会对安全性造成潜在风险或者导致依赖于Cookie的应用程序失败。

## 三、Session对象【重点】

### 3.1Session 概述

- Session用于记录用户的状态。Session指的是在一段时间内，单个客户端与Web服务器的一连串相关的交互过程。(多次的请求与响应)
- 在一个Session中，客户可能会多次请求访问同一个资源，也有可能请求访问各种不同的服务器资源。

### 3.2 Session原理

- 服务器会为每一次会话分配一个Session对象
- 同一个浏览器发起的多次请求，同属于一次会话(Session)
- 首次使用到Session时，服务器会自动创建Session，并创建Cookie存储Sessionld发送回客户端
- **注意：session是由服务端创建的。**

### 3.3 Session使用

- Session作用域：拥有存储数据的空间，作用范围是一次会话有效
  - 一次会话是使用同一浏览器发送的多次请求。一旦浏览器关闭，则结束会话 。
  - 可以将数据存入Session中，在一次会话的任意位置进行获取 。
  - 可传递任何数据(基本数据类型、对象、集合、数组>

#### 3.3.1 获取 Session

- session是服务器端自动创建的，通过request对象获取

```java
//获取Session对象
HttpSession session = request.getSession();
System.out.println("Id:"+session.getId())//唯一标记，
```

#### 3.3.2 Session保存数据

- setAttribute(属性名，Object)保存数据到 session 中

```java
session.setAttribute("key", value) ;//以键值对形式存储在session作用域中。
```

#### 3.3.3 Session获取数据

- getAttribute(属性名);获取session中数据

```java
session.getAttribute("key");//通过String 类型的 key 访问 Object 类型的 value
```

#### 3.3.4 Session 移除数据

- removeAttribute(属性名);从session中删除数据

```java
session.removeAttribute("key");//通过键移除session作用域中的值
```

代码：

```java
package com..servlet.demo5_sessions;
.servlet.demo5_sessions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SessionServlet",value = "/ss")
public class SessionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.通过request对象获取session
        HttpSession session = request.getSession();

        //2.使用session保存数据
        session.setAttribute("username","xiaoming");
        System.out.println("id:"+session.getId());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}


package com..servlet.demo5_sessions;
.servlet.demo5_sessions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "GetValueServlet",value = "/getvalue")
public class GetValueServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.通过request对象获取session
        HttpSession session = request.getSession();

        //2.使用session获得数据
        String username = (String)session.getAttribute("username");
        System.out.println("从session中获得数据："+username);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}


package com.package com.zssea.servlet.demo3_servletProject.servlet.controller;
.servlet.demo5_sessions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RemoveServlet" ,value="/remove")
public class RemoveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.通过request对象获取session
        HttpSession session = request.getSession();

        //2.使用session移除
        session.removeAttribute("username");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```

### 3.4Session 与 Request 应用区别

- request是一次请求有效，请求改变，则request改变
- (如果采用重定向，改变之后就不能获取request内的数据)
- session是一次会话有效，浏览器改变，则session改变

#### 3.4.1 Session 与 Request 应用区别代码

```java
package com.package com.zssea.servlet.demo3_servletProject.servlet.controller;
.servlet.demo5_sessions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SessionServlet",value = "/ss")
public class SessionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.通过request对象获取session
        HttpSession session = request.getSession();
        //2.使用session保存数据
        session.setAttribute("username","xiaoming");

        //request域存数据
        request.setAttribute("password","123456");
        //重定向
        response.sendRedirect("/javaweb_servlet_war_exploded/getvalue");

        System.out.println("id:"+session.getId());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```

#### 3.4.2 GetValueServlet.java

```java
package com.package com.zssea.servlet.demo3_servletProject.servlet.controller;
.servlet.demo5_sessions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "GetValueServlet",value = "/getvalue")
public class GetValueServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.通过request对象获取session
        HttpSession session = request.getSession();
        //2.使用session获得数据
        String username = (String)session.getAttribute("username");

        //request获得数据
        String password = (String)request.getParameter("password");

        System.out.println("从session中获得数据："+username);
        System.out.println("从request中获得数据："+password);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
/*结果：
id:DCED407FD55002F10EB8BBB05C4123FD
从session中获得数据：xiaoming
从request中获得数据：null
*/
```

### 3.5Session的生命周期

- 开始：第一次使用到Session的请求产生，则创建Session
- 结束：
  - 浏览器关闭，则失效 （自动失效）
  - Session超时，则失效
    - session.setMaxInactivelnterval(seconds);//设置最大有效时间(单位：秒) 。
  - 手工销毁，则失效
    - session.invalidate();//登录退出、注销

#### 3.5.1 Session 失效

```java
session.setMaxInactiveInterval(60*60) //设置session最大有效期为一小时 
session.invalidate();//手工销毁
```

### 3.6 Session实战权限验证

- 在之前（121 Servlet_4 _Servlet详解与应用【重点】 2.3 综合案例(Servlet + JDBC)）的基础上编写的

| Session记录登录状态                                          |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/2020080819431878.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |

#### 3.6.1创建管理员表

```sql
CREATE TABLE manager()
	username VARCHAR(20) PRIMARY KEY,
    password VARCHAR(20) NOT NULL 
)charset=utf8;
```

#### 3.6.2管理员登录页面

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>管理员登录页面</title>
</head>
<body>
    <form action="/javaweb_servlet_war_exploded/loginMgr" method="post">
        <label>用户名: </label><input type="text" name="username"/><br/>
        <label>密码: </label><input type="password" name="password"/><br/>
        <input type="submit" value="登录"/>
    </form>
</body>
</html>
```

#### 3.6.3 LoginMgrController

```java
package com.package com.zssea.servlet.demo3_servletProject.servlet.controller;
.servlet.demo3_servletProject.servlet.controller;

import com.package com.zssea.servlet.demo3_servletProject.servlet.controller;
.servlet.demo3_servletProject.entity.Manager;
import com.package com.zssea.servlet.demo3_servletProject.servlet.controller;
.servlet.demo3_servletProject.service.ManagerService;
import com.package com.zssea.servlet.demo3_servletProject.servlet.controller;
.servlet.demo3_servletProject.service.impl.ManagerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginMgrController" ,value="/loginMgr")
public class LoginMgrController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.处理乱码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //2.收参
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //3.调用业务逻辑
        ManagerService managerService = new ManagerServiceImpl();
        Manager manager = managerService.login(username, password);

        //4.处理结果，跳转流程
        if(manager != null){
            //管理员登录成功，将管理员信息存入session
            HttpSession session = request.getSession();
            session.setAttribute("manager",manager);
            //跳转，目标，方式(用重定向跳转到  ShowAllAdminController,不需要传递数据)
            response.sendRedirect("/javaweb_servlet_war_exploded/showallcontroller");
        }else {
            //管理员登录失败，重新登录
            response.sendRedirect("/javaweb_servlet_war_exploded/LoginMgr.html");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```

#### 3.6.4 ShowAllAdminController

```java
package com.package com.zssea.servlet.demo3_servletProject.servlet.controller;
.servlet.demo3_servletProject.servlet.controller;

import com.package com.zssea.servlet.demo3_servletProject.servlet.controller;
.servlet.demo3_servletProject.entity.Admin;
import com.package com.zssea.servlet.demo3_servletProject.servlet.controller;
.servlet.demo3_servletProject.entity.Manager;
import com.package com.zssea.servlet.demo3_servletProject.servlet.controller;
.servlet.demo3_servletProject.service.AdminService;
import com.package com.zssea.servlet.demo3_servletProject.servlet.controller;
.servlet.demo3_servletProject.service.impl.AdminServiceImpl;

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
        //通过HTTPsession完成权限控制
        HttpSession session = req.getSession();
        Manager manager = (Manager)session.getAttribute("manager");
        if(manager != null){ //表明已登录
            //只调用业务逻辑
            AdminService adminService = new AdminServiceImpl();
            List<Admin> admins = adminService.showAllAdmin();

            //request作用存储数据
            req.setAttribute("admins",admins);
            //通过转发 跳转到显示页面（同时存储的数据也可以通过作用域被获得）
            req.getRequestDispatcher("/showalljsp").forward(req,resp);
        }else {//表未登录，跳转登录页面
            resp.sendRedirect("/javaweb_servlet_war_exploded/LoginMgr.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
```

### 3.7 Session实战保存验证码

#### 3.7.1创建验证码

- 导入ValidateCode.jar
- 创建生成验证码的Servlet

```java
package com.zssea.servlet.demo3_servletProject.servlet.controller;

import cn.dsna.util.images.ValidateCode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CreateCode",value = "/createcode")
public class CreateCode extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.创建验证码图片
        ValidateCode code = new ValidateCode(200, 30, 4, 20);

        String codes = code.getCode();//获得生成的验证码的文字内容
        HttpSession session = request.getSession();
        session.setAttribute("codes",codes); //将验证码的文字内容存入session，用于在LoginMgrController做验证
        //2.响应给客户端
        code.write(response.getOutputStream());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doPost(request,response);
    }
}
```

#### 3.7.2登录页面

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>管理员登录页面</title>
</head>
<body>
    <form action="/javaweb_servlet_war_exploded/loginMgr" method="post">
        <label>用户名: </label><input type="text" name="username"/><br/>
        <label>密码: </label><input type="password" name="password"/><br/>
        <!-- img 的src路径指向的是生成验证码的servlet的访问路径-->
        <label>验证码: </label><input type="text" name="inputVcode"/><img src="/javaweb_servlet_war_exploded/createcode"/><br/>
        <input type="submit" value="登录"/>
    </form>
</body>
</html>
```

#### 3.7.3 LoginMgrController

```java
package com.zssea.servlet.demo3_servletProject.servlet.controller;

import com.zssea.servlet.demo3_servletProject.entity.Manager;
import com.zssea.servlet.demo3_servletProject.service.ManagerService;
import com.zssea.servlet.demo3_servletProject.service.impl.ManagerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginMgrController" ,value="/loginMgr")
public class LoginMgrController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.处理乱码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //2.收参
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String inputVcode = request.getParameter("inputVcode");//得到客户端输入的验证码

        String codes = (String)request.getSession().getAttribute("codes");//获得CreateCode 生成的验证码的内容
        if(inputVcode!=null && inputVcode.equalsIgnoreCase(codes)){//不区分大小写对比,验证码正确
            //3.调用业务逻辑
            ManagerService managerService = new ManagerServiceImpl();
            Manager manager = managerService.login(username, password);

            //4.处理结果，跳转流程
            if(manager != null){
                //管理员登录成功，将管理员信息存入session
                HttpSession session = request.getSession();
                session.setAttribute("manager",manager);
                //跳转，目标，方式(用重定向跳转到  ShowAllAdminController,不需要传递数据)
                response.sendRedirect("/javaweb_servlet_war_exploded/showallcontroller");
            }else {
                //管理员登录失败，重新登录
                response.sendRedirect("/javaweb_servlet_war_exploded/LoginMgr.html");
            }
        }else{//验证码错误 ，重新登录
            response.sendRedirect("/javaweb_servlet_war_exploded/LoginMgr.html");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```

### 四、浏览器禁用Cookie解决方案【了解】

#### 4.1浏览器禁用Cookie的后果

- 服务器在默认情况下，会使用Cookie的方式将sessionlD发送给浏览器，如果用户禁止Cookie，则sessionlD不会被浏览器保存，此时，服务器可以使用如URL重写这样的方式来发送sessionID。

#### 4.2URL重写

- 浏览器在访问服务器上的某个地址时，不再使用原来的那个地址，而是使用经过改写的地址（即在原来的地址后面加上了 sessionID)

#### 4.3实现URL重写

- response.encodeRedirectURL(String url)生成重写的URL。

```java
package com.zssea.servlet.demo5_sessions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LifeSessionServlet", value = "/lifesession")
public class LifeSessionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //session.setMaxInactiveInterval(10); //设置有效期为10秒

        //浏览器 cookie被禁用，服务器可以使用如URL重写这样的方式来发送sessionID。
        String url = response.encodeRedirectURL("/javaweb_servlet_war_exploded/getsession");
        System.out.println(url);
        response.sendRedirect(url);
        System.out.println("id："+session.getId());

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```