### 1.概念

- EL使JSP写起来更简单、简洁。
- **主要用于获取作用域中的数据**

### 2.作用

- 用于替换 作用域对象.getAttribute(“name”);

### 3. EL的应用1（获取基本类型、字符串）

- ${scope.name}获取具体某个作用域中的数据
- ${name}获取作用域中的数据，逐级查找(pageContext、request、session、application)，这种方式需要确定name 的值是唯一的

#### 3.1 EL应用案例

```jsp
<%
	//存储在request作用域 
	request.setAttribute("name","tom"); 
    request.setAttribute("age",18);
%>

${requestScope.name}  <%--获取request作用域中name对应的值，找到就返回，没找到返回" "--%>
${name} <%--从最小作用域逐级查找name对应的值，找到就返回，没找到返回" " --%>
12345678
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EL表达式</title>
</head>
<body>
    <%
        request.setAttribute("key1","value1");
        session.setAttribute("key2","value2");
        application.setAttribute("key3","value3");

        request.setAttribute("key666","value6");
        session.setAttribute("key666","value7");
        application.setAttribute("key666","value8");
    %>
    <h1>通过作用域对象获取：</h1>
    <!--通过作用域对象获取数据 -->
    <h1><%=request.getAttribute("key8")%></h1>
    <h1><%=session.getAttribute("key2")%></h1>
    <h1><%=application.getAttribute("key3")%></h1>

    <hr/>
    <h1>通过EL表达式获取数据：</h1>
    <!--通过EL表达式获取数据 -->
    <h1>${requestScope.key666}</h1>
    <h1>${sessionScope.key666}</h1>
    <h1>${applicationScope.key666}</h1>

    <hr/>
    <h1>${key666}</h1>
    <h1>${key666}</h1>
    <h1>${key666}</h1>
</body>
</html>
```

#### 3.2EL和JSP脚本的区别

- <%=request.getAttribute() %> 没有找到返回 null
- ${requestScope.name}没找到返回 " "(空，就是不显示任何东西)

### 4. EL的应用2（获取引用类型）

- 使用EL获取作用域中的对象调用属性时，只能访问对象的get方法，必须遵守命名规范定义(实体类要有get方法)

```jsp
<%
    Emp e = new Emp(); 
    e.setName("gavin"); 
	e.setAge(19);
    request.setAttribute("e" ,e);
%>

${requestScope.e.name} <%--调用getName()方法--%>
12345678
<%@ page import="com.zssea.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EL获取对象</title>
</head>
<body>
    <%
        User user = new User("gavin","123456");
        request.setAttribute("user",user);
    %>

    <%
        User u = (User)request.getAttribute("user");
        out.println(u.getUsername());
        out.println(u.getPassword());
    %>
    <hr/>
    ${requestScope.user}<br/>
    ${user}<br/>
    ${user.username}<br/><%--调用getUsername()--%>
    ${user.password}<br/>
</body>
</html>
```

### 5. EL的应用3（获取数组、集合的元素）

- EL可以获取Array、List、Map中的元素，Set由于没下标，无法直接访问元素，后续可遍历

```jsp
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>数组、集合的访问</title>
</head>
<body>
    <%
        int[] array = new int[]{1,2,3,4,5};
        request.setAttribute("array",array);

        List<String> nums = new ArrayList<String>();
        nums.add("A");
        nums.add("B");
        nums.add("C");
        request.setAttribute("nums",nums);

        Map<String,String> maps = new HashMap<String,String>() ;
        maps.put ("CN","中国");
        maps.put ("US", "美国");
        maps.put ("IT", "意大利");
        request.setAttribute("maps", maps) ;


    %>
<!--EL访问数组 -->

    ${array[1]}<br/>
    ${array[2]}<br/>
    ${array[3]}<br/>

    <hr/>
    ${nums[0]}<br/>
    ${nums[1]}<br/>
    ${nums.get(2)}

    <hr/>
    ${requestScope.maps["CN"]}<br/>
    ${maps.US}<br/>
    ${maps["IT"]}

</body>
</html>
```

### 6. EL的运算符

| 操作符     | 描述                             |
| ---------- | -------------------------------- |
| .          | 访问一个Bean属性或者一个映射条目 |
| []         | 访问一个数组或者链表的元素       |
| +          | 加                               |
| -          | 减或负                           |
| *          | 乘                               |
| / or div   | 除                               |
| % or mod   | 取模                             |
| ==or eq    | 测试是否相等                     |
| !=or ne    | 测试是否不等                     |
| < or lt    | 测试是否小于                     |
| >or gt     | 测试是否大于                     |
| <=or le    | 测试是否小于等于                 |
| >=or ge    | 测试是否大于等于                 |
| &&orand    | 测试逻辑与                       |
| \|\| or or | 测试逻辑或                       |
| ! or not   | 测试取反                         |
| empty      | 测试是否空值                     |

#### 6.1 EL表达式执行运算

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EL表达式运算符</title>
</head>
<body>
    <%
        request.setAttribute("nums",1234);
        request.setAttribute("ss","b");
    %>
    <h1>empty运算符</h1>
    <h1>${empty ss}</h1><!--测试ss 是否为空 ， null和" " 都是空-->

    <hr/>
    <h1>算术运算符</h1>
    <h1>${nums + 5 } </h1>
    <h1>${nums - 5 } </h1>
    <h1>${nums * 5 } </h1>
    <h1>${nums div 5 } </h1>
    <h1>${nums mod 5 } </h1>

    <hr/>
    <h1>关系运算符</h1>
    <h1>${nums eq 1234}</h1>
    <h1>${nums ne 1234}</h1>
    <h1>${nums gt 1234}</h1>
    <h1>${nums lt 1234}</h1>
    <h1>${nums ge 1234}</h1>
    <h1>${nums le 1234}</h1>

    <h1>逻辑运算符</h1>
    <h1>${nums > 1000 and nums !=1200}</h1>
    <h1>${nums > 1000 or nums == 2000}</h1>
    <h1>${not(num > 1234)}</h1>
</body>
</html>
```

#### 6.2 empty 关键字

```jsp
<%
    String s1=" ";
    pageContext.setAttribute("s1",s1);
    String s2=null;
    pageContext.setAttribute("s2",s2);
    String s3="122222";
    pageContext.setAttribute("s3",s3);
    List listl =new ArrayList(); 
	pageContext.setAttribute( "list1", list1);
%>

<!-- empty关键只要内容是空true -->

${empty s1}<br>
${empty s2}<br>
${empty s3}<br>
${empty listl}<br>
```

### 7.隐式对象

- EL表达式语言定义了 11个隐式对象

| 隐含对象         | 描述                          |
| ---------------- | ----------------------------- |
| pageScope        | page作用域                    |
| requestScope     | request作用域                 |
| sessionScope     | session作用域                 |
| applicationScope | application 作用域            |
| param            | Request对象的参数，字符串     |
| paramValues      | Request对象的参数，字符串集合 |
| header           | HTTP信息头，字符串            |
| headerValues     | HTTP信息头，字符串集合        |
| initParam        | 上下文初始化参数              |
| cookie           | Cookie 值                     |
| pageContext      | 当前页面的pageContext         |

#### 7.1获得应用上下文

```jsp
<%=request.getContextPath() %> 
${pageContext.request.contextPath}
12
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EL内置对象</title>
    <link href="${pageContext.request.contextPath}/css/xxx.css">
</head>
<body>

    <%
        String path = request.getContextPath();
    %>
    <%=path%>

    <br/>
    <a href="<%=request.getContextPath()%>/manager/safe/xxxController">Click me</a><br/>
    <a href="${pageContext.request.contextPath}/manager/safe/xxxController">Click target</a>
</body>
</html>
```

#### 7.2获取Cookie对象

```jsp
${cookie.username}//获取名为 username 的 cookie 对象 
${cookie.password} //获取名为 password 的cookie 对象 
${cookie.password.value}//获取password 的cookie 的value值
123
package com.zssea.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CookieServlet",value = "/cookie")
public class CookieServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie cookie = new Cookie("username","tom");
        Cookie cookie1 = new Cookie("password","123456");

        response.addCookie(cookie);
        response.addCookie(cookie1);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}



<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>获取Cookie的EL</title>
</head>
<body>
    <!--之前的做法-->
    <%
        Cookie[] cookies = request.getCookies();
        String username = "";
        String password = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                }
                if(cookie.getName().equals("password")){
                    password = cookie.getValue();
                }
            }
        }
    %>
    <%=username%> <br/>
    <%=password%>

    <hr/>
    <!--EL-->
    <h1>${cookie.username.value}</h1>
    <h1>${cookie.password.value}</h1>

    <!--自动登录-->
    <input type="text" name="username" value="<%=username%>"><br/>
    <input type="text" name="password" value="<%=password%>"><br/>
    <input type="text" name="username" value="${cookie.username.value}"><br/>
    <input type="text" name="username" value="${cookie.password.value}"><br/>
</body>
 </html>
```