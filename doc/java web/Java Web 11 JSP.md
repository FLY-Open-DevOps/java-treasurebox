## 一、引言

### 1.1现有问题

- 在之前学习Servlet时，服务端通过Servlet响应客户端页面，有什么不足之处?
  - 开发方式麻烦：继承父类、覆盖方法、配置Web.xml或注解
  - 代码修改麻烦：重新编译、部署、重启服务
  - 显示方式麻烦：获取流、使用println(" "); 逐行打印
  - 协同开发麻烦：UI负责美化页面，程序员负责编写代码。UI不懂Java, 程序员又不能将所有前端页面的内容通过流输出

## 二、JSP (Java Server Pages)

### 2.1概念

- 简化的Servlet设计，在HTML标签中嵌套Java代码，用以高效开发Web应用的动态网页

### 2.2作用

- 替换显示页面部分的Servlet (使用*.jsp文件替换XxxJSP.java)

## 三、JSP开发【重点】

### 3.1创建JSP

- 在web目录下新建*.jsp文件（与WEB-INF平级）

#### 3.1.1 JSP编写Java代码

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<tit1e>This 1s my first page</title>
</head>
<body>
	 NOW: <%= new java.util.Date() %>
</body>
</html>
```

- 使用<%=%> 标签编写Java代码在页面中打印当前系统时间

### 3.1.2 访问JSP

- 在浏览器输入 http://ip:port/项目路径/资源名称

### 3.2 JSP与Servlet

- 关系
  - JSP文件在容器中会转换成Servlet执行。
  - JSP是对Servlet的一种高级封装。本质还是Servlet。
- 区别
  - 与Servlet相比：JSP可以很方便的编写或者修改HTML网页而不用去面对大量的println语句。
- JSP 与 Servlet 区别

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200813194128286.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)



### 3.3 JSP实现原理

- Tomcat会将xxx.jsp转换成Java代码，进而编译成.class文件运行，最终将运行结果通过response响应给客户端。
- JSP实现原理

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200813194144205.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

#### 3.3.1 JSP.java源文件存放目录

- 使用IDEA开发工具，Tomcat编译后的JSP文件 (Xxx jsp.class 和Xxx jsp.java) 的存放地点:
  - C:\Users\Administrator.IntelliJIdea2019.3\system\tomcat\Tomcat_7_0_90_javaweb_jsp

## 四、JSP与HTML集成开发

### 4.1脚本

- 脚本可以编写Java语句、变量、方法或表达式。

#### 4.1.1普通脚本(代码脚本)

- 语法：<% Java代码 ％>

```jsp
<html>
<head><title>Hello World</title></head> 
<body>
Hello World!<br/>
<%
     int a =10; //这是一个局部变量
    //jsp中，使用小脚本嵌入java代码！ 
    out.println(a);//打印内容在客户端页面
    System.out.println(a);//打印内容在控制台
%>
</body>
</html>
```

- 经验：普通脚本可以使用所有Java语法，除了定义函数。
- 注意：脚本与脚本之间不可嵌套，脚本与HTML标签不可嵌套

#### 4.1.2声明脚本

- 语法：<%! 定义变量、函数 ％>

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>脚本的使用</title>
</head>
<body>

    <%!
        int b = 20;//这是一个全局变量
        public void paly(){
            System.out.println("paly...............");//在控制台输出
        }
        public int num(){
            return 100;
        }
    %>
    <%
        out.println(b);
        paly(); //调用 声明脚本中的无返回值方法
        int num = num();调用 声明脚本中的有返回值方法
        out.println(num);
    %>
    <%=num()%>
</body>
</html>
```

- 注意：声明脚本声明的变量是全局变量。(声明脚本 只能定义变量与函数，不能做访问的操作)
- 声明脚本的内容必须在普通脚本<%%>中调用。
- 如果声明脚本中的函数具有返回值，可以使用输出脚本调用<%= %>

#### 4.1.3输出脚本

- 语法：<%= Java表达式 ％> （就是 out.println(Java表达式);）

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<tit1e>This 1s my first page</title>
</head>
<body>
	 NOW: <%= new java.util.Date() %>
</body>
</html>
```

- 经验：输出脚本可以输出带有返回值的函数
- 注意：输出脚本中不能加；（英文分号）

### 4.2 JSP注释

- JSP注释主要有两个作用：为脚本代码作注释 以及 HTML内容注释。

#### 4.2.1语法规则

| 语法           | 描述                                                 |
| -------------- | ---------------------------------------------------- |
| <%-- 注释 --%> | JSP注释，注释内容不会被发送至浏览器甚至不会被编译    |
| < !-- 注释 --> | HTML注释，通过浏览器查看网页源代码时可以看见注释内容 |

```jsp
<%@ page language:"java" contentType="text/htnil; charset=UTF-8
pageEncoding="UTF-8"%>
<html>
<head>
    <title>脚本的使用</title>
</head>
<body>
<%-- JSP注释在网页中不会被显示--%>
<!-- HTML注释在网页源代码中会显示-->
<p>
     NOW: <%= new java.util.Date() %>
</p>

</body>
</html>
```

### 4.3 JSP指令

- JSP指令用来设置与整个JSP页面相关的属性。

| 指令            | 描述                                                      |
| --------------- | --------------------------------------------------------- |
| <%@ page …％>   | 定义页面的依赖属性，比如脚本语言、error页面、缓存需求等等 |
| <%@ include…%〉 | 包含其他文件                                              |
| <%@ taglib… %>  | 引入标签库的定义，可以是自定义标签                        |

#### 4.3.1 page 指令

- 语法：<%@ page attributel=“value1” attribute2=“value2” %>
- Page指令为容器提供当前页面的使用说明。一个JSP页面可以包含多个page指令。

| 属性         | 描述                                                         |
| ------------ | ------------------------------------------------------------ |
| contentType  | 指定当前JSP页面的MIME类型和字符编码格式                      |
| error Page   | 指定当JSP页面发生异常时需要转向的错误处理页面                |
| isErrorPage  | 指定当前页面是否可以作为另一个JSP页面的错误处理页面          |
| import       | 导入要使用的Java类                                           |
| language     | 定义JSP页面所用的脚本语言，默认是Java                        |
| session      | 指定JSP页面是否使用session。默认为true立即创建，false为使用时创建 |
| pageEncoding | 指定JSP页面的解码格式                                        |

#### 4.3.2 include 指令

- 语法：<%@ include file =“被包含的JSP路径” %>
- 通过indude指令来包含其他文件。
- 被包含的文件可以是JSP文件、HTML文件或文本文件。包含的文件就好像是当前JSP文件的一部分，会被同时编译执行（静态包含）。

```jsp
<%@ include file="header.jsp"%> 
...
...
<%@ include file="footer.jsp" %>
```

- 注意：可能会有重名的冲突问题，不建议使用。

#### 4.3.3 taglib 指令

- 语法：<%@ taglib uri=“外部标签库路径” prefix=" 前缀"%>
- 引入JSP的标准标签库

```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
```

### 4.4动作标签

- 语法：<jsp:action_name attribute=“value” />
- 动作标签指的是JSP页面在运行期间的命令

#### 4.4.1include

- 语法：<jsp:include page=“相对 URL 地址”/>

• < jsp:indude>动作元素会将外部文件输出结果包含在JSP中（动态包含）。

| 属性 | 描述                        |
| ---- | --------------------------- |
| page | 包含在页面中的相对URL地址。 |

```jsp
  < jsp :include page="index.jsp"/>     
```

- 注意：前面已经介绍过include指令，它是将外部文件的输出代码复制到了当前JSP文件中。而这里的jsphdiide动作不同，是将外部文 件的输出结果引入到了当前JSP文件中。

#### 4.4.2 useBean

- 语法：<jsp:useBean id=“name” class=“package.className” />
- jsp:useBean动作用来加载一个将在JSP页面中使用的JavaBean。

```jsp
<jsp:useBean id="user" class="com.zssea.entity.User"/>
```

- 在类载入后，我们可以通过jsp:setProperty和jsp:getProperty动作来修改和获取bean的属性。

#### 4.4.3 setProperty

- 可以在jsp:useBean元素之后使用jsp:setProperty进行属性的赋值

| 属性     | 描述                                             |
| -------- | ------------------------------------------------ |
| name     | name属性是必需的。它表示要设置属性的是哪个Bean。 |
| property | property属性是必需的。它表示要设置哪个属性。     |
| value    | value属性是可选的。该属性用来指定Bean属性的值。  |

```jsp
<jsp:useBean id="user"  class="com.zssea.entity.User" />     
<jsp:setProperty name="user" property="name" value="gavin" />
```

#### 4.4.4 getProperty

- jsp:getProperty动作提取指定Bean属性的值，转换成字符串，然后输出。

| 属性     | 描述                                   |
| -------- | -------------------------------------- |
| name     | 要检索的Bean属性名称。Bean必须已定义。 |
| property | 表示要提取Bean属性的值                 |

```jsp
<jsp:useBean id="user" class="com.zssea.entity.User" /> 
<jsp:setProperty name="user" property="name" value="gavin" />                         <jsp:getProperty name="user" property= "name1" />
```

#### 4.4.5 forward

- 语法：<jsp:forward page="相对 URL 地址” />
- jsp:forward动作把请求转到另外的页面。

| 属性 | 描述                          |
| ---- | ----------------------------- |
| page | page属性包含的是一个相对URL。 |

```jsp
  <jsp:forward page="index.jsp" />     
```

#### 4.4.6 param

- 语法：<jsp:param name=" " value=""/〉
- 在转发动作内部使用，做参数传递

```jsp
<jsp :forward page="index. jsp">
    <!-- http请求参数传递-->
    <jsp:param name="sex" value= "nan" />
</jsp:forward>
```

### 4.5内置对象

- 由JSP自动创建的对象，可以直接使用

| 对象名      | 类型                                   | 说明                        |
| ----------- | -------------------------------------- | --------------------------- |
| request     | javax.servlet.http.HttpServletRequest  |                             |
| response    | javax.servlet.http.HttpServletResponse |                             |
| session     | javax.servlet.http.HttpSession         | 由 session=“true”开关       |
| application | javax.servlet.ServletContext           |                             |
| config      | javax.servlet.ServletConfig            |                             |
| exception   | java.Iang.Throwable                    | 甶 isErrorPage=“false”开关  |
| out         | javax.servlet.jsp.JspWriter            | javax.servlet.jsp.JspWriter |
| pageContext | javax.servlet.jsp. PageContext         |                             |
| page        | java.lang.Object 当前对象 this         | 当前servlet实f列            |

#### 4.5.1四大域对象

- JSP有四大作用域对象，存储数据和获取数据的方式一样，不同的是取值的范围有差别
  - pageContext (javax.servlet.jsp.PageContext)当前JSP页面范围
  - request (javax.servlet.http.HttpServletRequest)—次请求有效
  - session (javax.servlet.http.HttpSession)—次会话有效(关闭浏览器失效）
  - application (javax.servlet.ServletContext)整个Web应用有效(服务器重启或关闭失效)

#### 4.5.2 pageContext 对象

- pageContext对象是javax.servlet.jsp.PageContext类的实例，拥有作用域，用来代表整个JSP页面。
  - 当前页面的作用域对象，一旦跳转则失效
  - 通过 setAttribute(“name”,value);存储值
  - 通过 getAttribute("namen);获取值
  - 用于获取其他8个内置对象或者操作其他对象的作用域

```jsp
<%
pageContext.setAttribute( name , value) ;//当前页面作用域有效
%>
```

#### 4.5.3 pageContext获取其他内置对象

```jsp
<%
pageContext .getRequest();//返回request内对象 
pageContext .getResponse();//返回response内S对象 
pageContext .getServletConfig();//返回config内置对象 
pageContext .getException();//返回exception内置对象 
pageContext .getPage();//返回page内置对象 (this)
pageContext .getOut();//返回out内置对象

pageContext .getServletContext();//返回 application 内置对象 
pageContext .getSession();//返回session内置对象
%>
```

#### 4.5.4 pageContext操作其他内置对象的作用域

- pageContext对象可以操作其他作用域存储和获取。

```jsp
 <%
        pageContext.setAttribute("page","123");//当前JSP有效
        pageContext.setAttribute("req","aaa",PageContext.REQUEST_SCOPE);//request作用域 
        pageContext.setAttribute("sess","bbb",PageContext.SESSION_SCOPE);//session作用域
        pageContext.setAttribute("app","ccc",PageContext.APPLICATION_SCOPE); //application作用域

//        String req = (String)request.getAttribute("req");
//        String sess = (String)session.getAttribute("sess");
//        String app = (String) application.getAttribute("app");
		String page= (String)pageContext .getAttribute("page"); //当前页面作用域
        String req = (String) pageContext.getAttribute("req",PageContext.REQUEST_SCOPE);//request作用域
        String sess = (String) pageContext.getAttribute("sess",PageContext.SESSION_SCOPE);//session作用域
        String app = (String) pageContext.getAttribute("app",PageContext.APPLICATION_SCOPE);//application作用域

        //pageContext、request、session、application
        String result = (String)pageContext.findAttribute("app");
    %>
    <h1>request:<%=req%></h1>
    <h1>session:<%=sess%></h1>
    <h1>application:<%=app%></h1>
    <h1>find:<%=result%></h1>
```

### 4.6整合

- 将EmpProject项目所有显示页面JSP的Servlet替换为JSP页面，使用脚本进行显示
- 就是（128 Servlet_11 _综合案例 ）

```jsp
<%@ page import="java.util.List" %>
<%@ page import="com.zssea.emp.entity.Emp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>显示所有员工</title>
</head>
<body>
    <table border="1">
        <tr>
            <td>编号</td>
            <td>姓名</td>
            <td>工资</td>
            <td>年龄</td>
            <td colspan="2">操作</td>
        </tr>
    <%
        List<Emp> emps = (List<Emp>)request.getAttribute("emps");
        for (Emp emp:emps){
    %>
        <tr>
            <td><%=emp.getId()%></td>
            <td><%=emp.getName()%></td>
            <td><%=emp.getSalary()%></td>
            <td><%=emp.getAge()%></td>
            <td><a href="<%= request.getContextPath()+"/manager/safe/removeEmpController?id="+emp.getId()%>">删除</a></td>
            <td><a href="<%= request.getContextPath()+"/manager/safe/showEmpController?id="+emp.getId()%>">修改</a></td>
        </tr>
    <%
        }
    %>
    </table>
</body>
</html>


<%@ page import="com.zssea.emp.entity.Emp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改员工信息页面</title>
</head>
<body>
    <%
        Emp emp = (Emp) request.getAttribute("emp");
    %>

    <!--<form action="/empproject/manager/safe/updateEmpController" method="post"> -->
    <form action="<%=request.getContextPath()+"/manager/safe/updateEmpController" %>" method="post">
        编号：<input type="text" name="id" value="<%=emp.getId()%>" readonly><br/>
        姓名：<input type="text" name="name" value="<%=emp.getName()%>"><br/>
        工资：<input type="text" name="salary" value="<%=emp.getSalary()%>"><br/>
        年龄：<input type="text" name="age" value="<%=emp.getAge()%>"><br/>
        <input type="submit" value="修改">
    </form>
</body>
</html>
```