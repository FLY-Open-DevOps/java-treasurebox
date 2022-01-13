## 一、封装工具类

- 在实际JDBC的使用中，存在着大量的重复代码：例如连接数据库、关闭数据库等这些操作！
- 我们需要把传统的JDBC代码进行重构，抽取出通用的JDBC工具类！以后连接任何数据库、释放资源都可以使用这个工具类。
- 工具类核心思想：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200731220033163.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

### 1.1重用性方案

- 封装获取连接、释放资源两个方法：
  - 提供public static Connection getConnection(){}方法。
  - 提供public static void closeAll(Connection conn , Statement sm，ResultSet rs)(){}方法。

#### 1.1.1重用工具类实现

```java
package com.wlw.chapter1_JDBC;

import java.sql.*;
//重用性方案
public class DBUtils {

    static { //类加载，只执行一次
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //获得连接
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/companydb", "root", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //关闭连接
    public static void CloseAll(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(resultSet == null){
                resultSet.close();
            }
            if(statement == null){
                statement.close();
            }
            if(connection == null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
```

### 1.2跨平台方案

- 定义 public static final Properties prop = new Properties(); 读取配置文件的Map

- 定义 static{

  - //首次使用工具类时，加载驱动

    InputStream is = JDBCUtil.class.getResourceAsStream(”路径"); //通过复用本类自带流，读取jdbc.properties配置文件。 classPath = bin

  - prop.load(is); //通过prop对象将流中的配置信息分割成键值对

  - String driverName = prop.getProperty(“driver”); //通过driverName的键获取对应的值 (com.mysql.jdbc.Driver)

  - Class.forName(driverName); //加载驱动

- }

#### 1.2.1跨平台工具类实现

```java
package com.wlw.chapter1_JDBC.demo02;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

//跨平台方案
public class DBUtils {
    private static final Properties properties = new Properties();//存储配置文件的map
    static { //类加载，只执行一次
        InputStream is = DBUtils.class.getResourceAsStream("/db.properties");
        try {
            properties.load(is);//通过流，将配置文件里的内容加载到properties 集合中
            Class.forName(properties.getProperty("driver"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //获得连接
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //关闭连接
    public static void CloseAll(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(resultSet != null){
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
db.properties
    
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/companydb
username=root
password=123456
```

## 二、ORM

- ORM (Object Relational Mapping）对象关系映射
- 从数据库查询到的结果集(ResultSet)在逬行遍历时，逐行遍历，取出的都是零散的数据。在实际应用幵发中，我们需要将零散的数据逬 行封装整理。

### 2.1实体类(entity):零散数据的载体

- 一行数据中，多个零散的数据进行整理。
- 通过entity的规则对表中的数据进行对象的封装。
- **表名=类名；列名=属性名；提供各个属性的get、set方法。 **
- 提供无参构造方法、（视情况添加有参构造)。

### 2.1.1ORM 应用

```java
package com.wlw.chapter1_JDBC.demo02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestORM {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T_Jobs> list = new ArrayList<>();
        try {
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement("select * from t_jobs;");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String job_id = resultSet.getString("job_id");
                String job_title = resultSet.getString("job_title");
                String min_salary = resultSet.getString("min_salary");
                String max_salary = resultSet.getString("max_salary");
                //创建对象，封装查询到的数据
                T_Jobs t_jobs = new T_Jobs();
                t_jobs.setJob_id(job_id);
                t_jobs.setJob_title(job_title);
                t_jobs.setMix_salary(min_salary);
                t_jobs.setMax_salary(max_salary);
                //每遍历一次得到的对象，存放到集合中
                list.add(t_jobs);
            }
            //遍历集合
            for (T_Jobs t_jobs : list) {
                System.out.println(t_jobs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.CloseAll(connection,preparedStatement,resultSet);
        }

    }
}
package com.wlw.chapter1_JDBC.demo02;
// entity 实体类
public class T_Jobs {
    private String job_id;
    private String job_title;
    private String mix_salary;
    private String max_salary;

    public T_Jobs() {
    }

    public T_Jobs(String job_id, String job_title, String mix_salary, String max_salary) {
        this.job_id = job_id;
        this.job_title = job_title;
        this.mix_salary = mix_salary;
        this.max_salary = max_salary;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getMix_salary() {
        return mix_salary;
    }

    public void setMix_salary(String mix_salary) {
        this.mix_salary = mix_salary;
    }

    public String getMax_salary() {
        return max_salary;
    }

    public void setMax_salary(String max_salary) {
        this.max_salary = max_salary;
    }

    @Override
    public String toString() {
        return "T_Jobs{" +
                "job_id='" + job_id + '\'' +
                ", job_title='" + job_title + '\'' +
                ", mix_salary='" + mix_salary + '\'' +
                ", max_salary='" + max_salary + '\'' +
                '}';
    }
}
```