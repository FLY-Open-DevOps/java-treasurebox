## 一、SpringMVC

![](https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2056956485,2428868870&fm=26&gp=0.jpg)

### 1.1引言

- java开源框架，Spring Framework的一个独立模块。
- MVC框架，在项目中开辟MVC层次架构
- 对控制器中的功能包装简化扩展践行工厂模式，功能架构在工厂之上

### 1.2 MVC架构

#### 1.2.1概念

| 名称       | 职责                                                         |
| ---------- | ------------------------------------------------------------ |
| Model      | 模型：即业务模型，负责完成业务中的数据通信处理，对应项目中的service和dao |
| View       | 视图：渲染数据，生成页面。对应项目中的 Jsp                   |
| Controller | 控制器：直接对接请求，控制MVC流程，调度模型，选择视图。对应项目中的Servlet |

#### 1.2.2好处

- MVC是现下软件开发中的最流行的代码结构形态;
- 人们根据负责的不同逻辑，将项目中的代码分成MVC3个层次;
- 层次内部职责单一，层次之间耦合度低;
- 符合低耦合高内聚的设计理念。也实际有利于项目的长期维护。

## 二、开发流程

### 2.1导入依赖

```xml
		<dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.6.RELEASE</version>
        </dependency>
```

### 2.2配置核心(前端)控制器

- 作为一个MVC框架，首先要解决的是：如何能够收到请求!
- 所以MVC框架大都会设计一款前端控制器，选型在Servlet或Filter两者之一,在框架最前沿率先工作，接收所有请求。(这里是选择Servlet，这是一个SpringMVC帮我们写好的)
- 此控制器在接收到请求后，还会负责springMVC的核心的调度管理，所以既是前端又是核心。
- web.xml ：

```xml
	 <servlet>
        <servlet-name>mvc_zssea</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
     	<!-- 局部参数，声明配置文件位置-->
     	<init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:mvc.xml</param-value>
        </init-param>
        <!-- 懒汉式  饿汉式加载 （servlet启动时刻） 可选-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>mvc_zssea</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

### 2.3后端控制器

- 等价于之前定义的Servlet

```java
package com.zssea.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //声明是后端控制器 ，（应该就是<bean> 标签的注解）
@RequestMapping("/hello") //这是访问路径
public class HelloController {

    @RequestMapping("/test01")
    public String hello01(){ //此方法可以理解为之前在servlet中写的service dopost  doget方法
        System.out.println("hello world 1");
        return null;
    }

    @RequestMapping("/test02")
    public String hello02(){ 
        System.out.println("hello world 2");
        return null;
    }
}
```

### 2.4配置文件（mvc.xml）

- 默认名称：核心控制器名-servet.xml 默认位置: WEB-INF
- 随意名称：mvc.xml 随意位置: resources 但需要配置在核心(前端)控制器中
- mvc.xml 文件：

```xml
<beans 	xmlns="http://www.springframework.org/schema/beans"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:mvc="http://www.springframework.org/schema/mvc"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
							http://www.springframework.org/schema/beans/spring-beans.xsd
							http://www.springframework.org/schema/context
							http://www.springframework.org/schema/context/spring-context.xsd
							http://www.springframework.org/schema/mvc
							http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 注解扫描 -->
    <context:component-scan base-package="com.qf.web"/>

    <!-- 注解驱动 -->
    <mvc:annotation-driven></mvc:annotation-driven>

    <!-- 视图解析器
	     作用：1.捕获后端控制器的返回值="hello"
	          2.解析： 在返回值的前后 拼接 ==> "/hello.jsp"
	 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!-- 前缀 -->
        <property name="prefix" value="/"></property>
        <!-- 后缀 -->
        <property name="suffix" value=".jsp"></property>
    </bean>
</beans>
```

### 2.5访问

```java
http://localhost:8080/SpringMVC01/hello/test01
http://localhost:8080/SpringMVC01/hello/test02
```

### 2.6过程分析

当我们项目启动时， （web.xml）前端控制器会启动，它启动时会加载mvc.xml文件，启动SprignMVC工厂，生产配置好的bean，扫描注解，后端控制器就创建出来；（就是项目启动时，配置文件就加载好了，就等着用户访问了）之后，用户请求，先经过前端控制器处理，再转发到后端控制器上，匹配访问路径，执行对应的后端控制器的方法，再根据返回值（通过视图解析）跳转到对应的jsp。

## 三、接收请求参数

- 通过控制器中方法的形参，接收请求参数

### 3.1基本类型参数

- 请求参数 和 方法的形参 同名即可
- springMVC默认可以识别的日期字符串格式为: YYYY/MM/dd HH:mm:ss通过@DateTimeFormat可以修改默认日志格式

```java
package com.zssea.web;

import com.zssea.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/param")
public class ParamController {

    // http://xxxx/param/test01?id=1&name=shine&gender=true&birth=2020/11/11 12:20:30
    @RequestMapping("/test1")
    public String test01(Integer id, String name, Boolean gender, Date birth){
        System.out.println(id);
        System.out.println(name);
        System.out.println(gender);
        System.out.println(birth);
        return "hello";
    }
}
/*
1
shine
true
Wed Nov 11 12:20:30 CST 2020
*/
```

### 3.2实体收参【重点】

- 请求参数 和 实体的属性 同名即可

```java
package com.zssea.web;

import com.zssea.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/param")
public class ParamController {

    // http://xxxx/param/test1?id=1&name=zhangsan&gender=true&birth=2020/12/12 12:13:20
    //请求参数 和 实体的属性 同名
    @RequestMapping("/test2")
    public String test2(User user){
        System.out.println("test2");
        System.out.println(user);
        return "hello";
    }
}
/*
test2
User{id=1, name='zhangsan', gender=true, birth=Wed Nov 11 12:20:30 CST 2020}
*/


package com.zssea.entity;

import java.util.Arrays;
import java.util.Date;

public class User {
    private Integer id;
    private String name;
    private Boolean gender;
    private Date birth;
	// get  set  toString() ......
}
```

### 3.3数组收参

- 简单类型的数组

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>param</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/param/test3">
        id:<input type="text" name="id"> <br>
        name:<input type="text" name="name"><br>
        gender:<input type="text" name="gender"><br>
        birth:<input type="text" name="birth"><br>
        <input type="checkbox" name="hobby" value="football">足球
        <input type="checkbox" name="hobby" value="basketball">篮球
        <input type="checkbox" name="hobby" value="volleyball">排球 <br>
        <input type="submit" value="提交">
    </form>
</body>
</html>


package com.zssea.web;

import com.zssea.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/param")
public class ParamController {

    // http://xxxx/param/test3?hobby=football&hobby=basketball&hobby=volleyball
    // 请求路径中有多个重名的参数，这些参数可以存储到一个数组中
    @RequestMapping("/test3")
    public String test3(String[] hobby) {
        System.out.println("test3");
        for (String s : hobby) {
            System.out.println(s);
        }
        return "hello";
    }

     // http://xxxx/param/test3?hobby=football&hobby=basketball&hobby=volleyball
    // 请求路径中有多个重名的参数，这些参数可以存储到一个数组中
    @RequestMapping("/test3")
    public String test3(User user) {
        System.out.println("test3");
        System.out.println(user);
        return "hello";
    }
}
/*
test3
football
basketball
volleyball
*/

/*
test3
User{id=10, name='zssea', gender=false, birth=Wed Nov 11 12:20:30 CST 2020, hobby=[football, basketball, volleyball]}

*/
```

### 3.4集合收参【了解】

```java
package com.zssea.entity;

import java.util.List;

public class UserList {
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
    private List<User> users;
}


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>param</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/param/test4">
        id:<input type="text" name="users[0].id"> <br>
        name:<input type="text" name="users[0].name"><br>
        gender:<input type="text" name="users[0].gender"><br>
        <hr>
        id:<input type="text" name="users[1].id"> <br>
        name:<input type="text" name="users[1].name"><br>
        gender:<input type="text" name="users[1].gender"><br>
        <input type="submit" value="提交">
    </form>
</body>
</html>

            
package com.zssea.web;

import com.zssea.entity.User;
import com.zssea.entity.UserList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.nio.cs.US_ASCII;

import java.util.Date;

@Controller
@RequestMapping("/param")
public class ParamController {
    // http://xxx/param/test4?users[0].id=1&users[0].name=shine&users[0].gender=true&users[1].id=2&users[1].name=zhangsan
    @RequestMapping("/test4")
    public String test4(UserList userList){
        System.out.println("test4");
        for (User user : userList.getUsers()) {
            System.out.println(user);
        }
        return "hello";
    }
}
/*
test4
User{id=12, name='wal', gender=false, birth=null, hobby=null}
User{id=13, name='zssea', gender=true, birth=null, hobby=null}
*/
```

### 3.5路径参数

```java
package com.zssea.web;

import com.zssea.entity.User;
import com.zssea.entity.UserList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.nio.cs.US_ASCII;

import java.util.Date;

@Controller
@RequestMapping("/param")
public class ParamController {
    // http://localhost:8080/SpringMVC01/param/test5/2
    // {id} 命名路径
    // {id} 等价于*   /test5/1   test5/2   test5/xxxx
    @RequestMapping("/test5/{id}")
    public String test5( @PathVariable("id")  Integer id){
        System.out.println("test5");
        System.out.println("id:"+id);
        return "hello";
    }
}
/*
test5
id:2
*/
```

### 3.6中文乱码

- 首先，页面中字符集统一

  ```jsp
  JSP 文件要有: <%@page pageEncoding="utf-8" %>
  HTML 文件要有: <meta charset= "UTF-8">
  ```

- 其次，tomcat中字符集设置，对get请求中， 中文参数乱码有效

  ```java
  Tomcat配置: URIEncoding=utf-8
  ```

- 最后，设置此filter，对post请求中，中文参数乱码有效

  ```xml
  <!-- 此过滤器会进行：request.setCharactorEncoding("utf-8"); -->
      <filter>
        <filter-name>encoding</filter-name>
          <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
          <init-param>
              <param-name>encoding</param-name>
              <param-value>utf-8</param-value>
          </init-param>
      </filter>
      <filter-mapping>
          <filter-name>encoding</filter-name>
          <url-pattern>/*</url-pattern>
      </filter-mapping>
  ```

## 四、跳转

跳转关键字：forward：(转发) ; redirect:（重定向）

### 4.1转发

```java
package com.zssea.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jump")
public class ForwardController {

    @RequestMapping("/test1")
    public String test1(){
        System.out.println("test1");
        return "hello"; // 转发,这是转发跳转(经过视图解析器)
        //return "forward:/hello.jsp";  //如果返回这个就不会经过视图解析器解析
    }

    @RequestMapping("/test2")
    public String test2(){
        System.out.println("test2");
        //return "forward:/jump/test1"; //（绝对地址）转发
        return "forward:test1";//相对路径 （这个test1 是当前类中的test1），在本类中可以用相对地址，如果要跳转到另外一个类就要用绝对地址
    }

}
```

### 4.2重定向

```java
package com.zssea.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jump")
public class ForwardController {

    @RequestMapping("/test3")
    public String test3(){
        System.out.println("test3");
        return "redirect:/hello.jsp"; // 重定向到helo.jsp
    }

    @RequestMapping("/test4")
    public String test4(){
        System.out.println("test4");
        return "redirect:test3";   //重定向到test3（相对地址）
        //return "redirect:/jump/test3"
    }

    @RequestMapping("/query")
    public String test5(){
        System.out.println("querr数据1");
        return "forward:test1";  //转发
        //return "redirect:test1";  //重定向
    }
}
```

### 4.3跳转细节

- 在增删改之后，为了防止请求重复提交，重定向跳转
- 在查询之后，可以做转发跳转

## 五、传值

- C得到数据后，跳转到V，并向V传递数据。进而V中可以渲染数据，让用户看到含有数据的页面
- 转发跳转：Request作用域
- 重定向跳转： Session作用域

### 5.1 Request和Session

```java
package com.zssea.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/data")
public class DataController {

    @RequestMapping("/test1")
    public String test1(HttpServletRequest request, HttpSession session){
        request.setAttribute("name","zssea");
        session.setAttribute("age",10);
        return "data";
    }
}
```

### 5.2 JSP中取值

- 建议：重点复习EL JSTL

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>data</title>
</head>
<body>
    name:${requestScope.name} <br>
    age:${sessionScope.age}
</body>
</html>
```

### 5.3 Model

```java
package com.zssea.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/data")
@SessionAttributes(names = {"city","street"})
public class DataController {

    @RequestMapping("/test2")
    public String test2(Model model){
        model.addAttribute("gender",true); //Model 默认的会把数据存放到request 域中
        return "data2";
    }

}


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>data2</title>
</head>
<body>
    gender:${requestScope.gender} <br>
</body>
</html>
```

### 5.4 ModelAndView

```java
package com.zssea.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/data")
public class DataController {

    @RequestMapping("/test4")
    public ModelAndView test4(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("claz","001班");
        modelAndView.setViewName("forward:/hello.jsp");
        return modelAndView;
    }
}


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello</title>
</head>
<body>
    Hello World!  <br>
    claz:${requestScope.claz}
</body>
</html>
```

### 5.5 @SessionAttributes

- @SessionAttributes({“gender”,“name”}) : model中的 name和gender会存入session中
- SessionStatus 移除session

```java
package com.zssea.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/data")
@SessionAttributes(names = {"city","street"})
public class DataController {


    @RequestMapping("/test2")
    public String test2(Model model){
        model.addAttribute("gender",true); //Model 默认的会把数据存放到request 域中
        model.addAttribute("city","北京");
        model.addAttribute("street","长安街"); //这两个数据会存放在session域中，因为类名上面定义了@SessionAttributes(names = {"city","street"})
        return "data2";
    }

    @RequestMapping("/test3")
    public String test3(SessionStatus sessionStatus){
        //清空所有 由model存入session域的数据
        sessionStatus.setComplete();
        return "data2";
    }

}


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>data2</title>
</head>
<body>
    gender:${requestScope.gender} <br>
    city:${sessionScope.city} <br>
    street:${sessionScope.street} <br>
</body>
</html>
```

## 六、静态资源

### 6.1静态资源问题

- 静态资源: html, js文件， css文件，图片文件
- 静态文件没有url-pattern,所以默认是访问不到的，之所以可以访问，是因为，tomcat中有 一个全局的servlet:org.apache.catalina.servlets.DefaultServlet，它的url-pattern是 “/”, 是全局默认的Servlet.所以每个项目中不能匹配的静态资源的请求，有这个Servlet来处理即可。
- 但， 在SpringMVC中DispatcherServlet（前端控制器）也采用了 “/” 作为url-pattern,则项目中不会再使用全局的Serlvet,则静态资源不能完成访问。

### 6.2解决方案1

- DispathcerServlet采用其他的url-pattern
- 此时，所有访问handler的路径都要以action结尾! !

```xml
<servlet>
        <servlet-name>mvc_zssea</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 局部参数，声明mvc配置文件位置-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:mvc.xml</param-value>
        </init-param>
        <!-- 懒汉式  饿汉式加载  可选-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>mvc_zssea</servlet-name>
        <!--<url-pattern>/</url-pattern>-->
        <url-pattern>*.action</url-pattern>
    </servlet-mapping>
```

### 6.3解决方案2

- DispathcerServlet的url-pattern依然采用"/",但追加配置
- 在mvc.xml 中

```xml
 <!--
      会额外的增加一个handler(就是Controller中的一个方法)，且其requestMapping:  "/**" 可以匹配所有请求，但是优先级最低
      所以如果其他所有的handler都匹配不上，请求会转向 "/**" ,恰好，这个handler就是处理静态资源的
      处理方式：将请求转发到tomcat中名为default的Servlet

      RequestMapping  /*   /a   /b  /c   /dxxxx    /a/b
                      /**
     -->
    <mvc:default-servlet-handler/>
```

- web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!-- SpringMVC前端(核心)控制器
         1. 前端，接收所有请求
         2. 启动SpringMVC工厂  mvc.xml
         3. 负责springMVC流程调度
    -->
    <servlet>
        <servlet-name>mvc_zssea</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 局部参数，声明mvc配置文件位置-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:mvc.xml</param-value>
        </init-param>
        <!-- 懒汉式  饿汉式加载  可选-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>mvc_zssea</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

</web-app>
```

- 这样就可以访问静态资源了

### 6.4解决方案3

- mapping是访问路径，location是静态资源存放的路径
- 将/html/ ** 中/ **匹配到的内容， 拼接到/hhh/后 http://…/html/a.html 访问/hhh/a.html

```xml
<mvc:resources mapping="/html/**" location="/page/"/>
<mvc:resources mapping="/css/**" location="/css/"/>
```

## 七、Json处理

### 7.1导入依赖

```xml
<!-- Jackson springMVC默认的Json解决方案选择是Jackson, 所以只需要导入jackson的jar,即可使用。-->
<!-- Jackson -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.9</version>
        </dependency>
```

### 7.2使用@ResponseBody

```java
package com.zssea.web;

import com.zssea.entity.User;
import com.zssea.entity.User2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/json")
public class JsonController {

    @RequestMapping("/test1")
    @ResponseBody //这个注解的意思是将这个handler的返回值自动转成json，并响应给客户端
    public User test1(){
        System.out.println("test1");
        User user = new User(1,"张三",new Date());
        return user;
    }

    @RequestMapping("/test2")
    @ResponseBody //这个注解的意思是将这个handler的返回值自动转成json，并响应给客户端
    public List<User> test2(){
        System.out.println("test2");
        User user = new User(1,"张三",new Date());
        User user2 = new User(2,"李四",new Date());
        List<User> users = Arrays.asList(user, user2);
        return users;
    }

    @RequestMapping(value = "/test3",produces = "text/html;charset=utf-8")//produces防止中文乱码

    @ResponseBody //如果返回值是 String 类型，就将字符串显示出来，而不是跳转
    public String test3(){
        System.out.println("test3");
        return "你好";
    }
}
```

### 7.3使用@RestController

- Controller类上加了@RestControllr注解，等价于在类中的每个方法上都加了@ResponseBody

```java
package com.zssea.web;

import com.zssea.entity.User;
import com.zssea.entity.User2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//@Controller
@RequestMapping("/json")
@RestController //这个注解如果写了 @Controller 和 类中的每个方法的 @ResponseBody 都不用加了
public class JsonController {

    @RequestMapping("/test1")
    //@ResponseBody //这个注解的意思是将这个handler的返回值自动转成json，并响应给客户端
    public User test1(){
        System.out.println("test1");
        User user = new User(1,"张三",new Date());
        return user;
    }

    @RequestMapping("/test2")
    //@ResponseBody //这个注解的意思是将这个handler的返回值自动转成json，并响应给客户端
    public List<User> test2(){
        System.out.println("test2");
        User user = new User(1,"张三",new Date());
        User user2 = new User(2,"李四",new Date());
        List<User> users = Arrays.asList(user, user2);
        return users;
    }

    @RequestMapping(value = "/test3",produces = "text/html;charset=utf-8") //produces防止中文乱码

    //@ResponseBody //如果返回值是 String 类型，就将字符串显示出来，而不是跳转
    public String test3(){
        System.out.println("test3");
        return "你好";
    }
}
```

### 7.4使用@RequestBody

- @RequestBody,接收Json参数

#### 7.4.1定义Handler

```java
package com.zssea.entity;

public class User {
    private Integer id;
    private String name;
    private Date birth;
	//set get 
}

123456789
package com.zssea.web;

@Controller
@RequestMapping("/json")
public class JsonController {

    @RequestMapping("/test4")
    public String test4(@RequestBody User user){ //@RequestBody 是指将接收的json数据转成User ,post方式提交的(将请求体中的json数据转换为java对象)
        System.out.println(user);
        return "ok";
    }
}
```

#### 7.4.2Ajax发生json

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>json</title>
    <script src="${pageContext.request.contextPath}/js/jquery-2.1.0.js"></script>
</head>
<body>
    <input type="button" value="ajax" οnclick="send_json();"/>
    <script>
        function send_json() {
            var user = {id:1,name:"zssea"};
            var userJSON = JSON.stringify(user);   //转换js对象成json
            $.ajax({
                url:"${pageContext.request.contextPath}/json/test4",
                type:"post",
                data:userJSON,
                contentType:"application/json",  //声明请求参数类型为json

                success:function (ret) {
                    alert(ret);
                }
            })
        }
    </script>
</body>
</html>
```

### 7.5 Jackson常用注解

#### 7.5.1日期格式化

- @JsonFormat(pattern=“yyy-MM-dd HH:mm:ss”,timezone = “GMT+8”)

```java
package com.zssea.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
public class User {

    private Integer id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //日期格式化
    private Date birth;

    private List<String> hobby;

    private Double salary = 10000.126;
	//set get
}
```

#### 7.5.2属性名修改

- @JsonProperty(“new_ name”)

```java
package com.zssea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zssea.serialize.MySerializer;

import java.util.Date;
import java.util.List;

public class User {

    @JsonProperty("id2") //修改属性名 ,json数据显示的为id2
    private Integer id;

    @JsonIgnore //json格式数据中 忽略这个属性
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //日期格式化
    private Date birth;

    //@JsonInclude(JsonInclude.Include.NON_NULL) //如果这个属性值为null，就不输出
    @JsonInclude(JsonInclude.Include.NON_EMPTY) //这个属性要有值，如果为null或者 size = 0 也不输出
    private List<String> hobby;// null   size=0

    @JsonSerialize(using = MySerializer.class)
    private Double salary = 10000.126;//在输出此属性时，使用MySerializer输出

    //set get
}
```

#### 7.5.3属性忽略

- @Jsonignore

```java
package com.zssea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zssea.serialize.MySerializer;

import java.util.Date;
import java.util.List;

public class User {

    private Integer id;

    @JsonIgnore //json格式数据中 忽略这个属性
    private String name;

    private Date birth;
    private List<String> hobby;
    private Double salary = 10000.126;

    //set get
}
```

#### 7.5.4 null和empty属性排除

- Jackson默认会输出null值的属性，如果不需要，可以排除。
- @Jsonlnclude(JsonInclude.Include.NON_NULL) //null值 属性不输出
- @ JsonInclude(value= Jsonlnclude.Include.NON_EMPTY) // empty属性
  不输出(空串，长度为0的集合，null值)

```java
package com.zssea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zssea.serialize.MySerializer;

import java.util.Date;
import java.util.List;

public class User {

    private Integer id;
    private String name;
    private Date birth;

    //@JsonInclude(JsonInclude.Include.NON_NULL) //如果这个属性值为null，就不输出
    @JsonInclude(JsonInclude.Include.NON_EMPTY) //这个属性要有值，如果为null或者 size = 0 就不输出
    private List<String> hobby;// null   size=0

    private Double salary = 10000.126;

    //set get
}
```

#### 7.5.5自定义序列化

- @JsonSerialize(using = MySerialzr.class) //使用MySerializer输出某属性

```java
package com.zssea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zssea.serialize.MySerializer;

import java.util.Date;
import java.util.List;

public class User {

    private Integer id;
    private String name;
    private Date birth;
    private List<String> hobby;// null   size=0

    @JsonSerialize(using = MySerializer.class)
    private Double salary = 10000.126;//在输出此属性时，使用MySerializer输出

    //set get
}


package com.zssea.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class MySerializer extends JsonSerializer<Double> {
    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // 将Double salary的值 四舍五入
        String number = BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        // 输出 四舍五入后的值
        gen.writeNumber(number);
    }
}
```

### 7.6 FastJson

- 如果不想使用Jackson,则也可以安装其他的Json处理方案: FastJson

#### 7.6.1导入依赖

```xml
 		<!-- FastJson -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.58</version>
        </dependency>
```

#### 7.6.2安装FastJson

- mvc.xml文件：

```xml
  	 <!-- 注解驱动 -->
    <mvc:annotation-driven>
        <!-- 安装FastJson,转换器 -->
        <mvc:message-converters>
            <bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter">
                <!-- 声明转换类型:json (将java对象转成josn)-->
                <property name="supportedMediaTypes">
                    <list>
                        <value>application/json</value>
                    </list>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
```

#### 7.6.3使用

- @ResponseBody @RequestBody @RestController使用方法不变

#### 7.6.4常用注解

- 日期格式化: @JSONField(format=“yyyy/MM/dd”)
- 属性名修改: @JSONField(name=“birth”)
- 忽略属性: @JSONField(serialize = false)
- 包含null值: @JSONField(serialzeFeatures = SerializerFeature .WriteMapNullValue)默认会忽略所有null值,有此注解会输出null
  - @JSONFieldlserialzeFeatures = SerializerFeature.WriteUullstringAsEmpty) null的String输出为"“
- 自定义序列化: @JSONField(serializeUsing = MySrializer.cass)

```java
package com.zssea.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zssea.serialize.MySerializer2;

import java.util.Date;

public class User2 {

    @JSONField(serialize = false)
    private Integer id; //忽略id

    @JSONField(name="NAME",serialzeFeatures = SerializerFeature.WriteNullStringAsEmpty)
    private String name;// 修改属性名为NAME , 当name为空时，输出一个空串

    @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private String city;// 如果为空，输出null

    @JSONField(format="yyyy/MM/dd")
    private Date birth; //日期格式化

    @JSONField(serializeUsing = MySerializer2.class)
    private Double salary; // 序列化 拼接“ 元 ”

	// 构造 set get tostring
}


package com.zssea.serialize;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class MySerializer2 implements ObjectSerializer {

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType,
                      int features) throws IOException {
        Double value = (Double) object; // salary属性值
        String text = value + "元";// 在salary后拼接 “元”
        serializer.write(text); // 输出拼接后的内容
    }
}


package com.zssea.web;

import com.zssea.entity.User;
import com.zssea.entity.User2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.nio.cs.US_ASCII;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//@Controller
@RequestMapping("/json")
@RestController //这个注解如果写了 @Controller 和 类中的每个方法的 @ResponseBody 都不用加了
public class JsonController {

    @RequestMapping("/test11")
    public User2 test11(){
        System.out.println("test1");
        User2 user2 = new User2(1,"张三","北京",new Date(),1000.5);
        return user2;
    }

}
/*
{"NAME":"张三","birth":"2020/09/06","city":"北京","salary":"1000.5元"}
*/
```

## 八、异常解析器

### 8.1现有方案，分散处理

- Controller中的每个Handler自己处理异常
- 此种处理方案，异常处理逻辑，分散在各个handler中，不利于集中管理

```java
@Controller
@RequestMapping("/ex")
public class ExController {
    
    @RequestMapping("/xx") //访问路径
    public string xx(){
        try{
            ...
        }catch(Exception1 e){
            e.printStackTrace();
            return"redirect:/xx/error1";
        }catch(Exception2 e){
            e.printStackTrace();
            return "redirect:/xx/error2";
        }
    }
    ...
    ...
}
```

### 8.2异常解析器，统一处理异常

- Controller中的每个Handler不再自己处理异常，而是直接throws所有异常。
- 定义一个“异常解析器”集中捕获处理所有异常
- 此种方案，在集中管理异常方面，更有优势!

```java
package com.zssea.resolver;

import com.zssea.ex.MyException1;
import com.zssea.ex.MyException2;
import com.zssea.ex.MyException3;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 异常解析器
// 任何一个Handler中抛出异常时，它就会执行（将异常处理代码提取出来）
public class MyExceptionResolver implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        ModelAndView modelAndView = new ModelAndView();

        if(e instanceof MyException1){
            // error1.jsp
            modelAndView.setViewName("redirect:/error1.jsp"); //重定向
        }else if(e instanceof MyException2){
            // error2.jsp
            modelAndView.setViewName("redirect:/error2.jsp");
        }else if(e instanceof MyException3){
            // error3.jsp
            modelAndView.setViewName("redirect:/error3.jsp");
        }else if(e instanceof MaxUploadSizeExceededException){
            modelAndView.setViewName("redirect:/uploadError.jsp");
        }

        return modelAndView;
    }
}


<!--mvc.xml文件中-->
    <!--异常解析器-->
    <bean class="com.zssea.resolver.MyExceptionResolver"></bean>
```

## 九、拦截器

### 9.1作用

- 作用：抽取handler中的冗余功能 （使handler中重复的代码只写一遍，实现复用）

### 9.2定义拦截器

- 执行顺序: preHandle–postHandle–afterCompletion

```java
package com.zssea.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyInterceptor implements HandlerInterceptor {

    // 在handler之前执行
    // 再次定义 handler中冗余的功能
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("pre Handle");
        // 判断登录状态
        HttpSession session = request.getSession();
        if(session.getAttribute("state") != null){
            return true; //放行，执行后续的handler
        }
        //中断之前，做出响应
        response.sendRedirect("/login.jsp");

        return false; //中断请求，不再执行后续的handler
    }

    // 在handler之后执行，响应之前执行
    // 改动请求中数据
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("post Handle");
    }

    // 在视图渲染完毕后，
    // 资源回收
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("after Handle");
    }
}
```

### 9.3配置拦截路径

- mvc.xml：

```xml
<!--拦截器配置，指明哪个拦截器拦截哪些路径访问请求-->
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/inter/test1"/>
            <mvc:mapping path="/inter/test2"/>
            <mvc:mapping path="/inter/test*"/> <!--test开头的路径-->
            <!--<mvc:mapping path="/inter/**"/>--><!--任意多级任意路径-->
            <mvc:exclude-mapping path="/inter/login"/><!--不拦截此路径-->
            <bean class="com.zssea.interceptor.MyInterceptor"/><!--拦截器类-->
        </mvc:interceptor>
    </mvc:interceptors>
```

## 十、上传

### 10.1导入jar(依赖)

```xml
 		<dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.6</version>
        </dependency>

        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.4</version>
            <exclusions>
                <exclusion>
                    <groupId>javax.servlet</groupId>
                    <artifactId>servlet-api</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

### 10.2表单

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/upload/test1" method="post" enctype="multipart/form-data">
        file：<input type="file" name="source"/><br>
        <input type="submit" value="上传"/>
    </form>

</body>
</html>
```

### 10.3.上传解析器

- mvc.xml 中追加：

```xml
<!-- 文件上传解析器-->
    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
<!-- 最大可上传的文件大小  单位：byte  超出后会抛出MaxUploadSizeExceededException异常，可以异常解析器捕获 （配置了之后有可能出现状态显示为200，而并没又上传成功，所以我们一般写一个拦截器代替这个功能）-->
        <!--<property name="maxUploadSize" value="1048576"></property>-->
</bean>
```

### 10.4 Handler

```java
package com.zssea.web;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/upload")
public class UploadController {

    @RequestMapping("/test1")
    public String test1(MultipartFile source, HttpSession session) throws IOException {
        System.out.println("tes1");
        // 获取上传文件的 原始名称
        String filename = source.getOriginalFilename();
        //生成一个唯一的文件名
        String uniqueFileName = UUID.randomUUID().toString();
        //获取上传文件的后缀名
        String extension = FilenameUtils.getExtension(filename);
        //拼接得到最终的唯一的文件名
        String uniqueFileName2 = uniqueFileName+"."+extension;

        // 获取上传文件的 类型
        String contentType = source.getContentType();
        System.out.println(filename);
        System.out.println(contentType);

        //保存
        String realPath = session.getServletContext().getRealPath("/upload");
        System.out.println("realPath:" + realPath);
        source.transferTo(new File(realPath+"\\"+uniqueFileName2));
        return "index";
    }
}
```

### 10.5自定义文件上传大小的拦截器

- MyFileUploadInterceptor

```java
package com.zssea.interceptor;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFileUploadInterceptor implements HandlerInterceptor {

    private long maxFileUploadSize;

    public long getMaxFileUploadSize() {
        return maxFileUploadSize;
    }

    public void setMaxFileUploadSize(long maxFileUploadSize) {
        this.maxFileUploadSize = maxFileUploadSize;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断上传文件的大小，1M= 1048576byte
        ServletRequestContext servletRequestContext = new ServletRequestContext(request);
        //获取上传文件的大小
        long length = servletRequestContext.contentLength();
        if(length > maxFileUploadSize){
            throw new MaxUploadSizeExceededException(maxFileUploadSize);
        }
        return true;
    }
}
```

- mvc.xml

```xml
<!--异常解析器-->
    <bean class="com.zssea.resolver.MyExceptionResolver"></bean>

<!--拦截器配置，指明哪个拦截器拦截哪些路径访问请求-->
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/upload/test1"/>
            <bean class="com.zssea.interceptor.MyFileUploadInterceptor">
                <property name="maxFileUploadSize" value="1048576"></property>
            </bean>
        </mvc:interceptor>
    </mvc:interceptors>
```

- 异常处理：MyExceptionResolver (文件过大就重定向到uploadError.jsp)

```java
package com.zssea.resolver;

import com.zssea.ex.MyException1;
import com.zssea.ex.MyException2;
import com.zssea.ex.MyException3;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 异常解析器
// 任何一个Handler中抛出异常时，它就会执行（将异常处理代码提取出来）
public class MyExceptionResolver implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        ModelAndView modelAndView = new ModelAndView();

        if(e instanceof MyException1){
            // error1.jsp
            modelAndView.setViewName("redirect:/error1.jsp"); //重定向
        }else if(e instanceof MyException2){
            // error2.jsp
            modelAndView.setViewName("redirect:/error2.jsp");
        }else if(e instanceof MyException3){
            // error3.jsp
            modelAndView.setViewName("redirect:/error3.jsp");
        }else if(e instanceof MaxUploadSizeExceededException){
            modelAndView.setViewName("redirect:/uploadError.jsp");
        }

        return modelAndView;
    }
}
```

## 十一、下载

### 11.1超链接

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>download</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/download/test1?name=jquery-2.1.0.js">下载</a>
</body>
</html>
```

### 11.2 Handler

```java
package com.zssea.web;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/download")
public class DowmLoadController {

    @RequestMapping("/test1")
    public void test1(String name, HttpSession session, HttpServletResponse response) throws IOException {

        //获取文件存放的地址 （就是下载这个文件夹里的文件）
        String realPath = session.getServletContext().getRealPath("/upload");
        String filePath = realPath + "\\"+ name;

        //设置响应头  告知浏览器，要以附件的形式保存内容   filename=浏览器显示的下载文件名
        response.setHeader("content-disposition","attachment;filename="+name);

        //响应
        IOUtils.copy(new FileInputStream(filePath),response.getOutputStream());

    }
}
```

## 十二、验证码

### 12.1作用

- 防止暴力攻击，前端安全保障

### 12.2导入jar（依赖）

```xml
 		<!-- Kaptcha -->
        <dependency>
            <groupId>com.github.penggle</groupId>
            <artifactId>kaptcha</artifactId>
            <version>2.3.2</version>
            <exclusions>
                <exclusion>
                    <groupId>javax.servlet</groupId>
                    <artifactId>javax.servlet-api</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

### 12.3声明验证码组件

- web.xml :追加

```xml
 <!-- 验证码Servlet 验证码组件-->
    <servlet>
        <servlet-name>cap</servlet-name>
        <servlet-class>com.google.code.kaptcha.servlet.KaptchaServlet</servlet-class>
        <init-param>
            <param-name>kaptcha.border</param-name>
            <param-value>no</param-value>
        </init-param>
        <init-param>
            <param-name>kaptcha.textproducer.char.length</param-name>
            <param-value>4</param-value>
        </init-param>
        <init-param>
            <param-name>kaptcha.textproducer.char.string</param-name>
            <param-value>abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789</param-value>
        </init-param>
        <init-param>
            <param-name>kaptcha.background.clear.to</param-name>
            <param-value>211,229,237</param-value>
        </init-param>
        <init-param>
            <!-- session.setAttribute("captcha","验证码") 存入session，便于校验-->
            <param-name>kaptcha.session.key</param-name>
            <param-value>captcha</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>cap</servlet-name>
        <url-pattern>/captcha</url-pattern>
    </servlet-mapping>
```

#### 12.4page

- capcha.jsp：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>验证码</title>
</head>
<body>

    <form action="${pageContext.request.contextPath}/captcha/test1">
        <img id="cap" src="${pageContext.request.contextPath}/captcha" style="width:100px" οnclick="refresh();">
        <input type="text" name="captcha"/>
        <br>
        <input type="submit" value="提交">
    </form>

    <script>
        function refresh(){
            var img = document.getElementById("cap");
            img.src="${pageContext.request.contextPath}/captcha?"+new Date().getTime()
        }
    </script>
</body>
</html>
```

- CapchaController

```java
package com.zssea.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/captcha")
public class CapchaController {

    @RequestMapping("/test1")
    public String test1(String captcha, HttpSession session){
        //验证码组件生成的验证码
        String realCaptcha = (String)session.getAttribute("captcha");
        //校验
        if(realCaptcha.equalsIgnoreCase(captcha)){
            return "index";
        }
        return "error1";
    }
}
```

## 十三、REST

### 13.1开发风格

- 是一种开发风格，遵从此风格开发软件，符合REST风格，则RESTFUL。
- 两个核心要求:
  - 每个资源都有唯一的标识(URL)
  - 不同的行为，使用对应的http-method

| 访问标识                                 | 资源            |
| ---------------------------------------- | --------------- |
| http://localhost:8080/xxx/users          | 所有用户        |
| http://localhost:8080/xxx/users/1        | 用户1           |
| http://localhost:8080/xxx/users/1/orders | 用户1的所有订单 |

| 请求方式 | 标识                                     | 意图                        |
| -------- | ---------------------------------------- | --------------------------- |
| GET      | http://localhost:8080/xxx/users          | 查询所有用户                |
| POST     | http://localhost:8080/xxx/users          | 在所有用户中增加一个        |
| PUT      | http://localhost:8080/xxx/users          | 在所有用户中修改一个        |
| DELETE   | http://localhost:8080/xxx/users/1        | 删除用户1                   |
| GET      | http://localhost:8080/xxx/users/1        | 查询用户1                   |
| GET      | http://localhost:8080/xxx/users/1/orders | 查询用户1的所有订单         |
| POST     | http://localhost:8080/xxx/users/1/orders | 在用户1的所有订单中增加一个 |

## 13.2优点

- 输出json:

### 13.3使用

#### 13.3.1定义Rest风格的Controller

- @RequestMapping(value="/users",method = RequestMethod.GET)
  等价 @GetMapping("/users")

```java
package com.zssea.web;

import com.zssea.entity.User;
import org.springframework.web.bind.annotation.*;
import sun.nio.cs.US_ASCII;

import java.util.Arrays;
import java.util.List;

/**
 *  查询： 所有用户
 *        查询id=xx 某一个用户
 *  删除： id=xx 某一个用户
 *  增加： 在所有用户中 增加一个
 *  修改： 在所有用户中 修改一个
 *
 *  资源： 所有用户    /users
 *        id=xx 某个用户   /users/{id}
 */
@RestController  //这个注解如果写了 @Controller 和 类中的每个方法的 @ResponseBody 都不用加了
public class MyRestController {

    @GetMapping("/users") //必须是get请求这个路径，才能访问到
    public List<User> queryUsers(){
        System.out.println("query users with get");
        User user1 = new User(1, "张三");
        User user2 = new User(2, "李四");
        return Arrays.asList(user1,user2);
    }

    @GetMapping("/users/{id}") //必须是get请求这个路径，才能访问到
    public User queryOne(@PathVariable("id") Integer id){
        System.out.println("query one User with get "+id);
        return new User(1, "张三");
    }

    @DeleteMapping("/users/{id}") //必须是delete请求这个路径，才能访问到
    public String deleteUser(@PathVariable("id") Integer id){
        System.out.println("delete one User with delete "+id);
        return "ok";
    }

    @PostMapping("/users") //必须是post请求这个路径，才能访问到
    public String saveUser(@RequestBody User user){
        System.out.println("save User with post "+user);
        return "ok";
    }

    @PutMapping("/users")//必须是put请求这个路径，才能访问到
    public String update(@RequestBody User user){
        System.out.println("update User with put "+user);
        return "ok";
    }
}
```

#### 13.3.2ajax请求

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Rest</title>
    <script src="${pageContext.request.contextPath}/js/jquery-2.1.0.js"></script>
</head>
<body>
    <input type="button" value="queryAll" οnclick="queryAll();"/><br>
    <input type="button" value="queryOne" οnclick="queryOne();"/><br>
    <input type="button" value="saveUser" οnclick="saveUser();"/><br>
    <input type="button" value="updateUser" οnclick="updateUser();"/><br>
    <input type="button" value="deleteOne" οnclick="deleteOne();"/><br>
    <script>
        function queryAll() {
            $.ajax({
                type:"get",
                url:"${pageContext.request.contextPath}/users",
                success:function(ret){
                    console.log("查询所有：");
                    console.log(ret);
                }
            });
        }
        function queryOne() {
            $.ajax({
                type:"get",
                url:"${pageContext.request.contextPath}/users/1",
                success:function(ret){
                    console.log("查询一个：");
                    console.log(ret);
                }
            });
        }
        function saveUser() {
            var user={name:"zssea",birth:"2020-11-12 12:12:12"};
            $.ajax({
                type:"post",
                url:"${pageContext.request.contextPath}/users",
                data:JSON.stringify(user),
                contentType:"application/json",
                success:function(ret){
                    console.log("增加用户：");
                    console.log(ret);
                }
            });
        }
        function updateUser() {
            var user={id:1,name:"zssea2",birth:"2020-11-12 12:12:12"};
            $.ajax({
                type:"put",
                url:"${pageContext.request.contextPath}/users",
                data:JSON.stringify(user),
                contentType:"application/json",
                success:function(ret){
                    console.log("更新用户：");
                    console.log(ret);
                }
            });
        }
        function deleteOne() {
            $.ajax({
                type:"delete",
                url:"${pageContext.request.contextPath}/users/1",
                success:function(ret){
                    console.log("删除一个用户：");
                    console.log(ret);
                }
            });
        }
    </script>
</body>
</html>
```

## 十四、跨域请求

### 14.1域

域：协议+IP+端口

- http://localhost:8080
- http://locathost:8080
- http://ww.baidu.com:80

### 14.2 Ajax跨域问题

- Ajax发送请求时，不允许跨域，以防用户信息泄露。
- 当Ajax跨域请求时， 响应会被浏览器拦截(同源策略)，并报错。即浏览器默认不允许ajax跨域得到响应内容。(不是不能访问到对方的域，而是得不到响应，浏览器把对方的响应拦截)
- 互相信任的域之间如果需要ajax访问， (比如前后端分离项目中，前端项目和后端项目之间)，则需要额外的设置才可正常请求。

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020091120365764.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

### 14.3解决方案

- 允许其他域访问
- 在**被访问方**的Controller类上，添加注解@CrossOrigin

```java
@RestController
@RequestMapping("/origin")
@CrossOrigin("http://localhost:8080")
public class OriginController {
 ......   
}
```

- 携带对方cookie,使得session可用
- **在访问方**，ajax中添加属性: withCredentials: true

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>origin</title>
    <script src="${pageContext.request.contextPath}/js/jquery-2.1.0.js"></script>
</head>
<body>


    <input type="button" value="ajax" οnclick="cross_origin();">
    <input type="button" value="ajax2" οnclick="cross_origin2();">
    <script>

        function cross_origin(){
            $.ajax({
                type:"get",
                url:"http://localhost:9999/SpringMVC03/origin/test1",
                xhrFields: {
                    // 跨域携带cookie
                    withCredentials: true
                },
                success:function(ret){
                    console.log("ret:"+ret);
                }
            });
        }

        function cross_origin2(){
            $.ajax({
                type:"get",
                url:"http://localhost:9999/SpringMVC03/origin/test2",
                xhrFields: {
                    // 跨域携带cookie
                    withCredentials: true
                },
                success:function(ret){
                    console.log("ret:"+ret);
                }
            });
        }
    </script>
</body>
</html>
```

## 十五、SpringMVC执行流程

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200911203716611.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

## 十六、Spring整合

### 16.1整合思路

此时项目中有两个工厂

- web.xml文件是随着项目启动而加载的，里面配置基本都是servlet组件
  - DispatcherServlet 启动的springMVC工厂 == 负责生产C (Controller) 及springMVC自己的系统组件，（这是前端（核心）控制器，加载的是SpringMVC的配置文件：mvc.xml）
  - ContextLoaderlistener 启动的spring工厂 == 负责生产其他所有组件（比如service，dao及其相关），（这个类ContextLoaderlistener， 加载的是Spring的配置文件：applicationContext.xml）
  - springMVC的工厂会被设置为spring工厂的子工厂，可以随意获取spring工厂中的组件（jar包帮我们完成的）
  - 整合过程，就是累加：代码+依赖+配置。然后将service注入给controller即可

### 16.2整合技巧

- 两个工厂不能有彼此侵入，即，生产的组件不能有重合。
- 告知SpringMVC 哪些包中 存在被注解的类
  use-default-filters=true凡是被@Controller @Service @Repository注解的类， 都会被扫描，
  use- default- filters=false默认不扫描包内的任何类，只扫描include- filter中指定的类，只扫描被@Controller注解的类
- mvc.xml ：

```xml
    <!-- 注解扫描  下面这些配置是指SpringMVC在扫描com.zssea包下的注解时，只扫描有Controller注解的类（就是类名之上加了@Controller的类），其他的忽略-->
    <context:component-scan base-package="com.zssea" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"></context:include-filter>
    </context:component-scan>
```

- applicationContext.xml

```xml
    <!--扫描注解 下面这些配置是指Spring在扫描com.zssea包下的注解时，不扫描有Controller注解的类（就是类名之上加了@Controller的类，因为这样的类被SpringMVC扫描了，区分职责关系，可以对比mvc.xml文件），其他的都要扫描-->

<context:component-scan base-package="com.zssea" use-default-filters="true">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
```

### 16.3总结

在整合时，

- xml配置文件与注解之间的选择：service层，controller层的代码中，注入dao，service、bean的声明

  建议用注解，service的实现类中的方法配置事务建议用注解；

- SpringMVC配置文件（mvc.xml）中写的是：开启注解扫描、注解驱动、视图解析器、静态资源访问、拦截器、异常解析器等

- Spring配置文件（applicationContext.xml）中写的是： DataSource、SqlSessionFactory、DAO接口 MapperScannerConfigurer、扫描注解、事务管理器、开启事务注解等

- 整个项目有大致三个配置文件 web.xml 、 mvc.xml （SpringMVC配置文件）、 applicationContext.xml （Spring配置文件）， 在web.xml文件中会声明另外两个配置文件地址，当tomcat启动时，会加载web.xml文件，进而加载另外两个配置文件，创建springmvc工厂（子工厂）与spring工厂（父工厂）；

- mvc.xml文件负责生产C (Controller) 及springMVC自己的系统组件，

- 而applicationContext.xml文件中是整合了spring和mybatis两个配置文件的内容，除了数据源（DataSource）bean的配置，之前mybatis配置文件里写的内容基本都是写在(SqlSessionfactory)的bean中

- web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!-- SpringMVC前端(核心)控制器
         1. 前端，接收所有请求
         2. 启动SpringMVC工厂  mvc.xml
         3. 负责springMVC流程调度
    -->
    <servlet>
        <servlet-name>mvc_zssea</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 局部参数，声明mvc配置文件位置-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:mvc.xml</param-value>
        </init-param>
        <!-- 懒汉式  饿汉式加载  可选-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>mvc_zssea</servlet-name>
        <url-pattern>/</url-pattern>
        <!--<url-pattern>*.action</url-pattern>-->
    </servlet-mapping>

    <!-- 此过滤器会进行：request.setCharactorEncoding("utf-8");  对post请求中，中文参数乱码有效-->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!-- 启动Spring工厂 -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>
</web-app>
```

- mvc.xml

```xml
<beans 	xmlns="http://www.springframework.org/schema/beans"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:mvc="http://www.springframework.org/schema/mvc"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
							http://www.springframework.org/schema/beans/spring-beans.xsd
							http://www.springframework.org/schema/context
							http://www.springframework.org/schema/context/spring-context.xsd
							http://www.springframework.org/schema/mvc
							http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 注解扫描  下面这些配置是指SpringMVC在扫描com.zssea包下的注解时，只扫描有Controller注解的类（就是类名之上加了@Controller的类），其他的忽略-->
    <context:component-scan base-package="com.zssea" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"></context:include-filter>
    </context:component-scan>

    <!-- 注解驱动 -->
    <mvc:annotation-driven>
        <!-- 安装FastJson,转换器 -->
        <mvc:message-converters>
            <bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter">
                <!-- 声明转换类型:json (将java对象转成josn)-->
                <property name="supportedMediaTypes">
                    <list>
                        <value>application/json</value>
                    </list>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>

    <!-- 视图解析器
        作用：1.捕获后端控制器的返回值="hello"
             2.解析： 在返回值的前后 拼接 ==> "/hello.jsp"
    -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!-- 前缀 -->
        <property name="prefix" value="/"></property>
        <!-- 后缀 -->
        <property name="suffix" value=".jsp"></property>
    </bean>

    <!-- 静态资源访问
      会额外的增加一个handler(就是Controller中的一个方法)，且其requestMapping:  "/**" 可以匹配所有请求，但是优先级最低
      所以如果其他所有的handler都匹配不上，请求会转向 "/**" ,恰好，这个handler就是处理静态资源的
      处理方式：将请求转发到tomcat中名为default的Servlet
      RequestMapping  /*   /a   /b  /c   /dxxxx    /a/b     /**
     -->
    <mvc:default-servlet-handler/>

</beans>
```

- applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/aop
                           http://www.springframework.org/schema/aop/spring-aop.xsd
                           http://www.springframework.org/schema/tx
                           http://www.springframework.org/schema/tx/spring-tx.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <!--整合时，DAO放在工厂中（bean标签）是由MapperScannerConfigurer 来帮我们完成的 ，
        而MapperScannerConfigurer 又需要sqlSessionFactory支持 ， sqlSessionFactory 又需要DataSource-->

    <!-- DataSource -->
    <!-- 导入文件-->
    <context:property-placeholder location="classpath:jdbc.properties"/>
    <!--创建数据源-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <!--基本配置-->
        <property name="driverClassName" value="${jdbc.driverClass}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>

        <!-- 配置初始化大小、最小、最大 -->
        <property name="initialSize" value="${jdbc.init}"/>
        <property name="minIdle" value="${jdbc.minIdle}"/>
        <property name="maxActive" value="${jdbc.maxActive}"/>

        <!-- 配置获取连接等待超时的时间 ms-->
        <property name="maxWait" value="60000"/>

        <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
        <property name="timeBetweenEvictionRunsMillis" value="60000"/>

        <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
        <property name="minEvictableIdleTimeMillis" value="300000"/>
    </bean>

    <!-- SqlSessionFactory -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!-- 注入连接池 -->
        <property name="dataSource" ref="dataSource"></property>
        <!-- （注册xxxDao-mapper文件）注入dao-mapper文件信息,如果映射文件和dao接口 同包且同名，则此配置可省略（要注意因为把mapper文件放在了dao文件夹下，所以要在pom.xml中更改maven编译规则）-->
        <property name="mapperLocations">
            <list>
                <value>classpath:com/zssea/dao/*.xml</value>
            </list>
        </property>
        <!-- 为实体类起别名 -->
        <property name="typeAliasesPackage" value="com.zssea.entity"></property>

        <property name="plugins">
            <array>
                <bean class="com.github.pagehelper.PageInterceptor">
                    <property name="properties">
                        <props>
                            <!-- 页号 调整到合理的值  0  max -->
                            <prop key="reasonable">true</prop>
                        </props>
                    </property>
                </bean>
            </array>
        </property>
    </bean>

    <!-- DAO接口 MapperScannerConfigurer-->
    <!-- mapperScannerConfigurer  管理DAO实现类的创建，并创建DAO对象，存入工厂管理，当执行完这个配置之后，在工厂中就会有一个id为userDAO 的bean，而这个bean就是UserDAO实现类的对象 -->
    <bean id="mapperScannerConfigurer9" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!-- dao接口所在的包  如果有多个包，可以用逗号或分号分隔 <property name="basePackage" value="com.a.dao,com.b.dao"></property>-->
        <property name="basePackage" value="com.zssea.dao"></property>
        <!-- 如果工厂中只有一个SqlSessionFactory的bean，此配置可省略 -->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"></property>
    </bean>

    <!--扫描注解 下面这些配置是指Spring在扫描com.zssea包下的注解时，不扫描有Controller注解的类（就是类名之上加了@Controller的类，因为这样的类被SpringMVC扫描了，区分职责关系，可以对比mvc.xml文件），其他的都要扫描-->
    <context:component-scan base-package="com.zssea" use-default-filters="true">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!-- 1. 引入一个事务管理器，其中依赖DataSource,借以获得连接，进而控制事务逻辑 -->
    <bean id="tx" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    <!--开启事务注解  只针对 这个@Transactional 事务注解-->
    <tx:annotation-driven transaction-manager="tx"/>
</beans>
```

- jdbc.properties

```properties
jdbc.driverClass=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatis_zssea?useUnicode=true&characterEncoding=UTF-8
jdbc.username=root
jdbc.password=123456
jdbc.init=1
jdbc.minIdle=1
jdbc.maxActive=3
```

)

### 1.1引言

- java开源框架，Spring Framework的一个独立模块。
- MVC框架，在项目中开辟MVC层次架构
- 对控制器中的功能包装简化扩展践行工厂模式，功能架构在工厂之上

### 1.2 MVC架构

#### 1.2.1概念

| 名称       | 职责                                                         |
| ---------- | ------------------------------------------------------------ |
| Model      | 模型：即业务模型，负责完成业务中的数据通信处理，对应项目中的service和dao |
| View       | 视图：渲染数据，生成页面。对应项目中的 Jsp                   |
| Controller | 控制器：直接对接请求，控制MVC流程，调度模型，选择视图。对应项目中的Servlet |

#### 1.2.2好处

- MVC是现下软件开发中的最流行的代码结构形态;
- 人们根据负责的不同逻辑，将项目中的代码分成MVC3个层次;
- 层次内部职责单一，层次之间耦合度低;
- 符合低耦合高内聚的设计理念。也实际有利于项目的长期维护。

## 二、开发流程

### 2.1导入依赖

```xml
		<dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.6.RELEASE</version>
        </dependency>
```

### 2.2配置核心(前端)控制器

- 作为一个MVC框架，首先要解决的是：如何能够收到请求!
- 所以MVC框架大都会设计一款前端控制器，选型在Servlet或Filter两者之一,在框架最前沿率先工作，接收所有请求。(这里是选择Servlet，这是一个SpringMVC帮我们写好的)
- 此控制器在接收到请求后，还会负责springMVC的核心的调度管理，所以既是前端又是核心。
- web.xml ：

```xml
	 <servlet>
        <servlet-name>mvc_zssea</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
     	<!-- 局部参数，声明配置文件位置-->
     	<init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:mvc.xml</param-value>
        </init-param>
        <!-- 懒汉式  饿汉式加载 （servlet启动时刻） 可选-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>mvc_zssea</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

### 2.3后端控制器

- 等价于之前定义的Servlet

```java
package com.zssea.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //声明是后端控制器 ，（应该就是<bean> 标签的注解）
@RequestMapping("/hello") //这是访问路径
public class HelloController {

    @RequestMapping("/test01")
    public String hello01(){ //此方法可以理解为之前在servlet中写的service dopost  doget方法
        System.out.println("hello world 1");
        return null;
    }

    @RequestMapping("/test02")
    public String hello02(){ 
        System.out.println("hello world 2");
        return null;
    }
}
```

### 2.4配置文件（mvc.xml）

- 默认名称：核心控制器名-servet.xml 默认位置: WEB-INF
- 随意名称：mvc.xml 随意位置: resources 但需要配置在核心(前端)控制器中
- mvc.xml 文件：

```xml
<beans 	xmlns="http://www.springframework.org/schema/beans"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:mvc="http://www.springframework.org/schema/mvc"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
							http://www.springframework.org/schema/beans/spring-beans.xsd
							http://www.springframework.org/schema/context
							http://www.springframework.org/schema/context/spring-context.xsd
							http://www.springframework.org/schema/mvc
							http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 注解扫描 -->
    <context:component-scan base-package="com.qf.web"/>

    <!-- 注解驱动 -->
    <mvc:annotation-driven></mvc:annotation-driven>

    <!-- 视图解析器
	     作用：1.捕获后端控制器的返回值="hello"
	          2.解析： 在返回值的前后 拼接 ==> "/hello.jsp"
	 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!-- 前缀 -->
        <property name="prefix" value="/"></property>
        <!-- 后缀 -->
        <property name="suffix" value=".jsp"></property>
    </bean>
</beans>
```

### 2.5访问

```java
http://localhost:8080/SpringMVC01/hello/test01
http://localhost:8080/SpringMVC01/hello/test02
```

### 2.6过程分析

当我们项目启动时， （web.xml）前端控制器会启动，它启动时会加载mvc.xml文件，启动SprignMVC工厂，生产配置好的bean，扫描注解，后端控制器就创建出来；（就是项目启动时，配置文件就加载好了，就等着用户访问了）之后，用户请求，先经过前端控制器处理，再转发到后端控制器上，匹配访问路径，执行对应的后端控制器的方法，再根据返回值（通过视图解析）跳转到对应的jsp。

## 三、接收请求参数

- 通过控制器中方法的形参，接收请求参数

### 3.1基本类型参数

- 请求参数 和 方法的形参 同名即可
- springMVC默认可以识别的日期字符串格式为: YYYY/MM/dd HH:mm:ss通过@DateTimeFormat可以修改默认日志格式

```java
package com.zssea.web;

import com.zssea.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/param")
public class ParamController {

    // http://xxxx/param/test01?id=1&name=shine&gender=true&birth=2020/11/11 12:20:30
    @RequestMapping("/test1")
    public String test01(Integer id, String name, Boolean gender, Date birth){
        System.out.println(id);
        System.out.println(name);
        System.out.println(gender);
        System.out.println(birth);
        return "hello";
    }
}
/*
1
shine
true
Wed Nov 11 12:20:30 CST 2020
*/
```

### 3.2实体收参【重点】

- 请求参数 和 实体的属性 同名即可

```java
package com.zssea.web;

import com.zssea.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/param")
public class ParamController {

    // http://xxxx/param/test1?id=1&name=zhangsan&gender=true&birth=2020/12/12 12:13:20
    //请求参数 和 实体的属性 同名
    @RequestMapping("/test2")
    public String test2(User user){
        System.out.println("test2");
        System.out.println(user);
        return "hello";
    }
}
/*
test2
User{id=1, name='zhangsan', gender=true, birth=Wed Nov 11 12:20:30 CST 2020}
*/


package com.zssea.entity;

import java.util.Arrays;
import java.util.Date;

public class User {
    private Integer id;
    private String name;
    private Boolean gender;
    private Date birth;
	// get  set  toString() ......
}
```

### 3.3数组收参

- 简单类型的数组

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>param</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/param/test3">
        id:<input type="text" name="id"> <br>
        name:<input type="text" name="name"><br>
        gender:<input type="text" name="gender"><br>
        birth:<input type="text" name="birth"><br>
        <input type="checkbox" name="hobby" value="football">足球
        <input type="checkbox" name="hobby" value="basketball">篮球
        <input type="checkbox" name="hobby" value="volleyball">排球 <br>
        <input type="submit" value="提交">
    </form>
</body>
</html>


package com.zssea.web;

import com.zssea.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/param")
public class ParamController {

    // http://xxxx/param/test3?hobby=football&hobby=basketball&hobby=volleyball
    // 请求路径中有多个重名的参数，这些参数可以存储到一个数组中
    @RequestMapping("/test3")
    public String test3(String[] hobby) {
        System.out.println("test3");
        for (String s : hobby) {
            System.out.println(s);
        }
        return "hello";
    }

     // http://xxxx/param/test3?hobby=football&hobby=basketball&hobby=volleyball
    // 请求路径中有多个重名的参数，这些参数可以存储到一个数组中
    @RequestMapping("/test3")
    public String test3(User user) {
        System.out.println("test3");
        System.out.println(user);
        return "hello";
    }
}
/*
test3
football
basketball
volleyball
*/

/*
test3
User{id=10, name='zssea', gender=false, birth=Wed Nov 11 12:20:30 CST 2020, hobby=[football, basketball, volleyball]}

*/
```

### 3.4集合收参【了解】

```java
package com.zssea.entity;

import java.util.List;

public class UserList {
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
    private List<User> users;
}


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>param</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/param/test4">
        id:<input type="text" name="users[0].id"> <br>
        name:<input type="text" name="users[0].name"><br>
        gender:<input type="text" name="users[0].gender"><br>
        <hr>
        id:<input type="text" name="users[1].id"> <br>
        name:<input type="text" name="users[1].name"><br>
        gender:<input type="text" name="users[1].gender"><br>
        <input type="submit" value="提交">
    </form>
</body>
</html>

            
package com.zssea.web;

import com.zssea.entity.User;
import com.zssea.entity.UserList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.nio.cs.US_ASCII;

import java.util.Date;

@Controller
@RequestMapping("/param")
public class ParamController {
    // http://xxx/param/test4?users[0].id=1&users[0].name=shine&users[0].gender=true&users[1].id=2&users[1].name=zhangsan
    @RequestMapping("/test4")
    public String test4(UserList userList){
        System.out.println("test4");
        for (User user : userList.getUsers()) {
            System.out.println(user);
        }
        return "hello";
    }
}
/*
test4
User{id=12, name='wal', gender=false, birth=null, hobby=null}
User{id=13, name='zssea', gender=true, birth=null, hobby=null}
*/
```

### 3.5路径参数

```java
package com.zssea.web;

import com.zssea.entity.User;
import com.zssea.entity.UserList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.nio.cs.US_ASCII;

import java.util.Date;

@Controller
@RequestMapping("/param")
public class ParamController {
    // http://localhost:8080/SpringMVC01/param/test5/2
    // {id} 命名路径
    // {id} 等价于*   /test5/1   test5/2   test5/xxxx
    @RequestMapping("/test5/{id}")
    public String test5( @PathVariable("id")  Integer id){
        System.out.println("test5");
        System.out.println("id:"+id);
        return "hello";
    }
}
/*
test5
id:2
*/
```

### 3.6中文乱码

- 首先，页面中字符集统一

  ```jsp
  JSP 文件要有: <%@page pageEncoding="utf-8" %>
  HTML 文件要有: <meta charset= "UTF-8">
  ```

- 其次，tomcat中字符集设置，对get请求中， 中文参数乱码有效

  ```java
  Tomcat配置: URIEncoding=utf-8
  ```

- 最后，设置此filter，对post请求中，中文参数乱码有效

  ```xml
  <!-- 此过滤器会进行：request.setCharactorEncoding("utf-8"); -->
      <filter>
        <filter-name>encoding</filter-name>
          <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
          <init-param>
              <param-name>encoding</param-name>
              <param-value>utf-8</param-value>
          </init-param>
      </filter>
      <filter-mapping>
          <filter-name>encoding</filter-name>
          <url-pattern>/*</url-pattern>
      </filter-mapping>
  ```

## 四、跳转

跳转关键字：forward：(转发) ; redirect:（重定向）

### 4.1转发

```java
package com.zssea.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jump")
public class ForwardController {

    @RequestMapping("/test1")
    public String test1(){
        System.out.println("test1");
        return "hello"; // 转发,这是转发跳转(经过视图解析器)
        //return "forward:/hello.jsp";  //如果返回这个就不会经过视图解析器解析
    }

    @RequestMapping("/test2")
    public String test2(){
        System.out.println("test2");
        //return "forward:/jump/test1"; //（绝对地址）转发
        return "forward:test1";//相对路径 （这个test1 是当前类中的test1），在本类中可以用相对地址，如果要跳转到另外一个类就要用绝对地址
    }

}
```

### 4.2重定向

```java
package com.zssea.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jump")
public class ForwardController {

    @RequestMapping("/test3")
    public String test3(){
        System.out.println("test3");
        return "redirect:/hello.jsp"; // 重定向到helo.jsp
    }

    @RequestMapping("/test4")
    public String test4(){
        System.out.println("test4");
        return "redirect:test3";   //重定向到test3（相对地址）
        //return "redirect:/jump/test3"
    }

    @RequestMapping("/query")
    public String test5(){
        System.out.println("querr数据1");
        return "forward:test1";  //转发
        //return "redirect:test1";  //重定向
    }
}
```

### 4.3跳转细节

- 在增删改之后，为了防止请求重复提交，重定向跳转
- 在查询之后，可以做转发跳转

## 五、传值

- C得到数据后，跳转到V，并向V传递数据。进而V中可以渲染数据，让用户看到含有数据的页面
- 转发跳转：Request作用域
- 重定向跳转： Session作用域

### 5.1 Request和Session

```java
package com.zssea.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/data")
public class DataController {

    @RequestMapping("/test1")
    public String test1(HttpServletRequest request, HttpSession session){
        request.setAttribute("name","zssea");
        session.setAttribute("age",10);
        return "data";
    }
}
```

### 5.2 JSP中取值

- 建议：重点复习EL JSTL

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>data</title>
</head>
<body>
    name:${requestScope.name} <br>
    age:${sessionScope.age}
</body>
</html>
```

### 5.3 Model

```java
package com.zssea.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/data")
@SessionAttributes(names = {"city","street"})
public class DataController {

    @RequestMapping("/test2")
    public String test2(Model model){
        model.addAttribute("gender",true); //Model 默认的会把数据存放到request 域中
        return "data2";
    }

}


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>data2</title>
</head>
<body>
    gender:${requestScope.gender} <br>
</body>
</html>
```

### 5.4 ModelAndView

```java
package com.zssea.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/data")
public class DataController {

    @RequestMapping("/test4")
    public ModelAndView test4(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("claz","001班");
        modelAndView.setViewName("forward:/hello.jsp");
        return modelAndView;
    }
}


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello</title>
</head>
<body>
    Hello World!  <br>
    claz:${requestScope.claz}
</body>
</html>
```

### 5.5 @SessionAttributes

- @SessionAttributes({“gender”,“name”}) : model中的 name和gender会存入session中
- SessionStatus 移除session

```java
package com.zssea.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/data")
@SessionAttributes(names = {"city","street"})
public class DataController {


    @RequestMapping("/test2")
    public String test2(Model model){
        model.addAttribute("gender",true); //Model 默认的会把数据存放到request 域中
        model.addAttribute("city","北京");
        model.addAttribute("street","长安街"); //这两个数据会存放在session域中，因为类名上面定义了@SessionAttributes(names = {"city","street"})
        return "data2";
    }

    @RequestMapping("/test3")
    public String test3(SessionStatus sessionStatus){
        //清空所有 由model存入session域的数据
        sessionStatus.setComplete();
        return "data2";
    }

}


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>data2</title>
</head>
<body>
    gender:${requestScope.gender} <br>
    city:${sessionScope.city} <br>
    street:${sessionScope.street} <br>
</body>
</html>
```

## 六、静态资源

### 6.1静态资源问题

- 静态资源: html, js文件， css文件，图片文件
- 静态文件没有url-pattern,所以默认是访问不到的，之所以可以访问，是因为，tomcat中有 一个全局的servlet:org.apache.catalina.servlets.DefaultServlet，它的url-pattern是 “/”, 是全局默认的Servlet.所以每个项目中不能匹配的静态资源的请求，有这个Servlet来处理即可。
- 但， 在SpringMVC中DispatcherServlet（前端控制器）也采用了 “/” 作为url-pattern,则项目中不会再使用全局的Serlvet,则静态资源不能完成访问。

### 6.2解决方案1

- DispathcerServlet采用其他的url-pattern
- 此时，所有访问handler的路径都要以action结尾! !

```xml
<servlet>
        <servlet-name>mvc_zssea</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 局部参数，声明mvc配置文件位置-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:mvc.xml</param-value>
        </init-param>
        <!-- 懒汉式  饿汉式加载  可选-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>mvc_zssea</servlet-name>
        <!--<url-pattern>/</url-pattern>-->
        <url-pattern>*.action</url-pattern>
    </servlet-mapping>
```

### 6.3解决方案2

- DispathcerServlet的url-pattern依然采用"/",但追加配置
- 在mvc.xml 中

```xml
 <!--
      会额外的增加一个handler(就是Controller中的一个方法)，且其requestMapping:  "/**" 可以匹配所有请求，但是优先级最低
      所以如果其他所有的handler都匹配不上，请求会转向 "/**" ,恰好，这个handler就是处理静态资源的
      处理方式：将请求转发到tomcat中名为default的Servlet

      RequestMapping  /*   /a   /b  /c   /dxxxx    /a/b
                      /**
     -->
    <mvc:default-servlet-handler/>
```

- web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!-- SpringMVC前端(核心)控制器
         1. 前端，接收所有请求
         2. 启动SpringMVC工厂  mvc.xml
         3. 负责springMVC流程调度
    -->
    <servlet>
        <servlet-name>mvc_zssea</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 局部参数，声明mvc配置文件位置-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:mvc.xml</param-value>
        </init-param>
        <!-- 懒汉式  饿汉式加载  可选-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>mvc_zssea</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

</web-app>
```

- 这样就可以访问静态资源了

### 6.4解决方案3

- mapping是访问路径，location是静态资源存放的路径
- 将/html/ ** 中/ **匹配到的内容， 拼接到/hhh/后 http://…/html/a.html 访问/hhh/a.html

```xml
<mvc:resources mapping="/html/**" location="/page/"/>
<mvc:resources mapping="/css/**" location="/css/"/>
```

## 七、Json处理

### 7.1导入依赖

```xml
<!-- Jackson springMVC默认的Json解决方案选择是Jackson, 所以只需要导入jackson的jar,即可使用。-->
<!-- Jackson -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.9</version>
        </dependency>
```

### 7.2使用@ResponseBody

```java
package com.zssea.web;

import com.zssea.entity.User;
import com.zssea.entity.User2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/json")
public class JsonController {

    @RequestMapping("/test1")
    @ResponseBody //这个注解的意思是将这个handler的返回值自动转成json，并响应给客户端
    public User test1(){
        System.out.println("test1");
        User user = new User(1,"张三",new Date());
        return user;
    }

    @RequestMapping("/test2")
    @ResponseBody //这个注解的意思是将这个handler的返回值自动转成json，并响应给客户端
    public List<User> test2(){
        System.out.println("test2");
        User user = new User(1,"张三",new Date());
        User user2 = new User(2,"李四",new Date());
        List<User> users = Arrays.asList(user, user2);
        return users;
    }

    @RequestMapping(value = "/test3",produces = "text/html;charset=utf-8")//produces防止中文乱码

    @ResponseBody //如果返回值是 String 类型，就将字符串显示出来，而不是跳转
    public String test3(){
        System.out.println("test3");
        return "你好";
    }
}
```

### 7.3使用@RestController

- Controller类上加了@RestControllr注解，等价于在类中的每个方法上都加了@ResponseBody

```java
package com.zssea.web;

import com.zssea.entity.User;
import com.zssea.entity.User2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//@Controller
@RequestMapping("/json")
@RestController //这个注解如果写了 @Controller 和 类中的每个方法的 @ResponseBody 都不用加了
public class JsonController {

    @RequestMapping("/test1")
    //@ResponseBody //这个注解的意思是将这个handler的返回值自动转成json，并响应给客户端
    public User test1(){
        System.out.println("test1");
        User user = new User(1,"张三",new Date());
        return user;
    }

    @RequestMapping("/test2")
    //@ResponseBody //这个注解的意思是将这个handler的返回值自动转成json，并响应给客户端
    public List<User> test2(){
        System.out.println("test2");
        User user = new User(1,"张三",new Date());
        User user2 = new User(2,"李四",new Date());
        List<User> users = Arrays.asList(user, user2);
        return users;
    }

    @RequestMapping(value = "/test3",produces = "text/html;charset=utf-8") //produces防止中文乱码

    //@ResponseBody //如果返回值是 String 类型，就将字符串显示出来，而不是跳转
    public String test3(){
        System.out.println("test3");
        return "你好";
    }
}
```

### 7.4使用@RequestBody

- @RequestBody,接收Json参数

#### 7.4.1定义Handler

```java
package com.zssea.entity;

public class User {
    private Integer id;
    private String name;
    private Date birth;
	//set get 
}

package com.zssea.web;

@Controller
@RequestMapping("/json")
public class JsonController {

    @RequestMapping("/test4")
    public String test4(@RequestBody User user){ //@RequestBody 是指将接收的json数据转成User ,post方式提交的(将请求体中的json数据转换为java对象)
        System.out.println(user);
        return "ok";
    }
}
```

#### 7.4.2Ajax发生json

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>json</title>
    <script src="${pageContext.request.contextPath}/js/jquery-2.1.0.js"></script>
</head>
<body>
    <input type="button" value="ajax" οnclick="send_json();"/>
    <script>
        function send_json() {
            var user = {id:1,name:"zssea"};
            var userJSON = JSON.stringify(user);   //转换js对象成json
            $.ajax({
                url:"${pageContext.request.contextPath}/json/test4",
                type:"post",
                data:userJSON,
                contentType:"application/json",  //声明请求参数类型为json

                success:function (ret) {
                    alert(ret);
                }
            })
        }
    </script>
</body>
</html>
```

### 7.5 Jackson常用注解

#### 7.5.1日期格式化

- @JsonFormat(pattern=“yyy-MM-dd HH:mm:ss”,timezone = “GMT+8”)

```java
package com.zssea.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
public class User {

    private Integer id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //日期格式化
    private Date birth;

    private List<String> hobby;

    private Double salary = 10000.126;
	//set get
}
```

#### 7.5.2属性名修改

- @JsonProperty(“new_ name”)

```java
package com.zssea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zssea.serialize.MySerializer;

import java.util.Date;
import java.util.List;

public class User {

    @JsonProperty("id2") //修改属性名 ,json数据显示的为id2
    private Integer id;

    @JsonIgnore //json格式数据中 忽略这个属性
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //日期格式化
    private Date birth;

    //@JsonInclude(JsonInclude.Include.NON_NULL) //如果这个属性值为null，就不输出
    @JsonInclude(JsonInclude.Include.NON_EMPTY) //这个属性要有值，如果为null或者 size = 0 也不输出
    private List<String> hobby;// null   size=0

    @JsonSerialize(using = MySerializer.class)
    private Double salary = 10000.126;//在输出此属性时，使用MySerializer输出

    //set get
}
```

#### 7.5.3属性忽略

- @Jsonignore

```java
package com.zssea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zssea.serialize.MySerializer;

import java.util.Date;
import java.util.List;

public class User {

    private Integer id;

    @JsonIgnore //json格式数据中 忽略这个属性
    private String name;

    private Date birth;
    private List<String> hobby;
    private Double salary = 10000.126;

    //set get
}
```

#### 7.5.4 null和empty属性排除

- Jackson默认会输出null值的属性，如果不需要，可以排除。
- @Jsonlnclude(JsonInclude.Include.NON_NULL) //null值 属性不输出
- @ JsonInclude(value= Jsonlnclude.Include.NON_EMPTY) // empty属性
  不输出(空串，长度为0的集合，null值)

```java
package com.zssea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zssea.serialize.MySerializer;

import java.util.Date;
import java.util.List;

public class User {

    private Integer id;
    private String name;
    private Date birth;

    //@JsonInclude(JsonInclude.Include.NON_NULL) //如果这个属性值为null，就不输出
    @JsonInclude(JsonInclude.Include.NON_EMPTY) //这个属性要有值，如果为null或者 size = 0 就不输出
    private List<String> hobby;// null   size=0

    private Double salary = 10000.126;

    //set get
}
```

#### 7.5.5自定义序列化

- @JsonSerialize(using = MySerialzr.class) //使用MySerializer输出某属性

```java
package com.zssea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zssea.serialize.MySerializer;

import java.util.Date;
import java.util.List;

public class User {

    private Integer id;
    private String name;
    private Date birth;
    private List<String> hobby;// null   size=0

    @JsonSerialize(using = MySerializer.class)
    private Double salary = 10000.126;//在输出此属性时，使用MySerializer输出

    //set get
}


package com.zssea.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class MySerializer extends JsonSerializer<Double> {
    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // 将Double salary的值 四舍五入
        String number = BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        // 输出 四舍五入后的值
        gen.writeNumber(number);
    }
}
```

### 7.6 FastJson

- 如果不想使用Jackson,则也可以安装其他的Json处理方案: FastJson

#### 7.6.1导入依赖

```xml
 		<!-- FastJson -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.58</version>
        </dependency>
```

#### 7.6.2安装FastJson

- mvc.xml文件：

```xml
  	 <!-- 注解驱动 -->
    <mvc:annotation-driven>
        <!-- 安装FastJson,转换器 -->
        <mvc:message-converters>
            <bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter">
                <!-- 声明转换类型:json (将java对象转成josn)-->
                <property name="supportedMediaTypes">
                    <list>
                        <value>application/json</value>
                    </list>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
```

#### 7.6.3使用

- @ResponseBody @RequestBody @RestController使用方法不变

#### 7.6.4常用注解

- 日期格式化: @JSONField(format=“yyyy/MM/dd”)
- 属性名修改: @JSONField(name=“birth”)
- 忽略属性: @JSONField(serialize = false)
- 包含null值: @JSONField(serialzeFeatures = SerializerFeature .WriteMapNullValue)默认会忽略所有null值,有此注解会输出null
  - @JSONFieldlserialzeFeatures = SerializerFeature.WriteUullstringAsEmpty) null的String输出为"“
- 自定义序列化: @JSONField(serializeUsing = MySrializer.cass)

```java
package com.zssea.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zssea.serialize.MySerializer2;

import java.util.Date;

public class User2 {

    @JSONField(serialize = false)
    private Integer id; //忽略id

    @JSONField(name="NAME",serialzeFeatures = SerializerFeature.WriteNullStringAsEmpty)
    private String name;// 修改属性名为NAME , 当name为空时，输出一个空串

    @JSONField(serialzeFeatures = SerializerFeature.WriteMapNullValue)
    private String city;// 如果为空，输出null

    @JSONField(format="yyyy/MM/dd")
    private Date birth; //日期格式化

    @JSONField(serializeUsing = MySerializer2.class)
    private Double salary; // 序列化 拼接“ 元 ”

	// 构造 set get tostring
}


package com.zssea.serialize;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class MySerializer2 implements ObjectSerializer {

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType,
                      int features) throws IOException {
        Double value = (Double) object; // salary属性值
        String text = value + "元";// 在salary后拼接 “元”
        serializer.write(text); // 输出拼接后的内容
    }
}


package com.zssea.web;

import com.zssea.entity.User;
import com.zssea.entity.User2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.nio.cs.US_ASCII;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//@Controller
@RequestMapping("/json")
@RestController //这个注解如果写了 @Controller 和 类中的每个方法的 @ResponseBody 都不用加了
public class JsonController {

    @RequestMapping("/test11")
    public User2 test11(){
        System.out.println("test1");
        User2 user2 = new User2(1,"张三","北京",new Date(),1000.5);
        return user2;
    }

}
/*
{"NAME":"张三","birth":"2020/09/06","city":"北京","salary":"1000.5元"}
*/
```

## 八、异常解析器

### 8.1现有方案，分散处理

- Controller中的每个Handler自己处理异常
- 此种处理方案，异常处理逻辑，分散在各个handler中，不利于集中管理

```java
@Controller
@RequestMapping("/ex")
public class ExController {
    
    @RequestMapping("/xx") //访问路径
    public string xx(){
        try{
            ...
        }catch(Exception1 e){
            e.printStackTrace();
            return"redirect:/xx/error1";
        }catch(Exception2 e){
            e.printStackTrace();
            return "redirect:/xx/error2";
        }
    }
    ...
    ...
}
```

### 8.2异常解析器，统一处理异常

- Controller中的每个Handler不再自己处理异常，而是直接throws所有异常。
- 定义一个“异常解析器”集中捕获处理所有异常
- 此种方案，在集中管理异常方面，更有优势!

```java
package com.zssea.resolver;

import com.zssea.ex.MyException1;
import com.zssea.ex.MyException2;
import com.zssea.ex.MyException3;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 异常解析器
// 任何一个Handler中抛出异常时，它就会执行（将异常处理代码提取出来）
public class MyExceptionResolver implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        ModelAndView modelAndView = new ModelAndView();

        if(e instanceof MyException1){
            // error1.jsp
            modelAndView.setViewName("redirect:/error1.jsp"); //重定向
        }else if(e instanceof MyException2){
            // error2.jsp
            modelAndView.setViewName("redirect:/error2.jsp");
        }else if(e instanceof MyException3){
            // error3.jsp
            modelAndView.setViewName("redirect:/error3.jsp");
        }else if(e instanceof MaxUploadSizeExceededException){
            modelAndView.setViewName("redirect:/uploadError.jsp");
        }

        return modelAndView;
    }
}


<!--mvc.xml文件中-->
    <!--异常解析器-->
    <bean class="com.zssea.resolver.MyExceptionResolver"></bean>
```

## 九、拦截器

### 9.1作用

- 作用：抽取handler中的冗余功能 （使handler中重复的代码只写一遍，实现复用）

### 9.2定义拦截器

- 执行顺序: preHandle–postHandle–afterCompletion

```java
package com.zssea.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyInterceptor implements HandlerInterceptor {

    // 在handler之前执行
    // 再次定义 handler中冗余的功能
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("pre Handle");
        // 判断登录状态
        HttpSession session = request.getSession();
        if(session.getAttribute("state") != null){
            return true; //放行，执行后续的handler
        }
        //中断之前，做出响应
        response.sendRedirect("/login.jsp");

        return false; //中断请求，不再执行后续的handler
    }

    // 在handler之后执行，响应之前执行
    // 改动请求中数据
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("post Handle");
    }

    // 在视图渲染完毕后，
    // 资源回收
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("after Handle");
    }
}
```

### 9.3配置拦截路径

- mvc.xml：

```xml
<!--拦截器配置，指明哪个拦截器拦截哪些路径访问请求-->
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/inter/test1"/>
            <mvc:mapping path="/inter/test2"/>
            <mvc:mapping path="/inter/test*"/> <!--test开头的路径-->
            <!--<mvc:mapping path="/inter/**"/>--><!--任意多级任意路径-->
            <mvc:exclude-mapping path="/inter/login"/><!--不拦截此路径-->
            <bean class="com.zssea.interceptor.MyInterceptor"/><!--拦截器类-->
        </mvc:interceptor>
    </mvc:interceptors>
```

## 十、上传

### 10.1导入jar(依赖)

```xml
 		<dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.6</version>
        </dependency>

        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.4</version>
            <exclusions>
                <exclusion>
                    <groupId>javax.servlet</groupId>
                    <artifactId>servlet-api</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

### 10.2表单

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/upload/test1" method="post" enctype="multipart/form-data">
        file：<input type="file" name="source"/><br>
        <input type="submit" value="上传"/>
    </form>

</body>
</html>
```

### 10.3.上传解析器

- mvc.xml 中追加：

```xml
<!-- 文件上传解析器-->
    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
<!-- 最大可上传的文件大小  单位：byte  超出后会抛出MaxUploadSizeExceededException异常，可以异常解析器捕获 （配置了之后有可能出现状态显示为200，而并没又上传成功，所以我们一般写一个拦截器代替这个功能）-->
        <!--<property name="maxUploadSize" value="1048576"></property>-->
</bean>
```

### 10.4 Handler

```java
package com.zssea.web;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/upload")
public class UploadController {

    @RequestMapping("/test1")
    public String test1(MultipartFile source, HttpSession session) throws IOException {
        System.out.println("tes1");
        // 获取上传文件的 原始名称
        String filename = source.getOriginalFilename();
        //生成一个唯一的文件名
        String uniqueFileName = UUID.randomUUID().toString();
        //获取上传文件的后缀名
        String extension = FilenameUtils.getExtension(filename);
        //拼接得到最终的唯一的文件名
        String uniqueFileName2 = uniqueFileName+"."+extension;

        // 获取上传文件的 类型
        String contentType = source.getContentType();
        System.out.println(filename);
        System.out.println(contentType);

        //保存
        String realPath = session.getServletContext().getRealPath("/upload");
        System.out.println("realPath:" + realPath);
        source.transferTo(new File(realPath+"\\"+uniqueFileName2));
        return "index";
    }
}
```

### 10.5自定义文件上传大小的拦截器

- MyFileUploadInterceptor

```java
package com.zssea.interceptor;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFileUploadInterceptor implements HandlerInterceptor {

    private long maxFileUploadSize;

    public long getMaxFileUploadSize() {
        return maxFileUploadSize;
    }

    public void setMaxFileUploadSize(long maxFileUploadSize) {
        this.maxFileUploadSize = maxFileUploadSize;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断上传文件的大小，1M= 1048576byte
        ServletRequestContext servletRequestContext = new ServletRequestContext(request);
        //获取上传文件的大小
        long length = servletRequestContext.contentLength();
        if(length > maxFileUploadSize){
            throw new MaxUploadSizeExceededException(maxFileUploadSize);
        }
        return true;
    }
}
```

- mvc.xml

```xml
<!--异常解析器-->
    <bean class="com.zssea.resolver.MyExceptionResolver"></bean>

<!--拦截器配置，指明哪个拦截器拦截哪些路径访问请求-->
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/upload/test1"/>
            <bean class="com.zssea.interceptor.MyFileUploadInterceptor">
                <property name="maxFileUploadSize" value="1048576"></property>
            </bean>
        </mvc:interceptor>
    </mvc:interceptors>
```

- 异常处理：MyExceptionResolver (文件过大就重定向到uploadError.jsp)

```java
package com.zssea.resolver;

import com.zssea.ex.MyException1;
import com.zssea.ex.MyException2;
import com.zssea.ex.MyException3;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 异常解析器
// 任何一个Handler中抛出异常时，它就会执行（将异常处理代码提取出来）
public class MyExceptionResolver implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        ModelAndView modelAndView = new ModelAndView();

        if(e instanceof MyException1){
            // error1.jsp
            modelAndView.setViewName("redirect:/error1.jsp"); //重定向
        }else if(e instanceof MyException2){
            // error2.jsp
            modelAndView.setViewName("redirect:/error2.jsp");
        }else if(e instanceof MyException3){
            // error3.jsp
            modelAndView.setViewName("redirect:/error3.jsp");
        }else if(e instanceof MaxUploadSizeExceededException){
            modelAndView.setViewName("redirect:/uploadError.jsp");
        }

        return modelAndView;
    }
}
```

## 十一、下载

### 11.1超链接

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>download</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/download/test1?name=jquery-2.1.0.js">下载</a>
</body>
</html>
```

### 11.2 Handler

```java
package com.zssea.web;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/download")
public class DowmLoadController {

    @RequestMapping("/test1")
    public void test1(String name, HttpSession session, HttpServletResponse response) throws IOException {

        //获取文件存放的地址 （就是下载这个文件夹里的文件）
        String realPath = session.getServletContext().getRealPath("/upload");
        String filePath = realPath + "\\"+ name;

        //设置响应头  告知浏览器，要以附件的形式保存内容   filename=浏览器显示的下载文件名
        response.setHeader("content-disposition","attachment;filename="+name);

        //响应
        IOUtils.copy(new FileInputStream(filePath),response.getOutputStream());

    }
}
```

## 十二、验证码

### 12.1作用

- 防止暴力攻击，前端安全保障

### 12.2导入jar（依赖）

```xml
 		<!-- Kaptcha -->
        <dependency>
            <groupId>com.github.penggle</groupId>
            <artifactId>kaptcha</artifactId>
            <version>2.3.2</version>
            <exclusions>
                <exclusion>
                    <groupId>javax.servlet</groupId>
                    <artifactId>javax.servlet-api</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

### 12.3声明验证码组件

- web.xml :追加

```xml
 <!-- 验证码Servlet 验证码组件-->
    <servlet>
        <servlet-name>cap</servlet-name>
        <servlet-class>com.google.code.kaptcha.servlet.KaptchaServlet</servlet-class>
        <init-param>
            <param-name>kaptcha.border</param-name>
            <param-value>no</param-value>
        </init-param>
        <init-param>
            <param-name>kaptcha.textproducer.char.length</param-name>
            <param-value>4</param-value>
        </init-param>
        <init-param>
            <param-name>kaptcha.textproducer.char.string</param-name>
            <param-value>abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789</param-value>
        </init-param>
        <init-param>
            <param-name>kaptcha.background.clear.to</param-name>
            <param-value>211,229,237</param-value>
        </init-param>
        <init-param>
            <!-- session.setAttribute("captcha","验证码") 存入session，便于校验-->
            <param-name>kaptcha.session.key</param-name>
            <param-value>captcha</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>cap</servlet-name>
        <url-pattern>/captcha</url-pattern>
    </servlet-mapping>
```

#### 12.4page

- capcha.jsp：

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>验证码</title>
</head>
<body>

    <form action="${pageContext.request.contextPath}/captcha/test1">
        <img id="cap" src="${pageContext.request.contextPath}/captcha" style="width:100px" οnclick="refresh();">
        <input type="text" name="captcha"/>
        <br>
        <input type="submit" value="提交">
    </form>

    <script>
        function refresh(){
            var img = document.getElementById("cap");
            img.src="${pageContext.request.contextPath}/captcha?"+new Date().getTime()
        }
    </script>
</body>
</html>
```

- CapchaController

```java
package com.zssea.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/captcha")
public class CapchaController {

    @RequestMapping("/test1")
    public String test1(String captcha, HttpSession session){
        //验证码组件生成的验证码
        String realCaptcha = (String)session.getAttribute("captcha");
        //校验
        if(realCaptcha.equalsIgnoreCase(captcha)){
            return "index";
        }
        return "error1";
    }
}
```

## 十三、REST

### 13.1开发风格

- 是一种开发风格，遵从此风格开发软件，符合REST风格，则RESTFUL。
- 两个核心要求:
  - 每个资源都有唯一的标识(URL)
  - 不同的行为，使用对应的http-method

| 访问标识                                 | 资源            |
| ---------------------------------------- | --------------- |
| http://localhost:8080/xxx/users          | 所有用户        |
| http://localhost:8080/xxx/users/1        | 用户1           |
| http://localhost:8080/xxx/users/1/orders | 用户1的所有订单 |

| 请求方式 | 标识                                     | 意图                        |
| -------- | ---------------------------------------- | --------------------------- |
| GET      | http://localhost:8080/xxx/users          | 查询所有用户                |
| POST     | http://localhost:8080/xxx/users          | 在所有用户中增加一个        |
| PUT      | http://localhost:8080/xxx/users          | 在所有用户中修改一个        |
| DELETE   | http://localhost:8080/xxx/users/1        | 删除用户1                   |
| GET      | http://localhost:8080/xxx/users/1        | 查询用户1                   |
| GET      | http://localhost:8080/xxx/users/1/orders | 查询用户1的所有订单         |
| POST     | http://localhost:8080/xxx/users/1/orders | 在用户1的所有订单中增加一个 |

## 13.2优点

- 输出json:

### 13.3使用

#### 13.3.1定义Rest风格的Controller

- @RequestMapping(value="/users",method = RequestMethod.GET)
  等价 @GetMapping("/users")

```java
package com.zssea.web;

import com.zssea.entity.User;
import org.springframework.web.bind.annotation.*;
import sun.nio.cs.US_ASCII;

import java.util.Arrays;
import java.util.List;

/**
 *  查询： 所有用户
 *        查询id=xx 某一个用户
 *  删除： id=xx 某一个用户
 *  增加： 在所有用户中 增加一个
 *  修改： 在所有用户中 修改一个
 *
 *  资源： 所有用户    /users
 *        id=xx 某个用户   /users/{id}
 */
@RestController  //这个注解如果写了 @Controller 和 类中的每个方法的 @ResponseBody 都不用加了
public class MyRestController {

    @GetMapping("/users") //必须是get请求这个路径，才能访问到
    public List<User> queryUsers(){
        System.out.println("query users with get");
        User user1 = new User(1, "张三");
        User user2 = new User(2, "李四");
        return Arrays.asList(user1,user2);
    }

    @GetMapping("/users/{id}") //必须是get请求这个路径，才能访问到
    public User queryOne(@PathVariable("id") Integer id){
        System.out.println("query one User with get "+id);
        return new User(1, "张三");
    }

    @DeleteMapping("/users/{id}") //必须是delete请求这个路径，才能访问到
    public String deleteUser(@PathVariable("id") Integer id){
        System.out.println("delete one User with delete "+id);
        return "ok";
    }

    @PostMapping("/users") //必须是post请求这个路径，才能访问到
    public String saveUser(@RequestBody User user){
        System.out.println("save User with post "+user);
        return "ok";
    }

    @PutMapping("/users")//必须是put请求这个路径，才能访问到
    public String update(@RequestBody User user){
        System.out.println("update User with put "+user);
        return "ok";
    }
}
```

#### 13.3.2ajax请求

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Rest</title>
    <script src="${pageContext.request.contextPath}/js/jquery-2.1.0.js"></script>
</head>
<body>
    <input type="button" value="queryAll" οnclick="queryAll();"/><br>
    <input type="button" value="queryOne" οnclick="queryOne();"/><br>
    <input type="button" value="saveUser" οnclick="saveUser();"/><br>
    <input type="button" value="updateUser" οnclick="updateUser();"/><br>
    <input type="button" value="deleteOne" οnclick="deleteOne();"/><br>
    <script>
        function queryAll() {
            $.ajax({
                type:"get",
                url:"${pageContext.request.contextPath}/users",
                success:function(ret){
                    console.log("查询所有：");
                    console.log(ret);
                }
            });
        }
        function queryOne() {
            $.ajax({
                type:"get",
                url:"${pageContext.request.contextPath}/users/1",
                success:function(ret){
                    console.log("查询一个：");
                    console.log(ret);
                }
            });
        }
        function saveUser() {
            var user={name:"zssea",birth:"2020-11-12 12:12:12"};
            $.ajax({
                type:"post",
                url:"${pageContext.request.contextPath}/users",
                data:JSON.stringify(user),
                contentType:"application/json",
                success:function(ret){
                    console.log("增加用户：");
                    console.log(ret);
                }
            });
        }
        function updateUser() {
            var user={id:1,name:"zssea2",birth:"2020-11-12 12:12:12"};
            $.ajax({
                type:"put",
                url:"${pageContext.request.contextPath}/users",
                data:JSON.stringify(user),
                contentType:"application/json",
                success:function(ret){
                    console.log("更新用户：");
                    console.log(ret);
                }
            });
        }
        function deleteOne() {
            $.ajax({
                type:"delete",
                url:"${pageContext.request.contextPath}/users/1",
                success:function(ret){
                    console.log("删除一个用户：");
                    console.log(ret);
                }
            });
        }
    </script>
</body>
</html>
```

## 十四、跨域请求

### 14.1域

域：协议+IP+端口

- http://localhost:8080
- http://locathost:8080
- http://ww.baidu.com:80

### 14.2 Ajax跨域问题

- Ajax发送请求时，不允许跨域，以防用户信息泄露。
- 当Ajax跨域请求时， 响应会被浏览器拦截(同源策略)，并报错。即浏览器默认不允许ajax跨域得到响应内容。(不是不能访问到对方的域，而是得不到响应，浏览器把对方的响应拦截)
- 互相信任的域之间如果需要ajax访问， (比如前后端分离项目中，前端项目和后端项目之间)，则需要额外的设置才可正常请求。

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020091120365764.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

### 14.3解决方案

- 允许其他域访问
- 在**被访问方**的Controller类上，添加注解@CrossOrigin

```java
@RestController
@RequestMapping("/origin")
@CrossOrigin("http://localhost:8080")
public class OriginController {
 ......   
}
```

- 携带对方cookie,使得session可用
- **在访问方**，ajax中添加属性: withCredentials: true

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>origin</title>
    <script src="${pageContext.request.contextPath}/js/jquery-2.1.0.js"></script>
</head>
<body>


    <input type="button" value="ajax" οnclick="cross_origin();">
    <input type="button" value="ajax2" οnclick="cross_origin2();">
    <script>

        function cross_origin(){
            $.ajax({
                type:"get",
                url:"http://localhost:9999/SpringMVC03/origin/test1",
                xhrFields: {
                    // 跨域携带cookie
                    withCredentials: true
                },
                success:function(ret){
                    console.log("ret:"+ret);
                }
            });
        }

        function cross_origin2(){
            $.ajax({
                type:"get",
                url:"http://localhost:9999/SpringMVC03/origin/test2",
                xhrFields: {
                    // 跨域携带cookie
                    withCredentials: true
                },
                success:function(ret){
                    console.log("ret:"+ret);
                }
            });
        }
    </script>
</body>
</html>
```

## 十五、SpringMVC执行流程

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200911203716611.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

## 十六、Spring整合

### 16.1整合思路

此时项目中有两个工厂

- web.xml文件是随着项目启动而加载的，里面配置基本都是servlet组件
  - DispatcherServlet 启动的springMVC工厂 == 负责生产C (Controller) 及springMVC自己的系统组件，（这是前端（核心）控制器，加载的是SpringMVC的配置文件：mvc.xml）
  - ContextLoaderlistener 启动的spring工厂 == 负责生产其他所有组件（比如service，dao及其相关），（这个类ContextLoaderlistener， 加载的是Spring的配置文件：applicationContext.xml）
  - springMVC的工厂会被设置为spring工厂的子工厂，可以随意获取spring工厂中的组件（jar包帮我们完成的）
  - 整合过程，就是累加：代码+依赖+配置。然后将service注入给controller即可

### 16.2整合技巧

- 两个工厂不能有彼此侵入，即，生产的组件不能有重合。
- 告知SpringMVC 哪些包中 存在被注解的类
  use-default-filters=true凡是被@Controller @Service @Repository注解的类， 都会被扫描，
  use- default- filters=false默认不扫描包内的任何类，只扫描include- filter中指定的类，只扫描被@Controller注解的类
- mvc.xml ：

```xml
    <!-- 注解扫描  下面这些配置是指SpringMVC在扫描com.zssea包下的注解时，只扫描有Controller注解的类（就是类名之上加了@Controller的类），其他的忽略-->
    <context:component-scan base-package="com.zssea" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"></context:include-filter>
    </context:component-scan>
```

- applicationContext.xml

```xml
    <!--扫描注解 下面这些配置是指Spring在扫描com.zssea包下的注解时，不扫描有Controller注解的类（就是类名之上加了@Controller的类，因为这样的类被SpringMVC扫描了，区分职责关系，可以对比mvc.xml文件），其他的都要扫描-->

<context:component-scan base-package="com.zssea" use-default-filters="true">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
```

### 16.3总结

在整合时，

- xml配置文件与注解之间的选择：service层，controller层的代码中，注入dao，service、bean的声明

  建议用注解，service的实现类中的方法配置事务建议用注解；

- SpringMVC配置文件（mvc.xml）中写的是：开启注解扫描、注解驱动、视图解析器、静态资源访问、拦截器、异常解析器等

- Spring配置文件（applicationContext.xml）中写的是： DataSource、SqlSessionFactory、DAO接口 MapperScannerConfigurer、扫描注解、事务管理器、开启事务注解等

- 整个项目有大致三个配置文件 web.xml 、 mvc.xml （SpringMVC配置文件）、 applicationContext.xml （Spring配置文件）， 在web.xml文件中会声明另外两个配置文件地址，当tomcat启动时，会加载web.xml文件，进而加载另外两个配置文件，创建springmvc工厂（子工厂）与spring工厂（父工厂）；

- mvc.xml文件负责生产C (Controller) 及springMVC自己的系统组件，

- 而applicationContext.xml文件中是整合了spring和mybatis两个配置文件的内容，除了数据源（DataSource）bean的配置，之前mybatis配置文件里写的内容基本都是写在(SqlSessionfactory)的bean中

- web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!-- SpringMVC前端(核心)控制器
         1. 前端，接收所有请求
         2. 启动SpringMVC工厂  mvc.xml
         3. 负责springMVC流程调度
    -->
    <servlet>
        <servlet-name>mvc_zssea</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- 局部参数，声明mvc配置文件位置-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:mvc.xml</param-value>
        </init-param>
        <!-- 懒汉式  饿汉式加载  可选-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>mvc_zssea</servlet-name>
        <url-pattern>/</url-pattern>
        <!--<url-pattern>*.action</url-pattern>-->
    </servlet-mapping>

    <!-- 此过滤器会进行：request.setCharactorEncoding("utf-8");  对post请求中，中文参数乱码有效-->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!-- 启动Spring工厂 -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>
</web-app>
```

- mvc.xml

```xml
<beans 	xmlns="http://www.springframework.org/schema/beans"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:mvc="http://www.springframework.org/schema/mvc"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
							http://www.springframework.org/schema/beans/spring-beans.xsd
							http://www.springframework.org/schema/context
							http://www.springframework.org/schema/context/spring-context.xsd
							http://www.springframework.org/schema/mvc
							http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 注解扫描  下面这些配置是指SpringMVC在扫描com.zssea包下的注解时，只扫描有Controller注解的类（就是类名之上加了@Controller的类），其他的忽略-->
    <context:component-scan base-package="com.zssea" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"></context:include-filter>
    </context:component-scan>

    <!-- 注解驱动 -->
    <mvc:annotation-driven>
        <!-- 安装FastJson,转换器 -->
        <mvc:message-converters>
            <bean class="com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter">
                <!-- 声明转换类型:json (将java对象转成josn)-->
                <property name="supportedMediaTypes">
                    <list>
                        <value>application/json</value>
                    </list>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>

    <!-- 视图解析器
        作用：1.捕获后端控制器的返回值="hello"
             2.解析： 在返回值的前后 拼接 ==> "/hello.jsp"
    -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!-- 前缀 -->
        <property name="prefix" value="/"></property>
        <!-- 后缀 -->
        <property name="suffix" value=".jsp"></property>
    </bean>

    <!-- 静态资源访问
      会额外的增加一个handler(就是Controller中的一个方法)，且其requestMapping:  "/**" 可以匹配所有请求，但是优先级最低
      所以如果其他所有的handler都匹配不上，请求会转向 "/**" ,恰好，这个handler就是处理静态资源的
      处理方式：将请求转发到tomcat中名为default的Servlet
      RequestMapping  /*   /a   /b  /c   /dxxxx    /a/b     /**
     -->
    <mvc:default-servlet-handler/>

</beans>
```

- applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/aop
                           http://www.springframework.org/schema/aop/spring-aop.xsd
                           http://www.springframework.org/schema/tx
                           http://www.springframework.org/schema/tx/spring-tx.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <!--整合时，DAO放在工厂中（bean标签）是由MapperScannerConfigurer 来帮我们完成的 ，
        而MapperScannerConfigurer 又需要sqlSessionFactory支持 ， sqlSessionFactory 又需要DataSource-->

    <!-- DataSource -->
    <!-- 导入文件-->
    <context:property-placeholder location="classpath:jdbc.properties"/>
    <!--创建数据源-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <!--基本配置-->
        <property name="driverClassName" value="${jdbc.driverClass}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>

        <!-- 配置初始化大小、最小、最大 -->
        <property name="initialSize" value="${jdbc.init}"/>
        <property name="minIdle" value="${jdbc.minIdle}"/>
        <property name="maxActive" value="${jdbc.maxActive}"/>

        <!-- 配置获取连接等待超时的时间 ms-->
        <property name="maxWait" value="60000"/>

        <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
        <property name="timeBetweenEvictionRunsMillis" value="60000"/>

        <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
        <property name="minEvictableIdleTimeMillis" value="300000"/>
    </bean>

    <!-- SqlSessionFactory -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!-- 注入连接池 -->
        <property name="dataSource" ref="dataSource"></property>
        <!-- （注册xxxDao-mapper文件）注入dao-mapper文件信息,如果映射文件和dao接口 同包且同名，则此配置可省略（要注意因为把mapper文件放在了dao文件夹下，所以要在pom.xml中更改maven编译规则）-->
        <property name="mapperLocations">
            <list>
                <value>classpath:com/zssea/dao/*.xml</value>
            </list>
        </property>
        <!-- 为实体类起别名 -->
        <property name="typeAliasesPackage" value="com.zssea.entity"></property>

        <property name="plugins">
            <array>
                <bean class="com.github.pagehelper.PageInterceptor">
                    <property name="properties">
                        <props>
                            <!-- 页号 调整到合理的值  0  max -->
                            <prop key="reasonable">true</prop>
                        </props>
                    </property>
                </bean>
            </array>
        </property>
    </bean>

    <!-- DAO接口 MapperScannerConfigurer-->
    <!-- mapperScannerConfigurer  管理DAO实现类的创建，并创建DAO对象，存入工厂管理，当执行完这个配置之后，在工厂中就会有一个id为userDAO 的bean，而这个bean就是UserDAO实现类的对象 -->
    <bean id="mapperScannerConfigurer9" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!-- dao接口所在的包  如果有多个包，可以用逗号或分号分隔 <property name="basePackage" value="com.a.dao,com.b.dao"></property>-->
        <property name="basePackage" value="com.zssea.dao"></property>
        <!-- 如果工厂中只有一个SqlSessionFactory的bean，此配置可省略 -->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"></property>
    </bean>

    <!--扫描注解 下面这些配置是指Spring在扫描com.zssea包下的注解时，不扫描有Controller注解的类（就是类名之上加了@Controller的类，因为这样的类被SpringMVC扫描了，区分职责关系，可以对比mvc.xml文件），其他的都要扫描-->
    <context:component-scan base-package="com.zssea" use-default-filters="true">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!-- 1. 引入一个事务管理器，其中依赖DataSource,借以获得连接，进而控制事务逻辑 -->
    <bean id="tx" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    <!--开启事务注解  只针对 这个@Transactional 事务注解-->
    <tx:annotation-driven transaction-manager="tx"/>
</beans>
```

- jdbc.properties

```properties
jdbc.driverClass=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatis_zssea?useUnicode=true&characterEncoding=UTF-8
jdbc.username=root
jdbc.password=123456
jdbc.init=1
jdbc.minIdle=1
jdbc.maxActive=3
```