## 一、Druid连接池

- 在程序初始化时，预先创建指定数量的数据库连接对象存储在池中。
- 当需要连接数据库时，从连接池中取出现有连接；
- 使用完毕后, 也不会进行关闭，而是放回池中，实现复用，节省资源。

### 1.1 Druid连接池使用步骤

- 创建 database.properties 配置文件。
- 引入druid-1.1.5.jar文件。

#### 1.1.1database.properties 配置文件

```java
#连接设置
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/companydb?useUnicode=true&characterEncoding=utf
username=root
password=root

#<!--初始化连接-->
initialSize=10

#最大连接数量
maxActive=50

#<!—最小空闲连接-->
minldle=5

#<!--超时等待时间以毫秒为单位60000毫秒/1000等于60秒 
maxWait=5000
```

### 1.2连接池工具类DbUtils

```java
package com.wlw.chapter1_JDBC.demo05_jdbcfinal.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtils {
    //声明连接池对象
    private static DruidDataSource ds;

    static {
        Properties properties = new Properties();
        InputStream is = DBUtils.class.getResourceAsStream("/database.properties");
        try {
            properties.load(is);
            //创建连接池
            ds =  (DruidDataSource)DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection(){
        try {
           return ds.getConnection();//通过连接池获得 连接对象
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}



package com.wlw.chapter1_JDBC.demo05_jdbcfinal.test;

import com.wlw.chapter1_JDBC.demo05_jdbcfinal.utils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class TestPool {
    public static void main(String[] args) throws SQLException {
        for (int i = 0; i < 20; i++){
            Connection connection = DBUtils.getConnection();
            System.out.println(connection);

            //关闭---> 放回池中
            connection.close();//调用的是DruidPooledConnection实现类里的close()方法
        }
    }
}
```

## 二、Apache的DbUtils使用

- Commons DbUtils是Apache组织提供的一个对JDBC逬行简单封装的开源工具类库，使用它能够简化JDBC应用程序的开发！同时，不会影响程序的性能。

### 2.1 DbUtils 简介

- DbUtils是Java编程中数据库操作实用小工具，小巧、简单、实用
  - 对于数据表的查询操作，可以把结果转换为List、Array、Set等集合。便于操作。
  - 对于数据表的DML操作，也变得很简单(只需要写SQL语句)。

#### 2.1.1DbUtils主要包含

- ResultSetHandler接口：转换类型接口
  - BeanHandler类：实现类，把一条记录转换成对象 (查询单个)
  - BeanListHandler类：实现类，把多条记录转换成List集合。 （查询所有）
  - ScalarHandler类：实现类，适合获取一行一列的数据。
- QueryRunner: 执行sql语句的类
  - 增、删、改：update();
  - 查询：query();

### 2.2DbUtils的使用步骤

- 导入jar包
  - mysql连接驱动jar包 mysql-connector-java-5.1.28-bin.jar
  - druid-1.1.5.jar
  - database.properties配置文件
  - commons-dbutils-1.6,jar

#### 2.2.1 DbUtils 工具类

```java
package com.wlw.chapter1_JDBC.demo05_jdbcfinal.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtils {
    //声明连接池对象
    private static DruidDataSource ds;

    static {
        Properties properties = new Properties();
        InputStream is = DbUtils.class.getResourceAsStream("/database.properties");
        try {
            properties.load(is);
            //创建连接池
            ds =  (DruidDataSource)DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static Connection getConnection(){
        try {
           return ds.getConnection();//通过连接池获得 连接对象
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static DataSource getDataSource(){ //获得 DataSource 连接池对象
        return ds;
    }
}

```

#### 2.2.2 UserDaoImpl数据访问对象

```java
package com.wlw.chapter1_JDBC.demo05_jdbcfinal.entity;

public class User {
    private int id;
    private String username;
    private String password;
    private String phone;

    public User() {}
    public User(int id, String username, String password, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
    }
    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}


package com.wlw.chapter1_JDBC.demo05_jdbcfinal.dao;

import com.wlw.chapter1_JDBC.demo05_jdbcfinal.entity.User;

import java.util.List;

public interface UserDao {
    public int insert(User user);
    public int update(User user);
    public int delete(int id);
    public User select(int id);
    public List<User> selectAll();
    //查看目前多少条记录（用户数量）
    public long selectUserNums();
}


package com.wlw.chapter1_JDBC.demo05_jdbcfinal.dao.impl;

import com.wlw.chapter1_JDBC.demo05_jdbcfinal.dao.UserDao;
import com.wlw.chapter1_JDBC.demo05_jdbcfinal.entity.User;
import com.wlw.chapter1_JDBC.demo05_jdbcfinal.utils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private QueryRunner queryRunner = new QueryRunner(DbUtils.getDataSource());

    @Override
    public int insert(User user) {
        String sql = "insert into user(username,password,phone) values (?,?,?);";
        try {
            return queryRunner.update(sql,user.getUsername(),user.getPassword(),user.getPhone());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(User user) {
        String sql = "update user set username=?,password=?,phone=? where id=?;";
        try {
            return queryRunner.update(sql,user.getUsername(),user.getPassword(),user.getPhone(),user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(int id) {
        String sql = "delete from user where id=?;";
        try {
            return queryRunner.update(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public User select(int id) {
        String sql = "select * from user where id=?;";
        try {
            User user = queryRunner.query(sql, new BeanHandler<User>(User.class), id);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> selectAll() {
        String sql = "select * from user;";
        try {
            List<User> list = queryRunner.query(sql, new BeanListHandler<User>(User.class));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public long selectUserNums() {
        String sql = "select count(*) from user";
        try {
            long count = queryRunner.query(sql, new ScalarHandler<>());
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}


package com.wlw.chapter1_JDBC.demo05_jdbcfinal.test;

import com.wlw.chapter1_JDBC.demo05_jdbcfinal.dao.UserDao;
import com.wlw.chapter1_JDBC.demo05_jdbcfinal.dao.impl.UserDaoImpl;
import com.wlw.chapter1_JDBC.demo05_jdbcfinal.entity.User;

import java.util.List;

public class TestDbUtils {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();

        //User user = new User(3,"xiaoli","123456","1234569874");
        //int result = userDao.insert(user); //新增

        //User user = new User(3,"xiaoli","123","1234569874");
        //int result = userDao.update(user); //修改

        //int result = userDao.delete(3);//删除
        //System.out.println(result);

        //查单个
       // User user = userDao.select(1);
       // System.out.println(user);

        //查所有
        //List<User> list = userDao.selectAll();
        //list.forEach(System.out::println);

        long count = userDao.selectUserNums();
        System.out.println("一共有："+count);

    }
}
```