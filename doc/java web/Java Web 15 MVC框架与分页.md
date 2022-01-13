## 一、MVC框架(Model-View-Controller)

### 1.1.MVC概念

- MVC又称为编程模式，是一种软件设计思想，将数据操作、页面展示、业务逻辑分为三个层级（模块），独立完成，相互调用
- 模型层（Model)
- 视图（View)
- 控制器（Controller)

### 1.2. MVC模式详解

- MVC并不是Java独有的，现在几乎所有的B/S的架构都采用了MVC模式。
  - 视图View：视图即是用户看到并与之交互的界面，比如HTML (静态资源），JSP (动态资源）等等。
  - 控制器Controller：控制器即是控制请求的处理逻辑，对请求进行处理，负责流程跳转(转发和重定向)。
  - 模型Model：对客观世界的一种代表和模拟(业务模拟、对象模似)。
- MVC流程

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200813194506737.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

### 1.3.优点

- 低耦合性：模块与模块之间的关联性不强，不与某一种具体实现产生密不可分的关联性
- 高维护性：基于低耦合性，可做到不同层级的功能模块灵活更换、插抜
- 高重用性：相同的数据库操作，可以服务于不同的业务处理。将数据作为独立模块，提高重用性

### 1.4. MVC在框架中应用

- MVC模式被广泛用于Java的各种框架中，比如Stmts2、SpringMVC等等都用到了这种思想。

### 1.5.三层架构与MVC

#### 1.5.1三层架构

- View层（表示|界面层）、Service层（业务逻辑层）、DAO层(数据访问层）

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200813194524108.jpg#pic_center)

#### 1.5.2 MVC与三层架构的区别

- MVC强调的是视图和业务代码的分离。严格的说MVC其实关注的是Web层。View就是单独的页面，如JSP、HTML等，不负责业务 处理，只负责数据的展示。而数据封装到Model里，甶Controller负责在V和M之间传递。MVC强调业务和视图分离。
- 三层架构是“数据访问层”、“业务逻辑层”、“表示层”，指的是代码之间的解耦，方便维护和复用。
- MVC是框架思想，三层架构是架构思想，架构思想在框架思想之下

## 二、分页

### 2.1概念

- 分页是Web应用程序非常重要的一个技术。数据库中的数据可能是成千上万的，不可能把这么多的数据一次显示在浏览器上面。一般根据每行数据在页面上所占的空间设置每页显示若干行，比如一般20行是一个比较理想的显示状态。

### 2.2分页实现思路

- 对于海量的数据查询，需要多少就取多少，显然是最佳的解决方法，假如某个表中有200万条记录，第一页取前20条，第二页取21〜40 条记录。

```sql
select  *  from 表名    limit   0,20;//第一页(下标从0开始)
select  *  from 表名    limit  20,20;//第二页
select  *  from 表名    limit  40,20;//第三页
```

### 2.3分页代码实现

- 步骤：
  - 确定每页显示的数据数量
  - 确定分页显示所需的总页数
  - 编写SQL查询语句，实现数据查询
  - 在JSP页面中进行分页显示设置

#### 2.3.1数据库准备

```sql
CREATE TABLE emp(
	id INT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(20) NOT NULL, 
    salary DOUBLE NOT NULL, 
    age INT NOT NULL 
)CHARSET=utf8；
```

#### 2.3.2数据库配置文件db.properties

```java
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/emp?useUnicode=true&characterEncoding=UTF-8
username=root
password=123456
initialSize=10
maxActive=20
minIdle=5
maxWait=3000
12345678
```

#### 2.3.3 PageBean类

- 分页数据所需要的实体类！内包含页码，页大小，总条数，总页数，起始行

```java
package com.zssea.emp.entity;

public class Page {
    private Integer pageIndex;//页码

    private Integer pageSize;//页大小  显示多少行数据

    private Integer totalCounts;//数据的总行数

    private Integer totalPages;//总页数

    private Integer startRows;//起始行

    public Page(Integer pageIndex) {
        this(pageIndex, 5);
    }

    public Page(Integer pageIndex, Integer pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.setStartRows((pageIndex - 1) * pageSize);
    }


    public Integer getPageIndex() {

        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex)
    {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
        this.setTotalPages(totalCounts % pageSize == 0? totalCounts/pageSize:totalCounts/pageSize +1);
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getStartRows() {
        return startRows;
    }

    public void setStartRows(Integer startRows) {
        this.startRows = startRows;
    }
}
```

#### 2.3.4 创建DAO

```java
package com.zssea.emp.dao;

import com.zssea.emp.entity.Emp;
import com.zssea.emp.entity.Page;

import java.util.List;

public interface EmpDao {
    public int insert(Emp emp);
    public int update(Emp emp);
    public int delete(int id);
    public Emp select(int id);
    public List<Emp> selectAll();
    public List<Emp> selectAll(Page page); //分页查询所有
    public long selectCount(); //查询数据总行数
}
```

#### 2.3.5 EmpDaoImpl实现类

```java
package com.zssea.emp.dao.impl;

import com.zssea.emp.dao.EmpDao;
import com.zssea.emp.entity.Emp;
import com.zssea.emp.entity.Page;
import com.zssea.emp.utils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class EmpDaoImpl implements EmpDao {
    private QueryRunner queryRunner = new QueryRunner();
	//....省略其他方法
    @Override
    public List<Emp> selectAll(Page page) {
        try {
            List<Emp> emps = queryRunner.query(DbUtils.getConnection(), "select * from emp limit ?,?;"
                    , new BeanListHandler<Emp>(Emp.class), page.getStartRows(), page.getPageSize());
            return emps;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long selectCount() {
        try {
            return queryRunner.query(DbUtils.getConnection(), "select count(*) from emp;"
                    , new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return  0;
    }
}
```

#### 2.3.6 创建 Service

```java
package com.zssea.emp.service;

import com.zssea.emp.entity.Emp;
import com.zssea.emp.entity.Page;

import java.util.List;

public interface EmpService {
    public List<Emp> showAllEmp();
    public int removeEmp(int id);
    public int modify(Emp emp);
    public Emp showEmp(int id);
    public List<Emp> showAllEmp(Page page);
}
```

#### 2.3.7 Service 实现类

```java
package com.zssea.emp.service.impl;

import com.zssea.emp.dao.EmpDao;
import com.zssea.emp.dao.impl.EmpDaoImpl;
import com.zssea.emp.entity.Emp;
import com.zssea.emp.entity.Page;
import com.zssea.emp.service.EmpService;
import com.zssea.emp.utils.DbUtils;

import java.util.ArrayList;
import java.util.List;

public class EmpServiceImpl implements EmpService {
    private EmpDao empDao = new EmpDaoImpl();
    @Override
    public List<Emp> showAllEmp() {
        List<Emp> results = new ArrayList<>();

        try {
            DbUtils.begin(); //开启事务
            List<Emp> emps = empDao.selectAll();
            if(emps!=null){
                results = emps;
            }
            DbUtils.commit();//提交事务
        } catch (Exception e) {
            DbUtils.rollback();//回滚事务
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public int removeEmp(int id) {
        int result = 0;
        try {
            DbUtils.begin();
            result = empDao.delete(id);
            DbUtils.commit();
        } catch (Exception e) {
            DbUtils.rollback();
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int modify(Emp emp) {
        int result = 0;
        try {
            DbUtils.begin();
            result = empDao.update(emp);
            DbUtils.commit();
        } catch (Exception e) {
            DbUtils.rollback();
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Emp showEmp(int id) {
        Emp emp = null;

        try {
            DbUtils.begin();
            emp = empDao.select(id);

            DbUtils.commit();
        } catch (Exception e) {
            DbUtils.rollback();
            e.printStackTrace();
        }
        return emp;
    }

    @Override
    public List<Emp> showAllEmp(Page page) {
        List<Emp> emps = new ArrayList<>();
        try {
            DbUtils.begin();
            long count = empDao.selectCount();
            page.setTotalCounts((int)count);//设置总行数，进而设置总页数
            emps = empDao.selectAll(page); //分页查询
            DbUtils.commit();
        } catch (Exception e) {
            DbUtils.rollback();
            e.printStackTrace();
        }
        return emps;
    }
}
```

#### 2.3.8 ShowAllEmpController实现

```java
package com.zssea.emp.servlet.controller;

import com.zssea.emp.entity.Emp;
import com.zssea.emp.entity.Page;
import com.zssea.emp.service.EmpService;
import com.zssea.emp.service.impl.EmpServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShowAllEmpController",value = "/manager/safe/showAllEmpController")
public class ShowAllEmpController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //权限验证，放在了过滤器里

        String pageIndex = request.getParameter("pageIndex");//获取浏览器传过来的页码数
        if(pageIndex == null){//如果是第一次访问
            pageIndex = "1";
        }

        Page page = new Page(Integer.valueOf(pageIndex));

        //业务逻辑，查询所有
        EmpService empService = new EmpServiceImpl();
        List<Emp> emps = empService.showAllEmp(page); //分页查询

        //request域保存，转发跳转到ShowAllEmpJsp
        request.setAttribute("emps",emps);
        request.setAttribute("page",page);
        //request.getRequestDispatcher("/manager/safe/ShowAllEmpJsp").forward(request,response);
        request.getRequestDispatcher("/manager/safe/ShowAllEmp.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```

#### 2.3.9 showAllEmp.jsp

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
        <tr >
            <td colspan="6">
                <a href="<c:url context='${pageContext.request.contextPath}' value='/manager/safe/showAllEmpController?pageIndex=1'/>">首页</a>
                <c:if test="${requestScope.page.pageIndex > 1}">
                    <a href=" <c:url context='${pageContext.request.contextPath}' value='/manager/safe/showAllEmpController?pageIndex=${requestScope.page.pageIndex - 1}'/>">上一页</a>
                </c:if>
                <c:if test="${requestScope.page.pageIndex == 1}">
                    <a>上一页</a>
                </c:if>
                <c:if test="${requestScope.page.pageIndex < requestScope.page.totalPages}">
                    <a href=" <c:url context='${pageContext.request.contextPath}' value='/manager/safe/showAllEmpController?pageIndex=${requestScope.page.pageIndex + 1}'/>">下一页</a>
                </c:if>
                <c:if test="${page.pageIndex == page.totalPages}">
                    <a>下一页</a>
                </c:if>
                <a href="<c:url context='${pageContext.request.contextPath}' value='/manager/safe/showAllEmpController?pageIndex=${page.totalPages}'/>">尾页</a>
            </td>
        </tr>

    </table>
</body>
</html>
```

#### 2.3.10运行效果图

- 页面运行结果

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200813194551898.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)