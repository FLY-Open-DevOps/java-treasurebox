### 1.现有问题

- EL主要是用于作用域获取数据，虽然可以做运算判断，但是得到的都是一个结果，做展示。
- EL不存在流程控制。比如判断。
- EL对于集合只能做单点访问，不能实现遍历操作。比如循环。

### 2.什么是JSTL?

- JSTL ：全称Java Server Pages Standard Tag Library
- JSP标准标签库（JSTL)是一个JSP标签集合。

### 3.JSTL的作用

- 可对EL获取到的数据进行逻辑操作。
- 与EL合作完成数据的展示。

### 4.JSTL使用

- （第一步）导入两个jar文件：standard.jar 和 jstl.jar 文件拷贝到/WEB-INF/lib/下
- （第二步）在JSP页面引入标签库<% @taglib uri=“http://java.sun.com/jsp/jstl/core” prefix=“c”>

### 6.5核心标签

#### 6.5.1条件标签if判断

- 语法: <c:if test ="条件> < /cif>

```jsp
<!-- test属性中是条件， 但是条件需要使用EL表达式来书写-->
<h3>条件标签: if</h3>
<c:if test="${8>2}">
    8大于2是成立的
</c:if>
<c:if test="${8<2}">
    8小于2是成立的
</c:if>
```

#### 6.5.2多条件choose判断

- 语法：

- < c:choose>

  <c:when test=“条件 1”>结果1 < /c:when>

  <c:when test=“条件2”>结果2< /c:when>

  <c:when test=“条件3”>结果3< /c:when>

  <c:otherwise >结果4< /c:otherwise>

  < /c:choose>

```jsp
<h3>条件标签: choose(等价于java中多重if)</h3>
<%-- 测试成绩等级>90优秀 >80 良好  >70中等  >60及格--%>
<c:set var="score" value="80"></c:set>
<C :choose>
    <c:when test="${score>=90 }">优秀</c:when>
    <c:when test="${score>=80 }">良好</c:when>
    <c:when test="${score>=70 }">中等</c:when>
    <c:when test="${score>=60 }">及格</c:when>
    <c :otherwise>不及格</c:otherwise>
< /c :choose>
```

#### 6.5.3迭代foreach标签

- 语法

  <c:foreach

  var=“变量名”

  items=“集合”

  begin=“起始下标”

  end=“结束下标”

  step=“间隔长度”

  varstatus=”遍历状态” >

  < /c:foreach>

```jsp
<h3>测试list集合遍历获取学生列表</h3>
<table border="1" width=" 80%" bordercolor="red" cellspacing="0" align= " center ">
	<tr>
        <th>学号</th>
        <th>姓名</th>
        <th>成绩</th>
        <th>班级</th>
        <th>是否是第一个</th>
        <th>是否是最后一个</th>
        <th>计数count</th>
        <th>索引index</th>
    </tr>
    <!-- varStatus :变量状态:遍历出的每一项内容的状态:
    first是否是第一行
    last是否是最后 一行
    count当前行数
    index当前元素的下标-->
    
    <!-- var :遍历出的每-项使用变量先存储
    items:集合(使用E1表达式)-->
    <c:forEach var="stu" items="${students}" varStatus="vs" >
        <tr>
            <td>${stu.1d}</td>
            <td>${stu.name }</td>
            <td>${stu.score}</td>
            <td>${stu.classes}</td>
            <td>${vs.first}</td>
            <td>${vs.last}</td>
            <td>${vs.count}</td>
            <td>${vs.index}</td>
        </tr>
    </c:forEach>
</table>
```

代码：

```jsp
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.zssea.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>JSTL标签库1</title>
</head>
<body>
    <%
        request.setAttribute("username","tom123");
        request.setAttribute("age",58);

        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        request.setAttribute("list",list);

        List<User> users = new ArrayList<>();
        users.add(new User("tom","123456"));
        users.add(new User("marry","123456"));
        users.add(new User("jack","123456"));
        users.add(new User("gavin","123456"));
        request.setAttribute("users",users);
    %>
    ${username}
    <c:if test="${username eq 'tom'}">
        <h1>欢迎您，${username}</h1>
    </c:if>
    <c:if test="${username ne 'tom'}">
        <h1>请您重新登录！</h1>
    </c:if>

    <hr/>
    <c:choose>
        <c:when test="${age < 18}" ><h1>少年</h1></c:when>
        <c:when test="${age>=18 && age<30}"><h1>青年</h1></c:when>
        <c:when test="${age >=30 && age<50}"><h1>中年</h1></c:when>
        <c:otherwise><h1>老年</h1></c:otherwise>
    </c:choose>

    <c:forEach var="s" items="${list}" begin="0" end="4" step="1" varStatus="i">
        <h1>${s}&nbsp;&nbsp;${i.first}&nbsp;&nbsp;${i.last}&nbsp;&nbsp;${i.count}&nbsp;&nbsp;${i.index}</h1>
    </c:forEach>
    <hr/>
    <%
        for(String s : list){
            out.println(s);
        }
    %>

    <hr/>
    <c:forEach var="user" items="${users}">
        <h1>${user.username}:${user.password}</h1>
    </c:forEach>
</body>
</html>
```

#### 6.5.4 url 标签

- 在Cookie禁用的情况下，通过重写URL拼接JSESSIONID来传递ID值。便于下一次访问时仍可查找到上一次的Session对象。

```jsp
<c:url context='${pageContext.request.contextPath}' value='/xxxController' />
//在form表单的action中嵌套动态路径
<form action=" <c:url context='${pageContext.request.contextPath}' value='/xxxController' />">
    
</form>
```

- 经验：所有涉及到页面跳转或者重定向跳转时，都应该使用URL重写。

代码:

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>JSTL标签库2</title>
</head>
<body>
    <%
        String newURL = response.encodeRedirectURL(request.getContextPath()+"/jstl/jstl1.jsp");
    %>
    <%=newURL%>

    <a href="<%=response.encodeRedirectURL(request.getContextPath()+"/jstl/jstl1.jsp")%>">跳转</a>
    <br/>
    <c:url context="${pageContext.request.contextPath}" value="/jstl/jstl1.jsp"></c:url>
    <a href="<c:url context='${pageContext.request.contextPath}' value='/jstl/jstl1.jsp'></c:url>">跳转2</a>
</body>
</html>
```

### 6.6整合

将现有的EmpProject项目逬行整合，使用EL+JSTL替换脚本代码

```jsp
<%@ page import="java.util.List" %>
<%@ page import="com.zssea.emp.entity.Emp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

    <%--
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
    --%>

        
        <c:forEach var="emp" items="${requestScope.emps}">
            <tr>
                <td>${emp.id}</td>
                <td>${emp.name}</td>
                <td>${emp.salary}</td>
                <td>${emp.age}</td>
                <td><a href=" <c:url context='${pageContext.request.contextPath}'
                    value='/manager/safe/removeEmpController?id=${emp.id}'></c:url>">删除</a></td>
                <td><a href=" <c:url context='${pageContext.request.contextPath}'
                    value='//manager/safe/showEmpController?id=${emp.id}'></c:url>">修改</a></td>
            </tr>
        </c:forEach>

    </table>
</body>
</html>


<%@ page import="com.zssea.emp.entity.Emp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>修改员工信息页面</title>
</head>
<body>
<%--
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
--%>
    
    <form action="<c:url context='${pageContext.request.contextPath}' value='/manager/safe/updateEmpController'></c:url>
        " method="post">
        编号：<input type="text" name="id" value="${emp.id}" readonly><br/>
        姓名：<input type="text" name="name" value="${emp.name}"><br/>
        工资：<input type="text" name="salary" value="${emp.salary}"><br/>
        年龄：<input type="text" name="age" value="${emp.age}"><br/>
        <input type="submit" value="修改">
    </form>
</body>
</html>
```