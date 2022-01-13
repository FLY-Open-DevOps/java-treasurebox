## 一、Servlet【重点】

### 1.1概念

- Servlet: Server Applet的简称，是服务器端的程序（代码、功能实现），可交互式的处理客户端发送到服务端的请求，并完成操 作响应。
- 动态网页技术
- JavaWeb程序开发的基础，JavaEE规范（一套接口）的一个组成部分。

#### 1.1.1 Servlet 作用

- 接收客户端请求，完成操作。
- 动态生成网页（页面数据可变）。
- 将包含操作结果的动态网页响应给客户端（浏览器）。

### 1.2 Servlet开发步骤（未用开发工具）

#### 1.2.1搭建开发环境

- 将Servlet相关jar包 (lib\servlet-api.jar) 配置到classpath（系统变量）中

#### 1.2.2 编写 Servlet

- 实现 javax.servlet.Servlet
- 重写5个主要方法
- 在核心的service（）方法中编写输出语句，打印访问结果

```java
package com.qf.servlet; 
import javax.servlet.Servlet;
import javax.servlet.ServletConfig; 
import javax.servlet.ServletRequest; 
import javax.servlet.ServletResponse;
import javax.servlet.ServletException; 
import java.io.IOException;

public class MyServlet implements Servlet{

public void init(ServletConfig config) throws ServletException{}

public void service(ServletRequest request,ServletResponse response) throws 	ServletException,IOException{ 
    System.out.println("My First Servlet!");
}

public void destroy(){}
public ServletConfig getServletConfig(){ return null;}
public String getServletInfo(){ return null;}
```

#### 1.2.3 部署 Servlet

- 编译MyServlet后，将生成的 **.class**文件放在WEB-INF/classes文件中。（之前在tomcat/webapps下创建的myweb项目中）

#### 1.2.4 配置 Servlet

- 编写WEB-INF下项目配置文件web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi = "http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.jcp.org/xml/ns/]avaee" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd" version="3.1">

<!--1、添加servlet节点-->
    <servlet>
        <servlet-name>my</servlet-name>
        <servlet-class>MyServlet</servlet-class>
    </servlet>

<!--2、添加servlet-mapping节点-->
	<servlet-mapping>
        <servlet-name>my</servlet-name>
        <url-pattern>/myservlet</url-pattern> <!--访问路径-->
    </servlet-mapping>
</web-app>
```

- 注意：url-pattern配置的内容就是浏览器地址栏输入的URL中项目名称后资源的内容

### 1.3运行测试

- 启动Tomcat,在浏览器地址栏中输入http://localhost:8080/myweb/myservlet访问，在Tomcat中打印时间表示成功。

### 1.4常见错误

#### 1.4.1 500错误

- 服务端出现异常

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808192544449.jpg)

## 二、IDEA创建Web项目

### 2.1 IDEA创建Web项目

- 创建项目窗口，选择JavaEE7,并勾选Web Application

| 创建web应用                                                  |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808192618317.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |

- 输入项目名称和项目保存位置，点击Finish,完成项目创建

| 项目目录设置                                                 |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808192634452.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |

- Web项目目录介绍

| 目录结构                                                     |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808192708141.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |

### 2.2 IDEA开发Servlet

使用开发工具编写Servlet,仍要手工导入 servlet-api.jar文件，并与项目关联。

#### 2.2.1 编写 Servlet

创建MyServlet,实现Servlet接口，覆盖5个方法

```java
package com.zssea.servlet;

import javax.servlet.*;
import java.io.IOException;

public class MyServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {}

    @Override
    public ServletConfig getServletConfig() { return null;}

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("My First Web Project");
    }

    @Override
    public String getServletInfo() { return null;}

    @Override
    public void destroy() {}
}
```

#### 2.2.2 **配置** web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
    
    <!--1、添加servlet节点-->
    <servlet>
        <servlet-name>my</servlet-name>
        <servlet-class>com.zssea.servlet.MyServlet</servlet-class>
    </servlet>
    <!--2、添加servlet-mapping节点-->
    <servlet-mapping>
        <servlet-name>my</servlet-name>
        <url-pattern>/myservlet</url-pattern>
    </servlet-mapping>
</web-app>
```

#### 2.2.3部署Web项目(手动)

- 在Tomcat的webapps目录下，新建WebProject项目文件夹
  - 创建WEB-INF,存放核心文件
  - 在WEB-INF下，创建classes文件夹，将编译后的MyServlet.class文件复制至此。
- 问题：每当我们编写了新的Servle域者重新编译，都需要手工将新的.class部署到Tomcat中，较为麻烦。如何实现自动部署?

### 2.3 IDEA部署Web项目

- 前面我们是在Tomcat的webapps目录新建应用程序目录myweb，然后把静态资源和Servlet复制到相关目录下。使用IDEA不需要我们复制 了。可以通过IDEA集成Tomcat服务器，实现自动部署。

#### 2.3.1 IDEA 集成 Tomcat

- 点击File选项，选择Settings
- 选择Build, Execution, Deployment下的Application Servers。
- 点击+号，选择Tomcat Server
- 选择Tomcat安装目录，点击OK即可
- 最后，点击OK

| IDEA 集成 Tomcat                                             |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808192912465.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808192936470.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |

#### 2.3.2项目部署Tomcat

- 点击Add Configuration
- 点击+号，选择Tomcat Server,选择Local
- 点击+号，选择Artifact,添加当前项目
- 点击运行按钮，即可运行项目

| 项目部署Tomcat                                               |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808192958403.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808193026324.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808193100683.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808193118593.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |

### 2.4其他操作

#### 2.4.1关联第三方jar包

- 在WEB-INF目录下新建lib目录
- 输入lib目录
- 复制jar包到lib目录中
- 右击lib目录，选择Add as Library…
- 选择Project Library,完成。
  - Global Library表示所有工程都可以使用。
  - Project Library表示当前工程中所有模块都可以使用。
  - Module Library表示当前模块可以使用。

| 关联第三方jar包                                              |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/2020080819314628.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808193200124.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70) |

#### 2.4.2如何导出war包

- 项目完成后，有时候需要打成war方便部署。war包可以直接放入Tomcat的webapps目录中，启动Tomcat后自动解压，即可访问。
- 要注意：导出war包部署到tomcat的webapps下之后，如果对项目再进行修改，需要重新导出war，重新部署
- 点击项目结构
- 选择Artifacts,点击+号
- 选择Archive---->For…
- 构建项目
- 在out\artifacts\目录中,查看生产的war包，把war放入Tomcat的webapps目 录，启动Tomcat自动解压即可访问。

导出war包

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808193249387.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808193256386.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808193304201.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808193312939.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808193320355.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808193407445.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808193418862.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)