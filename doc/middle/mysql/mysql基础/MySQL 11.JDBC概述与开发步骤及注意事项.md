## 一、引言

### 1.1如何操作数据库

- 使用客户端工具访问数据库，需要手工建立连接，输入用户名和密码登录，编写SQL语句，点击执行，查看操作结果（结果集或受影 响行数）。
- 客户端操作数据库步骤：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200731215722325.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

### 1.2实际开发中，会采用客户端操作数据库吗？

在实际开发中，当用户的数据发生改变时，不可能通过客户端操作执行SQL语句，因为操作量过大，无法保证效率和正确性。

## 二、JDBC (Java Database Connectivity)

### 2.1什么是JDBC

> JDBC (Java Database Connectivity) Java连接数据库 的规范（标准），可以使用Java语言连接数据库完成CRUD操作。

### 2.2 JDBC核心思想

 Java中定义了访问数据库的接口，可以为多种关系型数据库提供统一的访问方式。甶数据库厂商提供驱动实现类（Driver数据库驱动）

 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200731215758137.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

#### 2.2.1 MySQL数据库驱动

> mysql-connector-java-5.1.X 适用于 5.X 版本
>
> mysql-connector-java-8.0.X 适用于 8.X 版本

#### 2.2.2 JDBC API

JDBC是甶多个接口和类逬行功能实现。

| 类型      | 权限定名               | 简介                                                         |
| --------- | ---------------------- | ------------------------------------------------------------ |
| class     | java.sql.DriverManager | 管理多个数据库驱动类，提供了获取数据库连接的方法             |
| interface | java.sql.Connection    | 代表一个数据库连接（当connection不是null时，表示已连接数据库） |
| interface | java.sql.Statement     | 发送SQL语句到数据库工具                                      |
| interface | java.sql.ResultSet     | 保存SQL查询语句的结果数据（结果集）                          |
| class     | java.sql.SQLException  | 处理数据库应用程序时所发生的异常                             |

## 2.3环境搭建

- 在项目下新建 lib 文件夹，用于存放jar文件。
- 将mysql驱动mysql-connector-java-5.1.X复制到项目的lib文件夹中。
- 选中lib文件夹右键Add as Libraay，点击0K。

## 三、JDBC开发步骤【重点】

### 3.1注册驱动

> 使用 Class.forName("com.mysqLjdbc.Driver:); 手动加载字节码文件到 JVM 中。

```java
Class.forName("com.mysql.jdbc.Driver"）；//加载驱动
```

### 3.2连接数据库

> 通过 DriverManager.getConnection(url，user，password)获取数据库连接对象
>
>  URL:jdbc:mysql://localhost:3306/database (数据库名称)
>
>  username:root
>
>  password:123456

```java
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database? useUnicode=true&characterEncoding=utf8M, "root","1234");
```

- URL(Uniform Resource Locator)统一资源定位符：由协议、IP、端口、SID (程序实例名称）组成

### 3.3获取发送SQL的对象

> 通过Connection对象获得Statement对象，用于对数据库进行通用访问。

```java
Statement statement = conn.createStatement();
```

### 3.4执行SQL语句

> 执行SQL语句并接收执行结果。

```java
String sql =MINSERT INTO t_jobs(JOB.ID,JOB.TITLE,MIN.SALARY,MAX.SALARY) VALUES(1JAVA.Le1,'JAVA.Lecturer',4000,10000);

int result = statement.executeUpdate(sql);//执行SQL语句并接收结果
// 执行 增删改 insert into  delete  update 调用的方法是statement.executeUpdate(sql)
```

- 注意：在编写DML语句时，一定要注意字符串参数的符号是单引号’值’
- DML语句：增删改时，返回受影响行数（int类型）。
- DQL语句：查询时，返回结果数据(ResultSet结果集)。

### 3.5处理结果

> 接受处理操作结果。

```java
if(result == 1){
	System.out.println("Success );
}
```

- 受影响行数：逻辑判断、方法返回。
- 查询结果集：迭代、依次获取。

### 3.6释放资源

> 遵循 **先开后关** 原则，释放所使用到的资源对象。

```java
statement.close(); 
conn.close();
```

### 3.7综合案例

整合以上核心六步，实现向数据库表中插入一条数据。

```java
package com.wlw.chapter1_JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TestJDBC {
    public static void main(String[] args) throws Exception {
        //1.加载驱动
        Class.forName("com.mysql.jdbc.Driver");

        //2.获取连接
        String url = "jdbc:mysql://localhost:3306/companydb";
        String username = "root";
        String password = "123456";
        Connection connection = DriverManager.getConnection(url, username, password);

        if(connection != null){
            System.out.println("数据库连接成功");
        }else {
            System.out.println("数据库连接未成功");
        }
        //3.获取发送SQL的对象
        Statement statement = connection.createStatement();

        //4. 编写sql语句，执行SQL语句
        String sql  = "INSERT INTO t_jobs(JOB_ID,JOB_TITLE,MIN_SALARY,MAX_SALARY) VALUES('H5_Mgr','H5_Lecturer',4000,10000);";
        int result = statement.executeUpdate(sql); //执行 DML语句：增删改时，返回受影响行数（int类型）。

        //5.处理结果
        if( result == 1 ){
            System.out.println("执行成功");
        }else {
            System.out.println("执行失败");
        }

        //6.断开连接，释放资源  先开后关
        statement.close();
        connection.close();
    }
}
```

## 四、ResultSet (结果集)

> 在执行查询SQL后，存放查询到的结果集数据。

### 4.1接收结果集

> ResultSet rs = statement.executeQuery(sql);

```java
ResultSet resultSet = statement.executeQuery("select * from t_jobs;");
```

### 4.2遍历ResultSet中的数据

> ResultSet以表（table)结构进行临时结果的存储，需要通过JDBC API将其中数据进行依次获取。
>
> - 数据行指针：初始位置在第一行数据前，每调用一次boolean next〇方法ResultSet的指针向下移动一行，结果为true,表示当前行有数据。
> - rs.getXxx(整数);代表根据列的编号顺序获得，从1开始。
> - rs.getXxx(“列名”);代表根据列名获得。

```java
boolean next() throws SQLException //判断 rs 结果集中下一行是否存在数据
```

#### 4.2.1遍历方法

```java
int getlnt(int columnlndex) throws SQLException //获得当前行第N列的int值

int getlnt(String columnLabel) throws SQLException //获得当前行columnLabel列的int值 
    
double getDouble(int columnlndex) throws SQLException //获得当前行第N列的double值

double getDouble(St「ing columnLabel) throws SQLException //获得当前行columnLabel列的double值

String getString(int columnlndex) throws SQLException //获得当前行第 N 列的 String 值

String getString(String columnLabel) throws SQLException //获得当前行columnLabel列的String值
```

- 注意：列的编号从1开始。

### 4.3综合案例

> 对t_jobs表中的所有数据进行遍历。

```java
package com.wlw.chapter1_JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class QueryJDBC {
    public static void main(String[] args) throws Exception{
        //1.加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取连接
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/companydb", "root", "123456");
        //3.获取sql对象
        Statement statement = connection.createStatement();
        //4.执行sql语句
        ResultSet resultSet = statement.executeQuery("select * from t_jobs;");
        //5.处理结果
        while (resultSet.next()){//判断resultSet结果集中下一行是否存在数据
			/*
            String job_id = resultSet.getString(1);  //根据列的编号顺序获得，从1开始。
            String job_title = resultSet.getString(2);
            String min_salary = resultSet.getString(3);
            String max_salary = resultSet.getString(4);
			*/

            String job_id = resultSet.getString("job_id"); //根据列名获得
            String job_title = resultSet.getString("job_title");
            String min_salary = resultSet.getString("min_salary");
            String max_salary = resultSet.getString("max_salary");

            System.out.println(job_id+"\t"+job_title+"\t"+min_salary+"\t"+max_salary);
        }
        //6.关闭资源
        resultSet.close();
        statement.close();
        connection.close();
    }
}
```

## 五、 常见错误

```java
java.lang.ClassNotFoundException//找不到类（类名书写错误、没有导入jar包）

java.sqLSQLException://与sql语句相关的错误（约束锗误、表名列名书写错误）建议：在客户端工具中测试SQL语句之后再粘贴 在代码中

com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column 
    //原因：列值Sting类型没有加单引号

Duplicate entry'1'for key'PRIMARY' //原因，主键值已存在或混乱，更改主键值或清空表

com.mysqLjdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown column 'password' in // 原因：可能输入的值的类型不对，确定是否插入的元素时对应的值的类型正确
```

## 六、 综合案例-登录

### 6.1创建表

- 创建一张用户表User
  id，主键、自动增长,
  用户名，字符串类型，唯一、非空,
  密码，字符串类型，非空 ,
  手机号码，字符串类型
- 插入2条测试语句

### 6.2实现登录

- 通过控制台用户输入用户名和密码。
- 用户输入的用户名和密码作为条件，编写查询SQL语句。
- 如果该用户存在，提示登录成功，反之提示失败。

```java
package com.wlw.chapter1_JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class LoginJDBC {
    public static void main(String[] args) throws Exception{
        Scanner input = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = input.next();
        System.out.println("请输入密码:");
        String password = input.next();

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/companydb", "root", "123456");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select* from user where username='"+username+"' and password='"+password+"';");
        if(resultSet.next()){
            System.out.println("登录成功");
        }else{
            System.out.println("登录失败");
        }

        resultSet.close();
        statement.close();
        connection.close();
    }
}
```

## 七、SQL注入问题

### 7.1问题描述

```java
package com.wlw.chapter1_JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class LoginJDBC {
    public static void main(String[] args) throws Exception{
        Scanner input = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = input.nextLine(); //注意点
        System.out.println("请输入密码:");
        String password = input.nextLine();//注意点

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/companydb", "root", "123456");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select* from user where username='"+username+"' and password='"+password+"';");
        if(resultSet.next()){
            System.out.println("登录成功");
        }else{
            System.out.println("登录失败");
        }

        resultSet.close();
        statement.close();
        connection.close();
    }
}
/* 执行：
请输入用户名：
abc' or 1=1;#
请输入密码:
123456789
登录成功

说明：
数据库中执行的语句是：
SELECT* FROM USER WHERE username='abc' OR 1=1;#' and password='password';
#后面是注释，所以#后面不算语句
where username ='abc' OR 1=1; 该条件永远是true，所以该语句相当于查询所有
*/
```

### 7.2什么是SQL注入

> 用户输入的数据中有SQL关键字或语法并且参与了 SQL语句的编译，导致SQL语句编译后的条件含义为true,一直得到正确的结果。 这种现象称为SQL注入。

### 7.3如何避免SQL注入

> 由于编写的SQL语句是在用户输入数据，整合后再逬行编译。所以为了避免SQL注入的问题，我们要使SQL语句在用户输入数据前就 已逬行编译成完整的SQL语句，再进行填充数据。

- 为了实现上述所说，java提供了一个新的类：PreparedStatement，看第八条

## 八、PreparedStatement【重点】

> PreparedStatemen t继承了 Statement接口，执行SQL语句的方法无异。

### 8.1PreparedStatement 的应用

- 作用：
  - 预编译SQL语句，效率高。
  - 安全，避免SQL注入。
  - 可以动态的填充数据，执行多个同构的SQL语句。

#### 8.1.1参数标记

```java
//1.预编译SQL语句
PreparedStatement pstmt = conn.prepareStatement("select * from user where username=? and password=?");
```

- 注意：JDBC中的所有参数都由？符号占位，这被称为参数标记。
- 在执行SQL语句之前，必须为每个参数提供值。

#### 8.1.2动态参数绑定

> pstmt.setXxxr(下标，值）参数下标从1开始，为指定参数下标绑定值

```java
//1.预编译SQL语句
PreparedStatement pstmt = conn.prepareStatement("select * from user where username=? and password=?"); 

//2.为参数下标赋值
pstmt.setstring(1,username); 
pstmt.setstring(2,password);
```

代码：

```java
package com.wlw.chapter1_JDBC;

import java.sql.*;
import java.util.Scanner;

public class LoginJDBC2 {
    public static void main(String[] args) throws Exception{
        Scanner input = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = input.nextLine();
        System.out.println("请输入密码:");
        String password = input.nextLine();

        Class.forName("com.mysql.jdbc.Driver"); //1.
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/companydb", "root", "123456");//2.
        //3.获得PreparedStatement 对象，进行预编译sql语句
        PreparedStatement preparedStatement = connection.prepareStatement("select * from user where username=? and password=?;");
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        //4.执行sql语句
        ResultSet resultSet = preparedStatement.executeQuery();
        //System.out.println(preparedStatement);
        
        //5.处理结果
        if(resultSet.next()){
            System.out.println("登录成功");
        }else{
            System.out.println("登录失败");
        }

        //6.关闭资源
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
/*执行结果：
请输入用户名：
abc' or 1=1#
请输入密码:
123456
登录失败

请输入用户名：
zhangsan
请输入密码:
123456
登录成功
*/
```